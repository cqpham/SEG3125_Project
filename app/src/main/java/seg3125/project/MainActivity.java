package seg3125.project;

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
import android.support.design.widget.Snackbar;

import org.w3c.dom.Text;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private TextView countdownText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        minPicker.setMaxValue(59);
        minPicker.setWrapSelectorWheel(true);

        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // positive button logic
                        dialog.cancel();
                        //Get duration
                        //long totalMilliseconds = hrPicker.getValue()*60*60000 + minPicker.getValue()*60000;
                        long totalMilliseconds = 2000;
                        CustomCountDownTimer customCountDownTimer = new CustomCountDownTimer(totalMilliseconds, 1000);
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

        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // positive button logic
                        dialog.cancel();
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

    private class CustomCountDownTimer extends CountDownTimer {
        public CustomCountDownTimer (long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            //start();
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
            //showFinishedTaskDialog();
            Snackbar.make(findViewById(R.id.coordinatorLayout), "Congratulations!",
                    Snackbar.LENGTH_INDEFINITE)
                    .show();
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
