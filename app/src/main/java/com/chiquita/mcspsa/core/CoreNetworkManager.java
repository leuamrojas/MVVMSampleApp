package com.chiquita.mcspsa.core;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import java.net.HttpURLConnection;
import java.net.URL;

public class CoreNetworkManager {

    private static CoreNetworkManager singleton;

    private CoreNetworkManager() {

    }

    public static CoreNetworkManager getInstance() {
        if (singleton == null) {
            synchronized (CoreNetworkManager.class) {
                if (singleton == null) {
                    singleton = new CoreNetworkManager();
                }
            }
        }
        return singleton;
    }

    public boolean testConnection(String endpoint) {
        HttpURLConnection urlConnection;
        try {
            urlConnection = (HttpURLConnection) new URL(endpoint).openConnection();
            urlConnection.setReadTimeout(5000);
            urlConnection.setConnectTimeout(5000);
            urlConnection.setRequestMethod("HEAD");
            urlConnection.connect();
            int responseCode = urlConnection.getResponseCode();
            return (responseCode == 404 || responseCode == 200);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isConnected(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

}
