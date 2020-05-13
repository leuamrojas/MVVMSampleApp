package com.chiquita.mcspsa.worker;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import com.chiquita.mcspsa.core.CoreConfigurationManager;
import com.chiquita.mcspsa.core.helper.log.LogManager;
import com.chiquita.mcspsa.data.model.CoreUserEntity;
import com.chiquita.mcspsa.data.repository.SecurityRepository;

public class NetworkMonitorWorker extends Worker {

    public NetworkMonitorWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        try {

            SecurityRepository repository = SecurityRepository.getInstance((Application) getApplicationContext());
            CoreUserEntity user = repository.getDatabase().userDao().getConnected();

            if (user == null) {

                Data outputData = new Data.Builder()
                        .putString(ConstantsWorker.RESPONSE, ConstantsWorker.ERR_CONNECT_LOGIN)
                        .build();

                return Result.success(outputData);
            }

            final ConnectivityManager connMgr = (ConnectivityManager) getApplicationContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            final android.net.NetworkInfo wifi = connMgr
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            final android.net.NetworkInfo mobile = connMgr
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if (wifi.isConnected() || mobile.isConnected()) {

                if (ConstantsWorker.isHostUnreachable(user.getServerAddress())) {

                    Data outputData = new Data.Builder()
                            .putString(ConstantsWorker.RESPONSE, ConstantsWorker.API_ERR_CONNECT)
                            .build();
                    onServerUnreachable();

                    return Result.success(outputData);

                } else {

                    onConnectionSuccess();
                    Data outputData = new Data.Builder()
                            .putString(ConstantsWorker.RESPONSE, ConstantsWorker.CONNECTED_TAG)
                            .build();
                    return Result.success(outputData);

                }

            } else {
                Data outputData = new Data.Builder()
                        .putString(ConstantsWorker.RESPONSE, ConstantsWorker.ERR_CONNECT)
                        .build();

                onConnectionFailure();
                return Result.success(outputData);
            }

        } catch (Exception e) {
            LogManager.getInstance().info(getClass().getCanonicalName(), e.getLocalizedMessage());
            return Result.failure();
        }
    }

    protected void onConnectionFailure() {
        CoreConfigurationManager.getInstance().setOfflineMode(true);
        CoreConfigurationManager.getInstance().save(getApplicationContext());
    }

    protected void onServerUnreachable() {
        CoreConfigurationManager.getInstance().setOfflineMode(true);
        CoreConfigurationManager.getInstance().save(getApplicationContext());
    }

    protected void onConnectionSuccess() {
        CoreConfigurationManager.getInstance().setOfflineMode(false);
        CoreConfigurationManager.getInstance().save(getApplicationContext());
    }
}
