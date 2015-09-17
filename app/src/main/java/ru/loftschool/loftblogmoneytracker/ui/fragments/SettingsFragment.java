package ru.loftschool.loftblogmoneytracker.ui.fragments;

import android.support.v4.app.Fragment;
import android.widget.TextView;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import ru.loftschool.loftblogmoneytracker.R;

/**
 * Created by Александр on 01.09.2015.
 */
@EFragment(R.layout.other_fragment)
public class SettingsFragment extends Fragment {

    @ViewById(R.id.other_text)
    TextView textView;

    @AfterViews
    void ready(){
        getActivity().setTitle(getResources().getString(R.string.nav_drawer_settings));
        textView.setText(getResources().getString(R.string.settings_text));
    }
}
