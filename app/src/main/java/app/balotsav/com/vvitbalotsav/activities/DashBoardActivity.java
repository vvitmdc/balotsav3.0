package app.balotsav.com.vvitbalotsav.activities;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import app.balotsav.com.vvitbalotsav.R;
import app.balotsav.com.vvitbalotsav.fragments.AnnouncementFragment;
import app.balotsav.com.vvitbalotsav.fragments.EventDisplayFragment;
import app.balotsav.com.vvitbalotsav.fragments.HomeFragment;
import app.balotsav.com.vvitbalotsav.fragments.RegisteredEventsFragment;
import app.balotsav.com.vvitbalotsav.fragments.ResultsFragment;
import app.balotsav.com.vvitbalotsav.fragments.RulesFragment;
import app.balotsav.com.vvitbalotsav.fragments.ScheduleFragment;
import app.balotsav.com.vvitbalotsav.model.Schools;
import app.balotsav.com.vvitbalotsav.utils.DbHelper;
import app.balotsav.com.vvitbalotsav.utils.SyncWithCloud;

public class DashBoardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String scode;
    private Schools schools;
    SharedPreferences pref;
    DrawerLayout layout;
    DbHelper helper;
    Boolean value;
    boolean doubleBackToExitPressedOnce = false;

public void setValue(Boolean value) {
      this.value = value;
  }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        Toolbar toolbar = findViewById(R.id.toolbar);
        layout = findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, new HomeFragment())
                .commit();
        pref = getSharedPreferences("Balotsav", 0);

        helper = new DbHelper(this);

        @SuppressLint("CutPasteId") DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.vvit_balotsav));

        Gson gson = new Gson();
        String json = getSharedPreferences("Balotsav", MODE_PRIVATE).getString("data", "");
        schools = gson.fromJson(json, Schools.class);
        NavigationView navigationView =  findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView school_name = headerView.findViewById(R.id.textView);
        TextView coordinator_name = headerView.findViewById(R.id.id_coordinator_name);
        school_name.setText(schools.getSchool_Name());
        coordinator_name.setText(schools.getCoordinator_Name());
        navigationView.setNavigationItemSelectedListener(this);
        getTime();


    }

    public void getTime() {
        final Date today = new Date();
        int today_date = today.getDate();
        int today_month = today.getMonth() + 1;
        int today_year = today.getYear() + 1900;
        String t_date = today_year + "-" + today_month + "-" + today_date;

        String result = pref.getString("expiryDay", "");
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("today", t_date);
        Log.i("Test date", t_date);
        Log.i("Test date", result);
        editor.apply();
        String[] dte = result.split("-");
        int d1 = Integer.parseInt(dte[2]);
        int d2 = Integer.parseInt(dte[1]);
        //noinspection unused
        int d3 = Integer.parseInt(dte[0]);
        if (today.getMonth() <= d2 || today.getDate() <= d1) {
            editor.putBoolean("value", false);
        } else {
            Toast.makeText(this, getString(R.string.time_exceeded), Toast.LENGTH_LONG).show();
            editor.putBoolean("value", true);
        }
        editor.apply();

        Log.i("Test", "After setting location");
        Log.i("Test: ", String.valueOf(pref.getBoolean("value", true)));
        String time = today_year + "-" + d2 + "-" + (d1-1) +" 00:52:20";
        Timer t=new Timer();
        try {
            t.schedule(new TimerTask() {
                public void run() {
                    new SyncWithCloud(getApplicationContext()).syncWithCloud();
                }
            }, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time));
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        //getActivity().onBackPressed();
    }

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (String child : children) {
                boolean success = deleteDir(new File(dir, child));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        deleteCache(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else if (!doubleBackToExitPressedOnce) {
            this.doubleBackToExitPressedOnce = true;
            showSnack();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else {
            super.onBackPressed();
            deleteCache(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dash_board, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            SharedPreferences pref = getSharedPreferences("Balotsav", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            String expiry_date = pref.getString("expiryDay", "");
            Log.i("Test-DeleteSchool"," "+helper.deleteSchool(pref.getString("scode", "")));
            Log.i("Test-DeleteEvent:"," "+helper.deleteAllEvents());
            editor.clear();
            editor.putString("expiryDay", expiry_date);
            editor.putInt("registered ", 0);
            editor.apply();
            FragmentManager fm = this.getSupportFragmentManager();
            for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                fm.popBackStack();
            }
            startActivity(new Intent(DashBoardActivity.this, LoginActivity.class));
            finish();
        } else if (id == R.id.action_edit) {
            startActivity(new Intent(DashBoardActivity.this, EditRegistrationActivity.class));
            //getData();
        } else if (id == R.id.action_rules) {
            Fragment fragment = new RulesFragment();
            loadFragment(fragment);
        } else if (id == R.id.ection_schedule) {
            Fragment fragment = new ScheduleFragment();
            loadFragment(fragment);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;
        if (id == R.id.nav_edit) {
            fragment = new RegisteredEventsFragment();

        } else if (id == R.id.nav_event) {
            fragment = new EventDisplayFragment();//vs
        } else if (id == R.id.nav_results) {
            fragment = new ResultsFragment();
        } else if (id == R.id.nav_notifications) {
            fragment = new AnnouncementFragment();
        } else if (id == R.id.nav_rules) {
            fragment = new RulesFragment();
        } else if (id == R.id.nav_home) {
            fragment = new HomeFragment();
            getTime();
        } else if (id == R.id.about_us) {
            startActivity(new Intent(this, AboutActivity.class));
            finish();
        } else if (id == R.id.sync) {
            new SyncWithCloud(this).syncWithCloud();
        } else if (id == R.id.id_photo_gallery) {
            startActivity(new Intent(this, GalleryActivity.class));
        } else if (id == R.id.id_video_gallery) {
            startActivity(new Intent(this, VideoActivity.class));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return loadFragment(fragment);
    }

    //vs
    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            // getTime();
            Log.i("BackStackCount:Support", String.valueOf(this.getSupportFragmentManager().getBackStackEntryCount()));
            Log.i("BackStackCount :", String.valueOf(this.getFragmentManager().getBackStackEntryCount()));
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack("fragment")
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }


    private void showSnack() {
        int color;
        String message = "యాప్ CLOSE చేయుటకు  దయచేసి తిరిగి BACK నొక్కండి";
        color = getResources().getColor(R.color.colorAccent);
        Snackbar snackbar = Snackbar.make(layout, message, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        TextView textView =  sbView.findViewById(R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();
    }
}


