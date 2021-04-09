package com.example.textreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

public class ReadingActivity extends AppCompatActivity {

    View.OnTouchListener l = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            System.out.println(event);
            return false;
        }
    };

    TabLayout mTabs;
    ConstraintLayout mPageContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);

        mTabs = findViewById(R.id.tabs);
        mPageContainer = findViewById(R.id.container);
    }
}