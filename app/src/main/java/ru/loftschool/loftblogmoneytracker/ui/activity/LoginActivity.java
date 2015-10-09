package ru.loftschool.loftblogmoneytracker.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

import java.lang.ref.WeakReference;

import ru.loftschool.loftblogmoneytracker.MoneyTrackerApp;
import ru.loftschool.loftblogmoneytracker.R;
import ru.loftschool.loftblogmoneytracker.rest.RestService;
import ru.loftschool.loftblogmoneytracker.rest.models.UserLoginModel;
import ru.loftschool.loftblogmoneytracker.rest.status.UserLoginStatus;
import ru.loftschool.loftblogmoneytracker.util.LoginAndRegisterMessage;
import ru.loftschool.loftblogmoneytracker.util.NetworkConnectionUtil;
import ru.loftschool.loftblogmoneytracker.util.TextInputCheck;

/**
 * Created by Александр on 06.09.2015.
 */

@EActivity(R.layout.login_activity)
public class LoginActivity extends AppCompatActivity {

    // to get fields in the main UI thread from background thread use Handler
    private final WeakRefHandler handler = new WeakRefHandler(this);
    private static final String LOG_TAG = "MainActivity";

    @ViewById(R.id.login_content)
    View login_content;

    @ViewById
    Toolbar toolbar;

    @ViewById
    EditText etLogin, etPassword;

    @StringRes
    String no_internet_connection;

    @Bean
    TextInputCheck check;

    @Bean
    LoginAndRegisterMessage message;

    @AfterViews
    void ready() {
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.login_bar));
    }

    @Click(R.id.login_button)
    public void loginButton(){
        hideKeyboard();
        if (check.inputValidation(etLogin, etPassword))
            if(NetworkConnectionUtil.isNetworkConnected(this)) {
                login();
            } else {
                Snackbar.make(login_content, no_internet_connection, Snackbar.LENGTH_SHORT).show();
            }
    }

    @Click(R.id.registration_button)
    public void addRegistration() {
        Intent openActivityRegistrationIntent = new Intent(LoginActivity.this, RegistrationActivity_.class);
        startActivity(openActivityRegistrationIntent);
    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Background
    void login(){
        RestService restService = new RestService();
        UserLoginModel login = restService.login(etLogin.getText().toString(), etPassword.getText().toString());
        Log.d(LOG_TAG, "Status: " + login.getStatus() + ", ID: " + login.getId() + ", Token: " + login.getAuthToken());

        if (UserLoginStatus.STATUS_SUCCESS.equals(login.getStatus())){
            MoneyTrackerApp.setToken(this, login.getAuthToken());
            Intent openActivityIntent = new Intent(LoginActivity.this, MainActivity_.class);
            startActivity(openActivityIntent);
            finish();
        } else {message.errorLoginMessage(login.getStatus(), login_content, handler);}
    }

    //to avoid "leak might occur" warning while using handler create custom static class WeakRefHandler
    private static class WeakRefHandler extends Handler {
        private final WeakReference<LoginActivity> mActivity;

        public WeakRefHandler(LoginActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            LoginActivity activity = mActivity.get();
            if (activity != null) {
                if (msg.what == LoginAndRegisterMessage.MESSAGE_USER) {
                    activity.etLogin.requestFocus();
                    activity.etLogin.setError((String) msg.obj);
                } else {
                    activity.etPassword.requestFocus();
                    activity.etPassword.setError((String) msg.obj);
                }
            }
        }
    }
}
