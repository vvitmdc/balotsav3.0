package app.balotsav.com.vvitbalotsav.utils;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import app.balotsav.com.vvitbalotsav.model.Announcements;
import app.balotsav.com.vvitbalotsav.model.Event;

public class FirebaseHelper {
    private DatabaseReference reference;
    private DbHelper helper;

    public FirebaseHelper(Context context) {
        helper = new DbHelper(context);
    }



    public void getEventList(String sCode){
        reference = FirebaseDatabase.getInstance().getReference("Events").child(sCode);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    if (d.getChildrenCount() != 0) {
                        Event e = d.getValue(Event.class);
                        helper.addEvent(e);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getAnnouncements(final TextView view){
        reference = FirebaseDatabase.getInstance().getReference("Announcements");
        reference.addValueEventListener(new ValueEventListener() {
            private Announcements announcements;

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    announcements = dataSnapshot1.getValue(Announcements.class);
                    view.setText(announcements.getNotice());
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
