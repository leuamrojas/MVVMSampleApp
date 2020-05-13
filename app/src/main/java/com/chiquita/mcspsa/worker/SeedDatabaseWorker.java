package com.chiquita.mcspsa.worker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.chiquita.mcspsa.core.CoreMobileExecutors;
import com.chiquita.mcspsa.core.helper.log.LogManager;
import com.chiquita.mcspsa.data.model.CoreServerEntity;
import com.chiquita.mcspsa.data.room.MCSDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class SeedDatabaseWorker extends Worker {

    public SeedDatabaseWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            seedServers();
            return Result.success();
        } catch (Exception e) {
            LogManager.getInstance().error(getClass().getCanonicalName(), e.getLocalizedMessage());
            return Result.failure();
        }
    }

    private void seedServers() throws IOException {

        InputStream inputStream = getApplicationContext().getAssets().open("servers.json");
        int size = inputStream.available();
        byte[] buffer = new byte[size];
        inputStream.read(buffer);
        inputStream.close();
        String json = new String(buffer, "UTF-8");
        List<CoreServerEntity> servers = new Gson().fromJson(json, new TypeToken<List<CoreServerEntity>>() {
        }.getType());

        MCSDatabase appDatabase = MCSDatabase.getInstance(getApplicationContext(), CoreMobileExecutors.getInstance());
        appDatabase.coreServerDao().insertIgnore(servers);

    }
}
