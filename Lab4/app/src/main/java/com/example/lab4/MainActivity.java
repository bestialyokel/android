package com.example.lab4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private BookRepository bookRepository = BookRepository.getInstance();
    private BooksCartRepository cartRepository = BooksCartRepository.getInstance();

    private FrameLayout mContainer = null;

    private Button goCart;

   public void handleGoCart(View v) {
        if (mContainer != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.book_detail_container,
                            CartFragment.newInstance())
                    .addToBackStack(null)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        } else {
            Intent intent = new Intent(this, CartActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView list = (ListView) findViewById(android.R.id.list);

        MainActivity self = this;

        mContainer = (FrameLayout) findViewById(R.id.book_detail_container);

        goCart = findViewById(R.id.go_cart_button);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mContainer != null) {
                       self.getSupportFragmentManager().beginTransaction()
                               .replace(R.id.book_detail_container,
                                       BookDetailsFragment.newInstance(position))
                               .addToBackStack(null)
                               .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                               .commit();
                    return;
                }
                Intent intent = new Intent(self, BookDetailsActivity.class);
                intent.putExtra(BookDetailsActivity.BOOK_ID, position);
                startActivity(intent);
            }
        });

    }
}