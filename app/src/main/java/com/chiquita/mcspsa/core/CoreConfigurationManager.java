package com.chiquita.mcspsa.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.chiquita.mcspsa.R;
import com.chiquita.mcspsa.core.helper.log.LogManager;
import com.chiquita.mcspsa.data.model.CoreServerEntity;
import com.google.gson.Gson;
import java.util.Locale;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class CoreConfigurationManager {

    private static CoreConfigurationManager singleton;

    private boolean activeLogs;

    private boolean offlineMode = true;

    private boolean restartApp = false;

    private String rootFolder;

    private String timeout;

    private boolean updated;

    private String language = "ING";

    private String synchronization;

    private CoreServerEntity server;

    private boolean mainClosed = false;

    private Context context;

    public Context getContext() {
        return context;
    }

    private boolean isTablet;

    public void setContext(Context context) {
        this.context = context;
    }

    /**
     *
     *
     * Master Data Entities
     */

    public CoreConfigurationManager() {
    }

    public static CoreConfigurationManager getInstance() {
        if (singleton == null) {
            synchronized (CoreConfigurationManager.class) {
                if (singleton == null) {
                    singleton = new CoreConfigurationManager();
                }
            }
        }
        return singleton;
    }


    public void load(Context context) {
        this.context = context;

        Gson gson = new Gson();
        String json, jsonToConvert;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        this.activeLogs = preferences.getBoolean("activeLogs", false);
        this.timeout = preferences.getString("timeout", "1");
        this.rootFolder = preferences.getString("rootFolder", new StringBuilder(Environment.getExternalStorageDirectory().getAbsolutePath()).append("/MCSMobile").toString());
        this.synchronization = preferences.getString("synctime", "10");

        jsonToConvert = gson.toJson(new CoreServerEntity());
        json = preferences.getString("serverApi", jsonToConvert);
        this.server = gson.fromJson(json, CoreServerEntity.class);

        this.language = preferences.getString("language", "ING");

        this.isTablet = context.getResources().getBoolean(R.bool.isTablet);

    }

    public void save(Context context) {
        try {
            Gson gson = new Gson();
            String json;
            Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();

            editor.putBoolean("activeLogs", this.activeLogs);
            editor.putString("timeout", this.timeout);
            editor.putString("rootFolder", this.rootFolder);
            editor.putString("synctime", this.synchronization);

            if (getServer() != null) {
                json = gson.toJson(this.server);
                editor.putString("serverApi", json);
            }

            editor.putString("language", this.language);
            editor.commit();
        }catch (Exception e){
            LogManager.getInstance().info(getClass().getCanonicalName(), e.getLocalizedMessage());
        }
    }

    public boolean isActiveLogs() {
        return activeLogs;
    }

    public void setActiveLogs(boolean activeLogs) {
        this.activeLogs = activeLogs;
    }

    public boolean isOfflineMode() {
        return offlineMode;
    }

    public void setOfflineMode(boolean offlineMode) {
        this.offlineMode = offlineMode;
    }

    public boolean isRestartApp() {
        return restartApp;
    }

    public void setRestartApp(boolean restartApp) {
        this.restartApp = restartApp;
    }

    public String getRootFolder() {
        return rootFolder;
    }

    public void setRootFolder(String rootFolder) {
        this.rootFolder = rootFolder;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public boolean isUpdated() {
        return updated;
    }

    public void setUpdated(boolean updated) {
        this.updated = updated;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
        setLocale(language);
    }

    public void setLocale(String localeName) {

        switch (localeName) {
            case "POR":
                localeName = "pt";
                break;
            case "CAS":
                localeName = "es";
                break;
            case "ING":
                localeName = "en";
                break;
        }

        Locale locale = new Locale(localeName);
        Locale.setDefault(locale);

        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = locale;


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            conf.setLayoutDirection(conf.locale);
        }

        res.updateConfiguration(conf, dm);
        Toast.makeText(context, "Application need to be restarted to apply new language!", Toast.LENGTH_LONG).show();

    }

    public String getSynchronization() {
        return synchronization;
    }

    public void setSynchronization(String synchronization) {
        this.synchronization = synchronization;
    }

    public CoreServerEntity getServer() {
        return server;
    }

    public void setServer(CoreServerEntity server) {
        this.server = server;
    }

    public boolean isMainClosed() {
        return mainClosed;
    }

    public void setMainClosed(boolean mainClosed) {
        this.mainClosed = mainClosed;
    }

    public boolean isTablet() {
        return isTablet;
    }

    public boolean isPortraitMode() {
        int orientation = context.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return false;
        }
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            return true;
        }

        Log.d(TAG, "isPortraitMode returning false by default");
        return false;
    }
}
