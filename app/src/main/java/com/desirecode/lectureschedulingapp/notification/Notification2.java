package com.desirecode.lectureschedulingapp.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.desirecode.lectureschedulingapp.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Notification2 extends AppCompatActivity {
    private static final String CHANNEL_ID="Notification_System";
    private static final String CHANNEL_NAME="Time Table Nottification";
    private static final String CHANNEL_DESC="Nottifications";

    public Notification2(String shortname){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

            displayNotification();
        }
    }

    public void displayNotification(){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_school_24)
                .setContentTitle("Hurry")
                .setContentText("sdsdas")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(1,mBuilder.build());

    }
}
