package com.example.myapplication.Order;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.ConditionVariable;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.myapplication.R;

public class OrderService extends Service {

    private NotificationManager manager;
    private ConditionVariable conditionVariable;
    private Context context;
    private AlarmManager alarmManager;

    @Override
    public void onCreate() {
        super.onCreate();
        conditionVariable = new ConditionVariable(false);
//        Log.d("msg", "thread");
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Thread notifyingThread = new Thread(null, task, "OrderService");
        notifyingThread.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        conditionVariable.open();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private Runnable task = new Runnable() {
        @Override
        public void run() {


            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            CharSequence name = "order_complete";
            String description = "success finish the order";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            final NotificationChannel channel = new NotificationChannel("order_complete", name, importance);
            channel.setDescription(description);


            NotificationCompat.Builder builder = new NotificationCompat.Builder(OrderService.this, "order_complete")
                    .setContentTitle("订单通知")
                    .setContentText("您的订单已完成！")
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);


            manager.createNotificationChannel(channel);
            manager.notify(001, builder.build());

            Log.d("start", "thread");
            OrderService.this.stopSelf();
        }

    };
}
