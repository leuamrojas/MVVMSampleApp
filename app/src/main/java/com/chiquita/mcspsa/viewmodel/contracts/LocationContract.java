package com.chiquita.mcspsa.viewmodel.contracts;

import androidx.lifecycle.LiveData;

import com.chiquita.mcspsa.core.helper.api.Resource;
import com.chiquita.mcspsa.data.api.request.CoreTunnelTransform;
import com.chiquita.mcspsa.data.model.location.CoreLocationEntity;
import com.chiquita.mcspsa.data.model.location.CorePackerEntity;

import java.util.List;

public interface LocationContract {

    interface View extends CoreContract.View {

    }

    interface ViewModel extends CoreContract.ViewModel {
        LiveData<Resource<List<CoreLocationEntity>>> postLocations(CoreTunnelTransform ireq);
        LiveData<Resource<List<CorePackerEntity>>> postPackers(CoreTunnelTransform ireq);
    }
}
