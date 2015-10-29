package ru.loftschool.loftblogmoneytracker.ui.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.Chart;
import lecho.lib.hellocharts.view.LineChartView;
import ru.loftschool.loftblogmoneytracker.database.models.Categories;
import ru.loftschool.loftblogmoneytracker.database.models.Expenses;
import ru.loftschool.loftblogmoneytracker.ui.activity.AddExpensesActivity_;
import ru.loftschool.loftblogmoneytracker.ui.adapters.StatisticAdapter;
import ru.loftschool.loftblogmoneytracker.ui.view.PieChartView;
import ru.loftschool.loftblogmoneytracker.R;

/**
 * Created by Александр on 01.09.2015.
 */
@EFragment(R.layout.statistics_fragment)
public class StatisticsFragment extends Fragment {

    private PieChartData data;
    List<Integer> color;

    @ViewById(R.id.recycler_view_content)
    RecyclerView recyclerView;

    @ViewById(R.id.chart)
    lecho.lib.hellocharts.view.PieChartView chart;

    @ViewById(R.id.statistic_text)
    TextView addText;

    @ViewById(R.id.add_button)
    Button addButton;

    @Click(R.id.add_button)
    void addButton(){
        Intent openActivityIntent = new Intent(getActivity(), AddExpensesActivity_.class);
        getActivity().startActivity(openActivityIntent);
        getActivity().overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
    }

    @AfterViews
    void ready(){
        setColor();
        getActivity().setTitle(getResources().getString(R.string.nav_drawer_statistics));
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new StatisticAdapter(getDefinedCategoryList(), color));

        if(!getDefinedCategoryList().isEmpty()) {
            setHasOptionsMenu(true);
            generateData(color, getExpensesSum());
            chart.setOnValueTouchListener(new ValueTouchListener());
            addButton.setVisibility(View.INVISIBLE);
            addText.setVisibility(View.INVISIBLE);
        }
    }

    private void setColor(){
        Random random = new Random();
        this.color = new ArrayList<>();
        for (int i=0; i<getDefinedCategoryList().size();i++) {
            color.add(Color.argb(1000, random.nextInt(256), random.nextInt(256), random.nextInt(256)));
        }
    }

    private List<Float> getExpensesSum(){
        List<Float> sum = new ArrayList<>();
        List<Categories> categories = getDefinedCategoryList();
        for (Categories category : categories){
            float expensesSum = 0;
            List<Expenses> expenses = category.expenses();
            for (Expenses expense : expenses){
                expensesSum+=Float.valueOf(expense.sum);
            }
            Log.d("Сумма по категории ",category.toString()+": "+ String.valueOf(expensesSum));
            sum.add(expensesSum);
        }
        return sum;
    }

    private List<Categories> getDefinedCategoryList(){
        List<Categories> categories = new ArrayList<>();
        for (Categories category : getDataList()){
            if(!category.expenses().isEmpty()) categories.add(category);
        }
        return categories;
    }

    private List<Categories> getDataList(){
        return new Select().from(Categories.class).execute();
    }

    private void generateData(List<Integer> color, List<Float> sumExpenses) {

        List<SliceValue> values = new ArrayList<SliceValue>();
        for (int i = 0; i < getDefinedCategoryList().size(); ++i) {
            SliceValue sliceValue = new SliceValue(sumExpenses.get(i), ChartUtils.darkenColor(color.get(i)));
            values.add(sliceValue);
        }

        data = new PieChartData(values);
        chart.setValueSelectionEnabled(true);
        data.setHasLabels(true);
        data.setHasCenterCircle(true);
        data.setCenterText1("Категории");
        // Get roboto-italic font.
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Italic.ttf");
        data.setCenterText1Typeface(tf);
        // Get font size from dimens.xml and convert it to sp(library uses sp values).
        data.setCenterText1FontSize(ChartUtils.px2sp(getResources().getDisplayMetrics().scaledDensity,
                (int) getResources().getDimension(R.dimen.pie_chart_text1_size)));
        chart.setPieChartData(data);
    }

    private class ValueTouchListener implements PieChartOnValueSelectListener {

        @Override
        public void onValueSelected(int arcIndex, SliceValue value) {
            Toast.makeText(getActivity(), "Общая сумма трат по категории: "+ value.getValue(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onValueDeselected() {
        }
    }
}
