package com.chiquita.mcspsa.data.api.datasource;

import com.chiquita.mcspsa.core.helper.api.CoreServiceGenerator;
import com.chiquita.mcspsa.data.api.EndPoints;
import com.chiquita.mcspsa.data.api.request.CoreTunnelTransform;
import com.chiquita.mcspsa.data.api.response.setup.CampaignResp;
import com.chiquita.mcspsa.data.api.response.setup.CampaignStoreResp;

import io.reactivex.Observable;

public class SetupApiDS {

    private static final Object LOCK = new Object();

    private static SetupApiDS sInstance;

    private EndPoints endPoints;

    private SetupApiDS() {
    }

    public static SetupApiDS getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new SetupApiDS();
            }
        }
        return sInstance;
    }

    public Observable<CampaignResp> getCampaigns(CoreTunnelTransform ireq) {
        endPoints = CoreServiceGenerator.getInstance().createService(EndPoints.class, ireq.getUser());
        return endPoints.getCampaigns(ireq.getParameters());
    }

    public Observable<CampaignStoreResp> getStores(CoreTunnelTransform ireq) {
        endPoints = CoreServiceGenerator.getInstance().createService(EndPoints.class, ireq.getUser());
        return endPoints.getStores(ireq.getParameters());
    }

}
