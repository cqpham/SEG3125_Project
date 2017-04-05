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
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.NumberPicker;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView countdownText;
    private long taskDurationMilliseconds, taskDurationMinutes;
    private final String[] minArray = {"5", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"};
    private int levelPointsThreshold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        int defaultLevelValue = getResources().getInteger(R.integer.saved_level_default);
        int level = sharedPreferences.getInt(getString(R.string.saved_level), defaultLevelValue);
        int defaultPointsEarnedValue = getResources().getInteger(R.integer.points_earned_default);
        int pointsEarned = sharedPreferences.getInt(getString(R.string.points_earned), defaultPointsEarnedValue);

        levelPointsThreshold = getLevelPointsThreshold(level);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCreateTaskDialog();
            }
        });

        countdownText = (TextView) findViewById(R.id.countDownText) ;
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        //Inflate the menu -> this adds items to the action bar
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
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
                        //Get duration
                        taskDurationMinutes = hrPicker.getValue()*60 + Integer.parseInt(minArray[minPicker.getValue()]);
                        taskDurationMilliseconds = taskDurationMinutes*60000;
                        CustomCountDownTimer customCountDownTimer = new CustomCountDownTimer(taskDurationMilliseconds, 1000);
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

    private void showFinishedTaskDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.finished_task, null);
        builder.setView(dialogView);

        TextView earned_points_text = (TextView) dialogView.findViewById(R.id.earned_points_text);
        Long earnedPoints = calculateXpPoints(taskDurationMinutes);
        earned_points_text.setText(R.string.earned_points + Long.toString(earnedPoints) + R.string.points);

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

    private long calculateXpPoints(long minutes) {
        return minutes*100;
    }

    private boolean canLevelUp(long pointsEarned) {
        boolean bolLevelUp = false;

        if (pointsEarned == 0) {
            return bolLevelUp;
        }
        return bolLevelUp;
    }

    private int getLevelPointsThreshold(int level) {
        int points = R.integer.default_level_points_threshold;

        for (int i = 2; i <= level; i++) {
            points += points*i;
        }

        return points;
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
            showFinishedTaskDialog();
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
}
