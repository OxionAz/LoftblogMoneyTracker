package ru.loftschool.loftblogmoneytracker;

import android.support.v4.app.Fragment;
import android.widget.TextView;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Александр on 01.09.2015.
 */
@EFragment(R.layout.other_fragment)
public class StatisticsFragment extends Fragment {

    @ViewById(R.id.other_text)
    TextView textView;

    @AfterViews
    void ready(){
        getActivity().setTitle(getResources().getString(R.string.nav_drawer_statistics));
        textView.setText(getResources().getString(R.string.statistic_text));
    }
}
