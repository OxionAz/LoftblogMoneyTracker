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
import ru.loftschool.loftblogmoneytracker.ui.activity.MainActivity_;
import ru.loftschool.loftblogmoneytracker.R;
import ru.loftschool.loftblogmoneytracker.rest.RestService;
import ru.loftschool.loftblogmoneytracker.rest.models.UserRegisterModel;
import ru.loftschool.loftblogmoneytracker.rest.status.UserRegisterStatus;
import ru.loftschool.loftblogmoneytracker.util.NetworkConnectionUtil;

/**
 * Created by Александр on 06.09.2015.
 */

@EActivity(R.layout.registration_activity)
public class RegistrationActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainActivity";

    @ViewById
    View registration_content;

    @ViewById
    Toolbar toolbar;

    @ViewById
    EditText etLogin, etPassword;

    @StringRes
    String reg_name_empty, reg_password_empty, no_internet_connection, login_used;

    @AfterViews
    void ready() {
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.registation_bar));
    }

    @Click(R.id.registration_button)
    public  void addRegistrationButton(){
        hideKeyboard();
        if (inputValidation())
            if(NetworkConnectionUtil.isNetworkConnected(this)) {
                registrationTest();
            } else {
                Snackbar.make(registration_content, no_internet_connection, Snackbar.LENGTH_SHORT).show();
            }

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
    protected void message(){
        Snackbar.make(registration_content, login_used, Snackbar.LENGTH_SHORT).show();
    }

    @Background
    void registrationTest(){
        RestService restService = new RestService();
        UserRegisterModel response = restService.register(etLogin.getText().toString(),etPassword.getText().toString());
        Log.d(LOG_TAG, "Status: " + response.getStatus() + ", ID: " + response.getId());

        if (UserRegisterStatus.success.equals(response.getStatus())){
            Intent openActivityIntent = new Intent(RegistrationActivity.this, MainActivity_.class);
            startActivity(openActivityIntent);
            finish();
        }

        if (UserRegisterStatus.busy.equals(response.getStatus())) {
            message();
        }
    }
}
