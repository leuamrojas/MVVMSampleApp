package com.chiquita.mcspsa.data.api.datasource;

import com.chiquita.mcspsa.core.helper.api.CoreServiceGenerator;
import com.chiquita.mcspsa.data.api.EndPoints;
import com.chiquita.mcspsa.data.api.request.CoreTunnelTransform;
import com.chiquita.mcspsa.data.api.response.DefaultResp;
import com.chiquita.mcspsa.data.api.response.security.CoreLoginResp;

import io.reactivex.Observable;

public class SecurityApiDS {

    private static final Object LOCK = new Object();

    private static SecurityApiDS sInstance;

    private EndPoints endPoints;

    private SecurityApiDS() {
    }

    public static SecurityApiDS getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new SecurityApiDS();
            }
        }
        return sInstance;
    }

    public Observable<CoreLoginResp> doApplicationLogin(CoreTunnelTransform ireq) {
        endPoints = CoreServiceGenerator.getInstance().createService(EndPoints.class, ireq.getUser());
        return endPoints.doApplicationLogin(ireq.getParameters());
    }

    public Observable<DefaultResp> doVerifyAccess(CoreTunnelTransform ireq) {
        endPoints = CoreServiceGenerator.getInstance().createService(EndPoints.class, ireq.getUser());
        return endPoints.doVerifyAccess(ireq.getParameters());
    }

    public Observable<DefaultResp> doRegisterAccess(CoreTunnelTransform ireq) {
        endPoints = CoreServiceGenerator.getInstance().createService(EndPoints.class, ireq.getUser());
        return endPoints.doRegisterAccess(ireq.getParameters());
    }

}
