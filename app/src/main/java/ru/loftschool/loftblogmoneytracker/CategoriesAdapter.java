package ru.loftschool.loftblogmoneytracker;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import ru.loftschool.loftblogmoneytracker.database.models.Categories;

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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_category, parent, false);
        return new CardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        Categories category = categories.get(position);
        holder.name.setText(category.category);
        holder.date.setText("Здесь могла быть ваша дата");
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder{
        protected TextView name;
        protected TextView date;

        public CardViewHolder (View itemView){
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name_text);
            date = (TextView) itemView.findViewById(R.id.data_text);
        }
    }
}
