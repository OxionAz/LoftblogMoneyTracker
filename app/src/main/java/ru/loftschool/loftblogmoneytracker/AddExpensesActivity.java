package ru.loftschool.loftblogmoneytracker;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Александр on 06.09.2015.
 */

@EActivity(R.layout.add_expenses)
public class AddExpensesActivity extends AppCompatActivity {

    @ViewById
    Toolbar toolbar;

    @OptionsItem(android.R.id.home)
    void back(){
        onBackPressed();
    }

    @AfterViews
    void ready(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.add_expenses));
    }
}
