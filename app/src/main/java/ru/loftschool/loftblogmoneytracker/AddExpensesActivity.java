package ru.loftschool.loftblogmoneytracker;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.activeandroid.query.Select;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import ru.loftschool.loftblogmoneytracker.database.models.Categories;
import ru.loftschool.loftblogmoneytracker.database.models.Expenses;

/**
 * Created by Александр on 06.09.2015.
 */

@EActivity(R.layout.add_expenses)
public class AddExpensesActivity extends AppCompatActivity {

    @ViewById
    Toolbar toolbar;

    @ViewById
    EditText etPrice, etName;

    @ViewById
    Spinner etCategory;

    SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM y", myDateFormatSymbols);

    @OptionsItem(android.R.id.home)
    void back(){
        onBackPressed();
    }

    @AfterViews
    void ready(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.add_expenses));
        // адаптер
        ArrayAdapter<Categories> adapter = new ArrayAdapter<Categories>(this, android.R.layout.simple_spinner_item, getCategories());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        etCategory.setAdapter(adapter);
    }

    private List<Categories> getCategories(){
        return new Select().from(Categories.class).execute();
    }

    private static DateFormatSymbols myDateFormatSymbols = new DateFormatSymbols(){

        @Override
        public String[] getMonths() {
            return new String[]{"января", "февраля", "марта", "апреля", "мая", "июня",
                    "июля", "августа", "сентября", "октября", "ноября", "декабря"};
        }

    };

    @Click(R.id.add_expense_button)
    public  void addExpenseButton(){
        if (etName.getText().length() == 0 || etPrice.getText().length() == 0){
            Toast.makeText(this,"Не все поля заполнены!", Toast.LENGTH_SHORT).show();
        } else {
            new Expenses(
                    etName.getText().toString(),
                    etPrice.getText().toString(),
                    String.valueOf(dateFormat.format(new Date())),
                    (Categories)etCategory.getSelectedItem()).save();

            Toast.makeText(this,"Трата добавлена: "+
                    etName.getText().toString()+", "+
                    etPrice.getText().toString()+", "+
                    etCategory.getSelectedItem().toString()+", "+
                    String.valueOf(dateFormat.format(new Date())),
                    Toast.LENGTH_SHORT).show();

            etName.setText("");
            etPrice.setText("");
        }
    }
}
