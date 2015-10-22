package ru.loftschool.loftblogmoneytracker.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import ru.loftschool.loftblogmoneytracker.R;
import ru.loftschool.loftblogmoneytracker.database.models.Expenses;

/**
 * Created by Александр on 26.08.2015.
 */
public class ExpensesAdapter extends SelectableAdapter<ExpensesAdapter.CardViewHolder> {
    private List<Expenses> expenses;
    private CardViewHolder.ClickListener clickListener;

    public ExpensesAdapter(List<Expenses> expenses, CardViewHolder.ClickListener clickListener){
        this.expenses = expenses;
        this.clickListener = clickListener;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new CardViewHolder(itemView, clickListener);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        Expenses expense = expenses.get(position);
        holder.name.setText(expense.name);
        holder.sum.setText(expense.sum);
        holder.date.setText(expense.date);
        holder.category.setText(expense.category.toString());
        holder.selectedOverlay.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);
    }

    public void removeItems(List<Integer> positions){
        Collections.sort(positions, new Comparator<Integer>() {
            @Override
            public int compare(Integer lhs, Integer rhs) {
                return rhs - lhs;
            }
        });

        while (!positions.isEmpty()) {

            for (int i = 0; i < positions.size(); i++) {
                removeItem(positions.get(0) - i);
                Log.d("DeletedItem: ", String.valueOf(positions.get(0)));
                positions.remove(0);
            }
        }

//        while (!positions.isEmpty()){
//            if(positions.size() == 1){
//                removeItem(positions.get(0));
//                positions.remove(0);
//            } else {

//                int count = 1;
//                while (positions.size() > count && positions.get(count).equals(positions.get(count - 1) - 1)){
//                    count++;
//                }
//                if (count == 1) {
//                    removeItem(positions.get(0));
//                } else {
//                    removeRange(positions.get(count-1), count);
//                }
//                for (int i = 0; i < count; i++){
//                    positions.remove(0);
//                }
//            }
//        }
    }

    private void removeItem(int position){
        removeExpenses(position);
        notifyItemRemoved(position);
    }

    private void removeExpenses(int positions){
        if (expenses.get(positions) != null){
            expenses.get(positions).delete();
            expenses.remove(positions);
        }
    }

//    private void removeRange(int positionStart, int itemCount) {
//        for (int position = 0; position < itemCount; position++){
//            removeExpenses(positionStart);
//        }
//        notifyItemRangeChanged(positionStart, itemCount);
//    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        protected TextView name;
        protected TextView sum;
        protected TextView date;
        protected TextView category;
        protected View selectedOverlay;
        private ClickListener clickListener;

        public CardViewHolder (View itemView, ClickListener clickListener){
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name_text);
            sum = (TextView) itemView.findViewById(R.id.sum_text);
            date = (TextView) itemView.findViewById(R.id.data_text);
            category = (TextView) itemView.findViewById(R.id.category_text);
            selectedOverlay = itemView.findViewById(R.id.selected_overlay);
            this.clickListener = clickListener;
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
