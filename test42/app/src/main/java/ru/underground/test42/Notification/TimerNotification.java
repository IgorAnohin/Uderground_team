package ru.underground.test42.Notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.app.AlarmManager;
import android.content.BroadcastReceiver;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import ru.underground.test42.MainActivity;
import ru.underground.test42.R;

/**
 * Created by Egor on 23.04.2017.
 */

public class TimerNotification extends BroadcastReceiver {
    private static final int NOTIFY_ID = 101;

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.putExtra("notification",true);
        PendingIntent contentIntent = PendingIntent.getActivity(context,
                0, notificationIntent,PendingIntent.FLAG_CANCEL_CURRENT);

        Resources res = context.getResources();
        android.app.Notification.Builder builder = new android.app.Notification.Builder(context);

        builder.setContentIntent(contentIntent)
                //.setSmallIcon(R.drawable.ic_launcher)
                //.setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.ic_launcher))
                .setTicker("Конец таймера");
                //.setContentTitle("Новые изменения для " + clString)
                //.setContentText(changesString); // Текст уведомления
        Notification notification;
        if(Build.VERSION.SDK_INT >= 16)
            notification = new Notification.BigTextStyle(builder).bigText("Конец таймера").build();
        else
            notification = builder.getNotification();

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFY_ID, notification);

    }
}