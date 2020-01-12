package com.example.hashimoto_app;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.hashimoto_app.backend.DataHolder;
import com.example.hashimoto_app.backend.FileManager;
import com.google.gson.Gson;

import java.util.Objects;

public class NotificationWorker extends Worker
{
    // the channel id of the notification channel
    public static final String CHANNEL_ID = "pushChannelID";
    private NotificationManagerCompat notificationManagerCompat;

    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams)
    {
        super(context, workerParams);
        createNotificationChannel();
        notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
    }

    @NonNull
    @Override
    public Result doWork()
    {
        sendOnChannel(new Gson().fromJson(FileManager.getFileAsString("userData", getApplicationContext()), DataHolder.class)
                .getSymptomNotUpdatedForOneDay());
        return Result.success();
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
            NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            Objects.requireNonNull(manager).createNotificationChannel(channel);
        }
    }

    public void sendOnChannel(String symptom)
    {
        if(symptom != null)
        {
            Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
            PendingIntent resultPendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
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
