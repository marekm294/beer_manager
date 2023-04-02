package com.example.beermanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class Yesterday extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yesterday);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        getSupportActionBar().setTitle("Váš poslední den");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
        List<TimeInfo> allBeers = dataBaseHelper.getAllBeers();

        if(!allBeers.isEmpty()) {
            ArrayAdapter arrayAdapter = new ArrayAdapter<TimeInfo>(Yesterday.this, android.R.layout.simple_list_item_1, allBeers);
            ListView lv_list = findViewById(R.id.yesterday_lv_all);
            lv_list.setAdapter(arrayAdapter);
        }
        else
            Toast.makeText(this, "Databáze je prázdná, dnes jste ještě nevypil žádné pivo!", Toast.LENGTH_SHORT).show();

    }

    public void returnHome(View v){
        Intent newIntent = new Intent(this, MainActivity.class);
        startActivity(newIntent);
    }
}