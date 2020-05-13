package com.chiquita.mcspsa.data.api.datasource;

import androidx.lifecycle.LiveData;

import com.chiquita.mcspsa.core.helper.api.ApiResponsePaged;
import com.chiquita.mcspsa.core.helper.api.CoreServiceGeneratorV2;
import com.chiquita.mcspsa.data.api.EndPoints;
import com.chiquita.mcspsa.data.api.request.CoreTunnelTransform;
import com.chiquita.mcspsa.data.api.response.location.CoreLocationResp;
import com.chiquita.mcspsa.data.api.response.location.CorePackerResp;

public class LocationApiDS {

    public EndPoints api;

    private static final Object LOCK = new Object();

    private static LocationApiDS sInstance;

    private EndPoints coreapi;

    public static LocationApiDS getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new LocationApiDS();
            }
        }
        return sInstance;
    }

    public LocationApiDS() {

    }

    public EndPoints getApi() {
        return api;
    }

    public LiveData<ApiResponsePaged<CoreLocationResp>> postLocations(CoreTunnelTransform ireq) {
        coreapi = CoreServiceGeneratorV2.getInstance().createService(EndPoints.class, ireq.getUser());
        return coreapi.loadLocations(ireq.getParameters());
    }

    public LiveData<ApiResponsePaged<CorePackerResp>> postPackers(CoreTunnelTransform ireq){
        coreapi = CoreServiceGeneratorV2.getInstance().createService(EndPoints.class, ireq.getUser());
        return coreapi.loadPackers(ireq.getParameters());
    }

//    public LiveData<ApiResponsePaged<>>

//    public Observable<DefaultResp> postAppMenuValidate(CoreTunnelTransform ireq) {
//        coreapi = CoreServiceGenerator.getInstance().createService(EndPoints.class, ireq.getUser());
//        return coreapi.doVerifyAccess(ireq.getParameters());
//    }
}
