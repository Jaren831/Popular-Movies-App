package com.example.android.moviefragment;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;

/**
 * Created by Jaren Lynch on 9/23/2016.
 */

public class SettingsFragment extends PreferenceFragment {
    private ListPreference listPreference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        listPreference = (ListPreference) getPreferenceManager().findPreference("list_preference");
//        listPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
//            @Override
//            public  boolean onPreferenceChange(Preference preference, Object newValue) {
//                return true;
//            }
//        });
//        return inflater.inflate(R.layout.settings_fragment, container, false);
//    }
}
