package ru.loftschool.loftblogmoneytracker.ui.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.activeandroid.query.Select;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

import java.util.ArrayList;
import java.util.List;
import ru.loftschool.loftblogmoneytracker.R;
import ru.loftschool.loftblogmoneytracker.database.models.Categories;
import ru.loftschool.loftblogmoneytracker.ui.activity.ExpensesByCategoryActivity;
import ru.loftschool.loftblogmoneytracker.ui.activity.ExpensesByCategoryActivity_;
import ru.loftschool.loftblogmoneytracker.ui.adapters.CategoriesAdapter;
import ru.loftschool.loftblogmoneytracker.util.TextInputCheck;

/**
 * Created by Александр on 01.09.2015.
 */
@EFragment(R.layout.categories_fragment)
public class CategoriesFragment extends Fragment {

    private static ActionMode actionMode;
    private ActionModeCallback actionModeCallback = new ActionModeCallback();
    private CategoriesAdapter categoriesAdapter;

    @ViewById(R.id.recycler_view_content)
    RecyclerView recyclerView;

    @ViewById(R.id.fab)
    FloatingActionButton fab;

    @StringRes
    String category_add_added_text, category_remove_dialog_title,
           category_remove_dialog_text, alert_dialog_remove_accept, alert_dialog_text_cancel;

    @Bean
    TextInputCheck check;

    @Click
    void fab() {
        alertDialog();
    }

    @AfterViews
    void ready(){
        getActivity().setTitle(getResources().getString(R.string.nav_drawer_categories));
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT){
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if (actionMode != null) actionMode.finish();
                List<Integer> item = new ArrayList<>(1);
                item.add(viewHolder.getAdapterPosition());
                categoriesAdapter.removeItems(item);
                notifyDelete(item);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void notifyDelete(final List<Integer> positions){
        Snackbar.make(recyclerView, categoriesAdapter.getSelectedItemsCount() <= 1 ? "Категория удалена " : "Категории удалены", Snackbar.LENGTH_LONG)
                .setAction("Восстановить", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        categoriesAdapter.restoreItem(positions);
                    }
                })
                .show();
    }

    private void loadData() {
        getLoaderManager().restartLoader(1, null, new LoaderManager.LoaderCallbacks<List<Categories>>() {
            @Override
            public Loader<List<Categories>> onCreateLoader(int id, Bundle args) {
                final android.support.v4.content.AsyncTaskLoader<List<Categories>> loader = new android.support.v4.content.AsyncTaskLoader<List<Categories>>(getContext()) {
                    @Override
                    public List<Categories> loadInBackground() {
                        return getDataList();
                    }
                };
                loader.forceLoad();
                return loader;
            }

            @Override
            public void onLoadFinished(Loader<List<Categories>> loader, List<Categories> data) {
                categoriesAdapter = new CategoriesAdapter(getDataList(), new CategoriesAdapter.CardViewHolder.ClickListener() {
                    @Override
                    public void OnItemClicked(int position) {
                        if (actionMode != null) {
                            toggleSelection(position);
                        } else if (getDataList().get(position).expenses().size() > 0) {
                            Intent openActivityIntent = new Intent(getActivity(), ExpensesByCategoryActivity_.class);
                            getActivity().startActivity(openActivityIntent);
                            ExpensesByCategoryActivity.postCategory(getDataList().get(position));
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
                recyclerView.setAdapter(categoriesAdapter);
            }

            @Override
            public void onLoaderReset(Loader<List<Categories>> loader) {

            }
        });
    }

    private List<Categories> getDataList(){
        return new Select().from(Categories.class).execute();
    }

    private void alertDialog(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_category);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setWindowAnimations(R.style.AlertDialogCategoriesAdd);
        dialog.show();

        final EditText editText = (EditText) dialog.findViewById(R.id.dialog_category_edit_text);
        final Editable text  = editText.getText();

        Button cancelButton = (Button) dialog.findViewById(R.id.dialog_category_cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button okButton = (Button) dialog.findViewById(R.id.dialog_category_okButton);
        okButton.animate();
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check.inputAddCategoryValidation(editText, getContext())) {
                    categoriesAdapter.addCategory(text.toString());
                    Toast.makeText(getActivity(), category_add_added_text + text.toString(), Toast.LENGTH_SHORT).show();
                    Log.d("AlertDialog", text.toString());
                    dialog.dismiss();
                }
            }
        });
    }

    private void toggleSelection(int position){
        categoriesAdapter.toggleSelection(position);
        int count = categoriesAdapter.getSelectedItemsCount();
        if (count == 0){
            actionMode.finish();
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
    }

    private class ActionModeCallback implements ActionMode.Callback {
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
        public boolean onActionItemClicked(final ActionMode mode, MenuItem item) {
            switch (item.getItemId()){
                case R.id.menu_remove:
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity(), R.style.AlertDialog)
                            .setPositiveButton(alert_dialog_remove_accept, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // Accept action
                                    categoriesAdapter.removeItems(categoriesAdapter.getSelectedItems());
                                    notifyDelete(categoriesAdapter.getSelectedItems());
                                    mode.finish();
                                }
                            })
                            .setNegativeButton(alert_dialog_text_cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // Cancel action
                                    mode.finish();
                                    dialogInterface.dismiss();
                                }
                            })
                            .setTitle(category_remove_dialog_title)
                            .setMessage(category_remove_dialog_text)
                            .create();
                    alertDialog.getWindow().setWindowAnimations(R.style.AlertDialogCategoriesDel);
                    alertDialog.show();
                    return true;
                case R.id.menu_select_all:
                    if (categoriesAdapter.getItemCount() != categoriesAdapter.getSelectedItemsCount()) {
                        categoriesAdapter.SelectedAll(categoriesAdapter.getItemCount());
                        actionMode.setTitle(String.valueOf(categoriesAdapter.getSelectedItemsCount()));
                        actionMode.invalidate();
                    } else {
                        categoriesAdapter.clearSelection();
                        mode.finish();
                    }
                    return true;
                default: return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            categoriesAdapter.clearSelection();
            actionMode = null;
        }
    }

    public static ActionMode getActionMode(){
        return actionMode;
    }

    public static void finishActionMode(){
        actionMode.finish();
    }
}
