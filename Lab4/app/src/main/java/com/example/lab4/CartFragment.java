package com.example.lab4;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {

    private BookRepository bookRepository = BookRepository.getInstance();
    private BooksCartRepository cartRepository = BooksCartRepository.getInstance();

    public CartFragment() {
    }

    public static CartFragment newInstance() {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        List<Integer> bookIds = cartRepository.getAll();
        float total = 0;
        for (Integer x : bookIds) {
            Book book = bookRepository.getBook( x.intValue() );
            total += book.getPrice();
        }

        TextView amountView = (TextView) view.findViewById(R.id.books_amount);
        TextView priceView = (TextView) view.findViewById(R.id.total_cost);

        amountView.setText( String.valueOf( bookIds.size() ) );
        priceView.setText(String.valueOf(total));

        return view;
    }
}