package ru.loftschool.loftblogmoneytracker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.expenses_fragment, container, false);
        getActivity().setTitle(getResources().getString(R.string.nav_drawer_expenses));
        List<Expense> adapterData = getDataList();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_content);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        expensesAdapter = new ExpensesAdapter(adapterData);
        recyclerView.setAdapter(expensesAdapter);
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
