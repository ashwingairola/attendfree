package com.ashwingairola.attendfree;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.Toast;

public class AlarmBroadcastReceiver extends BroadcastReceiver
{
    private Context context;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        this.context = context;
        Toast.makeText(context, "Enter attendance for today!", Toast.LENGTH_SHORT).show();
        showNotification();
        Vibrator vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(3000);
    }

    private void showNotification()
    {
        //1. Build the content of the notification
        NotificationCompat.Builder builder= new NotificationCompat.Builder(context);
        builder.setContentTitle("Update your attendance!");
        builder.setContentInfo("Enter your attendance for today!");
        builder.setSmallIcon(R.mipmap.icon);
        builder.setTicker("Update your attendance for today!");
        builder.setAutoCancel(true);

        //2. Provide the explicit intent for the notification
        Intent intent = new Intent(context, StarterSplashActivity.class);

        //3. Add to the back stack using TaskBuilder and set the intent to the PendingIntent
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(StarterSplashActivity.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(5678, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        //4. Build the notification with NotificationManager
        Notification notification = builder.build();
        NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1234, notification);
    }
}
