package ru.loftschool.loftblogmoneytracker.ui.adapters;

import android.content.Context;
import android.os.Handler;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;
import ru.loftschool.loftblogmoneytracker.R;
import ru.loftschool.loftblogmoneytracker.database.models.Categories;
import ru.loftschool.loftblogmoneytracker.database.models.Expenses;

/**
 * Created by Александр on 26.08.2015.
 */
public class CategoriesAdapter extends SelectableAdapter<CategoriesAdapter.CardViewHolder>{
    private List<Categories> categories;
    private List<Categories> saveCategoriesVH;
    private List<Boolean> itemsForDelete;
    private CardViewHolder.ClickListener clickListener;
    private Context context;
    private int lastPositions = -1;

    public CategoriesAdapter(List<Categories> categories, CardViewHolder.ClickListener clickListener){
        this.categories = categories;
        this.saveCategoriesVH = new ArrayList<>(categories.size());
        this.itemsForDelete = new ArrayList<>(categories.size());
        this.clickListener = clickListener;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_category, parent, false);
        context = parent.getContext();
        for (int i = 0; i < categories.size(); i++) itemsForDelete.add(false);
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
        if (!saveCategoriesVH.isEmpty()) saveCategoriesVH.clear();
        saveCategoriesVH.addAll(categories);

        if (positions.size() <= 1){
            removeItem(positions.get(0));
            itemsForDelete.set(positions.get(0), true);
            delayDeleteFromDB(positions);
        } else {
            for (int i = 0; i < positions.size(); i++) {
                removeItem(positions.get(i)-i);
                itemsForDelete.set(positions.get(i), true);
                Log.d("DeletedItem: ", String.valueOf(positions.get(0)));
            }
            delayDeleteFromDB(positions);
        }
    }

    public void removeItem(int position){
        categories.remove(position);
        notifyItemRemoved(position);
    }

    public void delayDeleteFromDB(final List<Integer> position){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < position.size(); i++){
                    if (itemsForDelete.get(position.get(i))) saveCategoriesVH.get(position.get(i)).delete();
                    Log.d("Delete message: ", "position: "+position.get(i)+" "+
                            saveCategoriesVH.get(position.get(i))+" "+itemsForDelete.get(position.get(i)));
                }
            }
        }, 3400);
    }

    public void restoreItem(List<Integer> position){
        categories.clear();
        categories.addAll(saveCategoriesVH);
        for(Integer i : position){
            itemsForDelete.set(i, false);
            notifyItemInserted(i);
        }
    }

    public void addCategory(String category) {
        Categories cat = new Categories(category);
        cat.save();
        categories.add(cat);
        notifyItemInserted(getItemCount());
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
