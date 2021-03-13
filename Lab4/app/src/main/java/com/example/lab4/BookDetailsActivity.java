package com.example.lab4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class BookDetailsActivity extends AppCompatActivity {

    public static final String BOOK_ID = "bookId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        final int bookId = getIntent().getIntExtra(BOOK_ID, -1);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.activity_book_detail,
                        BookDetailsFragment.newInstance(bookId))
                .commit();
    }

}