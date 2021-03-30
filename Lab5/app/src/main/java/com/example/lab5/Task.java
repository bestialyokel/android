package com.example.lab5;

import java.util.Date;

public class Task {

    public static final String TABLE_NAME = "tasks";
    public static final String TITLE = "title";
    public static final String DATE = "date";
    public static final String ID = "_id";

    private String mTitle;
    private Date mDate;
    private String mId;

    public Task(String title, Date date, String id) {
        mTitle = title;
        mDate = date;
        mId = id;
    }

    public Date getDate() {
        return mDate;
    }

    public String getId() {
        return mId;
    }

    public void setDate(Date date) {
        this.mDate = date;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    @Override
    public String toString() {
        return "Задача: "+ mTitle +", дата: "+ mDate;
    }

}
