package com.example.lab5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {

    private Context mContext;
    private List<Task> mTasks;

    private TaskHolder.OnTaskClickListeners listener;

    public void setTasks(List<Task> tasks) {
        mTasks.clear();
        mTasks.addAll(tasks);
        this.notifyDataSetChanged();
    }


    public TaskAdapter(Context ctx, List<Task> tasks, TaskHolder.OnTaskClickListeners listener) {
        mContext = ctx;
        mTasks = tasks;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.task_list_item, parent, false);

        TaskHolder taskHolder = new TaskHolder(mContext, view);

        return taskHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
        Task task = mTasks.get(position);

        holder.bindTask(task, listener, position);
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

}

