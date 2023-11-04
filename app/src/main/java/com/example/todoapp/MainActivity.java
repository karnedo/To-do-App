package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.Date;

import models.Task;


public class MainActivity extends AppCompatActivity {

    private RecyclerView rv_tasks;

    private TaskListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv_tasks = (RecyclerView) findViewById(R.id.rv_tasks);
        //------------------------------------------------------------------------------------------
        //Iniciamos el array de tareas y añadimos una tarea de ejemplo
        ArrayList<Task> taskList = new ArrayList<>();
        taskList.add(new Task("Go shopping", new Date(), Task.Priority.MED));
        Task task = new Task("Go shopping", new Date(), Task.Priority.MED);
        task.setChecked(true);
        taskList.add(task);

        adapter = new TaskListAdapter(taskList, this);
        rv_tasks.setAdapter(adapter);
        int orientation = getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            //In landscape
            rv_tasks.setLayoutManager(new GridLayoutManager(this, 2));
        }else{
            //In portrait
            rv_tasks.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    public void goToAddCard(View view){
        Intent intent = new Intent(this, AddCardActivity.class);
        startActivity(intent);
    }

}