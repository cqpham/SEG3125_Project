package com.cpham.lab1_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SuggestATipActivity extends AppCompatActivity {

    private Toolbar mainToolbar;
    private RatingBar ratingBar;
    private LinearLayout layout;
    private TextView txtSuggestTipPercent;
    private Button btnRatingSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest_atip);

        //Set up tool bar
        mainToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //add listener to rating bar
        addListenerOnRatingBar();

        //add listener to submit button
        addListenerOnButton();
    }

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

    public void addListenerOnRatingBar() {
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        txtSuggestTipPercent = (TextView) findViewById(R.id.tipSuggestResult);
        layout = (LinearLayout) findViewById(R.id.tipSuggestLayout);

        //if rating value has changed, display the new rating value
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                layout.setVisibility(LinearLayout.GONE);
                txtSuggestTipPercent.setText(String.valueOf(getTipPercentageSuggestion(rating)));
            }
        });
    }

    public void addListenerOnButton() {
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        btnRatingSubmit = (Button) findViewById(R.id.submit_rating);
        layout = (LinearLayout) findViewById(R.id.tipSuggestLayout);

        //if button is clicked, display tip suggest percentage
        btnRatingSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.setVisibility(LinearLayout.VISIBLE);
            }
        });
    }

    private int getTipPercentageSuggestion(float rating) {
        return 10 + (Math.round(rating) * 2);
    }
}
