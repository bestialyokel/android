package com.example.lab5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskManager {

    private SQLiteDatabase mDatabase;
    private Context mContext;

    public TaskManager(Context ctx) {
        mContext = ctx;
        mDatabase = new TaskDbHelper(mContext).getWritableDatabase();
    }

    public static ContentValues getContentValues(Task task) {
        ContentValues values = new ContentValues();
        values.put(Task.TITLE, task.getTitle());
        values.put(Task.DATE, task.getDate().getTime());
        return values;
    }

    public void addTask(Task task) {
        ContentValues values = getContentValues(task);
        mDatabase.insert(Task.TABLE_NAME, null, values);
    }

    public void removeTask(String id) {
        Task task = null;
        mDatabase.delete(Task.TABLE_NAME, "_id="+id, null);
    }

    private TaskCursorWrapper queryTask(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(Task.TABLE_NAME,null, whereClause, whereArgs,null,null,null);
        return new TaskCursorWrapper(cursor);
    }

    public List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();
        TaskCursorWrapper cursor = queryTask(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                tasks.add(cursor.getTask());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return tasks;
    }

    public Task getById(String id) {
        Task task = null;
        TaskCursorWrapper cursor = queryTask("_id=" + id, null);
        try {
            cursor.moveToFirst();
            task = cursor.getTask();
        } finally {
            cursor.close();
        }
        return task;
    }

}
