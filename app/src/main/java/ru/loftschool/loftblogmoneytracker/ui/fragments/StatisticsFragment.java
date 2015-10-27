package ru.loftschool.loftblogmoneytracker.ui.fragments;

import android.support.v4.app.Fragment;
import android.view.animation.AnimationUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import ru.loftschool.loftblogmoneytracker.ui.view.PieChartView;
import ru.loftschool.loftblogmoneytracker.R;

/**
 * Created by Александр on 01.09.2015.
 */
@EFragment(R.layout.statistics_fragment)
public class StatisticsFragment extends Fragment {

    float[] dataPoints = {3000, 5000, 200, 100, 2300};

    @ViewById(R.id.statistic_pie)
    PieChartView pieChartView;

    @AfterViews
    void ready(){
        getActivity().setTitle(getResources().getString(R.string.nav_drawer_statistics));
        pieChartView.setDatapoints(dataPoints);
        pieChartView.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.rotate));
    }
}
