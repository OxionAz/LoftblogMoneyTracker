package ru.loftschool.moneytrackerbyoxion.ui.adapters;

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

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import ru.loftschool.moneytrackerbyoxion.R;
import ru.loftschool.moneytrackerbyoxion.database.models.Expenses;

/**
 * Created by Александр on 26.08.2015.
 */
public class ExpensesAdapter extends SelectableAdapter<ExpensesAdapter.CardViewHolder> {

    private List<Expenses> expenses;
    private List<Expenses> saveExpensesVH;
    private CardViewHolder.ClickListener clickListener;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM y", myDateFormatSymbols);
    private Context context;
    private int lastPositions = -1;

    public ExpensesAdapter(List<Expenses> expenses, CardViewHolder.ClickListener clickListener){
        this.expenses = expenses;
        this.saveExpensesVH = new ArrayList<>(expenses.size());
        this.clickListener = clickListener;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        context = parent.getContext();
        return new CardViewHolder(itemView, clickListener);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        Expenses expense = expenses.get(position);
        holder.name.setText(expense.name);
        holder.sum.setText(expense.sum);
        holder.date.setText(dateFormat.format(expense.date));
        holder.category.setText(expense.category.toString());
        holder.selectedOverlay.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);
        setAnimation(holder.cardView, position);
    }

    private static DateFormatSymbols myDateFormatSymbols = new DateFormatSymbols(){

        @Override
        public String[] getMonths() {
            return new String[]{"января", "февраля", "марта", "апреля", "мая", "июня",
                    "июля", "августа", "сентября", "октября", "ноября", "декабря"};
        }

    };

    private void setAnimation(View viewToAnimate, int position){
        if (position > lastPositions) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_up);
            viewToAnimate.startAnimation(animation);
            lastPositions = position;
        }
    }

    public void removeItems(List<Integer> positions){
        if (!saveExpensesVH.isEmpty()) saveExpensesVH.clear();
        saveExpensesVH.addAll(expenses);

        if (positions.size() <= 1){
            removeItem(positions.get(0));
        } else {
            for (int i = 0; i < positions.size(); i++) {
                    removeItem(positions.get(i) - i);
                    Log.d("DeletedItem: ", String.valueOf(positions.get(0)));
            }
        }
    }

    public void removeItem(int position){
        expenses.get(position).delete();
        expenses.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(List<Integer> position){
        expenses.clear();
        new Delete().from(Expenses.class).execute();
        expenses.addAll(saveExpensesVH);
        for (Expenses expense : saveExpensesVH){
            new Expenses(expense.name, expense.sum, expense.date, expense.category).save();
        }
        for(Integer i : position){
            notifyItemInserted(i);
        }
    }

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
        protected CardView cardView;
        private ClickListener clickListener;

        public CardViewHolder (View itemView, ClickListener clickListener){
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name_text);
            sum = (TextView) itemView.findViewById(R.id.sum_text);
            date = (TextView) itemView.findViewById(R.id.data_text);
            category = (TextView) itemView.findViewById(R.id.category_text);
            selectedOverlay = itemView.findViewById(R.id.selected_overlay);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
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
