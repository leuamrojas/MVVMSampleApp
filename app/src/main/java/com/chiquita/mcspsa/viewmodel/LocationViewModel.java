package com.chiquita.mcspsa.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.chiquita.mcspsa.R;
import com.chiquita.mcspsa.core.helper.api.AbsentLiveData;
import com.chiquita.mcspsa.core.helper.api.ApiResponsePaged;
import com.chiquita.mcspsa.core.helper.api.NetworkBoundResourceV2;
import com.chiquita.mcspsa.core.helper.api.Resource;
import com.chiquita.mcspsa.data.api.request.CoreTunnelTransform;
import com.chiquita.mcspsa.data.api.response.location.CoreLocationResp;
import com.chiquita.mcspsa.data.api.response.location.CorePackerResp;
import com.chiquita.mcspsa.data.model.location.CoreLocationEntity;
import com.chiquita.mcspsa.data.model.location.CorePackerEntity;
import com.chiquita.mcspsa.data.repository.LocationRepository;
import com.chiquita.mcspsa.viewmodel.contracts.LocationContract;

import java.util.List;

public class LocationViewModel extends CoreViewModel implements LocationContract.ViewModel {

    private LocationContract.View viewCallback;

    private LocationRepository locationRepository;

    public LocationViewModel(Application app) {
        super(app);

        locationRepository = LocationRepository.getInstance(app);
    }

    public LocationRepository getLocationRepository() {
        return locationRepository;
    }

    @Override
    public void showProgress(Boolean flag) {
        if (viewCallback != null)
            viewCallback.showProgress(flag);
    }

    @Override
    public void showProgressLockable(Boolean show) {
        if (viewCallback != null)
            viewCallback.showProgress(show);
    }

    @Override
    public void showMessage(String message) {
        if (viewCallback != null)
            viewCallback.showMessage(message);
    }

    @Override
    public void showError(Boolean show) {

    }

    @Override
    public void showAdvert(String message) {

    }

    @Override
    public void showLoginMessage() {
        if (viewCallback != null)
            viewCallback.showLoginMessage();
    }

    @Override
    public void onViewResumed() {

    }

    @Override
    public void onViewAttached(@NonNull Object viewCallback) {
        this.viewCallback = (LocationContract.View) viewCallback;
    }

    @Override
    public void onViewDetached() {
        this.viewCallback = null;
    }

    @Override
    public LiveData<Resource<List<CoreLocationEntity>>> postLocations(CoreTunnelTransform ireq) {
        showProgress(true);
        return loadLocation(ireq);
    }

    @Override
    public LiveData<Resource<List<CorePackerEntity>>> postPackers(CoreTunnelTransform ireq) {
        showProgress(true);
        return loadPackers(ireq);
    }


    private LiveData<Resource<List<CoreLocationEntity>>> loadLocation(CoreTunnelTransform ireq) {
        return new NetworkBoundResourceV2<List<CoreLocationEntity>, CoreLocationResp>(getLocationRepository().getExecutor()) {

            @Override
            protected void saveCallResult(@NonNull CoreLocationResp item) {
                if (item != null) {
                    if (item.getErrorMessage() != null) {
                        showMessage(item.getErrorMessage());
                        return;

                    } else if (item.getRows() != null && !item.getRows().isEmpty()) {
                        if (shouldDoLogin(item.getRows().get(0).getStatus())) {
                            showLoginMessage();
                            return;
                        }
                        getLocationRepository().getExecutor().diskIO().execute(() -> getLocationRepository()
                                .getDatabase().coreLocationDao()
                                .updateData(getLocationRepository().transformTo(item, ireq), ireq.getUser().getUsername(), ireq.getUser().getServerId()));
                    } else {
                        showMessage(getApplicationContext().getString(R.string.contact_system_admin));
                    }
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<CoreLocationEntity> data) {
                return data == null || data.isEmpty();
            }

            @NonNull
            @Override
            protected LiveData<List<CoreLocationEntity>> loadFromDb() {
                return Transformations.switchMap(getLocationRepository().getDatabase().coreLocationDao()
                        .getAll(ireq.getUser().getUsername(), ireq.getUser().getServerId())
                        , (List<CoreLocationEntity> locationData) -> {
                    if (locationData == null) {
                        return AbsentLiveData.create();
                    } else {
                        return getLocationRepository().getDatabase().coreLocationDao().getAll(ireq.getUser().getUsername(), ireq.getUser().getServerId());
                    }
                });
            }

            @NonNull
            @Override
            protected LiveData<ApiResponsePaged<CoreLocationResp>> createCall() {
                return getLocationRepository().getLocationApiDS().postLocations(ireq);
            }

            @Override
            protected void onFetchFailed() {

            }

        }.getAsLiveData();
    }

    private LiveData<Resource<List<CorePackerEntity>>> loadPackers(CoreTunnelTransform ireq) {
        return new NetworkBoundResourceV2<List<CorePackerEntity>, CorePackerResp>(getLocationRepository().getExecutor()) {

            @Override
            protected void saveCallResult(@NonNull CorePackerResp item) {
                if (item != null) {
                    if (item.getErrorMessage() != null) {
                        showMessage(item.getErrorMessage());
                        return;

                    } else if (item.getRows() != null && !item.getRows().isEmpty()) {
                        if (shouldDoLogin(item.getRows().get(0).getStatus())) {
                            showLoginMessage();
                            return;
                        }
                        getLocationRepository().getExecutor().diskIO().execute(() -> getLocationRepository()
                                .getDatabase().corePackerDao()
                                .updateData(getLocationRepository().transformToPacker(item, ireq), ireq.getUser().getUsername(), ireq.getUser().getServerId()));
                    } else {
                        showMessage(getApplicationContext().getString(R.string.contact_system_admin));
                    }
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<CorePackerEntity> data) {
                return data == null || data.isEmpty();
            }

            @NonNull
            @Override
            protected LiveData<List<CorePackerEntity>> loadFromDb() {
                return Transformations.switchMap(getLocationRepository().getDatabase().corePackerDao()
                                .getAll(ireq.getUser().getUsername(), ireq.getUser().getServerId())
                        , (List<CorePackerEntity> locationData) -> {
                            if (locationData == null) {
                                return AbsentLiveData.create();
                            } else {
                                return getLocationRepository().getDatabase().corePackerDao().getAll(ireq.getUser().getUsername(), ireq.getUser().getServerId());
                            }
                        });
            }

            @NonNull
            @Override
            protected LiveData<ApiResponsePaged<CorePackerResp>> createCall() {
                return getLocationRepository().getLocationApiDS().postPackers(ireq);
            }

            @Override
            protected void onFetchFailed() {

            }

        }.getAsLiveData();
    }
}
