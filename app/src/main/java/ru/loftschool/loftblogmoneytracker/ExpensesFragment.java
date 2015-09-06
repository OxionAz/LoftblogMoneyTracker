package ru.loftschool.loftblogmoneytracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Александр on 28.08.2015.
 */
public class ExpensesFragment extends Fragment {

    private ExpensesAdapter expensesAdapter;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.expenses_fragment, container, false);
        getActivity().setTitle(getResources().getString(R.string.nav_drawer_expenses));
        List<Expense> adapterData = getDataList();
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_content);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        expensesAdapter = new ExpensesAdapter(adapterData);
        recyclerView.setAdapter(expensesAdapter);
        Snackbar.make(recyclerView, getActivity().getTitle() +" pressed", Snackbar.LENGTH_SHORT).show();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openActivityIntent = new Intent(getActivity(), AddExpensesActivity.class);
                getActivity().startActivity(openActivityIntent);
            }
        });
        return view;
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
