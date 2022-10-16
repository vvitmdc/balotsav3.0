package app.balotsav.com.vvitbalotsav.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;

import app.balotsav.com.vvitbalotsav.R;
import app.balotsav.com.vvitbalotsav.model.Schools;
import app.balotsav.com.vvitbalotsav.utils.CheckNetwork;
import app.balotsav.com.vvitbalotsav.utils.DbHelper;

public class EditRegistrationActivity extends AppCompatActivity {
    EditText sname;
    EditText scode;
    EditText hmname;
    EditText hmaemail;
    EditText hmphno;
    EditText coname;
    EditText cophno2;
    EditText district;
    EditText address;
    EditText town;
    EditText state;
    EditText pincode;
    EditText participants;
    EditText boys;
    EditText girls;
    EditText aParticipants;
    EditText aBoys;
    EditText aGirls;
    ArrayList<String> a;
    Spinner boardingPlace;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    private Schools s;
    DbHelper helper;
    private String code;
    private CheckBox accomodation, date1, date2, date3;
    private LinearLayout accomodation_layout;

    private int participantCount, participantHostelCount;
    private int girlsCount, girlsHostelCount;
    private int boysCount, boysHostelCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_registration);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        a = new ArrayList<>();
        a.add("--- స్కూల్ టైపు ని సెలెక్ట్ చేసుకొండి ---");
        a.add("STATE");
        a.add("CBSE");
        a.add("ICSE");
        sname = findViewById(R.id.sname_edit);
        scode = findViewById(R.id.scode_edit);
        hmaemail = findViewById(R.id.prinicpal_mail_edit);
        address = findViewById(R.id.id_address_edit);
        hmname = findViewById(R.id.principal_edit);
        hmphno = findViewById(R.id.principal_phone_edit);
        coname = findViewById(R.id.coordinator_name_edit);
        cophno2 = findViewById(R.id.coordinator_phone_edit);
        town = findViewById(R.id.village_edit);
        district = findViewById(R.id.district_edit);
        state = findViewById(R.id.state_edit);
        pincode = findViewById(R.id.pincode_edit);
        boardingPlace = findViewById(R.id.id_boarding_place_edit);
        accomodation = findViewById(R.id.id_needAcomdiation_edit);
        accomodation_layout = findViewById(R.id.id_accomodation_layout_edit);
        participants = findViewById(R.id.id_participants_edit);
        boys = findViewById(R.id.id_boys_edit);
        girls = findViewById(R.id.id_girls_edit);
        aParticipants = findViewById(R.id.id_accomodation_participants_edit);
        aBoys = findViewById(R.id.id_accomodation_boys_edit);
        aGirls = findViewById(R.id.id_accomodation_girls_edit);
        date1 = findViewById(R.id.id_date1_edit);
        date2 = findViewById(R.id.id_date2_edit);
        date3 = findViewById(R.id.id_date3_edit);
        address.setHint(getResources().getString(R.string.multilinehint));
        helper = new DbHelper(this);
        code = getSharedPreferences("Balotsav", 0).getString("scode", "");
        Log.i("test-code ", code);
        Log.i("test-code replaced", code.replaceAll("@", "/"));
        s = helper.getSchool(code.replaceAll("@", "/"));
        Log.i("test-get-address",s.getAddress());
        sname.setText(s.getSchool_Name());
        scode.setText(s.getSchool_Code().replaceAll("@", "/"));
        hmname.setText(s.getHeadMaster_Name());
        hmaemail.setText(s.getHeadMaster_Email());
        hmphno.setText(s.getHeadMaster_PhNo());
        coname.setText(s.getCoordinator_Name());
        cophno2.setText(s.getCoordinator_PhNo());
        town.setText(s.getTown());
        district.setText(s.getDistrict());
        state.setText(s.getState());
        pincode.setText(s.getPinCode());
        participants.setText(s.getStudentCount());
        girls.setText(s.getGirlsCount());
        boys.setText(s.getBoysCount());
        address.setText(s.getAddress());
        address.addTextChangedListener(new TextWatcher() {
            private String text;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                text = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                address.setHint(getResources().getString(R.string.multilinehint));
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
        Log.i("Test-Hostel", "" + s.getHostel());
        accomodation.setChecked(s.getHostel());
        if (accomodation.isChecked()) {
            accomodation_layout.setVisibility(View.VISIBLE);
            boardingPlace.setSelection(((ArrayAdapter<String>) boardingPlace.getAdapter()).getPosition(s.getBoardingPlace()));
            if (s.getDate1().equals("నవంబర్ 28"))
                date1.setChecked(true);
            else
                date1.setChecked(false);
            if (s.getDate2().equals("నవంబర్ 29"))
                date2.setChecked(true);
            else
                date2.setChecked(false);
            if (s.getDate3().equals("నవంబర్ 30"))
                date3.setChecked(true);
            else
                date3.setChecked(false);
            aGirls.setText(s.getGirlsHostelCount());
            aBoys.setText(s.getBoysHostelCount());
            aParticipants.setText(s.getStudentHostelCount());
        } else {
            accomodation_layout.setVisibility(View.GONE);
        }
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

    public void Changes(View view) {

        if(checkHostelCount() && checkCount()) {
            if (new CheckNetwork(this).isNetworkAvailable()) {
                Schools s1 = new Schools();
                s1.setSchool_Name(sname.getText().toString());
                s1.setBoard(s.getBoard());
                s1.setSchool_Code(getSharedPreferences("Balotsav", 0).getString("scode", ""));
                s1.setHeadMaster_Name(hmname.getText().toString());
                s1.setHeadMaster_Email(hmaemail.getText().toString());
                s1.setHeadMaster_PhNo(hmphno.getText().toString());
                s1.setPassword(s.getPassword());
                s1.setCoordinator_Name(coname.getText().toString());
                s1.setCoordinator_PhNo(cophno2.getText().toString());
                s1.setTown(town.getText().toString());
                s1.setAddress(address.getText().toString());
                s1.setDistrict(district.getText().toString());
                s1.setState(state.getText().toString());
                s1.setPinCode(pincode.getText().toString());
                s1.setStudentCount(s.getStudentCount());
                s1.setGirlsCount(s.getGirlsCount());
                s1.setBoysCount(s.getBoysCount());
                if (!accomodation.isChecked()) {
                    s1.setHostel(false);
                    s1.setStudentHostelCount(s.getStudentHostelCount());
                    s1.setGirlsHostelCount(s.getGirlsHostelCount());
                    s1.setBoysHostelCount(s.getBoysHostelCount());
                    s1.setDate1(s.getDate1());
                    s1.setDate2(s.getDate2());
                    s1.setDate3(s.getDate3());
                    s1.setBoardingPlace(s.getBoardingPlace());
                } else {
                    s1.setHostel(true);
                    s1.setStudentHostelCount("" + participantHostelCount);
                    s1.setGirlsHostelCount(String.valueOf(girlsHostelCount));
                    s1.setBoysHostelCount(String.valueOf(boysHostelCount));
                    s1.setDate1(date1.getText().toString());
                    s1.setDate2(date2.getText().toString());
                    s1.setDate3(date3.getText().toString());
                    s1.setBoardingPlace(boardingPlace.getSelectedItem().toString());
                }
                Gson g = new Gson();
                String str = g.toJson(s1);
                SharedPreferences sh = getSharedPreferences("Balotsav", MODE_PRIVATE);
                SharedPreferences.Editor editor = sh.edit();
                editor.putString("data", str);
                editor.apply();
                helper.updateSchool(s1, s1.getSchool_Code());
                Schools s2 = helper.getSchool(s1.getSchool_Code());
                Log.i("test-update-address",s2.getAddress());
                databaseReference.child("Schools").child(s1.getSchool_Code()).setValue(s1);
                Toast.makeText(this, "మార్పులు చేయబడినవి", Toast.LENGTH_SHORT).show();
                super.onBackPressed();
            } else
                Toast.makeText(this, R.string.check_connection, Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(EditRegistrationActivity.this,"విద్యార్థుల సంఖ్యను తనిఖీ చేయండి" , Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(this, DashBoardActivity.class));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                //startActivity(new Intent(this, DashBoardActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void clickNeedAcom(View view) {
        if (accomodation.isChecked()) {
            accomodation_layout.setVisibility(View.VISIBLE);
        } else {
            accomodation_layout.setVisibility(View.GONE);
        }
    }
}