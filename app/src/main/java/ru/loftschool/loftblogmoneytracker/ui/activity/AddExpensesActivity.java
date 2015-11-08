package ru.loftschool.loftblogmoneytracker.ui.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.activeandroid.query.Select;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import ru.loftschool.loftblogmoneytracker.R;
import ru.loftschool.loftblogmoneytracker.database.models.Categories;
import ru.loftschool.loftblogmoneytracker.database.models.Expenses;
import ru.loftschool.loftblogmoneytracker.util.TextInputCheck;

/**
 * Created by Александр on 06.09.2015.
 */

@EActivity(R.layout.add_expenses)
public class AddExpensesActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private Date currentDate;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM y", myDateFormatSymbols);
    private static DateFormatSymbols myDateFormatSymbols = new DateFormatSymbols(){

        @Override
        public String[] getMonths() {
            return new String[]{"января", "февраля", "марта", "апреля", "мая", "июня",
                    "июля", "августа", "сентября", "октября", "ноября", "декабря"};
        }

    };

    @ViewById
    Toolbar toolbar;

    @ViewById
    EditText etPrice, etName;

    @ViewById
    TextView etDate;

    @ViewById
    Spinner etCategory;

    @ViewById(R.id.add_expense_card)
    CardView cardView;

    @Bean
    TextInputCheck check;

    @AfterViews
    void ready(){
        initToolbar();
        currentDate = new Date();
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_down_card);
        cardView.setAnimation(animation);
        ArrayAdapter<Categories> adapter = new ArrayAdapter<Categories>(this, android.R.layout.simple_spinner_item, getCategories());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        etCategory.setAdapter(adapter);
    }

    private void initToolbar(){
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            setTitle(getString(R.string.add_expenses));
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
        this.currentDate = new Date(year-1900,monthOfYear,dayOfMonth);
        etDate.setText(date);
    }

    @Click(R.id.date_picker_day)
    public void setData(){
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.vibrate(false);
        dpd.setMaxDate(now);
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    @Click(R.id.add_expense_button)
    public  void addExpenseButton(){
        if (check.addExpensesInputValidation(etName, etPrice, etCategory, this)) {
            new Expenses(etName.getText().toString(), etPrice.getText().toString(), currentDate,
                    (Categories) etCategory.getSelectedItem()).save();
            Toast.makeText(this, "Трата добавлена: " +
                            etName.getText().toString() + ", " +
                            etPrice.getText().toString() + ", " +
                            etCategory.getSelectedItem().toString() + ", " +
                            String.valueOf(dateFormat.format(currentDate)),
                    Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
    }

    @OptionsItem(android.R.id.home)
    void back(){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity_.class));
        finish();
    }

    private List<Categories> getCategories(){
        return new Select().from(Categories.class).execute();
    }
}
