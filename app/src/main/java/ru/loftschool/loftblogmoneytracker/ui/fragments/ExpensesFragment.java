package ru.loftschool.loftblogmoneytracker.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.activeandroid.query.Select;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import java.util.List;
import ru.loftschool.loftblogmoneytracker.ui.activity.AddExpensesActivity_;
import ru.loftschool.loftblogmoneytracker.R;
import ru.loftschool.loftblogmoneytracker.database.models.Expenses;
import ru.loftschool.loftblogmoneytracker.ui.adapters.ExpensesAdapter;

/**
 * Created by Александр on 28.08.2015.
 */

@EFragment(R.layout.expenses_fragment)
public class ExpensesFragment extends Fragment {

    private ActionModeCallback actionModeCallback = new ActionModeCallback();
    private ActionMode actionMode;
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
        if (!getDataList().isEmpty())
            for (Expenses expense : getDataList()) Log.d("Category: ", String.valueOf(expense.category));
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
                expensesAdapter = new ExpensesAdapter(getDataList(), new ExpensesAdapter.CardViewHolder.ClickListener() {
                    @Override
                    public void OnItemClicked(int position) {
                        if(actionMode != null){
                            toggleSelection(position);
                        }
                    }

                    @Override
                    public boolean OnItemLongClicked(int position) {
                        if (actionMode == null){
                            AppCompatActivity activity = (AppCompatActivity) getActivity();
                            actionMode = activity.startSupportActionMode(actionModeCallback);
                        }
                        toggleSelection(position);
                        return true;
                    }
                });
                recyclerView.setAdapter(expensesAdapter);
            }

            @Override
            public void onLoaderReset(Loader<List<Expenses>> loader) {
            }
        });
    }

    private void toggleSelection(int position){
        expensesAdapter.toggleSelection(position);
        int count = expensesAdapter.getSelectedItemsCount();
        if (count == 0){
            actionMode.finish();
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
    }

    private List<Expenses> getDataList(){
        return new Select().from(Expenses.class).execute();
    }

    private class ActionModeCallback implements ActionMode.Callback{

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.cab, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()){
                case R.id.menu_remove:
                    expensesAdapter.removeItems(expensesAdapter.getSelectedItems());
                    mode.finish();
                    return true;
                case R.id.menu_select_all:
                    if (expensesAdapter.getItemCount() != expensesAdapter.getSelectedItemsCount()) {
                        expensesAdapter.SelectedAll(expensesAdapter.getItemCount());
                        actionMode.setTitle(String.valueOf(expensesAdapter.getSelectedItemsCount()));
                        actionMode.invalidate();
                    } else {
                        expensesAdapter.clearSelection();
                        mode.finish();
                    }
                    return true;
                default: return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            expensesAdapter.clearSelection();
            actionMode = null;
        }
    }
}
