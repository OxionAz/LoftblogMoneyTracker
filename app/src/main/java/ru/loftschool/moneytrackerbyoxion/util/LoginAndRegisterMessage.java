package ru.loftschool.moneytrackerbyoxion.util;

import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.view.View;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.res.StringRes;
import ru.loftschool.moneytrackerbyoxion.rest.status.UserLoginStatus;

/**
 * Created by Александр on 04.10.2015.
 */

@EBean
public class LoginAndRegisterMessage {

    public static final int MESSAGE_USER = 0;
    public static final int MESSAGE_PASSWORD = 1;

    @StringRes
    String  reg_name_wrong, login_name_wrong, login_password_wrong, login_wrong, unknown_error;

    public void errorLoginMessage(String status, View view, Handler handler){

        Message msg = new Message();

        switch (status){
            case UserLoginStatus.STATUS_WRONG_LOGIN:
                msg.obj = login_name_wrong;
                msg.what = MESSAGE_USER;
                handler.sendMessage(msg);
                break;

            case UserLoginStatus.STATUS_WRONG_PASSWORD:
                msg.obj = login_password_wrong;
                msg.what = MESSAGE_PASSWORD;
                handler.sendMessage(msg);
                break;

            case UserLoginStatus.ERROR: Snackbar.make(view, login_wrong, Snackbar.LENGTH_SHORT).show(); break;

            default: Snackbar.make(view, unknown_error, Snackbar.LENGTH_SHORT).show();
        }
    }

    public void errorRegMessage(boolean flag, View view, Handler handler){

        Message msg = new Message();

        if(flag){
            msg.obj = reg_name_wrong;
            handler.sendMessage(msg);
        } else {
            Snackbar.make(view, unknown_error, Snackbar.LENGTH_SHORT).show();
        }
    }
}
