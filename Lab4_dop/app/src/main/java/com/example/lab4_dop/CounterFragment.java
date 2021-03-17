package com.example.lab4_dop;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CounterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CounterFragment extends Fragment {

    public CounterFragment() {
    }

    private TextView mCounter = null;

    public void setCounterText(String s) {
        mCounter.setText(s);
    }

    public static CounterFragment newInstance() {
        CounterFragment fragment = new CounterFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_display, container,false);
        mCounter = (TextView) view.findViewById(R.id.counter);
        mCounter.setText("0");
        return view;
    }
}