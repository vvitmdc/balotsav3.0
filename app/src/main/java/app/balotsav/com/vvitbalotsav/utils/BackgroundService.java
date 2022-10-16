package app.balotsav.com.vvitbalotsav.utils;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import app.balotsav.com.vvitbalotsav.activities.LoginActivity;
import app.balotsav.com.vvitbalotsav.model.Schools;

public class BackgroundService extends Service {

    private boolean isRunning;
    private Context context;
    private Thread backgroundThread;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        this.context = this;
        this.isRunning = false;
        this.backgroundThread = new Thread(myTask);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        new SyncWithCloud(context).syncWithCloud();
    }

    private Runnable myTask = new Runnable() {
        public void run() {
            // Do something here

            Log.i("Info", "Updating Database");
//            Toast.makeText(context, "Updating Database", Toast.LENGTH_SHORT).show();
            stopSelf();
        }
    };

    @Override
    public void onDestroy() {
        this.isRunning = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!this.isRunning) {
            this.isRunning = true;
            this.backgroundThread.start();
        }
        return START_STICKY;
    }

}