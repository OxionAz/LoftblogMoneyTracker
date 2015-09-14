package ru.loftschool.loftblogmoneytracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

/**
 * Created by Александр on 06.09.2015.
 */

@EActivity(R.layout.splash_screen)
public class SplashActivity extends Activity {

    @AfterViews
    void ready(){
        int secondsDelayed = 1;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity_.class));
                finish();
            }
        }, secondsDelayed * 2500);
    }

}
