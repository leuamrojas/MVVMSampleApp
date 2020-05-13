package com.chiquita.mcspsa.worker;

import android.os.Environment;

import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkContinuation;
import androidx.work.WorkManager;

import com.chiquita.mcspsa.BuildConfig;
import com.chiquita.mcspsa.core.CoreNetworkManager;

import java.io.File;

public final class ConstantsWorker {

    /**
     *
     * API
     */
    public static final String DEVICE_ERR_CONNECT = "0001: NETWORK ERROR CONNECT";

    public static final String API_ERR_CONNECT = "0002: API ERROR CONNECT";

    public static final String API_AUTH_CONNECT = "0003: API AUTHENTICATION PROBLEM";

    public static final String CONNECTED_TAG = "NETWORK CONNECTED";

    public static final String RESPONSE = "RESPONSE";

    public static final String BUSINESS = "BUSINESS";

    public static final String DOWNLOAD_PATH = Environment.getExternalStorageDirectory() + File.separator + BuildConfig.APP + File.separator + "update" + File.separator;

    public static final String REPORT_PATH = Environment.getExternalStorageDirectory() + File.separator + BuildConfig.APP + File.separator + "report" + File.separator;

    public static final String APP_REPORT_AVAILABLE = "AN APP REPORT IS AVAILABLE";

    public static final String ERR_CONNECT_LOGIN = "LOGIN";

    public static final String ERR_CONNECT = "NETWORK ERROR CONNECT";



    /**
     *
     * JOB NETWORK MONITOR
     */
    public static final String NM_TAG = "NET-MONITOR";

    private ConstantsWorker() {

    }

    public static boolean isHostUnreachable(String server) {
        return !CoreNetworkManager.getInstance().testConnection(server);
    }

    public static void addNetworkMonitorWorker() {

        OneTimeWorkRequest attendWork;

        attendWork = new OneTimeWorkRequest.Builder(NetworkMonitorWorker.class)
                .addTag(NM_TAG)
                .build();

        WorkContinuation continuation = WorkManager.getInstance()
                .beginUniqueWork(NM_TAG,
                        ExistingWorkPolicy.REPLACE,
                        attendWork);

        continuation.enqueue();
    }

    public static void addSeedDatabaseWorker() {
        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(SeedDatabaseWorker.class).build();
        WorkManager.getInstance().enqueue(workRequest);
    }


}
