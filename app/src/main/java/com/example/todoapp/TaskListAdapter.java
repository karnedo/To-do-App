package com.example.todoapp;

import static com.example.todoapp.R.layout.task_rv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import models.Task;

public class TaskListAdapter extends RecyclerView.Adapter<TaskViewHolder> {

    private final ArrayList<Task> tasks;

    private final Context context;

    private final LayoutInflater inflater;

    public TaskListAdapter(ArrayList<Task> tasks, Context context) {
        this.tasks = tasks;
        this.context = context;
        this.inflater = LayoutInflater.from(this.context);
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myItemView = inflater.inflate(task_rv, parent, false);
        return new TaskViewHolder(myItemView);
    }

    public void checkTask(View view){

    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        String date = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(task.getDate());
        String priority = context.getString(task.getPriority().getStringId());
        holder.getFab_button().setVisibility( (task.isChecked()) ? View.VISIBLE : View.GONE );
        holder.getTxt_name().setText(task.getName());
        holder.getTxt_date().setText(date);
        holder.getTxt_priority().setText(priority);
        holder.getCb_done().setChecked(task.isChecked());
    }

    @Override
    public int getItemCount() { return tasks.size(); }
}
