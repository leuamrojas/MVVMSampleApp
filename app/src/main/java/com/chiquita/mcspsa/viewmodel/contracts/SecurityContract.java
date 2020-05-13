package com.chiquita.mcspsa.viewmodel.contracts;

import androidx.lifecycle.LiveData;

import com.chiquita.mcspsa.core.helper.api.ApiResponseSingle;
import com.chiquita.mcspsa.data.api.request.CoreTunnelTransform;
import com.chiquita.mcspsa.data.model.CoreServerEntity;

import java.util.List;

public interface SecurityContract {

    interface View extends CoreContract.View {

    }

    interface ViewModel extends CoreContract.ViewModel {

        void doApplicationLogin(CoreTunnelTransform parameters);

        LiveData<ApiResponseSingle> doApplicationLogin();

        void createServers(List<CoreServerEntity> servers);

        LiveData<List<CoreServerEntity>> loadServers();
    }
}
