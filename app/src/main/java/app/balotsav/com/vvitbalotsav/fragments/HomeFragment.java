package app.balotsav.com.vvitbalotsav.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.balotsav.com.vvitbalotsav.R;
import app.balotsav.com.vvitbalotsav.activities.DashBoardActivity;
import app.balotsav.com.vvitbalotsav.utils.FirebaseHelper;


public class HomeFragment extends Fragment {


    ImageView er, reg, anounce, res;
    private SliderLayout sliderLayout;
    TextView view;
    private HashMap<String, String> Hash_file_maps;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ActionBar actionBar = ((DashBoardActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.vvit_balotsav));
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        View rootview = inflater.inflate(R.layout.fragment_home, container, false);

        // Inflate the layout for this fragment
        sliderLayout = rootview.findViewById(R.id.slider);
        Hash_file_maps = new HashMap<>();
        er = rootview.findViewById(R.id.e_register);
        reg = rootview.findViewById(R.id.e_Registered);
        anounce = rootview.findViewById(R.id.announce);
        res = rootview.findViewById(R.id.results);
        view = rootview.findViewById(R.id.id_scrolling_text);
        view.setSelected(true);
        new FirebaseHelper(getContext()).getAnnouncements(view);
        er.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new EventDisplayFragment();
                loadFragment(fragment);

            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new RegisteredEventsFragment();
                loadFragment(fragment);
            }
        });
        anounce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new AnnouncementFragment();
                loadFragment(fragment);
            }
        });
        res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ResultsFragment();
                loadFragment(fragment);
            }
        });

        showSlideShow();


        return rootview;
    }

    private void showSlideShow() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("photos");
        final List<String> urlList = new ArrayList<>();

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot d:dataSnapshot.getChildren()){
                    String link = d.getValue(String.class);
                    Log.i("Test:Image",link);
                    urlList.add(link);
                }
                for (int i = 1; i < urlList.size(); i++) {
                    Hash_file_maps.put(String.valueOf(i), urlList.get(i));
                }
                for (String name : Hash_file_maps.keySet()) {
                    TextSliderView textSliderView = new TextSliderView(getActivity());
                    textSliderView
                            .image(Hash_file_maps.get(name))
                            .setScaleType(BaseSliderView.ScaleType.Fit);
                    sliderLayout.addSlider(textSliderView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Stack);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setDuration(3000);
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getActivity().getSupportFragmentManager().popBackStack();
            getFragmentManager()
                    .beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
