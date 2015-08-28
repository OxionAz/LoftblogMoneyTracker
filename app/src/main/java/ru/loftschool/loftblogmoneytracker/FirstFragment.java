package ru.loftschool.loftblogmoneytracker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Александр on 28.08.2015.
 */
public class FirstFragment extends Fragment {

    private ListView listView;
    private List<Transaction> data = new ArrayList<>();
    private TransactionAdapter transactionAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_fragment, container, false);
        getActivity().setTitle(getResources().getString(R.string.first_fragment));
        listView = (ListView)view.findViewById(R.id.main_listview);
        List<Transaction> adapterData = getDataList();
        transactionAdapter = new TransactionAdapter(getActivity(), adapterData);
        listView.setAdapter(transactionAdapter);
        return view;
    }

    private List<Transaction> getDataList(){
        data.add(new Transaction("Telephone",2000,new Date()));
        data.add(new Transaction("Telephone",3000,new Date()));
        data.add(new Transaction("Telephone",4000,new Date()));
        data.add(new Transaction("Telephone", 5000, new Date()));
        return data;
    }
}
