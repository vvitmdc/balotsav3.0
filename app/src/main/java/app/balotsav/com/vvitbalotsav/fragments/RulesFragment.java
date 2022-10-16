package app.balotsav.com.vvitbalotsav.fragments;


import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

import app.balotsav.com.vvitbalotsav.R;


public class RulesFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_rules, container, false);
        // Inflate the layout for this fragment

        try {
            PDFView pdfView = rootview.findViewById(R.id.pdfView1);
            File file = new File(Environment.getExternalStorageDirectory().toString() + "/rules.pdf");
            pdfView.fromFile(file)
                    .enableSwipe(true)
                    .load();
        } catch (Exception e) {
            Log.e("Error", "brochure view problem");
        }

        return rootview;
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