package com.chiquita.mcspsa.data.api.datasource;

import androidx.lifecycle.LiveData;
import com.chiquita.mcspsa.core.helper.api.ApiResponsePaged;
import com.chiquita.mcspsa.core.helper.api.CoreServiceGenerator;
import com.chiquita.mcspsa.core.helper.api.CoreServiceGeneratorV2;
import com.chiquita.mcspsa.data.api.EndPoints;
import com.chiquita.mcspsa.data.api.request.CoreTunnelTransform;
import com.chiquita.mcspsa.data.api.response.DefaultResp;
import com.chiquita.mcspsa.data.api.response.menu.CoreMenuResp;

import io.reactivex.Observable;

public class MenuApiDS {

    public EndPoints api;

    private static final Object LOCK = new Object();

    private static MenuApiDS sInstance;

    private EndPoints coreapi;

    public static MenuApiDS getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new MenuApiDS();
            }
        }
        return sInstance;
    }

    public MenuApiDS() {

    }

    public EndPoints getApi() {
        return api;
    }

    public LiveData<ApiResponsePaged<CoreMenuResp>> postAppMenu(CoreTunnelTransform ireq) {
        coreapi = CoreServiceGeneratorV2.getInstance().createService(EndPoints.class, ireq.getUser());
        return coreapi.loadMenu(ireq.getParameters());
    }

    public Observable<DefaultResp> postAppMenuValidate(CoreTunnelTransform ireq) {
        coreapi = CoreServiceGenerator.getInstance().createService(EndPoints.class, ireq.getUser());
        return coreapi.doVerifyAccess(ireq.getParameters());
    }
}
