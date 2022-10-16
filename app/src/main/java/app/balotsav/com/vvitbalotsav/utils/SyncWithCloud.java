package app.balotsav.com.vvitbalotsav.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import app.balotsav.com.vvitbalotsav.R;
import app.balotsav.com.vvitbalotsav.model.Event;
import app.balotsav.com.vvitbalotsav.model.Schools;

public class SyncWithCloud {
    private final SharedPreferences pref;
    private final String school;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private Context context;
    private DbHelper helper;

    public SyncWithCloud(Context context) {
        this.context = context;
        helper = new DbHelper(context);

        FirebaseApp.initializeApp(context);
        database = FirebaseDatabase.getInstance();
        pref = context.getSharedPreferences("Balotsav", Context.MODE_PRIVATE);
        school = pref.getString("scode", "");
    }

    private void uploadToCloud(){
        reference = database.getReference().child("Events").child(school);
        reference.removeValue();
        ArrayList<Event> myEventList = helper.getAllEvents();
        Log.i("Info",""+myEventList.size());
        for(Event event:myEventList){
            reference = database.getReference().child("Events").child(school).child(event.getName());
            reference.setValue(event);
        }
        reference = database.getReference().child("Schools").child(school);
        reference.removeValue();
        Schools mySchool = helper.getSchool(school);
        reference.setValue(mySchool);


    }

    public void syncWithCloud(){
        if(new CheckNetwork(context).isNetworkAvailable()) {
            uploadToCloud();
            //Toast.makeText(context, "యాప్ సింక్ చేయబడుతోంది", Toast.LENGTH_LONG).show();
        }
        else{
            Log.i("Info",context.getString(R.string.check_connection));
            //Toast.makeText(context, R.string.check_connection, Toast.LENGTH_LONG).show();
        }
    }

}
