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

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Александр on 01.09.2015.
 */
@EFragment(R.layout.expenses_fragment)
public class CategoriesFragment extends Fragment {

    private ExpensesAdapter expensesAdapter;

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
        data.add(new Expense("Telephone",2000,new Date()));
        data.add(new Expense("TV",3000,new Date()));
        data.add(new Expense("Ethernet",4000,new Date()));
        return data;
    }
}

//    private ExpensesAdapter expensesAdapter;
//    private RecyclerView recyclerView;
//    private FloatingActionButton floatingActionButton;
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.expenses_fragment, container, false);
//        getActivity().setTitle(getResources().getString(R.string.nav_drawer_categories));
//        final List<Expense> adapterData = getDataList();
//        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);
//        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_content);
//        recyclerView.setHasFixedSize(true);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        expensesAdapter = new ExpensesAdapter(adapterData);
//        recyclerView.setAdapter(expensesAdapter);
//        Snackbar.make(recyclerView, getActivity().getTitle() +" pressed", Snackbar.LENGTH_SHORT).show();
//        floatingActionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Snackbar.make(recyclerView, "Запись добавлена", Snackbar.LENGTH_SHORT).show();
//                adapterData.add(new Expense("Telephone", 2000, new Date()));
//            }
//        });
//        return view;
//    }
//
//    private List<Expense> getDataList(){
//        List<Expense> data = new ArrayList<>();
//        data.add(new Expense("Telephone",2000,new Date()));
//        data.add(new Expense("TV",3000,new Date()));
//        data.add(new Expense("Ethernet",4000,new Date()));
//        return data;
//    }
//}
