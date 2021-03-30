package com.example.lab5;


import android.database.Cursor;
import android.database.CursorWrapper;

import java.time.Instant;
import java.util.Date;

public class TaskCursorWrapper extends CursorWrapper {

    public TaskCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Task getTask() {
        String title = getString( getColumnIndex(Task.TITLE) );

        String id = getString( getColumnIndex(Task.ID) );

        long dateMs = getLong( getColumnIndex(Task.DATE));
        Instant instant = Instant.ofEpochMilli(dateMs);
        Date date = Date.from(instant);
        Task task = new Task(title, date, id);
        return task;
    }
}

