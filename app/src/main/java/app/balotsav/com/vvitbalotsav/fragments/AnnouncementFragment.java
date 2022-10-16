package app.balotsav.com.vvitbalotsav.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

import app.balotsav.com.vvitbalotsav.R;
import app.balotsav.com.vvitbalotsav.activities.DashBoardActivity;
import app.balotsav.com.vvitbalotsav.model.Announcements;
import app.balotsav.com.vvitbalotsav.utils.AnnouncementAdapter;
import app.balotsav.com.vvitbalotsav.utils.CheckNetwork;


public class AnnouncementFragment extends Fragment {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ArrayList<Announcements> arrayList;
    RecyclerView recyclerView;
    Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ActionBar actionBar = ((DashBoardActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.announcements));
        final View rootview = inflater.inflate(R.layout.fragment_announcement, container, false);
        arrayList = new ArrayList<>();
        context = getActivity();
        final AnnouncementAdapter announcementAdapter = new AnnouncementAdapter();
        recyclerView = rootview.findViewById(R.id.announce);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        if(new CheckNetwork(getContext()).isNetworkAvailable()) {
            databaseReference.child("Announcements").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        if (dataSnapshot1.getChildrenCount() != 0)
                            arrayList.add(dataSnapshot1.getValue(Announcements.class));
                    }
                    Collections.reverse(arrayList);
                    announcementAdapter.setArrayList(arrayList);
                    recyclerView.setAdapter(announcementAdapter);
                    recyclerView.smoothScrollToPosition(0);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else
        {
            Toast.makeText(getContext(),R.string.check_connection,Toast.LENGTH_SHORT).show();
        }
        return rootview;
    }

}
