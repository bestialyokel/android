package com.example.textreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;

import com.google.android.material.tabs.TabLayout;

import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.List;

public class ReadingActivity extends AppCompatActivity {

    public static String DATA = "data";

    public static int TEXT_SIZE = 64;
    public static int SPACING = 64;

    public static int PT_MUL = 72;

    ViewPager2 mPager;
    PageFragmentStateAdapter mAdapter;

    List<String> pages;

    public static class ScreenSize {
        public ScreenSize(double x, double y) {
            xPixels = x;
            yPixels = y;
        }
        public double xPixels;
        public double yPixels;
    }

    //я хз
    private ScreenSize getDimension() {
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        // status bar height
        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        int height = (int) (Resources.getSystem().getDisplayMetrics().heightPixels * 0.9);
        return new ScreenSize(width, height);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);

        Intent intent = getIntent();
        Bundle args = intent.getExtras();

        if (args != null) {
            String data = args.getString(DATA, "");
            pages = splitString(data, getDimension());
        }

        mPager = findViewById(R.id.pager);
        mAdapter = new PageFragmentStateAdapter(this, TEXT_SIZE, SPACING);

        mPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });
        mAdapter.appendList(pages);
        mPager.setAdapter(mAdapter);
    }

    private List<String> splitString(String data, ScreenSize size) {
        List<String> pages = new ArrayList<>();

        final double MAX_LINE_LETTERS = size.xPixels / (TEXT_SIZE * 0.6);
        final double MAX_LINES = size.yPixels / (TEXT_SIZE + 1);
        final double MAX_CHARS = MAX_LINE_LETTERS * MAX_LINES;

        System.out.println(MAX_CHARS);
        System.out.println(size.xPixels);
        System.out.println(size.yPixels);

        data = data.toUpperCase();

        double availableSpace = MAX_CHARS;
        StringBuilder pageBuf = new StringBuilder();
        String[] lines = data.split(System.lineSeparator());
        for (String line : lines) {
            String[] words = line.split(" ");
            for (int i = 0; i < words.length; i++) {
                String word = words[i];
                if (availableSpace < word.length()) {
                    pages.add(new String(pageBuf));
                    pageBuf = new StringBuilder();
                    availableSpace = MAX_CHARS;
                }
                pageBuf.append(words[i]);
                pageBuf.append(" ");
                availableSpace -= word.length() + 1;
            }
            pageBuf.append(System.lineSeparator());
            availableSpace -= (MAX_LINE_LETTERS - availableSpace % MAX_LINE_LETTERS);
        }

        pages.add(new String(pageBuf));

        return pages;
    }
}