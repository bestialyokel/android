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
import android.util.TypedValue;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.List;

public class ReadingActivity extends AppCompatActivity {

    public static String DATA = "data";

    public static int TEXT_SIZE = 64;
    public static int SPACING = TEXT_SIZE;

    ViewPager2 mPager;
    PageFragmentStateAdapter mAdapter;

    Toast pageToast;

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
        int height = (int) (Resources.getSystem().getDisplayMetrics().heightPixels) - statusBarHeight;
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
                String pos = String.valueOf(position+1);
                String max = String.valueOf(pages.size());
                if (pageToast != null) {
                    pageToast.cancel();
                    pageToast = null;
                }
                pageToast =Toast.makeText(ReadingActivity.this, pos + "/" + max, Toast.LENGTH_SHORT);
                pageToast.show();
            }
        });
        mAdapter.appendList(pages);
        mPager.setAdapter(mAdapter);
    }

    private float dpToPx(float dp) {
        Resources r = getResources();
        float px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                r.getDisplayMetrics()
        );
        return px;
    }

    private List<String> splitString(String data, ScreenSize size) {
        List<String> pages = new ArrayList<>();

        double mul = Math.sqrt(2) * 1.175;

        final int MAX_LINE_LETTERS = (int) (size.xPixels / TEXT_SIZE * mul);
        final int MAX_LINES = (int) (size.yPixels / SPACING);

        System.out.println(size.yPixels);
        System.out.println(size.xPixels);

        data = data.toUpperCase();

        StringBuilder pageBuf = new StringBuilder();
        String[] lines = data.split(System.lineSeparator());
        int availableLines = (int) MAX_LINES;
        int availableLineSpace = (int) MAX_LINE_LETTERS;
        for (String line : lines) {
            String[] words = line.split(" ");
            for (int i = 0; i < words.length; i++) {
                String word = words[i];
                if (word.length() > availableLineSpace) {
                    availableLines -= 1;
                    availableLineSpace = MAX_LINE_LETTERS;
                }
                if (availableLines == 0) {
                    pages.add(new String(pageBuf));
                    pageBuf = new StringBuilder();
                    availableLines = MAX_LINES;
                }
                pageBuf.append(words[i]);
                pageBuf.append(" ");
                availableLineSpace -= word.length() + 1;
            }
            pageBuf.append(System.lineSeparator());
            availableLines -= 1;
            availableLineSpace = MAX_LINE_LETTERS;
        }

        pages.add(new String(pageBuf));

        return pages;
    }
}