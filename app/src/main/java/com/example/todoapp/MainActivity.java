package com.example.todoapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import models.Task;


public class MainActivity extends AppCompatActivity {

    private RecyclerView rv_tasks;

    private TaskListAdapter adapter;
    private ArrayList<Task> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv_tasks = findViewById(R.id.rv_tasks);
        //------------------------------------------------------------------------------------------
        //Iniciamos el array de tareas
        if(savedInstanceState != null){
            taskList = (ArrayList<Task>) savedInstanceState.getSerializable("taskList");
        } else{
            //Cargamos las tareas guardadas
            taskList = new ArrayList<>();
            loadTasks();
            //Si es la primera vez que se inicia la app, anyadimos algunas tareas de ejemplo
            SharedPreferences preferences = getSharedPreferences("com.example.todoapp", MODE_PRIVATE);
            if(preferences.getBoolean("firstOpened", true)){
                taskList.add(new Task("Go shopping", new Date(), Task.Priority.MED));
                taskList.add(new Task("Study", new Date(), Task.Priority.HIGH));
                taskList.add(new Task("Bake cakes!", new Date(), Task.Priority.LOW));
                Task appTask = new Task("Make app", new Date(), Task.Priority.MED);
                appTask.setChecked(true);
                taskList.add(appTask);
                preferences.edit().putBoolean("firstOpened", false).apply();
            }
        }

        //Antes de mostrar la lista, la ordenamos.
        /*taskList.sort(new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                if(o1.isChecked()) return 1;
                if(o2.isChecked()) return -1;
                return o1.getDate().compareTo(o2.getDate());
            }
        });*/
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

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("taskList", taskList);
    }

    public void goToAddCard(View view){
        Intent intent = new Intent(this, AddCardActivity.class);
        taskAdder.launch(intent);
    }

    public void loadTasks(){
        File path = getApplicationContext().getFilesDir();
        File readFrom = new File(path, "notes.dat");
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        if(readFrom.exists() && readFrom.canRead()){
            try {
                fis = new FileInputStream(readFrom);
                ois = new ObjectInputStream(fis);
                Task task;
                while( (task = (Task)ois.readObject()) != null ){
                    taskList.add(task);
                }
            } catch (IOException | ClassNotFoundException e) {
            } finally {
                try {
                    ois.close();
                    fis.close();
                } catch (IOException e) {}
            }
        }
    }

    public void saveTasks(){
        File path = getApplicationContext().getFilesDir();
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(new File(path, "notes.dat"));
            oos = new ObjectOutputStream(fos);
            for(Task task : taskList){
                oos.writeObject(task);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                fos.close();
                oos.close();
            } catch (IOException e) {}
        }
    }

    @Override
    protected void onDestroy() {
        saveTasks();
        super.onDestroy();
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