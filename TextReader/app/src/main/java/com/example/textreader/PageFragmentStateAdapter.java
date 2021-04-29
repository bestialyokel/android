package com.example.textreader;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class PageFragmentStateAdapter extends FragmentStateAdapter {

    private List<String> mPages;
    private int mTextSize;
    private int mSpacing;

    public void appendList(List<String> pages) {
        mPages.addAll(pages);
    }

    public PageFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity, int textSize, int spacing) {
        super(fragmentActivity);
        mPages = new ArrayList<String>();
        mTextSize = textSize;
        mSpacing = spacing;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        String page = mPages.get(position);
        return PageFragment.newInstance(page, mTextSize, mSpacing);
    }

    @Override
    public int getItemCount() {
        return mPages.size();
    }
}
