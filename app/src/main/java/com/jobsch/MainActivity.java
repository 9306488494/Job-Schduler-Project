package com.jobsch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // WorkManager
        // if pass request without any putString
        //final OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(UploadWorker.class).build();
        //creating a data object
        //to pass the data with workRequest
        //we can put as many variables needed
        Data data = new Data.Builder()
                .putString(UploadWorker.TASK_DESC, "The task data passed from MainActivity")
                .build();

        Constraints constraints = new Constraints.Builder()
                .setRequiresCharging(true) // you can add as many constraints as you want
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

/*
        final OneTimeWorkRequest workRequest =
                new OneTimeWorkRequest.Builder(UploadWorker.class)
                        .setInputData(data)
                        .setConstraints(constraints)
                        .build();*/
        PeriodicWorkRequest workRequest =
                new PeriodicWorkRequest.Builder(UploadWorker.class, 15, TimeUnit.MINUTES)
                        .setConstraints(constraints)
                        .build();
        WorkManager.getInstance().enqueue(workRequest);

// Jobschduler
        ComponentName componentName = new ComponentName(MainActivity.this, ExampleJobService.class);
        JobInfo info = new JobInfo.Builder(123, componentName)
                .setRequiresCharging(true)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true)
                .setPeriodic(15*60*1000)
                .setRequiresDeviceIdle(false)
                .build();

        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);

        int resultCode = Objects.requireNonNull(scheduler).schedule(info);

        // Alarm Manager
        int i=30;
        Intent intent = new Intent(this, MyBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this.getApplicationContext(), 234324243, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        assert alarmManager != null;
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+ (i * 1000), pendingIntent);


    }


      /*  if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.e(TAG, "Job scheduled");
        } else {
            Log.e(TAG, "Job scheduling failed");
        }*/


    public void cancelJob(View v) {
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        assert scheduler != null;
        scheduler.cancel(123);
        Log.e(TAG, "Job cancelled");
    }
}