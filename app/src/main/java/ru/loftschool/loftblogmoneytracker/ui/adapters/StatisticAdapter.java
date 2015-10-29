package ru.loftschool.loftblogmoneytracker.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.loftschool.loftblogmoneytracker.R;
import ru.loftschool.loftblogmoneytracker.database.models.Categories;
import ru.loftschool.loftblogmoneytracker.ui.view.Oval;

/**
 * Created by Александр on 27.10.2015.
 */
public class StatisticAdapter extends RecyclerView.Adapter<StatisticAdapter.CardViewHolder> {

    List<Categories> categories;
    List<Integer> color;

    public StatisticAdapter(List<Categories> categories, List<Integer> color){
        this.categories=categories;
        this.color = color;
    }

    @Override
    public StatisticAdapter.CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_statistic, parent, false);
        return new CardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(StatisticAdapter.CardViewHolder holder, int position) {
        Categories category = categories.get(position);
        holder.category.setText(category.toString());
        holder.view.SetColor(color.get(position));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder{
        protected Oval view;
        protected TextView category;

        public CardViewHolder(View itemView) {
            super(itemView);
            this.category = (TextView) itemView.findViewById(R.id.name_text);
            this.view = (Oval) itemView.findViewById(R.id.color_oval);
        }
    }
}
