package com.example.beermanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


public class AlarmReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        DayInfo today = dataBaseHelper.getToday();
        int currentNumber = today.getCurrent();
        String current = Integer.toString(currentNumber);
        String output = "Piv vypito: " + current;

        if(!today.getTrue()) {
            int goalNumber = today.getGoal();
            String goal = Integer.toString(goalNumber - currentNumber);
            output = output + " Ještě Vám zbývá: " + goal;
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "myChannel")
                .setSmallIcon(R.drawable.ic_baseline_message_24)
                .setContentTitle("Dnes Vám ještě zbývá...")
                .setContentText(output)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(200, builder.build());


    }
}