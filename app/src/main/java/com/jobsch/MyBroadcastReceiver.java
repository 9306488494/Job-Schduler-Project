package com.jobsch;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

import static android.content.Context.ALARM_SERVICE;

public class MyBroadcastReceiver extends BroadcastReceiver {
    //MediaPlayer mp;
    @Override
    public void onReceive(Context context, Intent intent) {
        //mp=MediaPlayer.create(context, R.raw.twin);
        //mp.start();
      /*  Data data = new Data.Builder()
                .putString(UploadWorker.TASK_DESC, "The task data passed from MainActivity")
                .build();

        Constraints constraints = new Constraints.Builder()
                .setRequiresCharging(true) // you can add as many constraints as you want
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        PeriodicWorkRequest workRequest =
                new PeriodicWorkRequest.Builder(UploadWorker.class, 15, TimeUnit.MINUTES)
                        .setConstraints(constraints)
                        .build();
        WorkManager.getInstance().enqueue(workRequest);*/

       // send to mainActivity
        int i=30;
        Intent intent1 = new Intent(context, MyBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context.getApplicationContext(), 234324243, intent1, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        assert alarmManager != null;
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+ (i * 1000), pendingIntent);

        Intent intent4=new Intent(context,Test.class);
        intent4.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent4);

    }
}
