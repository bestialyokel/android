package com.example.textreader;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Type;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PageFragment extends Fragment {

    public static String DATA = "page_data";
    public static String TEXT_SIZE = "text_size";
    public static String SPACING = "spacing";

    String mData;
    int mTextSize;
    int mSpacing;
    TextView mText;

    public PageFragment() {}

    public static PageFragment newInstance(String data, int textSize, int spacing) {
        PageFragment fragment = new PageFragment();
        Bundle args = new Bundle();
        args.putString(DATA, data);
        args.putInt(TEXT_SIZE, textSize);
        args.putInt(SPACING, spacing);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            this.mData = args.getString(DATA, "no value");
            this.mTextSize = args.getInt(TEXT_SIZE, 0);
            this.mSpacing = args.getInt(SPACING, 0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);
        this.mText = view.findViewById(R.id.text);
        this.mText.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        this.mText.setIncludeFontPadding(false);
        this.mText.setLineHeight(mSpacing);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.mText.setText(this.mData);
    }
}