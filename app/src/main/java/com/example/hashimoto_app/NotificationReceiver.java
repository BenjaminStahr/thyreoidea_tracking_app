package com.example.hashimoto_app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationReceiver extends BroadcastReceiver
{
    public static final int REQUEST_CODE = 12345;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Intent notificationService = new Intent(context, NotificationService.class);
        context.startService(notificationService);
        //System.out.println("jojojojo");
    }
}
