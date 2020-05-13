package com.chiquita.mcspsa.core;

import android.app.Application;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.os.StrictMode;
import android.util.Base64;
import com.chiquita.mcspsa.BuildConfig;
import com.chiquita.mcspsa.core.helper.log.LogManager;
import com.chiquita.mcspsa.data.room.MCSDatabase;
import com.chiquita.mcspsa.worker.ConstantsWorker;
import com.orhanobut.hawk.Hawk;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import timber.log.Timber;

public class CoreMobileApp extends Application {

    public static String TAG = BuildConfig.APP;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        CoreConfigurationManager.getInstance().load(this);
        MCSDatabase.getInstance(this, CoreMobileExecutors.getInstance());
        LogManager.init();
        printKeyHash();
        Hawk.init(this).build();

        ConstantsWorker.addNetworkMonitorWorker();
        registerNetworkReceiver();

        /**
         *
         *
         * THis is for camera use
         * Todo: in Future it must be replaced
         *
         */
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
    }

    private void registerNetworkReceiver() {

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(new CoreNetworkChangeReceiver(), intentFilter);

    }

    public void printKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.chiquita.mcspsa",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                LogManager.getInstance().info("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }
}
