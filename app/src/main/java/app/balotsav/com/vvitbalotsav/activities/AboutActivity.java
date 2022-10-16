package app.balotsav.com.vvitbalotsav.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

import app.balotsav.com.vvitbalotsav.R;

public class AboutActivity extends AppCompatActivity {
    TextView develop;
    TextView design;
    TextView develop2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        design = findViewById(R.id.design_text);
        develop = findViewById(R.id.develop_text);
        develop2 = findViewById(R.id.develop_text2);

        List<String> develop_team1 = Arrays.asList(getResources().getStringArray(R.array.develop_team_1));
        List<String> develop_team2 = Arrays.asList(getResources().getStringArray(R.array.develop_team2));
        List<String> design_team = Arrays.asList(getResources().getStringArray(R.array.designer_team1));

        develop.setText(buildString(develop_team1));
        develop2.setText(buildString(develop_team2));
        design.setText(buildString(design_team));
        LinearLayout lv = findViewById(R.id.linear);
        Animation animationup = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.text);
        animationup.setRepeatCount(Animation.INFINITE);
        animationup.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(getApplicationContext(), DashBoardActivity.class);
                        startActivity(i);
                        finish();
                    }
                }, 20000);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        lv.setAnimation(animationup);

    }

    public String buildString(List<String> data) {
        StringBuilder builder = new StringBuilder();
        for (String s : data) {
            builder.append(s);
            builder.append("\n");
        }
        return builder.toString();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), DashBoardActivity.class);
        startActivity(i);
        finish();
    }
}
