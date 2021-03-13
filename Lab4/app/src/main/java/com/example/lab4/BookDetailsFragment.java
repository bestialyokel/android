package com.example.lab4;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookDetailsFragment extends Fragment {

    private BookRepository bookRepository = BookRepository.getInstance();
    private BooksCartRepository cartRepository = BooksCartRepository.getInstance();

    private int id;
    private String mName;
    private String mDescription;
    private float mPrice;

    private Button addButton = null;
    private Button removeButton = null;

    public BookDetailsFragment() {}

    public void handleAdd(View v) {
        cartRepository.addOne( Integer.valueOf(id) );
        addButton.setVisibility(View.GONE);
        removeButton.setVisibility(View.VISIBLE);
    }

    public void handleRemove(View v) {
        cartRepository.removeOne( Integer.valueOf(id) );
        removeButton.setVisibility(View.GONE);
        addButton.setVisibility(View.VISIBLE);
    }

    // TODO: Rename and change types and number of parameters
    public static BookDetailsFragment newInstance(int bookId) {
        BookDetailsFragment fragment = new BookDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(BookDetailsActivity.BOOK_ID, bookId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            int id = args.getInt(BookDetailsActivity.BOOK_ID);
            Book book = bookRepository.getBook(id);
            this.id = id;
            mName = book.getName();
            mDescription = book.getDescription();
            mPrice = book.getPrice();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View details = inflater.inflate(R.layout.fragment_book_detail, container, false);

        TextView name = details.findViewById(R.id.name_text_view);
        TextView description = details.findViewById(R.id.description_text_view);
        TextView price = details.findViewById(R.id.price_text_view);

        addButton = details.findViewById(R.id.add_button);
        removeButton = details.findViewById(R.id.remove_button);

        BookDetailsFragment self = this;

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                self.handleAdd(v);
            }
        });

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                self.handleRemove(v);
            }
        });

        if ( cartRepository.hasOne(Integer.valueOf(id)) ) {
            addButton.setVisibility(View.GONE);
        } else {
            removeButton.setVisibility(View.GONE);
        }

        name.setText(mName);
        description.setText(mDescription);
        price.setText(String.valueOf(mPrice));

        return details;
    }
}