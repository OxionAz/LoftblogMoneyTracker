package ru.loftschool.moneytrackerbyoxion.ui.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

import ru.loftschool.moneytrackerbyoxion.R;
import ru.loftschool.moneytrackerbyoxion.database.models.Categories;
import ru.loftschool.moneytrackerbyoxion.database.models.Expenses;
import ru.loftschool.moneytrackerbyoxion.ui.adapters.ExpensesAdapter;

/**
 * Created by Александр on 28.08.2015.
 */

@EActivity(R.layout.expenses_by_category_activity)
public class ExpensesByCategoryActivity extends AppCompatActivity {

    private ExpensesAdapter expensesAdapter;
    private static Categories category;

    @ViewById
    Toolbar toolbar;

    @ViewById(R.id.recycler_view_content)
    RecyclerView recyclerView;

    @OptionsItem(android.R.id.home)
    void back(){
        onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupWindowAnimations();
    }

    @AfterViews
    void ready(){
        initToolbar();
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        expensesAdapter = new ExpensesAdapter(category.expenses(), new ExpensesAdapter.CardViewHolder.ClickListener() {
            @Override
            public void OnItemClicked(int position) {
            }

            @Override
            public boolean OnItemLongClicked(int position) {
                return true;
            }
        });
        recyclerView.setAdapter(expensesAdapter);
    }

    private void initToolbar(){
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(category.category);
            actionBar.setSubtitle(getSum());
        }
    }

    public static void postCategory(Categories cat){
        category = cat;
    }

    private String getSum(){
        long sum = 0;
        for (Expenses expenses : category.expenses()) sum += Long.valueOf(expenses.sum);
        return String.valueOf(sum);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupWindowAnimations() {
        Fade fade = new Fade();
        fade.setDuration(1000);
        getWindow().setEnterTransition(fade);
    }
}