package ru.loftschool.moneytrackerbyoxion.ui.activity;

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
import android.widget.Toast;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;
import java.lang.ref.WeakReference;

import ru.loftschool.moneytrackerbyoxion.MoneyTrackerApp;
import ru.loftschool.moneytrackerbyoxion.R;
import ru.loftschool.moneytrackerbyoxion.rest.RestService;
import ru.loftschool.moneytrackerbyoxion.rest.models.login.UserRegisterModel;
import ru.loftschool.moneytrackerbyoxion.rest.status.UserRegisterStatus;
import ru.loftschool.moneytrackerbyoxion.util.LoginAndRegisterMessage;
import ru.loftschool.moneytrackerbyoxion.util.NetworkConnectionUtil;
import ru.loftschool.moneytrackerbyoxion.util.TextInputCheck;

/**
 * Created by Александр on 06.09.2015.
 */

@EActivity(R.layout.registration_activity)
public class RegistrationActivity extends AppCompatActivity {

    // to get fields in the main UI thread from background thread use Handler
    private final WeakRefHandler handler = new WeakRefHandler(this);
    private static final String LOG_TAG = "MainActivity";

    @ViewById
    View registration_content;

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

    @Click(R.id.registration_text_button)
    public  void addRegistrationButton(){
        hideKeyboard();
        if (check.inputValidation(etLogin,etPassword))
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

        if (UserRegisterStatus.STATUS_SUCCESS.equals(response.getStatus())){
            success();
            MoneyTrackerApp.setUserName(this, etLogin.getText().toString());
            Intent openActivityIntent = new Intent(RegistrationActivity.this, MainActivity_.class);
            startActivity(openActivityIntent);
            finish();
        } else if (UserRegisterStatus.STATUS_BISY.equals(response.getStatus())){
            message.errorRegMessage(true,registration_content,handler);
        } else {message.errorRegMessage(false,registration_content,handler);}
    }

    //to avoid "leak might occur" warning while using handler create custom static class WeakRefHandler
    private static class WeakRefHandler extends Handler {
        private final WeakReference<RegistrationActivity> mActivity;

        public WeakRefHandler(RegistrationActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            RegistrationActivity activity = mActivity.get();
            if (activity != null) {
                activity.etLogin.requestFocus();
                activity.etLogin.setError((String) msg.obj);
            }
        }
    }
}
