package ru.loftschool.loftblogmoneytracker.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;
import ru.loftschool.loftblogmoneytracker.MoneyTrackerApp;
import ru.loftschool.loftblogmoneytracker.R;
import ru.loftschool.loftblogmoneytracker.rest.RestService;
import ru.loftschool.loftblogmoneytracker.rest.models.UserLoginModel;
import ru.loftschool.loftblogmoneytracker.rest.status.UserLoginStatus;
import ru.loftschool.loftblogmoneytracker.util.NetworkConnectionUtil;

/**
 * Created by Александр on 06.09.2015.
 */

@EActivity(R.layout.login_activity)
public class LoginActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainActivity";

    @ViewById(R.id.login_content)
    View login_content;

    @ViewById
    Toolbar toolbar;

    @ViewById
    EditText etLogin, etPassword;

    @StringRes
    String reg_name_empty, reg_password_empty,
           no_internet_connection, login_used,
           reg_name_wrong, reg_password_wrong,
           reg_wrong, unknown_error;

    @AfterViews
    void ready() {
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.login_bar));
    }

    @Click(R.id.login_button)
    public  void loginButton(){
        hideKeyboard();
        if (inputValidation())
            if(NetworkConnectionUtil.isNetworkConnected(this)) {
                login();
            } else {
                Snackbar.make(login_content, no_internet_connection, Snackbar.LENGTH_SHORT).show();
            }

    }

    @Click(R.id.registration_button)
    public void addRegistration(){
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

    private boolean inputValidation() {

        boolean isValid = true;

        if (etLogin.getText().toString().isEmpty()) {
            etLogin.setError(reg_name_empty);
            isValid = false;
        }
        if (etPassword.getText().toString().isEmpty()) {
            etPassword.setError(reg_password_empty);
            isValid = false;
        }

        return isValid;
    }

    @UiThread
    protected void message(String status){
        switch (status){
            case UserLoginStatus.wrongLogin: etLogin.setError(reg_name_wrong); break;
            case UserLoginStatus.wrongPassword: etPassword.setError(reg_password_wrong); break;
            case UserLoginStatus.errorMessage: Snackbar.make(login_content, reg_wrong, Snackbar.LENGTH_SHORT).show(); break;
            case "unknown": Snackbar.make(login_content, unknown_error, Snackbar.LENGTH_SHORT).show(); break;
            default: break;
        }
    }

    @Background
    void login(){
        RestService restService = new RestService();
        UserLoginModel login = restService.login(etLogin.getText().toString(), etPassword.getText().toString());
        Log.d(LOG_TAG, "Status: " + login.getStatus() + ", ID: " + login.getId() + ", Token: " + login.getAuthToken());

        if (UserLoginStatus.success.equals(login.getStatus())){
            MoneyTrackerApp.setToken(this, login.getAuthToken());
            Intent openActivityIntent = new Intent(LoginActivity.this, MainActivity_.class);
            startActivity(openActivityIntent);
            finish();
        } else

        if (UserLoginStatus.wrongLogin.equals(login.getStatus())) {
            message(login.getStatus());
        } else

        if (UserLoginStatus.wrongPassword.equals(login.getStatus())) {
            message(login.getStatus());
        } else

        if (UserLoginStatus.errorMessage.equals(login.getStatus())) {
            message(login.getStatus());
        } else {message("unknown");}
    }
}
