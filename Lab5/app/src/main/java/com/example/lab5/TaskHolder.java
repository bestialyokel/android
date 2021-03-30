package com.example.lab5;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;

public class TaskHolder extends RecyclerView.ViewHolder {
    private TextView mTitle;
    private TextView mDate;
    private CheckBox mCheck;
    private View mView;

    private int mPosition;

    private Context mContext;
    private Task mTask;

    public interface OnTaskClickListeners {
        void onTaskClick(Task task);
        void onCheckBoxClick(Task task);
    }

    public TaskHolder(Context context, View itemView) {
        super(itemView);
        mContext = context;

        mView = itemView;
        mTitle = (TextView) itemView.findViewById(R.id.task_title);
        mDate = (TextView) itemView.findViewById(R.id.task_date);
        mCheck = (CheckBox) itemView.findViewById(R.id.checkBox5);
    }

    public void bindTask(Task task, OnTaskClickListeners listener, int position) {
        mPosition = position;
        mTask = task;
        mTitle.setText(task.getTitle());
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        mDate.setText(simpleDateFormat.format(task.getDate()));
        mCheck.setChecked(true);

        if (listener != null) {
            mView.setOnClickListener(v -> listener.onTaskClick(mTask));
            mCheck.setOnClickListener(v -> listener.onCheckBoxClick(mTask));
        }

    }
}