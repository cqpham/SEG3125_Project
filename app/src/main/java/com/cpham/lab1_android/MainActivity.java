package com.cpham.lab1_android;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Toolbar mainToolbar;
    private TextInputLayout inputLayoutTipPercent, inputLayoutTotalDistance;
    private EditText inputTipPercent, inputTotalDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set up tool bar
        mainToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mainToolbar);

        inputLayoutTipPercent = (TextInputLayout) findViewById(R.id.input_layout_tipPercent);
        inputLayoutTotalDistance = (TextInputLayout) findViewById(R.id.input_layout_totalDistance);
        inputTipPercent = (EditText) findViewById(R.id.input_tipPercent);
        inputTotalDistance = (EditText) findViewById(R.id.input_totalDistance);

        //display first fragment view on activity
        displayView(0);

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    int size = getSupportFragmentManager().getBackStackEntryCount();
                    if (getSupportFragmentManager().getBackStackEntryAt(size - 1).getName().equals("main")) {
                        disableUpButton(); //hide back button
                    } else {
                        enableUpButton(); //show back button
                    }
                    mainToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onBackPressed();
                        }
                    });
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        //Inflate the menu -> this adds items to the action bar
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        /*
        Handle action bar item clicks here. The action bar will
        automatically handle clicks on the Home/Up button, so long
        as you specify a parent activity in AndroidManifest.xml.
         */
        int id = item.getItemId();
        Intent intent;

        switch (id) {
            case R.id.action_settings:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_suggestATip:
                intent = new Intent(this, SuggestATipActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String fragmentName = "";

        switch (position) {
            case 0:
                fragment = new MainFragment();
                fragmentName = "main";
                break;
            case 1:
                fragment = new SettingsFragment();
                break;
            case 2:
                fragment = new SummaryFragment();
                fragmentName = "summary";
                break;
            default:
                break;
        }

        if (fragment != null) {
            /*
            Replace container_body layout with fragment and add it to activity back stack
            Press the back button at the bottom of screen to go back
             */
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_body, fragment, fragmentName)
                    .addToBackStack(fragmentName)
                    .commit();
        }
    }

    public void displaySummary(View view) {
        //display summary view fragment
        displayView(2);
        enableUpButton();
    }

    public void displayMain(View view) {
        //display main view fragment
        displayView(0);
        disableUpButton();

    }

    private void enableUpButton() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void disableUpButton() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }
}
