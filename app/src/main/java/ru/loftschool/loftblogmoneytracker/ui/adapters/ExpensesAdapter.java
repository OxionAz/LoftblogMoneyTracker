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
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;
import ru.loftschool.loftblogmoneytracker.R;
import ru.loftschool.loftblogmoneytracker.database.models.Expenses;

/**
 * Created by Александр on 26.08.2015.
 */
public class ExpensesAdapter extends SelectableAdapter<ExpensesAdapter.CardViewHolder> {

    private List<Expenses> expenses;
    private CardViewHolder.ClickListener clickListener;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM y", myDateFormatSymbols);

    private Context context;
    private int lastPositions = -1;
    private Timer undoRemoveTimer;
    private static final long UNDO_TIMEOUT = 3600L;
    private boolean multipleRemove = false;
    private Map<Integer, Expenses> removedExpensesMap;

    public ExpensesAdapter(List<Expenses> expenses, CardViewHolder.ClickListener clickListener){
        this.expenses = expenses;
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
        if (positions.size() > 1) {
            multipleRemove = true;
        }
        saveRemovedItems(positions);

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
        multipleRemove = false;
    }

    public void removeItem(int position){
        if (!multipleRemove) {
            saveRemovedItem(position);
        }
        removeExpenses(position);
        notifyItemRemoved(position);
    }

    private void completelyRemoveExpensesFromDB() {
        if (removedExpensesMap != null) {
            for (Map.Entry<Integer, Expenses> pair : removedExpensesMap.entrySet()) {
                pair.getValue().delete();
            }
            removedExpensesMap = null;
        }
    }

    private void saveRemovedItems(List<Integer> positions) {
        if (removedExpensesMap != null) {
            completelyRemoveExpensesFromDB();
        }
        removedExpensesMap = new TreeMap<>();
        for (int position : positions) {
            removedExpensesMap.put(position, expenses.get(position));
        }
    }

    private void saveRemovedItem(int position) {
        if (removedExpensesMap != null) {
            completelyRemoveExpensesFromDB();
        }
        ArrayList<Integer> positions = new ArrayList<>(1);
        positions.add(position);
        saveRemovedItems(positions);
    }

    public void restoreRemovedItems() {
        stopUndoTimer();
        for (Map.Entry<Integer, Expenses> pair : removedExpensesMap.entrySet()){
            expenses.add(pair.getKey(), pair.getValue());
            notifyItemInserted(pair.getKey());
        }
        removedExpensesMap = null;
    }

    public void startUndoTimer(long timeout) {
        stopUndoTimer();
        this.undoRemoveTimer = new Timer();
        this.undoRemoveTimer.schedule(new UndoTimer(), timeout > 0 ? timeout : UNDO_TIMEOUT);
    }

    private void stopUndoTimer() {
        if (this.undoRemoveTimer != null) {
            this.undoRemoveTimer.cancel();
            this.undoRemoveTimer = null;
        }
    }

    private class UndoTimer extends TimerTask {
        @Override
        public void run() {
            undoRemoveTimer = null;
            completelyRemoveExpensesFromDB();
            removedExpensesMap = null;
        }
    }

    private void removeExpenses(int positions){
        if (expenses.get(positions) != null){
//            expenses.get(positions).delete();
            expenses.remove(positions);
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
