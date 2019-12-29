package com.example.hashimoto_app;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.hashimoto_app.backend.DataHolder;
import com.example.hashimoto_app.backend.FileManager;
import com.google.gson.Gson;

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
        String symptom = new Gson().fromJson(FileManager.getFileAsString("test", getApplicationContext()), DataHolder.class)
                .getSymptomNotUpdatedForOneDay();
        sendOnChannel(symptom);
    }
    private void createNotificationChannel()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Benachrichtigungen",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription("Es werden Benachrichtigungen zu aktuellen Therapievorgängen gesendet");
            NotificationManager manager = getSystemService(NotificationManager.class);
            Objects.requireNonNull(manager).createNotificationChannel(channel);
        }
    }

    public void sendOnChannel(String symptom)
    {
        if(symptom != null)
        {
            Intent resultIntent = new Intent(this, MainActivity.class);
            PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 1, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle("Therapievorgang")
                    .setContentText("Wie hat sich das Symptom \""+ symptom +"\" entwickelt?")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .setAutoCancel(true)
                    .setContentIntent(resultPendingIntent)
                    .build();

            // just one notification channel
            notificationManagerCompat.notify(1, notification);
        }
    }
}
