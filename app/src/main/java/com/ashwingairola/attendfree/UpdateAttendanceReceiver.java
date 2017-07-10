package com.ashwingairola.attendfree;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.Toast;

public class UpdateAttendanceReceiver extends BroadcastReceiver
{
    private static final String MY_PREFERENCES = "my_preferences";
    private static final String IS_ATTENDANCE_UPDATED = "is_attendance_updated";

    private Context context;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        this.context = context;
        Toast.makeText(context, "RESET!", Toast.LENGTH_SHORT).show();
        showNotification();
        Vibrator vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(3000);

        SharedPreferences preferences = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(IS_ATTENDANCE_UPDATED, false);
        editor.commit();
    }

    private void showNotification()
    {
        //1. Build the content of the notification
        NotificationCompat.Builder builder= new NotificationCompat.Builder(context);
        builder.setContentTitle("Preference Reset!");
        builder.setContentInfo("You may update your attendance again!");
        builder.setSmallIcon(R.mipmap.icon);
        builder.setTicker("Hello!");
        builder.setAutoCancel(true);

        //2. Provide the explicit intent for the notification
        Intent intent = new Intent(context, StarterSplashActivity.class);

        //3. Add to the back stack using TaskBuilder and set the intent to the PendingIntent
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(StarterSplashActivity.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(1111, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        //4. Build the notification with NotificationManager
        Notification notification = builder.build();
        NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(2222, notification);
    }
}
