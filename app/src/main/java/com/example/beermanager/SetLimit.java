package com.example.beermanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class SetLimit extends AppCompatActivity {

    EditText editText;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_limit);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        getSupportActionBar().setTitle("Nastavení dnešního cíle");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Calendar calendar = Calendar.getInstance();
        String myDate = DateFormat.getDateInstance().format(calendar.getTime());
        DataBaseHelper dataBaseHelper = new DataBaseHelper(SetLimit.this);
        if(dataBaseHelper.isThere(myDate)) {
            checkBox = findViewById(R.id.set_chb_todayCheck);
            checkBox.setChecked(true);
        }

    }

    public void plusMinus(View v) {
        editText = findViewById(R.id.set_et_numberInput);
        String s = editText.getText().toString();
        try {
            int i = Integer.parseInt(s);

            if (v == findViewById(R.id.set_btn1_plus)) {
                i++;
                editText.setText(Integer.toString(i));
            } else if (v == findViewById(R.id.set_btn2_minus)) {
                if (i > 0)
                    i--;
                editText.setText(Integer.toString(i));
            }
        } catch (Exception e) {
            editText.setText(Integer.toString(0));

        }
    }
    public void returnHome(View v){
        Intent newIntent = new Intent(this, MainActivity.class);
        startActivity(newIntent);

    }
    public void setLimit(View v){

        DataBaseHelper dataBaseHelper = new DataBaseHelper(SetLimit.this);
        Calendar calendar = Calendar.getInstance();
        checkBox = findViewById(R.id.set_chb_todayCheck);
        editText = findViewById(R.id.set_et_numberInput);
        DayInfo newDay;

        if(Integer.parseInt(editText.getText().toString()) >= 0) {
            if (!checkBox.isChecked()) {
                checkBox.setChecked(true);
                dataBaseHelper.updateIsLast();
                dataBaseHelper.deleteBeers();

                String myDate = DateFormat.getDateInstance().format(calendar.getTime());
                int goal = Integer.parseInt(editText.getText().toString());
                newDay = new DayInfo(0, myDate, goal, 0, true);
                dataBaseHelper.addOneDay(newDay);
                Toast.makeText(SetLimit.this, "Dnešní limit nastaven", Toast.LENGTH_SHORT).show();

            } else
                Toast.makeText(SetLimit.this, "Dnešní limit je již nastaven", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(SetLimit.this, "Váš cíl nemůže být záporný", Toast.LENGTH_SHORT).show();

    }

}