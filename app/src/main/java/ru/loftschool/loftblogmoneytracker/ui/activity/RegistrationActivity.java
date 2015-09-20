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
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;
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
    String reg_name_empty, reg_password_empty, no_internet_connection, login_used, unknown_error;

    @OptionsItem(android.R.id.home)
    void back(){
        onBackPressed();
        finish();
    }

    @AfterViews
    void ready() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.registation_bar));
    }

    @Click(R.id.registration_button)
    public  void addRegistrationButton(){
        hideKeyboard();
        if (inputValidation())
            if(NetworkConnectionUtil.isNetworkConnected(this)) {
                registration();
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
    protected void message(boolean flag){
        if(flag){
            Snackbar.make(registration_content, login_used, Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(registration_content, unknown_error, Snackbar.LENGTH_SHORT).show();
        }
    }

    @UiThread
    protected void success(){
        Toast.makeText(this,
                "Логин: " +
                etLogin.getText().toString() +
                ", Пароль: " +
                etPassword.getText().toString(), Toast.LENGTH_SHORT).show();
    }

    @Background
    void registration(){
        RestService restService = new RestService();
        UserRegisterModel response = restService.register(etLogin.getText().toString(),etPassword.getText().toString());
        Log.d(LOG_TAG, "Status: " + response.getStatus() + ", ID: " + response.getId());

        if (UserRegisterStatus.success.equals(response.getStatus())){
            success();
            Intent openActivityIntent = new Intent(RegistrationActivity.this, MainActivity_.class);
            startActivity(openActivityIntent);
            finish();
        } else

        if (UserRegisterStatus.busy.equals(response.getStatus())) {
            message(true);
        } else {message(false);}
    }
}
