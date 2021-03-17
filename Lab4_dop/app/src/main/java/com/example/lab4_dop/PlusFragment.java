package com.example.lab4_dop;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.function.Consumer;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlusFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlusFragment extends Fragment {


    public PlusFragment() {
    }

    private Button mButton = null;

    private View.OnClickListener cb = null;


    public static PlusFragment newInstance(View.OnClickListener cb) {
        PlusFragment fragment = new PlusFragment();

        fragment.cb = cb;

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
        View view = inflater.inflate(R.layout.fragment_plus, container, false);
        mButton = (Button) view.findViewById(R.id.inc_button);
        mButton.setOnClickListener(cb);
        return view;
    }
}