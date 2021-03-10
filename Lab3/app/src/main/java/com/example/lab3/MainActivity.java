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

    private TextView mTimerView = null;

    // обработчик потока - обновляет сведения о времени
    // Создаётся в основном UI-потоке
    private Handler mHandler = new Handler();
    private boolean isRunning = false;

    private long startTS = 0;

    private void updateView() {
        if (!isRunning) {
            mTimerView.setText("00:00,00");
            return;
        }

        long msElapsed = SystemClock.uptimeMillis() - startTS;

        byte minutes = (byte) (msElapsed / (60 * 1000));

        byte seconds = (byte) ((msElapsed - minutes * 60 * 1000 ) / 1000);

        byte ms = (byte) ((msElapsed % 1000) / 10);

        String text = String.format("%02d" , minutes) + ":" + String.format("%02d" , seconds) + "," + String.format("%02d" , ms);

        mTimerView.setText(text);

    }

    private void startTimer() {

        if (startTS == 0) {
            startTS = SystemClock.uptimeMillis();
        }

        isRunning = true;
        mHandler.post(timerRunnable);
    }

    private void stopTimer() {
        isRunning = false;
        mHandler.removeCallbacks(timerRunnable);
    }

    private void resetTimer() {
        startTS = 0;
        stopTimer();
    }

    public void startHandler(View v) {
        startTimer();
    }
    public void stopHandler(View v) {
        stopTimer();
    }
    public void resetHandler(View v) {
        resetTimer();
        updateView();
    }

    private Runnable timerRunnable = new Runnable() {
        public void run() {
            updateView();
            mHandler.postDelayed(this, 10);
        }
    };

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        outState.putLong(START_TS, startTS);
        outState.putBoolean(IS_RUN_KEY, isRunning);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTimerView = (TextView) findViewById(R.id.timer_view);

        if (savedInstanceState != null) {
            isRunning = savedInstanceState.getBoolean(IS_RUN_KEY);
            startTS = savedInstanceState.getLong(START_TS);
        }

    }
}