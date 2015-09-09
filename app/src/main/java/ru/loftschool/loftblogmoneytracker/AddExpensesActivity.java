package ru.loftschool.loftblogmoneytracker;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

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

    String[] data = {"Fun", "Social", "Clothes", "Food", "five"};

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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        etCategory.setAdapter(adapter);
        etCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "positions: "+position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Click(R.id.add_expense_button)
    public  void addExpenseButton(){

        Expenses expenses = new Expenses();
        expenses.setName(etName.getText().toString());
        expenses.setPrice(etPrice.getText().toString());
        expenses.insert();

        Toast.makeText(this,
                etName.getText().toString()+
                ", "+etPrice.getText().toString(),
                Toast.LENGTH_SHORT).show();
    }
}
