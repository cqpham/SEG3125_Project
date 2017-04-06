package seg3125.project;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    private TextView countdownText, taskName;
    private TextView levelText;
    private int taskDurationMinutes;
    private final String[] minArray = {"5", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"};
    private int level, levelPointsThreshold, pointsEarnedTotal, pointsEarnedForLevel;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        int defaultLevel = getResources().getInteger(R.integer.saved_level_default);
        level = sharedPreferences.getInt(getString(R.string.saved_level), defaultLevel);
        int defaultPointsEarnedTotal = getResources().getInteger(R.integer.points_earned_default);
        pointsEarnedTotal = sharedPreferences.getInt(getString(R.string.points_earned), defaultPointsEarnedTotal);
        int defaultPointsThreshold = getResources().getInteger(R.integer.default_level_points_threshold);
        levelPointsThreshold = sharedPreferences.getInt(getString(R.string.level_points_threshold), defaultPointsThreshold);
        int defaultPointsEarnedForLevel = getResources().getInteger(R.integer.default_points_earned_level);
        pointsEarnedForLevel = sharedPreferences.getInt(getString(R.string.points_earned_level), defaultPointsEarnedForLevel);

        editor = sharedPreferences.edit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCreateTaskDialog();
            }
        });

        progressBar = (ProgressBar) findViewById(R.id.xpPointsProgressBar);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setMax(levelPointsThreshold);
        progressBar.setProgress(pointsEarnedTotal);

        countdownText = (TextView) findViewById(R.id.countDownText);
        taskName = (TextView) findViewById(R.id.taskName);
        levelText = (TextView) findViewById(R.id.level_text);
        levelText.setText(getResources().getString(R.string.level) + " " + String.valueOf(level));
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        //Inflate the menu -> this adds items to the action bar
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {

        int id = item.getItemId();
        Intent intent;

        switch (id) {
            case R.id.action_settings:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_help:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class CustomCountDownTimer extends CountDownTimer {

        public CustomCountDownTimer (long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick (long millisUntilFinished) {
            //Convert milliseconds into hours, minutes, seconds
            String hms = formatMilliseconds(millisUntilFinished);
            countdownText.setText(hms);
        }

        @Override
        public void onFinish() {
            //reset timer
            countdownText.setText(R.string.timer_default);
            int earnedPoints = calculateXpPoints(taskDurationMinutes);
            pointsEarnedTotal += earnedPoints;
            pointsEarnedForLevel += earnedPoints;
            //NEED TO SET PREFERENCES

            if (canLevelUp(earnedPoints)) {
                level += 1;
                levelPointsThreshold = getLevelPointsThreshold(level);
                showLevelUpDialog();
            } else {
                showFinishedTaskDialog(earnedPoints);
            }
        }

        private String formatMilliseconds(long milliseconds) {
            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;

            long elapsedHours = milliseconds / hoursInMilli;
            milliseconds = milliseconds % hoursInMilli;

            long elapsedMinutes = milliseconds / minutesInMilli;
            milliseconds = milliseconds % minutesInMilli;

            long elapsedSeconds = milliseconds / secondsInMilli;

            return String.format("%02d:%02d:%02d", elapsedHours, elapsedMinutes, elapsedSeconds);
        }
    }

    private int calculateXpPoints(int minutes) {
        return minutes*10;
    }

    private boolean canLevelUp(int pointsJustEarned) {
        if (pointsJustEarned == 0) {
            return false;
        } else if ((pointsJustEarned + pointsEarnedForLevel) == levelPointsThreshold) {
            return true;
        }

        return false;
    }

    private int getLevelPointsThreshold(int level) {
        int points = R.integer.default_level_points_threshold;

         for (int i = 2; i <= level; i++) {
            points += points*i;
         }

        return points;
    }

    private void showCreateTaskDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.task_create);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.create_task, null);
        builder.setView(dialogView);

        final NumberPicker hrPicker = (NumberPicker) dialogView.findViewById(R.id.hrPicker);
        hrPicker.setMinValue(0);
        hrPicker.setMaxValue(12);
        hrPicker.setWrapSelectorWheel(true);

        final NumberPicker minPicker = (NumberPicker) dialogView.findViewById(R.id.minPicker);
        minPicker.setMinValue(0);
        minPicker.setMaxValue(minArray.length-1);
        minPicker.setDisplayedValues(minArray);
        minPicker.setWrapSelectorWheel(true);

        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // positive button logic
                        dialog.cancel();

                        //Set task name and need to check validation
                        TextView taskInputName = (TextView) findViewById(R.id.create_task_name);
                        taskName.setText(taskInputName.getText());

                        //Get duration
                        taskDurationMinutes = hrPicker.getValue()*60 + Integer.parseInt(minArray[minPicker.getValue()]);
                        int taskDurationMilliseconds = taskDurationMinutes*60000;
                        CustomCountDownTimer customCountDownTimer = new CustomCountDownTimer(2000, 1000);
                        customCountDownTimer.start();
                    }
                });

        String negativeText = getString(android.R.string.cancel);
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // negative button logic
                        dialog.cancel();
                    }
                });

        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();
    }

    private void showFinishedTaskDialog(int earnedPoints) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.finished_task, null);
        builder.setView(dialogView);

        TextView earned_points_text = (TextView) dialogView.findViewById(R.id.earned_points_text);
        earned_points_text.setText(getResources().getString(R.string.earned_points) + " " + Integer.toString(earnedPoints) + " " + getResources().getString(R.string.points));

        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // positive button logic
                        dialog.cancel();
                    }
                });

        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();
    }

    private void showLevelUpDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.level_up, null);
        builder.setView(dialogView);

        TextView level_up_text = (TextView) dialogView.findViewById(R.id.level_up_text);
        level_up_text.setText(getResources().getString(R.string.level_up) + " " + level + "!");

        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // positive button logic
                        dialog.cancel();

                        //reset progress bar
                        progressBar.setProgress(0);
                        progressBar.setMax(levelPointsThreshold);

                        //set level label
                        levelText.setText(getResources().getString(R.string.level) + " " + String.valueOf(level));
                    }
                });

        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();
    }
}
