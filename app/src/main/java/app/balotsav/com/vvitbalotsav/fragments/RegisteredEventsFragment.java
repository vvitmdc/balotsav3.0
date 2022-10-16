package app.balotsav.com.vvitbalotsav.fragments;


import static android.content.Context.MODE_PRIVATE;
import static android.view.View.GONE;
import static app.balotsav.com.vvitbalotsav.utils.DbHelper.EVENT_JUNIORS;
import static app.balotsav.com.vvitbalotsav.utils.DbHelper.EVENT_MAX;
import static app.balotsav.com.vvitbalotsav.utils.DbHelper.EVENT_NAME;
import static app.balotsav.com.vvitbalotsav.utils.DbHelper.EVENT_REGISTERED;
import static app.balotsav.com.vvitbalotsav.utils.DbHelper.EVENT_SENOIRS;
import static app.balotsav.com.vvitbalotsav.utils.DbHelper.EVENT_SUBJUNIORS;
import static app.balotsav.com.vvitbalotsav.utils.DbHelper.EVENT_TEAM;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import app.balotsav.com.vvitbalotsav.R;
import app.balotsav.com.vvitbalotsav.activities.DashBoardActivity;
import app.balotsav.com.vvitbalotsav.activities.PdfViewActivity;
import app.balotsav.com.vvitbalotsav.model.Event;
import app.balotsav.com.vvitbalotsav.model.RegisterDetail;
import app.balotsav.com.vvitbalotsav.model.Schools;
import app.balotsav.com.vvitbalotsav.utils.CheckNetwork;
import app.balotsav.com.vvitbalotsav.utils.DbHelper;
import app.balotsav.com.vvitbalotsav.utils.EventAdapter;
import app.balotsav.com.vvitbalotsav.utils.GrantPermissions;
import app.balotsav.com.vvitbalotsav.utils.SendMailTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisteredEventsFragment extends Fragment {


    RecyclerView recyclerView;
    ArrayList<RegisterDetail> registerDetailActivityModels = new ArrayList<>();
    View rootview;
    Button submit;
    TextView noevent;
    DbHelper helper;
    List<Event> arrayList;
    Button viewPdf;
    private Schools schools;
    private String path1;
    private AcroFields acroFields;
    CheckNetwork checkNetwork;
    private SharedPreferences pref;
    private LinearLayout layout;

    @SuppressLint({"RestrictedApi", "Range"})
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_registered_events, container, false);
        noevent = rootview.findViewById(R.id.id_noEvents);
        layout = rootview.findViewById(R.id.id_events);
        recyclerView = rootview.findViewById(R.id.recycler1);
        submit = rootview.findViewById(R.id.id_submit);
        viewPdf = rootview.findViewById(R.id.id_view_pdf);
        ActionBar actionBar = ((DashBoardActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.registered_events));
        checkNetwork = new CheckNetwork(getContext());
        helper = new DbHelper(getContext());
        arrayList = new ArrayList<>();
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        pref = this.getActivity().getSharedPreferences("Balotsav", MODE_PRIVATE);
        String text = pref.getString("scode", "").replaceAll("/", "@");
        Log.i("Test-RegisteredEvents", text);
        schools = helper.getSchool(text.replaceAll("@", "/"));
        Cursor cursor = helper.getEventList();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Event event = new Event();
                RegisterDetail registerDetailActivityModel = null;
                event.setName(cursor.getString(cursor.getColumnIndex(EVENT_NAME)));
                event.setJ(cursor.getInt(cursor.getColumnIndex(EVENT_JUNIORS)));
                event.setMax(cursor.getInt(cursor.getColumnIndex(EVENT_MAX)));
                if (cursor.getInt(cursor.getColumnIndex(EVENT_REGISTERED)) == 0)
                    event.setRegistered(false);
                else
                    event.setRegistered(true);
                event.setS(cursor.getInt(cursor.getColumnIndex(EVENT_SENOIRS)));
                event.setSj(cursor.getInt(cursor.getColumnIndex(EVENT_SUBJUNIORS)));
                event.setTeam(cursor.getInt(cursor.getColumnIndex(EVENT_TEAM)));
                if (event.isRegistered()) {
                    if (event.getTeam() == 0) {
                        if (event.getSj() == -1 && event.getJ() != -1)
                            registerDetailActivityModel = new RegisterDetail(event.getName(), event.getJ(), event.getS());
                        if (event.getSj() == -1 && event.getJ() == -1)
                            registerDetailActivityModel = new RegisterDetail(event.getName(), event.getS());
                        if (event.getJ() == -1 && event.getS() == -1)
                            registerDetailActivityModel = new RegisterDetail(event.getName(), event.getSj(), 0, 0, 0, 0);
                        if (event.getJ() != -1 && event.getSj() != -1)
                            registerDetailActivityModel = new RegisterDetail(event.getName(), event.getSj(), event.getJ(), event.getS());
                    } else
                        registerDetailActivityModel = new RegisterDetail(event.getName(), 0, 0, 0, event.getTeam());
                    registerDetailActivityModels.add(registerDetailActivityModel);
                }
                arrayList.add(event);
                Log.i("test", event.getName() + registerDetailActivityModels.size());

            }
            if (registerDetailActivityModels.size() == 0) {
                noevent.setVisibility(View.VISIBLE);
                layout.setVisibility(GONE);

            }
            else{
                layout.setVisibility(View.VISIBLE);
            }

            recyclerView.setAdapter(new EventAdapter(getActivity(), registerDetailActivityModels, rootview.getContext()));

        } else {
            rootview = inflater.inflate(R.layout.noevents, container, false);
        }
        if (getTime()) {
            submit.setClickable(false);
            submit.setBackgroundResource(R.drawable.mybutton);
            submit.setTextColor(getActivity().getResources().getColor(R.color.solidColor));
            viewPdf.setBackgroundResource(R.drawable.border_button);
            viewPdf.setTextColor(getActivity().getResources().getColor(R.color.black));
        } else {
            submit.setClickable(true);
            submit.setBackgroundResource(R.drawable.border_button);
            submit.setTextColor(getActivity().getResources().getColor(R.color.black));
            viewPdf.setBackgroundResource(R.drawable.mybutton);
            viewPdf.setTextColor(getActivity().getResources().getColor(R.color.solidColor));
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getTime())
                    new MyTask(submit).execute();
            }
        });
        viewPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (schools != null && registerDetailActivityModels.size() != 0)
                    new MyTask(viewPdf).execute();


            }
        });
        recyclerView = rootview.findViewById(R.id.recycler1);
        LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayout);
        return rootview;
    }

    public boolean getTime() {
        final Date today = new Date();
        int today_date = today.getDate();
        int today_month = today.getMonth() + 1;
        int today_year = today.getYear() + 1900;
        String result = pref.getString("expiryDay", "");

        String[] dte = result.split("-");
        int d1 = Integer.parseInt(dte[2]);
        int d2 = Integer.parseInt(dte[1]);
        //noinspection unused
        int d3 = Integer.parseInt(dte[0]);
        if (today_month >= d2 && today_date >= d1) {
            return true;
        }

        String time = today_year + "-" + d2 + "-" + d1 + " 10:10:10";
        Timer t = new Timer();
        try {
            t.schedule(new TimerTask() {
                public void run() {
                    new MyTask(viewPdf).execute();
                }
            }, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time));
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return false;
    }


    private void generatePDF() {
        try {

            PdfReader reader = new PdfReader(getResources().openRawResource(R.raw.balotsav_form));
            acroFields = reader.getAcroFields();


            if (new GrantPermissions(getActivity()).checkAndRequestPermissions()) {
                path1 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Balotsav_" + schools.getCoordinator_PhNo() + ".pdf";
                Log.i("path", path1);
                File file = new File(path1);
                OutputStream outputStream = new FileOutputStream(file);
                PdfStamper stamper = new PdfStamper(reader, outputStream, '\0');
                acroFields = stamper.getAcroFields();
                acroFields.setField("schoolName", schools.getSchool_Name());
                acroFields.setField("schoolId", schools.getSchool_Code());
                acroFields.setField("schoolAddress", schools.getAddress());
                acroFields.setField("schoolCity", schools.getTown());
                acroFields.setField("schoolDistrict", schools.getDistrict());
                acroFields.setField("schoolPrincipalName", schools.getHeadMaster_Name());
                acroFields.setField("schoolPrincipalMobileNo", schools.getHeadMaster_PhNo());
                acroFields.setField("schoolPrincipalEmail", schools.getHeadMaster_Email());
                acroFields.setField("teacherName", schools.getCoordinator_Name());
                acroFields.setField("teacherMobileNo", schools.getCoordinator_PhNo());
                acroFields.setField("hostelBoys", schools.getBoysHostelCount());
                acroFields.setField("hostelGirls", schools.getGirlsHostelCount());
                acroFields.setField("total", String.valueOf(schools.getTotal()));


                for (int i = 0; i < arrayList.size(); i++) {
                    Event e = arrayList.get(i);
                    Log.i("print e", e.getName());
                    switch (e.getName()) {
                        case "చిత్రలేఖనం":
                            if (e.getSj() != 0)
                                acroFields.setField("fill_1", String.valueOf(e.getSj()));
                            if (e.getS() != 0)
                                acroFields.setField("fill_2", String.valueOf(e.getJ()));
                            if (e.getJ() != 0)
                                acroFields.setField("fill_3", String.valueOf(e.getS()));
                            Log.i("print switch e", e.getName());
                            break;
                        case "వక్తృత్వం (తెలుగు)":
                            if (e.getJ() != 0)
                                acroFields.setField("fill_8", String.valueOf(e.getJ()));
                            if (e.getS() != 0)
                                acroFields.setField("fill_11_2", String.valueOf(e.getS()));
                            Log.i("print switch e", e.getName());
                            break;
                        case "ఏకపాత్రాభినయం":
                            if (e.getJ() != 0)
                                acroFields.setField("fill_17", String.valueOf(e.getJ()));
                            if (e.getS() != 0)
                                acroFields.setField("fill_18_2", String.valueOf(e.getS()));
                            Log.i("print switch e", e.getName());

                            break;
                        case "కథ చెబుతా వింటారా?":
                            if (e.getSj() != 0)
                                acroFields.setField("fill_18", String.valueOf(e.getJ()));
                            Log.i("print switch e", e.getName());

                            break;
                        case "శాస్త్రీయ నృత్యం":
                            if (e.getSj() != 0)
                                acroFields.setField("fill_5", String.valueOf(e.getSj()));
                            if (e.getJ() != 0)
                                acroFields.setField("fill_10_2", String.valueOf(e.getJ()));
                            if (e.getS() != 0)
                                acroFields.setField("fill_6", String.valueOf(e.getS()));
                            Log.i("print switch e", e.getName());

                            break;
                        case "సాంప్రదాయ వేషధారణ":
                            if (e.getSj() != 0) {
                                acroFields.setField("fill_23", String.valueOf(e.getSj()));
                                Log.i("print switch e", e.getName());
                            }
                            break;
                        case "తెలుగులోనే మాట్లాడుదాం":
                            if (e.getSj() != 0)
                                acroFields.setField("fill_30_2", String.valueOf(e.getSj()));
                            if (e.getJ() != 0)
                                acroFields.setField("fill_21_2", String.valueOf(e.getJ()));

                            Log.i("print switch e", e.getName());

                            break;
                        case "శాస్త్రీయ సంగీతం (గాత్రం)":
                            if (e.getJ() != 0)
                                acroFields.setField("fill_16", String.valueOf(e.getJ()));
                            if (e.getS() != 0)
                                acroFields.setField("fill_17_2", String.valueOf(e.getS()));
                            Log.i("print switch e", e.getName());

                            break;
                        case "జనరల్ క్విజ్":
                            //acroFields.setField("18", " ");
                            if (e.getTeam() != 0) {
                                acroFields.setField("fill_12", String.valueOf(e.getTeam()));
                                Log.i("print switch e", e.getName());
                            }
                            break;
                        case "డిజిటల్ చిత్రలేఖనం":
                            if (e.getSj() != 0)
                                acroFields.setField("fill_24", String.valueOf(e.getSj()));
                            if (e.getJ() != 0)
                                acroFields.setField("fill_25", String.valueOf(e.getJ()));
                            if (e.getS() != 0)
                                acroFields.setField("fill_1_2", String.valueOf(e.getS()));
                            Log.i("print switch e", e.getName());

                            break;
                        case "తెలుగు పద్యం":
                            if (e.getSj() != 0)
                                acroFields.setField("fill_20_2", String.valueOf(e.getSj()));
                            if (e.getJ() != 0)
                                acroFields.setField("fill_4", String.valueOf(e.getJ()));
                            if (e.getS() != 0)
                                acroFields.setField("fill_31_2", String.valueOf(e.getS()));
                            Log.i("print switch e", e.getName());

                            break;
                        case "సినీ,లలిత,జానపద గీతాలు":
                            if (e.getJ() != 0)
                                acroFields.setField("fill_13", String.valueOf(e.getJ()));
                            if (e.getS() != 0)
                                acroFields.setField("fill_16_2", String.valueOf(e.getS()));
                            Log.i("print switch e", e.getName());

                            break;
                        case "ముఖాభినయం":
                            if (e.getJ() != 0)
                                acroFields.setField("fill_19", String.valueOf(e.getJ()));
                            if (e.getS() != 0)
                                acroFields.setField("fill_20", String.valueOf(e.getS()));
                            Log.i("print switch e", e.getName());

                            break;
                        case "వక్తృత్వం (ఇంగ్లీష్)":
                            if (e.getSj() != 0)
                                acroFields.setField("fill_9", String.valueOf(e.getSj()));
                            if (e.getJ() != 0)
                                acroFields.setField("fill_10", String.valueOf(e.getJ()));
                            if (e.getS() != 0)
                                acroFields.setField("fill_12_2", String.valueOf(e.getS()));
                            Log.i("print switch e", e.getName());

                            break;
                        case "సంస్కృత శ్లోకం":
                            if (e.getSj() != 0) {
                                acroFields.setField("fill_34", String.valueOf(e.getSj()));
                                Log.i("print switch e", e.getName());
                            }
                            break;
                        case "జానపద నృత్యం":
                            if (e.getSj() != 0)
                                acroFields.setField("fill_13_2", String.valueOf(e.getSj()));
                            if (e.getJ() != 0)
                                acroFields.setField("fill_11", String.valueOf(e.getJ()));
                            if (e.getS() != 0)
                                acroFields.setField("fill_14_2", String.valueOf(e.getS()));
                            Log.i("print switch e", e.getName());

                            break;
                        case "కవిత రచన (తెలుగు)":
                            if (e.getJ() != 0)
                                acroFields.setField("fill_22_2", String.valueOf(e.getJ()));
                            if (e.getS() != 0)
                                acroFields.setField("fill_23_2", String.valueOf(e.getS()));
                            Log.i("print switch e", e.getName());

                            break;
                        case "నాటికలు":
                            if (e.getTeam() != 0)
                                acroFields.setField("fill_15_2", String.valueOf(e.getTeam()));
                            Log.i("print switch e", e.getName());
                            break;
                        case "వాద్య సంగీతం (రాగ ప్రధానం)":
                            if (e.getSj() != 0)
                                acroFields.setField("fill_2_2", String.valueOf(e.getSj()));
                            if (e.getJ() != 0)
                                acroFields.setField("fill_3_2", String.valueOf(e.getJ()));
                            if (e.getS() != 0)
                                acroFields.setField("fill_4_2", String.valueOf(e.getS()));
                            Log.i("print switch e", e.getName());

                            break;
                        case "కథ రచన (తెలుగు)":
                            if (e.getJ() != 0)
                                acroFields.setField("fill_8_2", String.valueOf(e.getJ()));
                            if (e.getS() != 0)
                                acroFields.setField("fill_9_2", String.valueOf(e.getS()));
                            Log.i("print switch e", e.getName());

                            break;
                        case "స్పెల్ బీ":
                            if (e.getS() != 0)
                                acroFields.setField("fill_19_2", String.valueOf(e.getS()));
                            Log.i("print switch e", e.getName());
                            break;
                        case "మట్టితో బొమ్మ చేద్దాం":
                            if (e.getTeam() != 0)
                                acroFields.setField("fill_29_2", String.valueOf(e.getTeam()));
                            Log.i("print switch e", e.getName());
                            break;
                        case "లేఖా రచన":
                            if (e.getSj() != 0)
                                acroFields.setField("fill_24_2", String.valueOf(e.getSj()));
                            if (e.getJ() != 0)
                                acroFields.setField("fill_25_2", String.valueOf(e.getJ()));
                            if (e.getS() != 0)
                                acroFields.setField("fill_26_2", String.valueOf(e.getS()));
                            Log.i("print switch e", e.getName());

                            break;
                        case "కథావిశ్లేషణ":
                            if (e.getJ() != 0)
                                acroFields.setField("fill_14", String.valueOf(e.getJ()));
                            if (e.getS() != 0)
                                acroFields.setField("fill_15", String.valueOf(e.getS()));
                            Log.i("print switch e", e.getName());

                            break;
                        case "వాద్య సంగీతం (తాళ ప్రధానం)":
                            if (e.getSj() != 0)
                                acroFields.setField("fill_5_2", String.valueOf(e.getSj()));
                            if (e.getJ() != 0)
                                acroFields.setField("fill_6_2", String.valueOf(e.getJ()));
                            if (e.getS() != 0)
                                acroFields.setField("fill_7_2", String.valueOf(e.getS()));
                            Log.i("print switch e", e.getName());

                            break;
                        case "లఘు చిత్ర విశ్లేషణ":
                            if (e.getJ() != 0)
                                acroFields.setField("fill_21", String.valueOf(e.getJ()));
                            if (e.getS() != 0)
                                acroFields.setField("fill_22", String.valueOf(e.getS()));
                            Log.i("print switch e", e.getName());

                            break;
                        case "జానపద నృత్యం-బృంద ప్రదర్శన":
                            acroFields.setField("fill_27_2", String.valueOf(e.getTeam()));
                            Log.i("print switch e", e.getName());
                            break;
                        case "శాస్త్రీయ నృత్యం-బృంద ప్రదర్శన":
                            acroFields.setField("fill_28_2", String.valueOf(e.getTeam()));
                            break;
                        case "విచిత్ర (ఫాన్సీ) వేషధారణ (సెట్టింగ్స్ లేకుండా)":
                            acroFields.setField("fill_32_2", String.valueOf(e.getTeam()));
                            Log.i("print switch e", e.getName());
                            break;
                        case "విచిత్ర (ఫాన్సీ) వేషధారణ (సెట్టింగ్స్)":
                            acroFields.setField("fill_33_2", String.valueOf(e.getTeam()));
//                            Log.i("print switch e", e.getName());
                            break;

                    }
                }
                stamper.setFormFlattening(true);
                stamper.close();
                reader.close();
                outputStream.close();


            } else
                Log.i("no permissions", "no permissions");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }


    public class MyTask extends AsyncTask<String, Void, Void> {
        ProgressDialog dialog = new ProgressDialog(getContext());
        Button v;

        public MyTask(Button v) {
            this.v = v;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (dialog.isShowing())
                dialog.dismiss();
            dialog.show();
            dialog.setMessage("నమోదు పత్రం తయారు చేయబడుతున్నది");
            dialog.setCancelable(false);

        }

        @Override
        protected Void doInBackground(String... strings) {
            generatePDF();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (dialog.isShowing())
                dialog.dismiss();
            if (v.getId() == R.id.id_view_pdf)
                startActivity(new Intent(getActivity(), PdfViewActivity.class));
            else if (v.getId() == R.id.id_submit) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                String message = getResources().getString(R.string.registration_completed) + " \nమీ ఇమెయిల్ : " + schools.getHeadMaster_Email();
                builder.setMessage(message);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), R.string.sending_final_email, Toast.LENGTH_LONG).show();
                        if (checkNetwork.isNetworkAvailable()) {
                            List<String> toEmailList = Arrays.asList(schools.getHeadMaster_Email(), getResources().getString(R.string.email_id_bcc));
                            new SendMailTask(getActivity(), 0, 11).execute(getResources().getString(R.string.email_id), getResources().getString(R.string.email_password), toEmailList, getResources().getString(R.string.email_subject), getResources().getString(R.string.email_body), path1, "Balotsav_" + schools.getCoordinator_PhNo() + ".pdf");
                        }
                    }
                });
                builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().onBackPressed();
                    }
                });
                builder.show();
                builder.setCancelable(false);
            }

        }
    }
}
