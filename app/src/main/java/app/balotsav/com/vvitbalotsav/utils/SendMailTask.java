package app.balotsav.com.vvitbalotsav.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import app.balotsav.com.vvitbalotsav.R;
import app.balotsav.com.vvitbalotsav.activities.DashBoardActivity;

public class SendMailTask extends AsyncTask {

    private Activity sendMailActivity;
    // --Commented out by Inspection (22/10/19 3:40 PM):int code;
    int type;
    int code;
    ProgressDialog progressDialog;

    public SendMailTask(Activity activity, int code, int type) {
        sendMailActivity = activity;
        this.code = code;
        this.type = type;
        progressDialog = new ProgressDialog(activity);

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        String message;
        switch (code) {
            case 1:
                message = sendMailActivity.getResources().getString(R.string.sending_final_email);
                break;
            case 2:
                message = sendMailActivity.getResources().getString(R.string.sending_password);

            default: message = sendMailActivity.getResources().getString(R.string.loading_wait);
                break;
        }
        if (progressDialog != null || progressDialog.isShowing())
            progressDialog.dismiss();
        progressDialog.setMessage(message);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);

    }

    @Override
    protected Object doInBackground(Object... args) {
        try {
            GMail androidEmail;
            if (type == 10) {
                androidEmail = new GMail(args[0].toString(),
                        args[1].toString(), (List) args[2], args[3].toString(),
                        args[4].toString());
            } else {
                androidEmail = new GMail(args[0].toString(),
                        args[1].toString(), (List) args[2], args[3].toString(),
                        args[4].toString(), args[5].toString(), args[6].toString());
                androidEmail.addAttachment(args[5].toString());
            }
            androidEmail.createEmailMessage();
            androidEmail.sendEmail();
        } catch (Exception e) {
            Log.e("Error", e.toString());
        }
        return null;
    }


    @Override
    public void onPostExecute(Object result) {
        if (progressDialog != null || progressDialog.isShowing()) {
            progressDialog.dismiss();
            sendMailActivity.finish();
            Intent in = new Intent(sendMailActivity, DashBoardActivity.class);
            sendMailActivity.startActivity(in);
        }

    }

}