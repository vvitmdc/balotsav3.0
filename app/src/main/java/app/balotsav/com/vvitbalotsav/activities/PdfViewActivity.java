package app.balotsav.com.vvitbalotsav.activities;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import app.balotsav.com.vvitbalotsav.R;
import app.balotsav.com.vvitbalotsav.model.Schools;
import app.balotsav.com.vvitbalotsav.utils.DbHelper;
import app.balotsav.com.vvitbalotsav.utils.SendMailTask;

public class PdfViewActivity extends AppCompatActivity {
    DbHelper helper;
    FloatingActionButton email;
    private Schools schools;
    private String path1;

    @Override
    public void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdfview);
        helper = new DbHelper(this);
        schools = helper.getSchool(getSharedPreferences("Balotsav", MODE_PRIVATE).getString("scode", "").replaceAll("/", "@"));
        PDFView pdfView = findViewById(R.id.pdfView);
        path1 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Balotsav_" + schools.getCoordinator_PhNo() + ".pdf";
        Log.i("path", path1);
        final File file = new File(path1);
        pdfView.fromFile(file).load();

        Gson gson = new Gson();
        String json = getSharedPreferences("Balotsav", MODE_PRIVATE).getString("data", " ");
        //noinspection unused
        final Schools s = gson.fromJson(json, Schools.class);

        email = findViewById(R.id.id_email_fab);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PdfViewActivity.this, "Emailing....", Toast.LENGTH_SHORT).show();
                try {
                    path1 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Balotsav_" + schools.getCoordinator_PhNo() + ".pdf";
                    List<String> toEmailList = Arrays.asList(schools.getHeadMaster_Email(), getResources().getString(R.string.email_id_bcc));
                    new SendMailTask(PdfViewActivity.this, 1, 11).execute(getResources().getString(R.string.email_id), getResources().getString(R.string.email_password), toEmailList, getResources().getString(R.string.email_subject), getResources().getString(R.string.email_body), path1, "Balotsav_" + schools.getCoordinator_PhNo() + ".pdf");
                } catch (Exception e) {
                    System.out.println("is exception raises during sending mail" + e);
                }
            }
        });

    }
}