package ru.loftschool.loftblogmoneytracker;

import android.content.AsyncTaskLoader;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.raizlabs.android.dbflow.sql.language.Select;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.loftschool.loftblogmoneytracker.database.models.Expenses;

/**
 * Created by Александр on 28.08.2015.
 */

@EFragment(R.layout.expenses_fragment)
public class ExpensesFragment extends Fragment {

    private ExpensesAdapter expensesAdapter;

    @ViewById(R.id.recycler_view_content)
    RecyclerView recyclerView;

    @ViewById(R.id.fab)
    FloatingActionButton fab;

    @Click
    void fab() {
        Intent openActivityIntent = new Intent(getActivity(), AddExpensesActivity_.class);
        getActivity().startActivity(openActivityIntent);
    }

    @AfterViews
    void ready(){
        getActivity().setTitle(getResources().getString(R.string.nav_drawer_expenses));
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        expensesAdapter = new ExpensesAdapter(getDataList());
        recyclerView.setAdapter(expensesAdapter);
        Snackbar.make(recyclerView, getActivity().getTitle() +" pressed", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(0, null, new LoaderManager.LoaderCallbacks<List<Expenses>>(){
            @Override
            public Loader<List<Expenses>> onCreateLoader(int id, Bundle args) {
                final android.support.v4.content.AsyncTaskLoader<List<Expenses>> loader = new android.support.v4.content.AsyncTaskLoader<List<Expenses>>(getContext()) {
                    @Override
                    public List<Expenses> loadInBackground() {
                        return getDataList();
                    }
                };
                loader.forceLoad();
                return loader;
            }

            @Override
            public void onLoadFinished(Loader<List<Expenses>> loader, List<Expenses> data) {
                recyclerView.setAdapter(new ExpensesAdapter(getDataList()));
            }

            @Override
            public void onLoaderReset(Loader<List<Expenses>> loader) {

            }
        });
    }

    private List<Expenses> getDataList(){

        return new Select().from(Expenses.class).queryList();
    }
}
