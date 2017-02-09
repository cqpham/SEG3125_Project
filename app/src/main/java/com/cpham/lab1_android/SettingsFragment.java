package com.cpham.lab1_android;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

public class SettingsFragment extends PreferenceFragmentCompat {

    public SettingsFragment() {

    }

    @Override
    public void onCreatePreferences (Bundle bundle, String s) {
        //Load preferences from preferences.xml
        addPreferencesFromResource(R.xml.preferences);
    }
}
