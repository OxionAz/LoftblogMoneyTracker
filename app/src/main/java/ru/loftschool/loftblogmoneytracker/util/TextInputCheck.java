package ru.loftschool.loftblogmoneytracker.util;

import android.app.Activity;
import android.content.Context;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.res.StringRes;

import ru.loftschool.loftblogmoneytracker.R;
import ru.loftschool.loftblogmoneytracker.database.models.Categories;
import ru.loftschool.loftblogmoneytracker.ui.activity.MainActivity;

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

    public boolean inputAddCategoryValidation(EditText name, Context context) {

        boolean isValid = true;

        if (name.getText().toString().isEmpty()) {
            name.setError(category_name_empty);
            name.setAnimation(AnimationUtils.loadAnimation(context, R.anim.shake));
            isValid = false;
        }

        if (!Categories.selectByNameCaseInsensitive(name.getText().toString()).isEmpty()) {
            name.setError(category_name_used);
            name.setAnimation(AnimationUtils.loadAnimation(context, R.anim.shake));
            isValid = false;
        }

        return isValid;
    }
}
