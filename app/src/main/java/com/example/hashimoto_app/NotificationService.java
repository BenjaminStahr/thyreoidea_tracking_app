package com.example.hashimoto_app;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Objects;

public class NotificationService extends IntentService
{
    // the channel id of the notification channel
    public static final String CHANNEL_ID = "pushChannelID";
    private NotificationManagerCompat notificationManagerCompat;
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor
     */
    public NotificationService()
    {
        super("notificationService");
        setIntentRedelivery(true);
    }
    @Override
    public void onCreate()
    {
        super.onCreate();
        createNotificationChannel();
        notificationManagerCompat = NotificationManagerCompat.from(this);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent)
    {
        System.out.println("asdfasdfasdf");
        //Log.i("notificationService", "Service running");
        sendOnChannel();
    }
    private void createNotificationChannel()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Benachrichtigungen",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Es werden Benachrichtigungen zu aktuellen Therapievorgängen gesendet");
            NotificationManager manager = getSystemService(NotificationManager.class);
            Objects.requireNonNull(manager).createNotificationChannel(channel);
        }
    }

    public void sendOnChannel()
    {
        //String title = editTextTitle.getText().toString();
        //String message = editTextMessage.getText().toString();
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Therapievorgang")
                .setContentText("Wie hat sich das Symptom \"Zittern\" entwickelt?")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManagerCompat.notify(1, notification);
    }
}
