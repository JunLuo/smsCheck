package com.example.smscheck;


import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.os.SystemClock;

import androidx.annotation.Nullable;

public class ForegroundService extends Service {
    private static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    private Notification mNotification;

    @SuppressLint("ForegroundServiceType")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 设置前台通知
/*
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel("my_channel_01", "My Notifications", NotificationManager.IMPORTANCE_HIGH);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mNotificationManager.createNotificationChannel(channel);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mNotification = new Notification.Builder(this, "my_channel_01")
                    .setContentTitle("前台服务")
                    .setContentText("服务已启动来设置闹钟")
                    .build();
        }

        startForeground(NOTIFICATION_ID, mNotification);
*/

        Intent call = new Intent();
        call.setAction(Intent.ACTION_CALL);
        call.setData(Uri.parse("tel:18181406556"));
        this.startActivity(call);

        // 设置闹钟
     //   setAlarm(this);

        return START_STICKY;
    }


    @SuppressLint("ScheduleExactAlarm")
    private void setAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_IMMUTABLE);
        long triggerAtTime = SystemClock.elapsedRealtime() + 10 * 1000; // 10秒后
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mNotificationManager.cancel(NOTIFICATION_ID);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
