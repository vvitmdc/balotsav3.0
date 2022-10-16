package app.balotsav.com.vvitbalotsav.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import app.balotsav.com.vvitbalotsav.R;
import app.balotsav.com.vvitbalotsav.model.Event;
import app.balotsav.com.vvitbalotsav.utils.CheckNetwork;
import app.balotsav.com.vvitbalotsav.utils.DbHelper;

public class EventRegistrationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinnerSenior;
    Spinner spinnerSUBJUN;
    Spinner spinnerJUN;
    TextView team, teamno, junior, subjunior, senior, rules;
    String[] maxnumber;
    Event e, e1, event;
    int max;
    Button b1;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private AlertDialog.Builder builder;
    private SharedPreferences pref;
    private SliderLayout sliderLayout;
    private Button register;
    private HashMap<String, String> Hash_file_maps;
    DbHelper helper;
    CheckNetwork myNetwork;
    private Button b2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_registration);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.action_bar_title_layout);
        LinearLayout layout = (LinearLayout) actionBar.getCustomView();
        TextView titleTextView = layout.findViewById(R.id.page_title_layout);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        helper = new DbHelper(this);
        myNetwork = new CheckNetwork(this);
        register = findViewById(R.id.id_register_event);
        rules = findViewById(R.id.id_rules_text);
        Hash_file_maps = new HashMap<>();
        e = getIntent().getParcelableExtra("event");
        Log.i("Test:",e.getName());
        titleTextView.setText(getResources().getString(R.string.registration)+" "+e.getName());

        sliderLayout = findViewById(R.id.slider_image);
        max = e.getMax();
        pref = getSharedPreferences("Balotsav", Context.MODE_PRIVATE);
        String text = pref.getString("scode", "");
        Log.i("Test -EventRegistration", text);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Events").child(text).child(e.getName());
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setData();
            }
        });
        setRules(e.getName());
        setEventSlideView();

    }

    public void setRules(String eventName) {
        rules.setTextSize(18);
        switch (eventName) {
            case "చిత్రలేఖనం":
                rules.setText(R.string.chitralekanam);
                break;
            case "వక్తృత్వం (తెలుగు)":
                rules.setText(R.string.vaktrutvam);
                break;
            case "ఏకపాత్రాభినయం":
                rules.setText(R.string.ekapatrabhinayem);
                break;
            case "శాస్త్రీయ నృత్యం":
                rules.setText(R.string.sastriyanrutyam);
                break;
            case "సాంప్రదాయ వేషధారణ":
                rules.setText(R.string.samprathayaveshadarana);
                break;
            case "తెలుగులోనే మాట్లాడుదాం":
                rules.setText(R.string.telugulonematladudham);
                break;
            case "శాస్త్రీయ సంగీతం (గాత్రం)":
                rules.setText(R.string.sastriyasangeetam);
                break;
            case "జనరల్ క్విజ్":
                rules.setText(R.string.generalquiz);
                break;
            case "డిజిటల్ చిత్రలేఖనం":
                rules.setText(R.string.digitalchitralekhanam);
                break;
            case "తెలుగు పద్యం":
                rules.setText(R.string.telugupadhyam);
                break;
            case "సినీ,లలిత,జానపద గీతాలు":
                rules.setText(R.string.sinigeethalu);
                break;
            case "ముఖాభినయం":
                rules.setText(R.string.mukhabinayem);
                break;
            case "వక్తృత్వం (ఇంగ్లీష్)":
                rules.setText(R.string.vaktrutvam);
                break;
            case "సంస్కృత శ్లోకం":
                rules.setText(R.string.samskruthaslokam);
                break;
            case "జానపద నృత్యం":
                rules.setText(R.string.janapadhanruthyam);
                break;
            case "కవిత రచన (తెలుగు)":
                rules.setText(R.string.kavitharachana);
                break;
            case "నాటికలు":
                rules.setText(R.string.natikalu);
                break;
            case "వాద్య సంగీతం (రాగ ప్రధానం)":
                rules.setText(R.string.vadhyasangeetam);
                break;
            case "కథ రచన (తెలుగు)":
                rules.setText(R.string.kadharachana);
                break;
            case "స్పెల్ బీ":
                rules.setText(R.string.speelbee);
                break;
            case "మట్టితో బొమ్మ చేద్దాం":
                rules.setText(R.string.mattilobommaluchedham);
                break;
            case "లేఖా రచన":
                rules.setText(R.string.lekharachana);
                break;
            case "కథావిశ్లేషణ":
                rules.setText(R.string.kadhavisleshana);
                break;
            case "వాద్య సంగీతం (తాళ ప్రధానం)":
                rules.setText(R.string.vadhyasangeetham);
                break;
            case "లఘు చిత్ర విశ్లేషణ":
                rules.setText(R.string.laghuchitravisleshana);
                break;
            case "జానపద నృత్యం-బృంద ప్రదర్శన":
                rules.setText(R.string.janapadhanrutyam1);
                break;
            case "శాస్త్రీయ నృత్యం-బృంద ప్రదర్శన":
                rules.setText(R.string.sastriyanrutyam);
                break;
            case "విచిత్ర (ఫాన్సీ) వేషధారణ (సెట్టింగ్స్ లేకుండా)":
                rules.setText(R.string.vichitraveshadarana);
                break;
            case "విచిత్ర (ఫాన్సీ) వేషధారణ (సెట్టింగ్స్)":
                rules.setText(R.string.vichitraveshadarana_withsettings);
                break;
            case "కథ చెబుతా వింటారా?":
                rules.setText(R.string.kathacheutanu);
                break;
        }

    }

    public void setEventSlideView() {
        List<String> urlList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.photos)));

        for(int i=1;i<urlList.size();i++){
            Hash_file_maps.put(String.valueOf(i), urlList.get(i));
        }

        for (String name : Hash_file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);
            textSliderView
                    .image(Hash_file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.FitCenterCrop);
            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Stack);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setDuration(9000);
    }

    public void setData() {
        final AlertDialog.Builder layoutBuilder = new AlertDialog.Builder(this);
        layoutBuilder.setTitle(getString(R.string.registration) + " : " + e.getName());
        final int widthLayout = LinearLayout.LayoutParams.MATCH_PARENT;
        final int heightLayout = LinearLayout.LayoutParams.WRAP_CONTENT;
        LayoutInflater inflator = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        final View dialogLayout = inflator.inflate(R.layout.row_event_show, null);
        register = dialogLayout.findViewById(R.id.id_register_event);
        spinnerJUN = dialogLayout.findViewById(R.id.id_SpinnerJun);
        spinnerSUBJUN = dialogLayout.findViewById(R.id.id_SUBJUN);
        spinnerSenior = dialogLayout.findViewById(R.id.id_Spinnersenior);
        team = dialogLayout.findViewById(R.id.team);
        teamno = dialogLayout.findViewById(R.id.teamno);
        junior = dialogLayout.findViewById(R.id.junior);
        subjunior = dialogLayout.findViewById(R.id.subjunior);
        senior = dialogLayout.findViewById(R.id.senior);
        b1 = dialogLayout.findViewById(R.id.id_eventregister);
        b2 = dialogLayout.findViewById(R.id.id_event_cancel);
        e1 = helper.getEvent(e);


        junior.setVisibility(View.GONE);
        subjunior.setVisibility(View.GONE);
        senior.setVisibility(View.GONE);
        spinnerJUN.setVisibility(View.GONE);
        spinnerSUBJUN.setVisibility(View.GONE);
        spinnerSenior.setVisibility(View.GONE);
        team.setVisibility(View.GONE);
        teamno.setVisibility(View.GONE);
        maxnumber = new String[max + 1];
        for (int i = 0; i <= max; i++)
            maxnumber[i] = String.valueOf(i);
        ArrayAdapter<String> obj = new ArrayAdapter<String>(EventRegistrationActivity.this, android.R.layout.simple_spinner_dropdown_item, maxnumber);

        spinnerJUN.setOnItemSelectedListener(EventRegistrationActivity.this);
        spinnerJUN.setAdapter(obj);
        spinnerSUBJUN.setOnItemSelectedListener(EventRegistrationActivity.this);
        spinnerSUBJUN.setAdapter(obj);
        spinnerSenior.setOnItemSelectedListener(EventRegistrationActivity.this);
        spinnerSenior.setAdapter(obj);

        if (e1 == null)
            event = e;
        else {
            event = e1;
        }
        if (event.getTeam() != 0) {
            if(event.getSj()==0 && event.getS()==0 && event.getJ()==0 )
                team.setText(getResources().getString(R.string.for_all));
            else
                team.setText(getResources().getString(R.string.team));
            team.setVisibility(View.VISIBLE);
            teamno.setVisibility(View.VISIBLE);
            teamno.setText(String.valueOf(event.getTeam()));
        } else {
            if (event.getJ() == -1 && event.getSj() == -1) {
                senior.setVisibility(View.VISIBLE);
                spinnerSenior.setVisibility(View.VISIBLE);
            }
            if (event.getJ() == -1 && event.getS() == -1) {
                subjunior.setVisibility(View.VISIBLE);
                spinnerSUBJUN.setVisibility(View.VISIBLE);
            }
            if (event.getSj() == -1 && event.getJ() != -1) {
                junior.setVisibility(View.VISIBLE);
                spinnerJUN.setVisibility(View.VISIBLE);
                senior.setVisibility(View.VISIBLE);
                spinnerSenior.setVisibility(View.VISIBLE);
            }
            if (event.getJ() != -1 && event.getS() != -1 && event.getSj() != -1) {
                junior.setVisibility(View.VISIBLE);
                spinnerJUN.setVisibility(View.VISIBLE);
                senior.setVisibility(View.VISIBLE);
                spinnerSenior.setVisibility(View.VISIBLE);
                subjunior.setVisibility(View.VISIBLE);
                spinnerSUBJUN.setVisibility(View.VISIBLE);
            }
            spinnerJUN.setSelection(event.getJ());
            spinnerSUBJUN.setSelection(event.getSj());
            spinnerSenior.setSelection(event.getS());
        }
        if(b1.getText().toString().equalsIgnoreCase(getString(R.string.edit))){
            b2.setAlpha(1f);
        }
        else{
            b2.setAlpha(0.5f);
        }
        if (event.isRegistered()) {
            b1.setText(R.string.edit);
            b2.setEnabled(true);
            b2.setAlpha(1f);
        }
        layoutBuilder.setView(dialogLayout);
        final AlertDialog alertDialog = layoutBuilder.create();
        alertDialog.show();
        Rect displayRectangle = new Rect();
        Window window = EventRegistrationActivity.this.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        alertDialog.getWindow().setLayout(widthLayout, heightLayout);
    }

    public void clickReset(View view) {

        builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.cancel_registration);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Log.i("eventregistration", String.valueOf(e.getJ()));
                if (myNetwork.isNetworkAvailable())
                    databaseReference.removeValue();
                helper.deleteEvent(e.getName());
                b1.setText(R.string.registration);
                b2.setAlpha(.5f);
                b2.setEnabled(false);
                Toast.makeText(EventRegistrationActivity.this, e.getName() + " పోటీ రద్దు  చేయబడినది ", Toast.LENGTH_LONG).show();
                finish();

            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
        builder.setCancelable(false);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void clickRegister(View view) {
        String s = getSharedPreferences("Balotsav", MODE_PRIVATE).getString("scode", "");
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Events").child(s).child(e.getName());

        e.setJ(spinnerJUN.getSelectedItemPosition());
        e.setSj(spinnerSUBJUN.getSelectedItemPosition());
        e.setS(spinnerSenior.getSelectedItemPosition());
        if (e.getTeam() == 0) {
            if (e.getS() == 0) {
                if (e.getJ() == 0) {
                    if (e.getSj() == 0) {
                        e.setRegistered(false);
                        Toast.makeText(this, "మీరు ఈ " + e.getName() + "పోటీలో పాల్గొనలేదు  ", Toast.LENGTH_SHORT).show();
                    } else {
                        if (e.getSj() > 0) {
                            e.setRegistered(true);
                            if (myNetwork.isNetworkAvailable())
                                databaseReference.setValue(e);
                            Toast.makeText(this, "విజయవంతంగా " + e.getName() + " నమోదు అయ్యారు.", Toast.LENGTH_SHORT).show();
                        } else {
                            e.setRegistered(false);
                            Toast.makeText(this, "మీరు ఈ " + e.getName() + "పోటీలో పాల్గొనలేదు  ", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else if (e.getJ() > 0) {
                    if (e.getSj() == 0) {
                        e.setRegistered(true);
                        if (myNetwork.isNetworkAvailable())
                            databaseReference.setValue(e);
                        Toast.makeText(this, "విజయవంతంగా " + e.getName() + " నమోదు అయ్యారు.", Toast.LENGTH_SHORT).show();
                    } else {
                        e.setRegistered(true);
                        if (myNetwork.isNetworkAvailable())
                            databaseReference.setValue(e);
                        Toast.makeText(this, "విజయవంతంగా " + e.getName() + " నమోదు అయ్యారు.", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    if (e.getSj() < 0) {
                        e.setRegistered(false);
                        Toast.makeText(this, "మీరు ఈ " + e.getName() + "పోటీలో పాల్గొనలేదు  ", Toast.LENGTH_SHORT).show();

                    }
                }
            } else if (e.getS() > 0) {
                if (e.getJ() == 0) {
                    if (e.getSj() == 0) {
                        e.setRegistered(true);
                        if (myNetwork.isNetworkAvailable())
                            databaseReference.setValue(e);
                        Toast.makeText(this, "విజయవంతంగా " + e.getName() + " నమోదు అయ్యారు.", Toast.LENGTH_SHORT).show();
                    } else {
                        e.setRegistered(true);
                        if (myNetwork.isNetworkAvailable())
                            databaseReference.setValue(e);
                        Toast.makeText(this, "విజయవంతంగా " + e.getName() + " నమోదు అయ్యారు.", Toast.LENGTH_SHORT).show();
                    }
                } else if (e.getJ() > 0) {
                    e.setRegistered(true);
                    if (myNetwork.isNetworkAvailable())
                        databaseReference.setValue(e);
                    Toast.makeText(this, "విజయవంతంగా " + e.getName() + " నమోదు అయ్యారు.", Toast.LENGTH_SHORT).show();
                } else {
                    e.setRegistered(true);
                    if (myNetwork.isNetworkAvailable())
                        databaseReference.setValue(e);
                    Toast.makeText(this, "విజయవంతంగా " + e.getName() + " నమోదు అయ్యారు.", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (e.getJ() < 0) {
                    if (e.getSj() == 0) {
                        e.setRegistered(false);
                        Toast.makeText(this, "మీరు ఈ " + e.getName() + "పోటీలో పాల్గొనలేదు  ", Toast.LENGTH_SHORT).show();

                    } else if (e.getSj() > 0) {
                        // e.setSj(spinnerSUBJUN.getSelectedItemPosition());
                        e.setRegistered(true);
                        Toast.makeText(this, "విజయవంతంగా " + e.getName() + " నమోదు అయ్యారు.", Toast.LENGTH_SHORT).show();
                        if (myNetwork.isNetworkAvailable())
                            databaseReference.setValue(e);
                    }
                }


            }
        } else {
            e.setRegistered(true);
            if (myNetwork.isNetworkAvailable())
                databaseReference.setValue(e);
            Toast.makeText(this, "విజయవంతంగా " + e.getName() + " నమోదు అయ్యారు.", Toast.LENGTH_SHORT).show();
        }
        Log.i("e.team", String.valueOf(e.getTeam()));
        /*if (myNetwork.isNetworkAvailable()) {


        } else
            //Toast.makeText(this, R.string.unable_update, Toast.LENGTH_LONG).show();*/
        if (b1.getText().toString().equalsIgnoreCase(getString(R.string.edit)))
            helper.updateEvent(e, e.getName());
        else {
            helper.addEvent(e);
            b1.setText(R.string.edit);
            b2.setAlpha(1f);
            b2.setEnabled(true);
        }
        finish();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
