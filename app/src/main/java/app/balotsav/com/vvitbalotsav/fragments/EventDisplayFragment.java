package app.balotsav.com.vvitbalotsav.fragments;


import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import app.balotsav.com.vvitbalotsav.R;
import app.balotsav.com.vvitbalotsav.activities.DashBoardActivity;
import app.balotsav.com.vvitbalotsav.model.Event;
import app.balotsav.com.vvitbalotsav.model.Initialise;
import app.balotsav.com.vvitbalotsav.utils.EventAdapter;


public class EventDisplayFragment extends Fragment {

    RecyclerView recyclerView, recyclerView1;
    TextView solo, group;
    ArrayList<Event> singleEvent = new ArrayList<>();
    ArrayList<Event> groupEvent = new ArrayList<>();
    private View rootview;
    private SharedPreferences pref;

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

    Boolean value;

    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ActionBar actionBar = ((DashBoardActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.registration));
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);

        pref = getActivity().getSharedPreferences("Balotsav", 0);

        rootview = inflater.inflate(R.layout.fragment_event_display, container, false);
        // Inflate the layout for this fragment
        solo = rootview.findViewById(R.id.id_single_events);
        group = rootview.findViewById(R.id.id_group_events);
        recyclerView = rootview.findViewById(R.id.recycler);
        recyclerView1 = rootview.findViewById(R.id.recycler1);
        Initialise i = new Initialise();
        singleEvent = i.getSingleEvents();
        groupEvent = i.getGroupEvents();
        String soloText = " మొత్తం " + solo.getText().toString() + " సంఖ్య : " + singleEvent.size();
        String groupText = " మొత్తం " + group.getText().toString() + " సంఖ్య : " + groupEvent.size();
        solo.setText(soloText);
        group.setText(groupText);
        getTime();
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView1.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        return rootview;
    }

    public void getTime() {
        String ex_day = pref.getString("expiryDay", "");
        String today = pref.getString("today", "");
        String[] dte = ex_day.split("-");
        int d1 = Integer.parseInt(dte[2]);
        int d2 = Integer.parseInt(dte[1]);
        String[] dt = today.split("-");
        int d11 = Integer.parseInt(dt[2]);
        int d21 = Integer.parseInt(dt[1]);
        if (d21 <= d2 || d11 <= d1) {
            setValue(true);
        } else {
            Toast.makeText(getContext(), getString(R.string.time_exceeded), Toast.LENGTH_LONG).show();
            setValue(false);
        }
        recyclerView.setAdapter(new EventAdapter(getActivity(), singleEvent, getValue(), rootview.getContext()));
        recyclerView1.setAdapter(new EventAdapter(getActivity(), groupEvent, getValue(), rootview.getContext()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().getSupportFragmentManager().popBackStack();
                getActivity().getActionBar().setDisplayHomeAsUpEnabled(false);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
