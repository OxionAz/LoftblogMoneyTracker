package ru.loftschool.loftblogmoneytracker.ui.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.activeandroid.query.Select;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import java.util.List;
import ru.loftschool.loftblogmoneytracker.R;
import ru.loftschool.loftblogmoneytracker.database.models.Categories;
import ru.loftschool.loftblogmoneytracker.ui.adapters.CategoriesAdapter;

/**
 * Created by Александр on 01.09.2015.
 */
@EFragment(R.layout.categories_fragment)
public class CategoriesFragment extends Fragment {

    private CategoriesAdapter categoriesAdapter;

    @ViewById(R.id.recycler_view_content)
    RecyclerView recyclerView;

    @ViewById(R.id.fab)
    FloatingActionButton fab;

    @Click
    void fab() {
        Snackbar.make(recyclerView, "pressed", Snackbar.LENGTH_SHORT).show();
    }

    @AfterViews
    void ready(){
        getActivity().setTitle(getResources().getString(R.string.nav_drawer_categories));
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        categoriesAdapter = new CategoriesAdapter(getDataList());
        recyclerView.setAdapter(categoriesAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
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
                recyclerView.setAdapter(new CategoriesAdapter(getDataList()));
            }

            @Override
            public void onLoaderReset(Loader<List<Categories>> loader) {

            }
        });
    }

    private List<Categories> getDataList(){
        return new Select().from(Categories.class).execute();
    }
}
