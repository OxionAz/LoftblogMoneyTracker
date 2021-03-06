package ru.loftschool.loftblogmoneytracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.List;

/**
 * Created by Александр on 26.08.2015.
 */
public class TransactionAdapter extends ArrayAdapter<Transaction> {
    private List<Transaction> transactions;

    public TransactionAdapter(Context context, List<Transaction> transactions) {
        super(context, 0, transactions);
        this.transactions = transactions;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Transaction transaction = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        TextView textTitle = (TextView) convertView.findViewById(R.id.name_text);
        TextView sumTitle = (TextView) convertView.findViewById(R.id.sum_text);
        TextView dateTitle = (TextView) convertView.findViewById(R.id.data_text);

        textTitle.setText(transaction.getTitle());
        sumTitle.setText(transaction.getSum());
        dateTitle.setText(transaction.getDate());
        return convertView;
    }

}
