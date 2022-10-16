package app.balotsav.com.vvitbalotsav.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class GrantPermissions {
    Context c;

    public GrantPermissions(Context c) {
        this.c = c;
    }

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    public boolean checkAndRequestPermissions() {
        int internet = ContextCompat.checkSelfPermission(c, Manifest.permission.INTERNET);
        int storageWrite = ContextCompat.checkSelfPermission(c, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int storageRead = ContextCompat.checkSelfPermission(c, Manifest.permission.READ_EXTERNAL_STORAGE);
        int coarseLocation = ContextCompat.checkSelfPermission(c, Manifest.permission.ACCESS_COARSE_LOCATION);
        int networkState = ContextCompat.checkSelfPermission(c, Manifest.permission.ACCESS_NETWORK_STATE);
        int fineLocation = ContextCompat.checkSelfPermission(c, Manifest.permission.ACCESS_FINE_LOCATION);
        int readPhoneState = ContextCompat.checkSelfPermission(c,Manifest.permission.READ_PHONE_STATE);
        int wakeLock = ContextCompat.checkSelfPermission(c,Manifest.permission.WAKE_LOCK);
        List<String> listPermissionsNeeded = new ArrayList<>();

       if (internet != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.INTERNET);

        } else
            Log.i("permission", "internet");
        if (storageWrite != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

        } else
            Log.i("permission", "write external");
        if (storageRead != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.READ_EXTERNAL_STORAGE);

        } else
            Log.i("permission", "read external");
        if (coarseLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);

        } else
            Log.i("permission", "coarse Location");
        if (networkState != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_NETWORK_STATE);

        } else
            Log.i("permission", "network state");
        if (fineLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);

        } else
            Log.i("permission", "fine location");
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions((Activity) c, listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;

        }
    }


