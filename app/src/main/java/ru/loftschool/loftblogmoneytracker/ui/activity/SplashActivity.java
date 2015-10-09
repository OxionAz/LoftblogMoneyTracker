package ru.loftschool.loftblogmoneytracker.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import ru.loftschool.loftblogmoneytracker.MoneyTrackerApp;
import ru.loftschool.loftblogmoneytracker.R;

/**
 * Created by Александр on 06.09.2015.
 */

@EActivity(R.layout.splash_screen)
public class SplashActivity extends Activity {

    @AfterViews
    void ready(){
        final String token = MoneyTrackerApp.getToken(this);
        final int secondsDelayed = 1;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if(token.equals("1")){
                    startActivity(new Intent(SplashActivity.this, LoginActivity_.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashActivity.this, MainActivity_.class));
                    finish();
                }
            }
        }, secondsDelayed * 2500);
    }

}
