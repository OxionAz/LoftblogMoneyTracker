package ru.loftschool.loftblogmoneytracker;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.List;

import ru.loftschool.loftblogmoneytracker.database.models.Categories;
import ru.loftschool.loftblogmoneytracker.database.models.Expenses;

/**
 * Created by Александр on 26.08.2015.
 */
public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CardViewHolder> {
    private  List<Categories> categories;

    public CategoriesAdapter(List<Categories> categories){
        this.categories = categories;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new CardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        Categories category = categories.get(position);
        holder.name.setText(category.getName());
        holder.sum.setText("");
        holder.date.setText(category.getName());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder{
        protected TextView name;
        protected TextView sum;
        protected TextView date;

        public CardViewHolder (View itemView){
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name_text);
            sum = (TextView) itemView.findViewById(R.id.sum_text);
            date = (TextView) itemView.findViewById(R.id.data_text);
        }
    }
}
