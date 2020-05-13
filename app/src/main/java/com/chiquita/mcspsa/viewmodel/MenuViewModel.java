package com.chiquita.mcspsa.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.chiquita.mcspsa.R;
import com.chiquita.mcspsa.core.helper.api.AbsentLiveData;
import com.chiquita.mcspsa.core.helper.api.ApiResponsePaged;
import com.chiquita.mcspsa.core.helper.api.ApiResponseSingle;
import com.chiquita.mcspsa.core.helper.api.NetworkBoundResourceV2;
import com.chiquita.mcspsa.core.helper.api.Resource;
import com.chiquita.mcspsa.data.api.request.CoreTunnelTransform;
import com.chiquita.mcspsa.data.api.response.menu.CoreMenuResp;
import com.chiquita.mcspsa.data.model.CoreMenuEntity;
import com.chiquita.mcspsa.data.repository.MenuRepository;
import com.chiquita.mcspsa.viewmodel.contracts.MenuContract;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MenuViewModel extends CoreViewModel implements MenuContract.ViewModel {

    private MenuContract.View viewCallback;

    private MenuRepository menuRepository;

    protected final MutableLiveData<ApiResponseSingle> mldValidation = new MutableLiveData<>();

    public MenuViewModel(Application app) {
        super(app);
        menuRepository = MenuRepository.getInstance(app);
    }

    public MenuRepository getMenuRepository() {
        return menuRepository;
    }

    @Override
    public void onViewResumed() {
    }

    @Override
    public void onViewAttached(@NonNull Object viewCallback) {
        this.viewCallback = (MenuContract.View) viewCallback;
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
    public LiveData<Resource<List<CoreMenuEntity>>> postMenu(CoreTunnelTransform ireq) {
        showProgress(true);
        return loadMenu(ireq);
    }

    public LiveData<Resource<List<CoreMenuEntity>>> loadMenu(CoreTunnelTransform ireq) {
        return new NetworkBoundResourceV2<List<CoreMenuEntity>, CoreMenuResp>(getMenuRepository().getExecutor()) {

            @Override
            protected void saveCallResult(@NonNull CoreMenuResp item) {
                if (item != null) {
                    if (item.getErrorMessage() != null) {
                        showMessage(item.getErrorMessage());
                        return;

                    } else if (item.getRows() != null && !item.getRows().isEmpty()) {
                        if (shouldDoLogin(item.getRows().get(0).getStatus())) {
                            showLoginMessage();
                            return;
                        }
                        getMenuRepository().getExecutor().diskIO().execute(() -> getMenuRepository()
                                .getDatabase().coreMenuDao()
                                .updateData(getMenuRepository().transformTo(item, ireq), ireq.getUser().getUsername(), ireq.getUser().getServerId()));
                    } else {
                        showMessage(getApplicationContext().getString(R.string.contact_system_admin));
                    }
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<CoreMenuEntity> data) {
                return data == null || data.isEmpty();
            }

            @NonNull
            @Override
            protected LiveData<List<CoreMenuEntity>> loadFromDb() {
                return Transformations.switchMap(getMenuRepository().getDatabase().coreMenuDao().getAll(ireq.getUser().getUsername(), ireq.getUser().getServerId()), (List<CoreMenuEntity> menuData) -> {
                    if (menuData == null) {
                        return AbsentLiveData.create();
                    } else {
                        return getMenuRepository().getDatabase().coreMenuDao().getAll(ireq.getUser().getUsername(), ireq.getUser().getServerId());
                    }
                });
            }

            @NonNull
            @Override
            protected LiveData<ApiResponsePaged<CoreMenuResp>> createCall() {
                return getMenuRepository().getMenuApiDS().postAppMenu(ireq);
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
        getCompositeDisposable().add(getMenuRepository().getMenuApiDS().postAppMenuValidate(parameters)
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
