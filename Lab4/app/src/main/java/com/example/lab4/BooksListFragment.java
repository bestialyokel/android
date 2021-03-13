package com.example.lab4;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class BooksListFragment extends ListFragment {

    private BookRepository bookRepository = BookRepository.getInstance();

    public BooksListFragment() {}

    public static BooksListFragment newInstance() {
        BooksListFragment fragment = new BooksListFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ArrayAdapter<Book> adapter=new ArrayAdapter<Book>(
                inflater.getContext(),
                R.layout.fragment_books_list_item,
                bookRepository.getBooks());

        setListAdapter(adapter);

        return super.onCreateView(inflater,container,savedInstanceState);
    }
}