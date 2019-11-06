package com.jobsch;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class UploadWorker extends Worker {
    public static final String TASK_DESC = "task_desc";
    public UploadWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String taskDesc = getInputData().getString(TASK_DESC);



        Log.e("WorkManager","Working................");
        return Result.success();
    }
}
