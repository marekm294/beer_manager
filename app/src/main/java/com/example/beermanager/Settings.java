package com.example.beermanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Set;

public class Settings extends AppCompatActivity {

    CheckBox checkBox;
    TimePicker timePicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        getSupportActionBar().setTitle("Nastavení");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        createNotificationChannel();

        checkBox = findViewById(R.id.settings_chb1_tp1);
        timePicker = findViewById(R.id.settings_tp);

        SharedPreferences preferences = getSharedPreferences("CHECK_BOX", MODE_PRIVATE);

        boolean myCheckBox = preferences.getBoolean("myCheckBox", false);
        checkBox.setChecked(myCheckBox);

        int hour = preferences.getInt("myHour", 0);
        int minute = preferences.getInt("myMinute", 0);
        if(Build.VERSION.SDK_INT>=23) {
            timePicker.setHour(hour);
            timePicker.setMinute(minute);
        }
        else{
            timePicker.setCurrentHour(hour);
            timePicker.setCurrentMinute(minute);
        }

    }

    public void returnHome(View v){
        Intent newIntent = new Intent(this, MainActivity.class);
        startActivity(newIntent);
    }
    public void setTime(View v){
        //komponenty
        checkBox = findViewById(R.id.settings_chb1_tp1);
        timePicker = findViewById(R.id.settings_tp);
        //cas z pickru
        int hour;
        int minute;
        //ulozeni sharedPreferences
        SharedPreferences preferences = getSharedPreferences("CHECK_BOX", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("myCheckBox", checkBox.isChecked());

        if(Build.VERSION.SDK_INT >=23){
            hour = timePicker.getHour();
            minute = timePicker.getMinute();
        }else{
            hour = timePicker.getCurrentHour();
            minute = timePicker.getCurrentMinute();
        }
        editor.putInt("myHour", hour);
        editor.putInt("myMinute", minute);

        editor.apply();
        Toast.makeText(Settings.this, "Nastavení uloženo", Toast.LENGTH_SHORT).show();
        //ziskani roku,mesice,dnu
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), hour, minute, 0);
        //nastaveni alarm manageru
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(Settings.this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(Settings.this, 0, intent, 0);

        if(checkBox.isChecked()){

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            Toast.makeText(this, "Alarm byl nastaven!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            alarmManager.cancel(pendingIntent);
            Toast.makeText(Settings.this, "Alarm byl zrušen", Toast.LENGTH_SHORT ).show();
        }

    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "LemubitReminderChannel";
            String description = "Channel for Lemubit Reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel("myChannel", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }

    }

}