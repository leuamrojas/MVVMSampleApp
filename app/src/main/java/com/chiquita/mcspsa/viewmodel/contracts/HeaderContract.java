package com.chiquita.mcspsa.viewmodel.contracts;

import com.chiquita.mcspsa.core.helper.api.ApiResponseSingle;
import com.chiquita.mcspsa.core.helper.api.Resource;
import com.chiquita.mcspsa.data.api.request.CoreTunnelTransform;
import com.chiquita.mcspsa.data.model.CoreMenuEntity;
import com.chiquita.mcspsa.data.model.CoreViajeEntity;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.work.WorkInfo;

public interface HeaderContract {

    interface View extends CoreContract.View {

    }

    interface ViewModel extends CoreContract.ViewModel {

        LiveData<Resource<List<CoreViajeEntity>>> postViaje(CoreTunnelTransform ireq);
        LiveData<Resource<List<CoreViajeEntity>>> postExport(CoreTunnelTransform ireq);

        void doMenuValidate(CoreTunnelTransform parameters, String object);

        LiveData<ApiResponseSingle> doMenuValidate();

        /**
         *
         * Workers
         */
        LiveData<List<WorkInfo>> getWorkStatusLD(String workTag);

    }
}
