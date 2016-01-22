package ru.loftschool.loftblogmoneytracker.ui.activity;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import ru.loftschool.loftblogmoneytracker.R;

/**
 * Created by Александр on 01.09.2015.
 */

@EActivity(R.layout.settings_activity)
public class SettingsActivity extends AppCompatActivity{

    @ViewById
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().replace(R.id.frame_container, new SettingsFragment()).commit();
        }
    }

    @AfterViews
    void ready() {
        initToolbar();
    }

    private void initToolbar(){
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            setTitle(R.string.nav_drawer_settings);
        }
    }

    public static class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

        private CheckBoxPreference mainChkbox;
        private CheckBoxPreference vibroChkbox;
        private CheckBoxPreference ledChkbox;
        private CheckBoxPreference soundChkbox;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_general);
            mainChkbox = (CheckBoxPreference) findPreference(getString(R.string.pref_enable_notifications_key));
            vibroChkbox = (CheckBoxPreference) findPreference(getString(R.string.pref_enable_vibration_key));
            ledChkbox = (CheckBoxPreference) findPreference(getString(R.string.pref_enable_indicator_key));
            soundChkbox = (CheckBoxPreference) findPreference(getString(R.string.pref_enable_sound_key));

            vibroChkbox.setEnabled(mainChkbox.isChecked());
            ledChkbox.setEnabled(mainChkbox.isChecked());
            soundChkbox.setEnabled(mainChkbox.isChecked());

            mainChkbox.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    vibroChkbox.setEnabled(mainChkbox.isChecked());
                    ledChkbox.setEnabled(mainChkbox.isChecked());
                    soundChkbox.setEnabled(mainChkbox.isChecked());
                    return true;
                }
            });
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Тут мы биндим наши ListPreference
        //bindPreferenceSummaryToValue(findPreference(getString(R.string)));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String stringValue = newValue.toString();
            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int prefIndex = listPreference.findIndexOfValue(stringValue);
                if (prefIndex >= 0) {
                    preference.setSummary(listPreference.getEntries()[prefIndex]);
                }
            } else {
                preference.setSummary(stringValue);
            }
            return true;
        }

        private void bindPreferenceSummaryToValue(Preference preference) {
            // Set the listener to watch for value changes.
            preference.setOnPreferenceChangeListener(this);
            // Trigger the listener immediately with the preference's
            // current value.
            onPreferenceChange(preference,
                    PreferenceManager
                            .getDefaultSharedPreferences(preference.getContext())
                            .getString(preference.getKey(), ""));
        }
    }
}
