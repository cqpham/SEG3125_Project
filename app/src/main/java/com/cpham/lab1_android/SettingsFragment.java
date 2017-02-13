package com.cpham.lab1_android;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceManager;
import android.widget.EditText;
//import android.support.v7.preference.PreferenceFragmentCompat;
import com.takisoft.fix.support.v7.preference.PreferenceFragmentCompat;
import android.view.View;

import java.util.Map;

public class SettingsFragment extends PreferenceFragmentCompat implements OnSharedPreferenceChangeListener {

    SharedPreferences sharedPreferences;


    public SettingsFragment() {

    }


    @Override
    public void onCreatePreferencesFix (Bundle bundle, String s) {
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
            updateEditTextSummary((EditTextPreference) preference);
        } else if (preference  instanceof ListPreference) {
            updateListSummary((ListPreference) preference);
        } else if (preference instanceof CheckBoxPreference) {
            updateCheckBoxSummary((CheckBoxPreference) preference);
        }
    }

    private void updateEditTextSummary(EditTextPreference preference) {
        // set the EditTextPreference's summary value to its current text
        preference.setSummary(preference.getText());
    }

    private void updateListSummary (ListPreference preference) {
        Preference kiloPricepreference = findPreference("pref_default_perKilo_price");
        preference.setSummary(preference.getEntry());
        switch (preference.getValue()) {
            case "usd":
                kiloPricepreference.setSummary("0.75");
                break;
            case "cad":
                kiloPricepreference.setSummary("0.54");
                break;
            case "euro":
                kiloPricepreference.setSummary("0.22");
                break;
            case "pnd":
                kiloPricepreference.setSummary("0.34");
                break;
        }
    }

    private void updateCheckBoxSummary(CheckBoxPreference preference) {

        /*FragmentManager fm = getFragmentManager();
        MainFragment fragment = (MainFragment) fm.findFragmentById(R.id.fragment_main);
        fragment.setDefaultTipPercentage();*/

        /*EditText txtTipPercentage = (EditText) getView().findViewById(R.id.input_tipPercent);
        TextInputLayout txtLayout = (TextInputLayout) getView().findViewById(R.id.input_layout_tipPercent);
        EditTextPreference editTextPreference = (EditTextPreference) findPreference("@string/pref_default_tip_percent");

        if (preference.isChecked()) {
            //add default tip percentage
            txtTipPercentage.setText(editTextPreference.getText());
        } else {
            //remove default tip percentage
            txtTipPercentage.setText("");
            txtLayout.setErrorEnabled(false);
        }*/
    }
}
