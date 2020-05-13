package com.chiquita.mcspsa.data.api.datasource;

import com.chiquita.mcspsa.core.helper.api.ApiResponsePaged;
import com.chiquita.mcspsa.core.helper.api.CoreServiceGenerator;
import com.chiquita.mcspsa.core.helper.api.CoreServiceGeneratorV2;
import com.chiquita.mcspsa.data.api.EndPoints;
import com.chiquita.mcspsa.data.api.request.CoreTunnelTransform;
import com.chiquita.mcspsa.data.api.response.DefaultResp;
import com.chiquita.mcspsa.data.api.response.viaje.CoreViajeResp;

import androidx.lifecycle.LiveData;
import io.reactivex.Observable;

public class ViajeApiDS {

    public EndPoints api;

    private static final Object LOCK = new Object();

    private static ViajeApiDS sInstance;

    private EndPoints coreapi;

    public static ViajeApiDS getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new ViajeApiDS();
            }
        }
        return sInstance;
    }

    public ViajeApiDS() {

    }

    public EndPoints getApi() {
        return api;
    }

    public LiveData<ApiResponsePaged<CoreViajeResp>> postAppViaje(CoreTunnelTransform ireq) {
        coreapi = CoreServiceGeneratorV2.getInstance().createService(EndPoints.class, ireq.getUser());
        return coreapi.getViaje(ireq.getParameters());
    }

    public Observable<DefaultResp> postAppViajeValidate(CoreTunnelTransform ireq) {
        coreapi = CoreServiceGenerator.getInstance().createService(EndPoints.class, ireq.getUser());
        return coreapi.doVerifyAccess(ireq.getParameters());
    }
}
