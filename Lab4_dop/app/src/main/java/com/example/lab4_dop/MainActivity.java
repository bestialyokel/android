package com.example.lab4_dop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;

import java.util.function.BiFunction;
import java.util.function.Consumer;

public class MainActivity extends AppCompatActivity {

    private int counter = 0;

    private PlusFragment incFragment = null;
    private CounterFragment counterFragment = null;

    View.OnClickListener inc = (v) -> {
        counter++;
        counterFragment.setCounterText(String.valueOf(counter));
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();

        incFragment = PlusFragment.newInstance(inc);
        counterFragment = CounterFragment.newInstance();

        fm.beginTransaction()
                .add(R.id.inc, incFragment)
                .add(R.id.counter, counterFragment)
                .commit();

    }
}