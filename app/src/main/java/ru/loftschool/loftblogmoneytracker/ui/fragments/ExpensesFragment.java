package ru.loftschool.loftblogmoneytracker.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.activeandroid.query.Select;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.api.BackgroundExecutor;
import java.util.ArrayList;
import java.util.List;
import ru.loftschool.loftblogmoneytracker.ui.activity.AddExpensesActivity_;
import ru.loftschool.loftblogmoneytracker.R;
import ru.loftschool.loftblogmoneytracker.database.models.Expenses;
import ru.loftschool.loftblogmoneytracker.ui.activity.MainActivity;
import ru.loftschool.loftblogmoneytracker.ui.adapters.ExpensesAdapter;

/**
 * Created by Александр on 28.08.2015.
 */

@EFragment(R.layout.expenses_fragment)
@OptionsMenu(R.menu.search_menu)
public class ExpensesFragment extends Fragment {

    private ActionModeCallback actionModeCallback = new ActionModeCallback();
    private static ActionMode actionMode;
    private ExpensesAdapter expensesAdapter;
    private final static String FILTER_ID = "filter_id";

    @ViewById(R.id.recycler_view_content)
    RecyclerView recyclerView;

    @ViewById(R.id.fab)
    FloatingActionButton fab;

    @ViewById(R.id.refresh_expenses)
    SwipeRefreshLayout swipeRefreshLayout;

    @OptionsMenuItem(R.id.search_actionbar)
    MenuItem menuItem;

    @StringRes
    String search_hint;

    @Click
    void fab() {
        Intent openActivityIntent = new Intent(getActivity(), AddExpensesActivity_.class);
        getActivity().startActivity(openActivityIntent);
        getActivity().overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
    }

    @AfterViews
    void ready(){
        getActivity().setTitle(getResources().getString(R.string.nav_drawer_expenses));
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        swipeRefreshLayout.setColorSchemeColors(R.color.primary, R.color.black, R.color.primary_selected);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData("");
            }
        });
        recyclerView.setLayoutManager(linearLayoutManager);
        if (!getDataList(null).isEmpty())
            for (Expenses expense : getDataList(null)) Log.d("Category: ", String.valueOf(expense.category));
    }

    public static ActionMode getActionMode(){
        return actionMode;
    }

    public static void finishActionMode(){
        actionMode.finish();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        final android.widget.SearchView searchView = (android.widget.SearchView) menuItem.getActionView();
        searchView.isActivated();
        searchView.setQueryHint(search_hint);
        searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("TextSubmit: ", query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("TextChange: ", newText);
                BackgroundExecutor.cancelAll(FILTER_ID, true);
                delayedSearch(newText);
                return false;
            }
        });
    }

    @Background(delay = 600, id = FILTER_ID)
    void delayedSearch(String filter){
        loadData(filter);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData("");

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT){
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if (actionMode != null) actionMode.finish();
                List<Integer> item = new ArrayList<>(1);
                item.add(viewHolder.getAdapterPosition());
                expensesAdapter.removeItems(item);
                notifyDelete(item);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void notifyDelete(final List<Integer> positions){
        Snackbar.make(recyclerView, expensesAdapter.getSelectedItemsCount() <= 1 ? "Трата удалена " : "Траты удалены", Snackbar.LENGTH_LONG)
                .setAction("Восстановить", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        expensesAdapter.restoreItem(positions);
                    }
                })
                .show();
    }

    private void loadData(final String filter) {
        getLoaderManager().restartLoader(0, null, new LoaderManager.LoaderCallbacks<List<Expenses>>() {
            @Override
            public Loader<List<Expenses>> onCreateLoader(int id, Bundle args) {
                final android.support.v4.content.AsyncTaskLoader<List<Expenses>> loader = new android.support.v4.content.AsyncTaskLoader<List<Expenses>>(getContext()) {
                    @Override
                    public List<Expenses> loadInBackground() {
                        return getDataList(filter);
                    }
                };
                loader.forceLoad();
                return loader;
            }

            @Override
            public void onLoadFinished(Loader<List<Expenses>> loader, List<Expenses> data) {
                swipeRefreshLayout.setRefreshing(false);
                expensesAdapter = new ExpensesAdapter(getDataList(filter), new ExpensesAdapter.CardViewHolder.ClickListener() {
                    @Override
                    public void OnItemClicked(int position) {
                        if (actionMode != null) {
                            toggleSelection(position);
                        }
                    }

                    @Override
                    public boolean OnItemLongClicked(int position) {
                        if (actionMode == null) {
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
                    notifyDelete(expensesAdapter.getSelectedItems());
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

    private List<Expenses> getDataList(String filter){
        return new Select()
                .from(Expenses.class)
                .where("name LIKE ?", new String[]{'%' + filter + '%'})
                .orderBy("date DESC")
                .execute();
    }
}
