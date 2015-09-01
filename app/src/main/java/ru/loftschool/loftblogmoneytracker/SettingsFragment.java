package ru.loftschool.loftblogmoneytracker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Александр on 01.09.2015.
 */
public class SettingsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.other_fragment, container, false);
        getActivity().setTitle(getResources().getString(R.string.nav_drawer_settings));

        TextView textView = (TextView) view.findViewById(R.id.other_text);
        textView.setText(getResources().getString(R.string.settings_text));
        return view;

    }
}
