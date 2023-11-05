package com.example.todoapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Comparator;
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
        Task task = new Task("Make the laundry", new Date(), Task.Priority.LOW);
        task.setChecked(true);
        taskList.add(task);
        taskList.add(new Task("Go out", new Date(), Task.Priority.HIGH));
        taskList.add(new Task("Study", new Date(), Task.Priority.LOW));

        //Antes de mostrar la lista, la ordenamos.
        taskList.sort(new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                if(o1.isChecked()) return 1;
                if(o2.isChecked()) return -1;
                return o1.getDate().compareTo(o2.getDate());
            }
        });
        adapter = new TaskListAdapter(taskList, this);
        rv_tasks.setAdapter(adapter);
        int orientation = getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            //En horizontal
            rv_tasks.setLayoutManager(new GridLayoutManager(this, 2));
        }else{
            //En vertical
            rv_tasks.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    public void goToAddCard(View view){
        Intent intent = new Intent(this, AddCardActivity.class);
        taskAdder.launch(intent);
    }

    ActivityResultLauncher<Intent> taskAdder = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {
                if(o.getResultCode() == RESULT_OK){
                    //Obtenemos el Bundle y la tarea
                    Bundle extras = o.getData().getExtras();
                    Task task = (Task) extras.getSerializable(AddCardActivity.NEW_TASK);

                    //Y la anyadimos al RecyclerView
                    adapter.addTask(task);
                }
            }
    });

}