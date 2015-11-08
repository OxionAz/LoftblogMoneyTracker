package ru.loftschool.loftblogmoneytracker.util;


import android.content.Context;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.res.StringRes;
import ru.loftschool.loftblogmoneytracker.R;
import ru.loftschool.loftblogmoneytracker.database.models.Categories;

/**
 * Created by Александр on 04.10.2015.
 */

@EBean
public class TextInputCheck {

    @StringRes
    String reg_name_empty, reg_password_empty, category_name_empty,
            category_name_used, expense_name_empty, expense_sum_empty;

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

    public boolean addExpensesInputValidation(EditText etName, EditText etPrice, Spinner etCategory, Context context) {

        boolean isValid = true;

        if (etName.getText().toString().isEmpty()) {
            etName.setError(expense_name_empty);
            isValid = false;
        }
        if (etPrice.getText().toString().isEmpty()) {
            etPrice.setError(expense_sum_empty);
            isValid = false;
        }
        if (etCategory.getSelectedItem() == null) {
            Toast.makeText(context, "Список категорий пуст!", Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        return isValid;
    }
}
