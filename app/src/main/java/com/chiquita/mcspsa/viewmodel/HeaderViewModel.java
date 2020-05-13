package com.chiquita.mcspsa.viewmodel;

import android.app.Application;

import com.chiquita.mcspsa.R;
import com.chiquita.mcspsa.core.helper.api.AbsentLiveData;
import com.chiquita.mcspsa.core.helper.api.ApiResponsePaged;
import com.chiquita.mcspsa.core.helper.api.ApiResponseSingle;
import com.chiquita.mcspsa.core.helper.api.NetworkBoundResourceV2;
import com.chiquita.mcspsa.core.helper.api.Resource;
import com.chiquita.mcspsa.data.api.request.CoreTunnelTransform;
import com.chiquita.mcspsa.data.api.response.viaje.CoreViajeResp;
import com.chiquita.mcspsa.data.model.CoreViajeEntity;
import com.chiquita.mcspsa.data.repository.ViajeRepository;
import com.chiquita.mcspsa.viewmodel.contracts.HeaderContract;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HeaderViewModel extends CoreViewModel implements HeaderContract.ViewModel {

    private HeaderContract.View viewCallback;

    private ViajeRepository viajeRepository;

    protected final MutableLiveData<ApiResponseSingle> mldValidation = new MutableLiveData<>();

    public HeaderViewModel(Application app) {
        super(app);
        viajeRepository = ViajeRepository.getInstance(app);
    }

    public ViajeRepository getViajeRepository() {
        return viajeRepository;
    }

    @Override
    public void onViewResumed() {
    }

    @Override
    public void onViewAttached(@NonNull Object viewCallback) {
        this.viewCallback = (HeaderContract.View) viewCallback;
    }

    @Override
    public void onViewDetached() {
        this.viewCallback = null;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    /**
     * Menu (Menu applications) Version 2
     *
     * @param ireq
     * @return
     */
    @Override
    public LiveData<Resource<List<CoreViajeEntity>>> postViaje(CoreTunnelTransform ireq) {
        showProgress(true);
        return loadMenu(ireq);
    }

    @Override
    public LiveData<Resource<List<CoreViajeEntity>>> postExport(CoreTunnelTransform ireq) {
        return null;
    }

    public LiveData<Resource<List<CoreViajeEntity>>> loadMenu(CoreTunnelTransform ireq) {
        return new NetworkBoundResourceV2<List<CoreViajeEntity>, CoreViajeResp>(getViajeRepository().getExecutor()) {

            @Override
            protected void saveCallResult(@NonNull CoreViajeResp item) {
                if (item != null) {
                    if (item.getErrorMessage() != null) {
                        showMessage(item.getErrorMessage());
                        return;

                    } else if (item.getRows() != null && !item.getRows().isEmpty()) {
                        if (shouldDoLogin(item.getRows().get(0).getStatus())) {
                            showLoginMessage();
                            return;
                        }
                        getViajeRepository().getExecutor().diskIO().execute(() -> getViajeRepository()
                                .getDatabase().coreViajeDao()
                                .updateData(getViajeRepository().transformTo(item, ireq), ireq.getUser().getUsername(), ireq.getUser().getServerId()));
                    } else {
                        showMessage(getApplicationContext().getString(R.string.contact_system_admin));
                    }
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<CoreViajeEntity> data) {
                return data == null || data.isEmpty();
            }

            @NonNull
            @Override
            protected LiveData<List<CoreViajeEntity>> loadFromDb() {
                return Transformations.switchMap(getViajeRepository().getDatabase().coreViajeDao().getAll(ireq.getUser().getUsername(), ireq.getUser().getServerId()), (List<CoreViajeEntity> menuData) -> {
                    if (menuData == null) {
                        return AbsentLiveData.create();
                    } else {
                        return getViajeRepository().getDatabase().coreViajeDao().getAll(ireq.getUser().getUsername(), ireq.getUser().getServerId());
                    }
                });
            }

            @NonNull
            @Override
            protected LiveData<ApiResponsePaged<CoreViajeResp>> createCall() {
                return getViajeRepository().getViajeApiDS().postAppViaje(ireq);
            }

            @Override
            protected void onFetchFailed() {

            }

        }.getAsLiveData();
    }

    /**
     * Validate & Register Access
     *
     * @return
     */
    @Override
    public void doMenuValidate(CoreTunnelTransform parameters, String object) {
        showProgress(true);
        getCompositeDisposable().add(getViajeRepository().getViajeApiDS().postAppViajeValidate(parameters)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> mldValidation.setValue(ApiResponseSingle.loading()))
                .subscribe(
                        result -> mldValidation.setValue(ApiResponseSingle.success(result)),
                        throwable -> mldValidation.setValue(ApiResponseSingle.error(throwable))
                ));
    }

    @Override
    public LiveData<ApiResponseSingle> doMenuValidate() {
        return mldValidation;
    }

    @Override
    public void showProgress(Boolean show) {
        if (viewCallback != null)
            viewCallback.showProgress(show);
    }

    @Override
    public void showProgressLockable(Boolean show) {
        if (viewCallback != null)
            viewCallback.showProgressLockable(show);
    }

    @Override
    public void showMessage(String message) {
        if (viewCallback != null)
            viewCallback.showMessage(message);
    }

    @Override
    public void showLoginMessage() {
        if (viewCallback != null)
            viewCallback.showLoginMessage();
    }

    @Override
    public void showError(Boolean show) {
        if (viewCallback != null)
            viewCallback.showError(show, null);
    }

    @Override
    public void showAdvert(String message) {
        /**
         * Not implemented here
         */
    }
}
