package com.cpham.lab1_android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.audiofx.BassBoost;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import static com.cpham.lab1_android.R.xml.preferences;

public class SettingsActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "pref_file";
    private Toolbar mainToolbar;
    private SettingsFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //Set up tool bar
        mainToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            Fragment preferenceFragment = new SettingsFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.settings_body, preferenceFragment);
            fragmentTransaction.commit();
        }

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        //get access to preferences fragment by id
        fragment = (SettingsFragment) getSupportFragmentManager().findFragmentById(R.id.pref_fragment);


    }

/*    @Override
    protected void onStop(){
        super.onStop();

        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        // Commit the edits!
        editor.commit();
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This is called when the Home (Up) button is pressed in the action bar.
                Intent upIntent = new Intent(this, MainActivity.class);
                // Return to the same fragment as was originally in MainActivity
                upIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(upIntent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public SharedPreferences getDefaults() {
        SharedPreferences preferences;
        //preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        return preferences;
    }


    /*public static String getDefaults() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

    public static void setDefaults(String key, String value, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }*/
}
