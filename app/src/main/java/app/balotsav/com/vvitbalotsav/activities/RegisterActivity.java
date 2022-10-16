package app.balotsav.com.vvitbalotsav.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import app.balotsav.com.vvitbalotsav.R;
import app.balotsav.com.vvitbalotsav.model.Schools;
import app.balotsav.com.vvitbalotsav.utils.CheckNetwork;
import app.balotsav.com.vvitbalotsav.utils.SendMailTask;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String[] schoolType = {"--- స్కూల్ టైపు ని సెలెక్ట్ చేసుకొండి ---", "STATE", "CBSE", "ICSE"};
    EditText sname, scode, pswd, hmname, hmaemail, hmphno, coname, cophno1, cophno2, town, state, pincode, participants, boys, girls, aParticipants, aBoys, aGirls;
    String selected, otp, sent;
    Button register;
    FirebaseAuth mAuth;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, dr;
    CheckBox checkBoxaccd, date1, date2, date3;
    ProgressDialog progressDialog;
    EditText district, address;
    Spinner spinner, boardingPlace;
    LinearLayout accomodation_layout;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private String phone;
    PhoneAuthProvider.ForceResendingToken force;
    private String text;
    Button resend, ok;
    TextView resentText;
    boolean resendOtp = false;
    private boolean accomodationFlag = false;
    private AlertDialog alertDialog;
    private int participantCount, participantHostelCount;
    private int girlsCount, girlsHostelCount;
    private int boysCount, boysHostelCount;

    public void downloadFiles() {
        if (new CheckNetwork(this).isNetworkAvailable()) {
            databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.child("rules").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String upload = dataSnapshot.getValue(String.class);
                    Log.i("URL", upload);
                    assert upload != null;
                    Log.i("Test", "Not empty");
                    new RegisterActivity.DownloadFileFromURL(1).execute(upload);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            databaseReference.child("schedule").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String upload = dataSnapshot.getValue(String.class);
                    Log.i("URL", upload);
                    assert upload != null;
                    Log.i("Test", "Not empty");
                    new RegisterActivity.DownloadFileFromURL(2).execute(upload);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    class DownloadFileFromURL extends AsyncTask<String, String, String> {
        int value;

        public DownloadFileFromURL(int i) {
            value = i;
        }

        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();
                InputStream input = new BufferedInputStream(url.openStream(),
                        8192);
                String path = null;
                switch (value) {
                    case 1:
                        path = "rules";
                        break;
                    case 2:
                        path = "schedule";
                        break;
                }
                OutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory().toString() + "/" + path + ".pdf");
                byte[] data = new byte[1024];
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    output.write(data, 0, count);

                }
                output.flush();
                output.close();
                input.close();

            } catch (Exception e) {

                Log.e("Error: ", e.getMessage());
            }

            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        sharedPreferences = getSharedPreferences("Balotsav", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        mAuth = FirebaseAuth.getInstance();
        register = findViewById(R.id.register);
        sname = findViewById(R.id.sname);
        pswd = findViewById(R.id.pswd);
        scode = findViewById(R.id.scode);
        checkBoxaccd = findViewById(R.id.id_needAcomdiation);
        accomodation_layout = findViewById(R.id.id_accomodation_layout);
        participants = findViewById(R.id.id_participants);
        boys = findViewById(R.id.id_boys);
        girls = findViewById(R.id.id_girls);
        aParticipants = findViewById(R.id.id_accomodtion_participants);
        aBoys = findViewById(R.id.id_accomodation_boys);
        aGirls = findViewById(R.id.id_accomodation_girls);
        date1 = findViewById(R.id.id_date1);
        date2 = findViewById(R.id.id_date2);
        date3 = findViewById(R.id.id_date3);
        hmaemail = findViewById(R.id.tmail);
        hmname = findViewById(R.id.t1);
        hmphno = findViewById(R.id.p1);
        coname = findViewById(R.id.t2);
        cophno1 = findViewById(R.id.p21);
        cophno2 = findViewById(R.id.p2);
        town = findViewById(R.id.village);
        address = findViewById(R.id.address);
        district = findViewById(R.id.District);
        state = findViewById(R.id.State);
        pincode = findViewById(R.id.pincode);
        spinner = findViewById(R.id.id_spinner);
        boardingPlace = findViewById(R.id.id_boarding_place);
        address.setHint(getResources().getString(R.string.multilinehint));
        spinner.setOnItemSelectedListener(this);
        address.addTextChangedListener(new TextWatcher() {
            private String text;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                text = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int lineCount = address.getLineCount();
                if(lineCount > 3) {
                    address.setText(text);
                    address.clearFocus();
                    town.requestFocus();
                }
            }
        });
        address.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                int editTextLineCount = ((EditText)v).getLineCount();
                if (editTextLineCount >= 3)
                    return true;
                return false;
            }
        });
        checkAccomodationFlag();
        progressDialog = new ProgressDialog(this);
        Dialog dialog = new Dialog(this, android.R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.notemessage1);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        ArrayAdapter<String> obj = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, schoolType);
        spinner.setAdapter(obj);
        firebaseDatabase = FirebaseDatabase.getInstance();
        downloadFiles();
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                Toast.makeText(RegisterActivity.this, R.string.otp_sent, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(RegisterActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                Log.e("OTP error", e.getLocalizedMessage());
            }

            @Override
            public void onCodeSent(final String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                sent = s;
                force = forceResendingToken;
            }
        };


    }

    private boolean checkCount() {
        participantCount = Integer.parseInt(participants.getText().toString());
        girlsCount = Integer.parseInt(girls.getText().toString());
        boysCount = Integer.parseInt(boys.getText().toString());
        if (participantCount == (boysCount + girlsCount))
            return true;
        else
            return false;
    }

    private boolean checkHostelCount() {
        participantHostelCount = Integer.parseInt(aParticipants.getText().toString());
        girlsHostelCount = Integer.parseInt(aGirls.getText().toString());
        boysHostelCount = Integer.parseInt(aBoys.getText().toString());
        if (participantHostelCount == (boysHostelCount + girlsHostelCount))
            return true;
        else
            return false;
    }

    private boolean validate(EditText[] fields) {
        for (int i = 0; i < fields.length; i++) {
            EditText currentField = fields[i];
            if (currentField.getText().toString().length() <= 0) {
                return false;
            }
        }
        return true;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    protected void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void register(View view) {
        register.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(v);
                return false;
            }
        });

        if (validate(new EditText[]{sname, pswd, scode, hmaemail, hmname, hmphno, coname, cophno1, cophno2, town, participants, boys, girls, state, pincode, district, address}) && checkCount() && checkHostelCount()) {
            selected = spinner.getSelectedItem().toString();
            phone = "+" + cophno1.getText().toString() + cophno2.getText().toString();
            if (new CheckNetwork(RegisterActivity.this).isNetworkAvailable()) {
                //next(sent, otp);
                text = scode.getText().toString().replace("/", "@");
                Log.i("Test - RegisterActivity", text);
                databaseReference = firebaseDatabase.getReference("Schools").child(text);
                dr = databaseReference;
                progressDialog.setMessage("Loading");
                progressDialog.setCancelable(false);
                progressDialog.show();

                //noinspection unused
                String scode1 = scode.getText().toString();
                dr.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Log.i("Error :", dataSnapshot.getKey());
                        if (dataSnapshot.exists()) {
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, R.string.school_code_is_already_taken, Toast.LENGTH_LONG).show();
                        } else {
                            PhoneAuthProvider.getInstance().verifyPhoneNumber(phone, 60, TimeUnit.SECONDS, RegisterActivity.this, mCallbacks);
                            LayoutInflater li = LayoutInflater.from(RegisterActivity.this);
                            final View promptsView = li.inflate(R.layout.otp, null);
                            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                    RegisterActivity.this);
                            alertDialogBuilder.setView(promptsView);
                            alertDialogBuilder.setMessage(getResources().getString(R.string.message_otp));

                            resend = promptsView.findViewById(R.id.id_resent_otp);
                            ok = promptsView.findViewById(R.id.id_otp_ok);
                            resentText = promptsView.findViewById(R.id.id_otp_hint);
                            final EditText userInput = promptsView
                                    .findViewById(R.id.editTextDialogUserInput);
                            userInput.setInputType(InputType.TYPE_CLASS_NUMBER);
                            Timer buttonTimer = new Timer();
                            buttonTimer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (!resendOtp) {
                                                resend.setEnabled(true);
                                                resend.setVisibility(View.VISIBLE);
                                            }
                                        }
                                    });
                                }
                            }, 5000);
                            resend.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    PhoneAuthProvider.getInstance().verifyPhoneNumber(phone, 60, TimeUnit.SECONDS, RegisterActivity.this, mCallbacks, force);
                                    //register(view);
                                    resentText.setVisibility(View.VISIBLE);
                                    resend.setVisibility(View.VISIBLE);
                                    resendOtp = true;
                                    resend.setEnabled(false);
                                }
                            });
                            ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (!TextUtils.isEmpty(userInput.getText().toString())) {
                                        progressDialog.show();
                                        register.setEnabled(false);
                                        otp = userInput.getText().toString();
                                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(sent, otp);
                                        mAuth.signInWithCredential(credential)
                                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                    @Override
                                                    public void onComplete(Task<AuthResult> task) {
                                                        if (task.isSuccessful()) {
                                                            Schools schools = new Schools();
                                                            schools.setSchool_Name(sname.getText().toString());
                                                            schools.setSchool_Code(scode.getText().toString());
                                                            schools.setBoard(selected);
                                                            schools.setPassword(pswd.getText().toString());
                                                            schools.setHeadMaster_Name(hmname.getText().toString());
                                                            schools.setHeadMaster_Email(hmaemail.getText().toString());
                                                            schools.setHeadMaster_PhNo(hmphno.getText().toString());
                                                            schools.setCoordinator_Name(coname.getText().toString());
                                                            schools.setCoordinator_PhNo(phone);
                                                            schools.setAddress(address.getText().toString());
                                                            schools.setTown(town.getText().toString());
                                                            schools.setDistrict(district.getText().toString());
                                                            schools.setState(state.getText().toString());
                                                            schools.setPinCode(pincode.getText().toString());
                                                            schools.setStudentCount(String.valueOf(participantCount));
                                                            schools.setGirlsCount(String.valueOf(girlsCount));
                                                            schools.setBoysCount(String.valueOf(boysCount));
                                                            schools.setBoardingPlace(boardingPlace.getSelectedItem().toString());
                                                            if (cophno2.length() == 10) {
                                                                if (accomodationFlag) {
                                                                    schools.setHostel(true);
                                                                    schools.setStudentHostelCount(String.valueOf(participantHostelCount));
                                                                    schools.setGirlsHostelCount(String.valueOf(girlsHostelCount));
                                                                    schools.setBoysHostelCount(String.valueOf(boysHostelCount));
                                                                    schools.setDate1(date1.getText().toString());
                                                                    schools.setDate2(date2.getText().toString());
                                                                    schools.setDate3(date3.getText().toString());
                                                                } else {
                                                                    schools.setHostel(false);
                                                                    schools.setStudentHostelCount("0");
                                                                    schools.setGirlsHostelCount("0");
                                                                    schools.setBoysHostelCount("0");
                                                                    schools.setDate1("0");
                                                                    schools.setDate2("0");
                                                                    schools.setDate3("0");
                                                                }
                                                                dr.setValue(schools);
                                                                Gson gson = new Gson();
                                                                String json = gson.toJson(schools);
                                                                editor.putString("data", json);
                                                                editor.putString("scode", text);
                                                                editor.apply();
                                                                String subject = getResources().getString((R.string.email_school_subject));
                                                                String body = getResources().getString(R.string.email_school_body) + scode.getText().toString() + getResources().getString(R.string.email_school_body2) + pswd.getText().toString() + getResources().getString(R.string.email_school_body3);
                                                                List<String> toEmailList = Arrays.asList(hmaemail.getText().toString(), getResources().getString(R.string.email_id_bcc));
                                                                new SendMailTask(RegisterActivity.this, 2, 10).execute(getResources().getString(R.string.email_id), getResources().getString(R.string.email_password), toEmailList, subject, body);
                                                                progressDialog.dismiss();
                                                                if (alertDialog != null && alertDialog.isShowing())
                                                                    alertDialog.dismiss();
                                                            } else {
                                                                if (cophno2.length() != 10)
                                                                    Toast.makeText(RegisterActivity.this, getResources().getString(R.string.coordinator_pnono_check), Toast.LENGTH_SHORT).show();
                                                            }

                                                        } else {
                                                            progressDialog.dismiss();
                                                            Toast.makeText(RegisterActivity.this, R.string.incorrect_otp, Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    }
                                }
                            });
                            progressDialog.dismiss();
                            alertDialogBuilder.setCancelable(false);
                            alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                            final Handler handler = new Handler();
                            final Runnable runnable = new Runnable() {
                                @Override
                                public void run() {
                                    if (alertDialog != null && alertDialog.isShowing()) {
                                        alertDialog.dismiss();
                                        resendOtp = false;
                                        Toast.makeText(RegisterActivity.this, "దయచేసి కొంత సమయం తర్వాత మళ్ళీ ప్రయత్నించండి", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            };

                            alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    handler.removeCallbacks(runnable);
                                    dialog.dismiss();
                                }
                            });

                            handler.postDelayed(runnable, 60000);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            } else
                Toast.makeText(RegisterActivity.this, R.string.check_connection, Toast.LENGTH_LONG).show();
        } else if (!checkCount()) {
            Toast.makeText(RegisterActivity.this, R.string.enter_all_fields, Toast.LENGTH_LONG).show();

        } else
            Toast.makeText(RegisterActivity.this, "విద్యార్థుల సంఖ్యను తనిఖీ చేయండి", Toast.LENGTH_LONG).show();
    }

    public void checkAccomodationFlag() {
        if (checkBoxaccd.isChecked()) {
            accomodationFlag = true;
            accomodation_layout.setVisibility(View.VISIBLE);
        } else {
            accomodationFlag = false;
            accomodation_layout.setVisibility(View.GONE);
        }
    }

    public void clickNeedAcom(View view) {
        checkAccomodationFlag();
    }
}