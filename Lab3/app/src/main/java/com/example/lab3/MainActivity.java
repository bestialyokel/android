package com.example.lab3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;

import java.lang.Runnable;


public class MainActivity extends AppCompatActivity {

    private static final String IS_RUN_KEY = "IsRun";
    private static final String WAS_RUN = "WasRun";
    private static final String START_TS = "StartTS";
    private static final String STOP_TS = "StopTS";

    private TextView mTimerView = null;

    private Handler mHandler = new Handler();
    private boolean isRunning = false;
    private boolean wasRunning = false;

    private long startTS = 0;
    private long stopTS = 0;

    private void updateView(long msElapsed) {
        byte minutes = (byte) (msElapsed / (60 * 1000));
        byte seconds = (byte) ((msElapsed - minutes * 60 * 1000 ) / 1000);
        byte ms = (byte) ((msElapsed % 1000) / 10);
        String text = String.format("%02d" , minutes) + ":" + String.format("%02d" , seconds) + "," + String.format("%02d" , ms);
        mTimerView.setText(text);
    }

    private void startTimer() {
        long now = SystemClock.elapsedRealtime();
        if (startTS == 0)
            startTS = now;

        if (stopTS == 0)
            stopTS = now;

        isRunning = true;
        startTS = now - (stopTS - startTS);

        mHandler.post(timerRunnable);
    }

    private void stopTimer() {
        isRunning = false;
        stopTS = SystemClock.elapsedRealtime();

        mHandler.removeCallbacks(timerRunnable);
    }

    private void resetTimer() {
        isRunning = false;
        startTS = 0;
        stopTS = 0;

        mHandler.removeCallbacks(timerRunnable);
    }

    public void startHandler(View v) {
        if (isRunning)
            return;
        startTimer();
    }
    public void stopHandler(View v) {
        if (!isRunning)
            return;
        stopTimer();
    }
    public void resetHandler(View v) {
        if (startTS == 0 && stopTS == 0)
            return;

        resetTimer();
        updateView(0);
    }

    private Runnable timerRunnable = new Runnable() {
        public void run() {
            if (!isRunning)
                return;

            long msElapsed = SystemClock.elapsedRealtime() - startTS;
            updateView(msElapsed);
            mHandler.postDelayed(this, 25);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        if (isRunning) {
            wasRunning = true;
            stopTimer();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateView(stopTS - startTS);
        if (wasRunning) {
            startTimer();
            wasRunning = false;
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTimerView = findViewById(R.id.timer_view);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putLong(START_TS, startTS);
        outState.putBoolean(WAS_RUN, wasRunning);
        outState.putLong(STOP_TS, stopTS);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        wasRunning = savedInstanceState.getBoolean(WAS_RUN);
        startTS = savedInstanceState.getLong(START_TS);
        stopTS = savedInstanceState.getLong(STOP_TS, stopTS);

    }
}