package com.example.beermanager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        getSupportActionBar().setTitle("Historie");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    public void returnHome(View v) {
        Intent newIntent = new Intent(this, MainActivity.class);
        startActivity(newIntent);

    }

    public void deleteAll(View v) {
        AlertDialog.Builder confirmDelete = new AlertDialog.Builder(this);
        confirmDelete.setCancelable(false);
        confirmDelete.setMessage("Opravdu chcete smazat celou historii?");
        confirmDelete.setPositiveButton("ANO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(History.this, "SMAZÁNO", Toast.LENGTH_SHORT).show();
                DataBaseHelper dataBaseHelper = new DataBaseHelper(History.this);
                dataBaseHelper.deleteDays();
                Intent newIntent = new Intent(History.this, MainActivity.class);
                startActivity(newIntent);
            }
        });
        confirmDelete.setNegativeButton("NE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog showConfirmDelete = confirmDelete.create();
        showConfirmDelete.show();
    }

    public void showAll(View v) {
        showMeAll();
    }
    public void showGood(View v) {
        FragmentTwo fragmentTwo = new FragmentTwo();
        getSupportFragmentManager().beginTransaction().replace(R.id.history_fragment, fragmentTwo).commit();
    }

    public void showBad(View v) {

        FragmentThree fragmentThree = new FragmentThree();
        getSupportFragmentManager().beginTransaction().replace(R.id.history_fragment, fragmentThree).commit();

    }

    private void showMeAll() {

        FragmentOne fragmentOne = new FragmentOne();
        getSupportFragmentManager().beginTransaction().replace(R.id.history_fragment, fragmentOne).commit();
    }
}