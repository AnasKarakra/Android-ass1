package com.example.ass1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity_addTask extends AppCompatActivity {

    private ArrayList<Task> arrlisttask;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    public static final String DATA = "DATA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtask);

        EditText name=findViewById(R.id.name);
        EditText description=findViewById(R.id.description);
        Button b1=findViewById(R.id.addnewTask);

        setupSharedPrefs();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                arrlisttask=new ArrayList<>();
                String prevs_TasksString = prefs.getString(DATA, "");

                if (!prevs_TasksString.isEmpty()) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<ArrayList<Task>>() {}.getType();
                    arrlisttask = gson.fromJson(prevs_TasksString, type);
                }

                Task t=null;
                t=new Task(name.getText().toString(),description.getText().toString(),"Due");
                arrlisttask.add(t);

                Gson gson = new Gson();
                String tasksString = gson.toJson(arrlisttask);

                editor.putString(DATA, tasksString);
                editor.apply();

                Intent intent = new Intent(MainActivity_addTask.this, MainActivity.class);
                startActivity(intent);
            }
        });



    }



    private void setupSharedPrefs() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
    }
}
