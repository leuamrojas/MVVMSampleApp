package com.chiquita.mcspsa.viewmodel.contracts;

import com.chiquita.mcspsa.core.helper.api.ApiResponseSingle;
import com.chiquita.mcspsa.core.helper.api.Resource;
import com.chiquita.mcspsa.data.api.request.CoreTunnelTransform;
import com.chiquita.mcspsa.data.model.CoreMenuEntity;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.work.WorkInfo;

public interface DashboardContract {

    interface View extends CoreContract.View {

    }

    interface ViewModel extends CoreContract.ViewModel {

        LiveData<Resource<List<CoreMenuEntity>>> postMenu(CoreTunnelTransform ireq);

        void doMenuValidate(CoreTunnelTransform parameters, String object);

        LiveData<ApiResponseSingle> doMenuValidate();

        /**
         *
         * Workers
         */
        LiveData<List<WorkInfo>> getWorkStatusLD(String workTag);

    }
}
