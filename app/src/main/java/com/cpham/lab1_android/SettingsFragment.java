package com.cpham.lab1_android;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import java.util.Map;

public class SettingsFragment extends PreferenceFragmentCompat implements OnSharedPreferenceChangeListener {

    SharedPreferences sharedPreferences;

    public SettingsFragment() {

    }

    @Override
    public void onCreatePreferences (Bundle bundle, String s) {
        //Load preferences from preferences.xml
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onResume() {
        super.onResume();

        Preference preference;

        sharedPreferences = getPreferenceManager().getSharedPreferences();

        //Watch preference value's changes
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        Map<String, ?> preferencesMap = sharedPreferences.getAll();

        // iterate through the preference entries and update their summary if they are an instance of EditTextPreference
        for (Map.Entry<String, ?> preferenceEntry : preferencesMap.entrySet()) {
            preference = findPreference(preferenceEntry.getKey());
            updatePreferences(preference);
        }
    }

    @Override
    public void onPause() {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                          String key) {
        Map<String, ?> preferencesMap = sharedPreferences.getAll();

        // get the preference that has been changed
        Preference preference = findPreference(key);

        updatePreferences(preference);
    }

    private void updatePreferences(Preference preference) {
        // and if it's an instance of EditTextPreference class, update its summary
        if (preference instanceof EditTextPreference) {
            updateSummary((EditTextPreference) preference);
        }
    }

    private void updateSummary(EditTextPreference preference) {
        // set the EditTextPreference's summary value to its current text
        preference.setSummary(preference.getText());
    }
}
