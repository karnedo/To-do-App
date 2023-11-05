package com.example.todoapp;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TaskViewHolder extends RecyclerView.ViewHolder{

    private TextView txt_name;
    private TextView txt_date;
    private TextView txt_priority;
    private CheckBox cb_done;

    private FloatingActionButton fab_button;

    public TaskViewHolder(@NonNull View itemView) {
        super(itemView);
        txt_name = (TextView) itemView.findViewById(R.id.txt_name);
        txt_date = (TextView) itemView.findViewById(R.id.txt_date);
        txt_priority = (TextView) itemView.findViewById(R.id.txt_priority);
        cb_done = (CheckBox) itemView.findViewById(R.id.cb_done);
        fab_button = (FloatingActionButton) itemView.findViewById(R.id.fab_delete);
    }

    public int getListPosition(){
        return this.getLayoutPosition();
    }

    public TextView getTxt_name() {
        return txt_name;
    }

    public void setTxt_name(TextView txt_name) {
        this.txt_name = txt_name;
    }

    public TextView getTxt_date() {
        return txt_date;
    }

    public void setTxt_date(TextView txt_date) {
        this.txt_date = txt_date;
    }

    public TextView getTxt_priority() {
        return txt_priority;
    }

    public void setTxt_priority(TextView txt_priority) {
        this.txt_priority = txt_priority;
    }

    public CheckBox getCb_done() {
        return cb_done;
    }

    public void setCb_done(CheckBox cb_done) {
        this.cb_done = cb_done;
    }

    public FloatingActionButton getFab_button() {
        return fab_button;
    }

    public void setFab_button(FloatingActionButton fab_button) {
        this.fab_button = fab_button;
    }
}
