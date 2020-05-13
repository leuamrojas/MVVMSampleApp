package com.chiquita.mcspsa.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.chiquita.mcspsa.worker.ConstantsWorker;

public class CoreNetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {
        ConstantsWorker.addNetworkMonitorWorker();
    }
}
