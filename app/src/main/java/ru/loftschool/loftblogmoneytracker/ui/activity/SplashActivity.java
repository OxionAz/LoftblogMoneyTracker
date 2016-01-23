package ru.loftschool.loftblogmoneytracker.ui.activity;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.AccountPicker;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import java.io.IOException;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import ru.loftschool.loftblogmoneytracker.MoneyTrackerApp;
import ru.loftschool.loftblogmoneytracker.R;
import ru.loftschool.loftblogmoneytracker.rest.RestClient;
import ru.loftschool.loftblogmoneytracker.rest.models.GoogleTokenStatusModel;
import ru.loftschool.loftblogmoneytracker.sync.TrackerSyncAdapter;
import ru.loftschool.loftblogmoneytracker.util.AddDefaultCategories;
import ru.loftschool.loftblogmoneytracker.util.NetworkConnectionUtil;

/**
 * Created by Александр on 06.09.2015.
 */

@EActivity(R.layout.splash_screen)
public class SplashActivity extends Activity {

    private RestClient restClient;
    private String token;
    private String googleToken;

    private final static String G_PLUS_SCOPE =
            "oauth2:https://www.googleapis.com/auth/plus.me";
    private final static String USERINFO_SCOPE =
            "https://www.googleapis.com/auth/userinfo.profile";
    private final static String EMAIL_SCOPE =
            "https://www.googleapis.com/auth/userinfo.email";
    public final static String SCOPES = G_PLUS_SCOPE + " " + USERINFO_SCOPE + " " + EMAIL_SCOPE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TrackerSyncAdapter.initializeSyncAdapter(this);
    }

    @AfterViews
    void ready(){
        AddDefaultCategories.setCategories();
        token = MoneyTrackerApp.getToken(this);
        googleToken = MoneyTrackerApp.getGoogleToken(this);
        restClient = new RestClient();

        new Handler().postDelayed(new Runnable() {
            public void run(){

        if(token.equals("1") && googleToken.equals("1")){
            startActivity(new Intent(SplashActivity.this, LoginActivity_.class));
            finish();
        } else if (!token.equals("1")) {
            startMainActivity();
        } else {
            if (NetworkConnectionUtil.isNetworkConnected(getApplicationContext())){
            checkGoogleTokenValid();
            } else startMainActivity();
        }
            }
        }, 2000);
    }

    @Background
    void checkGoogleTokenValid(){
        restClient.getCheckGoogleTokenAPI().tokenStatus(googleToken, new Callback<GoogleTokenStatusModel>() {
            @Override
            public void success(GoogleTokenStatusModel googleTokenStatusModel, Response response) {
                Log.e("TokenStatus:", googleTokenStatusModel.getStatus());
                if (googleTokenStatusModel.getStatus().equals("success")){
                    startMainActivity();
                } else {
                    doubleTokenCheck();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                doubleTokenCheck();
            }
        });
    }

    private void doubleTokenCheck() {
        Intent intent = AccountPicker.
                newChooseAccountIntent(null, null, new String[]{"com.google"}, false, null, null, null, null);
        startActivityForResult(intent, 11);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 11 && resultCode == RESULT_OK){
            getToken(data);
        }
    }

    @Background
    void getToken(Intent data){
        final String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        String googleToken = null;
        try {
            googleToken = GoogleAuthUtil.getToken(this, accountName, SCOPES);
        } catch (IOException e) {
            Log.d("LOG_TAG", "WTF? " + e);
        } catch (final UserRecoverableAuthException e) {
            runOnUiThread(new Runnable() {
                public void run() {
                    startActivityForResult(e.getIntent(), 123);
                }
            });
        } catch (GoogleAuthException e) {
            e.printStackTrace();
        }

        MoneyTrackerApp.setGoogleToken(this, googleToken);
        String googleShareToken = MoneyTrackerApp.getGoogleToken(this);
        Log.d("LOG_TAG", "GoogleToken: " + googleShareToken);

        startMainActivity();
    }

    void startMainActivity() {
        startActivity(new Intent(SplashActivity.this, MainActivity_.class));
        finish();
    }
}
