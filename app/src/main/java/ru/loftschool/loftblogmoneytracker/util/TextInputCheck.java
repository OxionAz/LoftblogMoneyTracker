package ru.loftschool.loftblogmoneytracker.util;

import android.widget.EditText;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.res.StringRes;

import ru.loftschool.loftblogmoneytracker.database.models.Categories;

/**
 * Created by Александр on 04.10.2015.
 */

@EBean
public class TextInputCheck {

    @StringRes
    String reg_name_empty, reg_password_empty, category_name_empty, category_name_used;

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

    public boolean inputAddCategoryValidation(EditText name) {

        boolean isValid = true;

        if (name.getText().toString().isEmpty()) {
            name.setError(category_name_empty);
            isValid = false;
        }

        if (!Categories.selectByNameCaseInsensitive(name.getText().toString()).isEmpty()) {
            name.setError(category_name_used);
            isValid = false;
        }

        return isValid;
    }
}
