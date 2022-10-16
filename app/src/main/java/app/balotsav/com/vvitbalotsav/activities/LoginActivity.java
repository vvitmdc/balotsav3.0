package app.balotsav.com.vvitbalotsav.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import app.balotsav.com.vvitbalotsav.R;
import app.balotsav.com.vvitbalotsav.model.Schools;
import app.balotsav.com.vvitbalotsav.utils.CheckNetwork;
import app.balotsav.com.vvitbalotsav.utils.DbHelper;
import app.balotsav.com.vvitbalotsav.utils.FirebaseHelper;

public class LoginActivity extends AppCompatActivity {

    SharedPreferences.Editor editor;
    EditText et1, et2;
    FirebaseDatabase firebaseDatabase;
    Button login,register;
    ProgressDialog progressDialog;
    DbHelper helper;
    private HashMap<String,String> Hash_file_maps;
    private SliderLayout sliderLayout;
    private FrameLayout layout;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        layout = findViewById(R.id.id_login_layout);
        login =findViewById(R.id.id_login);
        register = findViewById(R.id.id_register);
        if (getSharedPreferences("Balotsav", MODE_PRIVATE).getInt("registered", 0) == 1) {
            startActivity(new Intent(LoginActivity.this, DashBoardActivity.class));
            finish();
        }
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.front_page);
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        final float roundPx = (float) bitmap.getWidth() * 0.06f;
        roundedBitmapDrawable.setCornerRadius(roundPx);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickToRegister();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickToDashBoard();
            }
        });
        editor = getSharedPreferences("Balotsav", MODE_PRIVATE).edit();
        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        sliderLayout = findViewById(R.id.login_slider);
        Hash_file_maps = new HashMap<>();
        setEventSlideView();
        helper = new DbHelper(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getResources().getString(R.string.loading_wait));
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    public void clickToRegister() {
        Intent in = new Intent(this, RegisterActivity.class);
        startActivity(in);
        finish();
    }

    public void setEventSlideView() {
        List<String> urlList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.event_inauguration_photos)));

        for (int i = 1; i < urlList.size(); i++) {
            Hash_file_maps.put(String.valueOf(i), urlList.get(i));
        }

        for (String name : Hash_file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);
            textSliderView
                    .image(Hash_file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop);
            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Stack);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setDuration(9000);
    }

    public void clickToDashBoard() {
        if (!TextUtils.isEmpty(et1.getText().toString()) && !TextUtils.isEmpty(et2.getText().toString())) {
            if (new CheckNetwork(LoginActivity.this).isNetworkAvailable()) {
                progressDialog.show();
                DatabaseReference databaseReference = firebaseDatabase.getReference("Schools").child(et1.getText().toString().replaceAll("/", "@"));
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {
                            Schools schools = dataSnapshot.getValue(Schools.class);

                            String pswd = schools.getPassword();
                            if (pswd.equals(et2.getText().toString())) {
                                editor.putInt("registered", 1);
                                Gson gson = new Gson();
                                String json = gson.toJson(schools);
                                Schools school = gson.fromJson(json, Schools.class);

                                Log.i("Test-Login-Gson",""+school.getHostel());
                                helper.setSchool(school);
                                Schools s = helper.getSchool(school.getSchool_Code());
                                Log.i("Test-Login",s.getSchool_Name());
                                Log.i("Test-Login",""+s.getHostel());

                                new FirebaseHelper(LoginActivity.this).getEventList(school.getSchool_Code());
                                editor.putString("data", json);
                                editor.putString("scode", et1.getText().toString().replaceAll("/", "@"));
                                editor.apply();
                                progressDialog.dismiss();
                                Intent in = new Intent(LoginActivity.this, DashBoardActivity.class);
                                startActivity(in);
                                finish();
                                /*final Handler handler = new Handler();
                                final Runnable runnable = new Runnable() {
                                    @Override
                                    public void run() {
                                        if (progressDialog!=null && progressDialog.isShowing()) {
                                            progressDialog.dismiss();
                                            Toast.makeText(LoginActivity.this, "దయచేసి కొంత సమయం తర్వాత మళ్ళీ ప్రయత్నించండి", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                };
                                progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialog) {
                                        handler.removeCallbacks(runnable);
                                        dialog.dismiss();
                                    }
                                });
                                handler.postDelayed(runnable, 30000);*/
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this, R.string.wrong_password, Toast.LENGTH_LONG).show();
                            }
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, getString(R.string.invalid_username), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "దయచేసి కొంత సమయం తర్వాత మళ్ళీ ప్రయత్నించండి", Toast.LENGTH_SHORT).show();


                    }
                });


            } else {
                Toast.makeText(LoginActivity.this, R.string.check_connection, Toast.LENGTH_LONG).show();
            }

        } else {
            if (TextUtils.isEmpty(et1.getText()) && TextUtils.isEmpty(et2.getText())) {
                Toast.makeText(LoginActivity.this, R.string.enter_all_fields, Toast.LENGTH_LONG).show();

            } else if (TextUtils.isEmpty(et2.getText())) {
                Toast.makeText(LoginActivity.this, R.string.empty_password, Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(LoginActivity.this, R.string.empty_school_code, Toast.LENGTH_LONG).show();


            }
        }

    }

    @Override
    public void onBackPressed() {
        if (!doubleBackToExitPressedOnce) {
            this.doubleBackToExitPressedOnce = true;
            showSnack();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else {
            super.onBackPressed();
        }
    }
    private void showSnack() {
        int color;
        String message = "యాప్ CLOSE చేయుటకు  దయచేసి తిరిగి BACK నొక్కండి";
        color = getResources().getColor(R.color.secondaryText);
        Snackbar snackbar = Snackbar.make(layout, message, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        TextView textView =  sbView.findViewById(R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();
    }
}
