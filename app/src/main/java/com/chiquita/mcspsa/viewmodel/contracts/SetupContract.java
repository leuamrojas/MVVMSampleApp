package com.chiquita.mcspsa.viewmodel.contracts;

import androidx.lifecycle.LiveData;

import com.chiquita.mcspsa.core.helper.api.ApiResponseSingle;
import com.chiquita.mcspsa.data.api.request.CoreTunnelTransform;

public interface SetupContract {

    interface View extends CoreContract.View {

    }

    interface ViewModel extends CoreContract.ViewModel {

        void getCampaigns(CoreTunnelTransform parameters);

        LiveData<ApiResponseSingle> getCampaigns();

        void getStores(CoreTunnelTransform parameters);

        LiveData<ApiResponseSingle> getStores();

    }
}
