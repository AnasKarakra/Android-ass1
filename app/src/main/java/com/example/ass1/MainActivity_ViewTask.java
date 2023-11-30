package com.example.ass1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity_ViewTask extends AppCompatActivity {

    ArrayList<Task> arrlisttask;
    public static final String DATA = "DATA";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        ListView listViewTask=findViewById(R.id.listViewTask);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String tasksString = prefs.getString("DATA", "");
        SharedPreferences.Editor editor = prefs.edit();
        arrlisttask=new ArrayList<>();

        if (!tasksString.isEmpty()) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Task>>() {}.getType();
            arrlisttask = gson.fromJson(tasksString, type);


            ArrayAdapter<Task> listAdapter = new ArrayAdapter<Task>(this,
                    android.R.layout.simple_list_item_1, arrlisttask);

            listViewTask.setAdapter(listAdapter);

            AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent,
                                        View view,
                                        int position,
                                        long id) {

                    arrlisttask.get((int)id).setStatus("Done");
                    String updateTasksString = gson.toJson(arrlisttask);
                    editor.putString(DATA, updateTasksString);
                    editor.apply();

                    recreate();

                }
            };
            listViewTask.setOnItemClickListener(itemClickListener);
        }
    }
}
