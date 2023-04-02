package com.example.beermanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.text.DateFormat;
import java.util.Calendar;

public class AddOne extends AppCompatActivity {

    TextView txt1;
    TextView txt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_one);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        getSupportActionBar().setTitle("Přidat pivo");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Calendar calendar = Calendar.getInstance();
        String myDate = DateFormat.getDateInstance().format(calendar.getTime());
        DataBaseHelper dataBaseHelper = new DataBaseHelper(AddOne.this);
        if(!dataBaseHelper.isThere(myDate)) {
            Toast.makeText(this, "Denšní limit ještě nebyl nastaven!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, SetLimit.class);
            startActivity(intent);
        }
        txt1 = findViewById(R.id.add_txt2_drunk);
        txt2 = findViewById(R.id.add_txt4_goal);
        DayInfo today = dataBaseHelper.getToday();
        txt1.setText(Integer.toString(today.getCurrent()));
        txt2.setText(Integer.toString(today.getGoal()));

    }

    public void returnHome(View v){
        Intent newIntent = new Intent(this, MainActivity.class);
        startActivity(newIntent);

    }

    public void addOneBeer(View v){

        Calendar calendar = Calendar.getInstance();
        String myTime = DateFormat.getTimeInstance().format(calendar.getTime());
        TimeInfo beer = new TimeInfo(0, myTime);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(AddOne.this);
        dataBaseHelper.updateCurrent();
        boolean b = dataBaseHelper.addOneBeer(beer);
        if(b)
            Toast.makeText(AddOne.this, "Pivo uloženo!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(AddOne.this, "Chyba v ukládání dat, zkuste to ještě jednou!", Toast.LENGTH_SHORT).show();

        txt1 = findViewById(R.id.add_txt2_drunk);
        DayInfo today = dataBaseHelper.getToday();
        txt1.setText(Integer.toString(today.getCurrent()));
        if(today.getTrue())
            dataBaseHelper.updateAchieved();

    }

}