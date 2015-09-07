package ru.loftschool.loftblogmoneytracker;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        List<Expense> adapterData = getDataList();
        getActivity().setTitle(getResources().getString(R.string.nav_drawer_expenses));
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        expensesAdapter = new ExpensesAdapter(adapterData);
        recyclerView.setAdapter(expensesAdapter);
        Snackbar.make(recyclerView, getActivity().getTitle() +" pressed", Snackbar.LENGTH_SHORT).show();
    }

    private List<Expense> getDataList(){
        List<Expense> data = new ArrayList<>();
        data.add(new Expense("PC",2000,new Date()));
        data.add(new Expense("Food",3000,new Date()));
        data.add(new Expense("Telephone",4000,new Date()));
        data.add(new Expense("Cloth", 5000, new Date()));
        data.add(new Expense("PC",2000,new Date()));
        data.add(new Expense("Food",3000,new Date()));
        data.add(new Expense("Telephone",4000,new Date()));
        data.add(new Expense("Cloth", 5000, new Date()));
        data.add(new Expense("PC",2000,new Date()));
        data.add(new Expense("Food",3000,new Date()));
        data.add(new Expense("Telephone",4000,new Date()));
        data.add(new Expense("Cloth", 5000, new Date()));
        data.add(new Expense("PC",2000,new Date()));
        data.add(new Expense("Food",3000,new Date()));
        data.add(new Expense("Telephone",4000,new Date()));
        data.add(new Expense("Cloth", 5000, new Date()));
        data.add(new Expense("PC",2000,new Date()));
        data.add(new Expense("Food",3000,new Date()));
        data.add(new Expense("Telephone",4000,new Date()));
        data.add(new Expense("Cloth", 5000, new Date()));
        return data;
    }
}
