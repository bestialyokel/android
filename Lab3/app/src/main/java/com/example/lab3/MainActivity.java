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
    private static final String START_TS = "StartTS";
    private static final String STOP_TS = "StopTS";

    private TextView mTimerView = null;

    private Handler mHandler = new Handler();
    private boolean isRunning = false;

    private boolean wasRunning = false;

    private long startTS = 0;
    private long stopTS = 0;

    private void updateView() {
        mTimerView.setText("00:00,00");
    }

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

        startTS = now - (stopTS - startTS);

        isRunning = true;
        mHandler.post(timerRunnable);
    }

    private void stopTimer() {
        isRunning = false;
        stopTS = SystemClock.elapsedRealtime();
        mHandler.removeCallbacks(timerRunnable);
    }

    private void resetTimer() {
        startTS = 0;
        stopTS = 0;
        isRunning = false;
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
        updateView();
    }

    private Runnable timerRunnable = new Runnable() {
        public void run() {
            long msElapsed = SystemClock.elapsedRealtime() - startTS;
            updateView(msElapsed);
            mHandler.postDelayed(this, 25);
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        if (isRunning) {
            wasRunning = true;
            stopTimer();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
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
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        outState.putLong(START_TS, startTS);
        outState.putBoolean(IS_RUN_KEY, isRunning);
        outState.putLong(STOP_TS, stopTS);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        isRunning = savedInstanceState.getBoolean(IS_RUN_KEY);
        startTS = savedInstanceState.getLong(START_TS);
        stopTS = savedInstanceState.getLong(STOP_TS, stopTS);
    }
}