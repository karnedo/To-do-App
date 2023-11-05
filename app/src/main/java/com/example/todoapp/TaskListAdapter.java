package com.example.todoapp;

import static com.example.todoapp.R.layout.task_rv;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import models.Task;

public class TaskListAdapter extends RecyclerView.Adapter<TaskViewHolder>{

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

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        final Task task = tasks.get(position);
        String date = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(task.getDate());
        String priority = context.getString(task.getPriority().getStringId());
        holder.getFab_button().setVisibility( (task.isChecked()) ? View.VISIBLE : View.GONE );
        holder.getTxt_name().setText(task.getName());
        holder.getTxt_date().setText(date);
        holder.getTxt_priority().setText(priority);
        holder.getCb_done().setChecked(task.isChecked());

        Log.d("MyApp", "Position: " + holder.getListPosition());
        //Anyadimos el listener a los botones de borrar y marcar como terminada.
        holder.getFab_button().setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Si el botón de borrar se presiona, se borra la tarea seleccionada.
                int pos = holder.getListPosition();
                tasks.remove(pos);
                notifyItemRemoved(pos);
                notifyItemRangeChanged(pos, tasks.size());
            }
        });

        holder.getCb_done().setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                boolean checked = holder.getCb_done().isChecked();
                //Mostramos el botón de borrar si la tarea está completada.
                int pos = holder.getListPosition();
                holder.getFab_button().setVisibility( (checked) ? View.VISIBLE : View.GONE );
                task.setChecked(checked);
                notifyItemChanged(pos);

                //Task movedTask = tasks.get(pos);
                if(checked){
                    //Lo llevamos al final de la lista
                    moveTaskToEnd(pos);
                }else{
                    moveTaskToStart(pos);
                }
            }
        });
    }

    public void moveTaskToStart(int pos){
        Task task = tasks.remove(pos);
        tasks.add(0, task);
        notifyItemMoved(pos, 0);

    }

    public void moveTaskToEnd(int pos){
        Task task = tasks.remove(pos);
        tasks.add(task);
        notifyItemMoved(pos, tasks.size() - 1);

    }

    public void addTask(Task task){
        tasks.add(0, task);
        notifyItemInserted(0);
    }

    @Override
    public int getItemCount() { return tasks.size(); }

}
