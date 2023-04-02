package com.example.beermanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        getSupportActionBar().setTitle("Menu");
    }

    public void addOneIntent(View v){
        Intent newIntent = new Intent(this, AddOne.class);
        startActivity(newIntent);

    }
    public void setLimitIntent(View v){
        Intent newIntent = new Intent(this, SetLimit.class);
        startActivity(newIntent);
    }
    public void historyIntent(View v){
        Intent newIntent = new Intent(this, History.class);
        startActivity(newIntent);
    }
    public void yesterdayIntent(View v){
        Intent newIntent = new Intent(this, Yesterday.class);
        startActivity(newIntent);
    }
    public void settingsIntent(View v){
        Intent newIntent = new Intent(this, Settings.class);
        startActivity(newIntent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.finish:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}