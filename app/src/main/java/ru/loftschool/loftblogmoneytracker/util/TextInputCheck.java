package ru.loftschool.loftblogmoneytracker.util;

import android.widget.EditText;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.res.StringRes;

/**
 * Created by Александр on 04.10.2015.
 */

@EBean
public class TextInputCheck {

    @StringRes
    String reg_name_empty, reg_password_empty;

    public boolean inputValidation(EditText login, EditText password) {

        boolean isValid = true;

        if (login.getText().toString().isEmpty()) {
            login.setError(reg_name_empty);
            isValid = false;
        }
        if (password.getText().toString().isEmpty()) {
            password.setError(reg_password_empty);
            isValid = false;
        }

        return isValid;
    }
}
