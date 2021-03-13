package com.example.lab4;

public class Book {
    private String mName;
    private String mDescription;
    private float mPrice;

    public Book(String name, String description, float price) {
        mName = name;
        mDescription = description;
        mPrice = price;
    }
    public String getName() {
        return mName;
    }
    public String getDescription() {
        return mDescription;
    }
    public Float getPrice() {
        return mPrice;
    }

    @Override
    public String toString(){
        return mName;
    }
}

