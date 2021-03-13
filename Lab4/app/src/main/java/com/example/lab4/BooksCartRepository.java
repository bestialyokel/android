package com.example.lab4;

import android.util.Pair;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BooksCartRepository {
    private static BooksCartRepository ourInstance = new BooksCartRepository();
    public static BooksCartRepository getInstance() {
        return ourInstance;
    }
    private BooksCartRepository(){}

    private Set< Integer > bookIds = new HashSet<>();

    public boolean addOne(Integer id) {
        return bookIds.add(id);
    }

    public List<Integer> getAll() {
        List<Integer> ret = new ArrayList<>();
        ret.addAll(bookIds);
        return ret;
    }

    public boolean removeOne(Integer id) {
        return bookIds.remove(id);
    }

    public boolean hasOne(Integer id) {
        return bookIds.contains(id);
    }





}
