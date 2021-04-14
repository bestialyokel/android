package com.example.textreader;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class PageFragmentStateAdapter extends FragmentStateAdapter {

    private List<String> mPages;

    private int mTextSize;
    private int mSpacing;

    public void pushBack(String page) {
        mPages.add(page);
        notifyItemInserted(mPages.size() - 1);
    }

    public void pushFront(String page) {
        mPages.add(0, page);
        notifyItemInserted(0);
    }

    public void popFront() {
        mPages.remove(0);
        notifyItemRemoved(0);
    }

    public void appendList(List<String> pages) {
        mPages.addAll(pages);
    }

    public void popBack() {
        int idx = mPages.size() - 1;
        mPages.remove(idx);
        notifyItemRemoved(idx);
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
