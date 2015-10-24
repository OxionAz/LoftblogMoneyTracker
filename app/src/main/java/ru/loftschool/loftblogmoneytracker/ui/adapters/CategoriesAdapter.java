package ru.loftschool.loftblogmoneytracker.ui.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.activeandroid.query.Delete;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import ru.loftschool.loftblogmoneytracker.R;
import ru.loftschool.loftblogmoneytracker.database.models.Categories;
import ru.loftschool.loftblogmoneytracker.database.models.Expenses;

/**
 * Created by Александр on 26.08.2015.
 */
public class CategoriesAdapter extends SelectableAdapter<CategoriesAdapter.CardViewHolder>{
    private List<Categories> categories;
    private CardViewHolder.ClickListener clickListener;
    private Context context;
    private int lastPositions = -1;

    public CategoriesAdapter(List<Categories> categories, CardViewHolder.ClickListener clickListener){
        this.categories = categories;
        this.clickListener = clickListener;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_category, parent, false);
        context = parent.getContext();
        return new CardViewHolder(itemView, clickListener);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        Categories category = categories.get(position);
        holder.name.setText(category.category);
        holder.selectedOverlay.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);
        setAnimation(holder.cardView, position);
    }

    private void setAnimation(View viewToAnimate, int position){
        if (position > lastPositions) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_left);
            viewToAnimate.startAnimation(animation);
            lastPositions = position;
        }
    }

    public void removeItems(List<Integer> positions) {
        Collections.sort(positions, new Comparator<Integer>() {
            @Override
            public int compare(Integer lhs, Integer rhs) {
                return rhs - lhs;
            }
        });

        while (!positions.isEmpty()) {

            for (int i = 0; i < positions.size(); i++) {
                new Delete().from(Expenses.class).where("Category = ?", categories.get(positions.get(0) - i).getId()).execute();
                Log.d("DeletedCategoriesName: ", categories.get(positions.get(0) - i).category);
                removeItem(positions.get(0) - i);
                Log.d("DeletedItem: ", String.valueOf(positions.get(0)));
                positions.remove(0);
            }
        }
    }

    public void removeItem(int position){
        removeCategory(position);
        notifyItemRemoved(position);
    }

    private void removeCategory(int positions){
        if (categories.get(positions) != null){
            categories.get(positions).delete();
            categories.remove(positions);
        }
    }

    public void addCategory(String name){
        Categories category = new Categories(name);
        category.save();
        categories.add(category);
        notifyItemInserted(getItemCount()-1);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        protected TextView name;
        protected View selectedOverlay;
        protected CardView cardView;
        private ClickListener clickListener;

        public CardViewHolder (View itemView, ClickListener clickListener){
            super(itemView);
            this.clickListener = clickListener;
            name = (TextView) itemView.findViewById(R.id.name_text);
            selectedOverlay = itemView.findViewById(R.id.categories_selected_overlay);
            cardView = (CardView) itemView.findViewById(R.id.card_view);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null)
                clickListener.OnItemClicked(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            if (clickListener != null)
                return clickListener.OnItemLongClicked(getAdapterPosition());
            return true;
        }

        public interface ClickListener{
            void OnItemClicked(int position);
            boolean OnItemLongClicked(int position);
        }
    }
}
