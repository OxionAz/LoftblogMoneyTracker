package ru.loftschool.loftblogmoneytracker.ui.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.activeandroid.query.Select;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.ViewById;
import java.util.List;
import ru.loftschool.loftblogmoneytracker.R;
import ru.loftschool.loftblogmoneytracker.database.models.Categories;
import ru.loftschool.loftblogmoneytracker.database.models.Expenses;
import ru.loftschool.loftblogmoneytracker.ui.adapters.ExpensesAdapter;

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
            actionBar.setSubtitle(getSumm());
        }
    }

    public static void postCategory(Categories cat){
        category = cat;
    }

    private String getSumm(){
        long sum = 0;
        for (Expenses expenses : category.expenses()) sum += Long.valueOf(expenses.sum);
        return String.valueOf(sum);
    }
}