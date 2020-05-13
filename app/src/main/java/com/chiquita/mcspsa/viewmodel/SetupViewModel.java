package com.chiquita.mcspsa.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.chiquita.mcspsa.core.helper.api.ApiResponseSingle;
import com.chiquita.mcspsa.data.api.request.CoreTunnelTransform;
import com.chiquita.mcspsa.data.api.response.setup.CampaignResp;
import com.chiquita.mcspsa.data.api.response.setup.CampaignStoreResp;
import com.chiquita.mcspsa.data.model.CampaignEntity;
import com.chiquita.mcspsa.data.model.CampaignStoreEntity;
import com.chiquita.mcspsa.data.model.CoreUserEntity;
import com.chiquita.mcspsa.data.repository.SetupRepository;
import com.chiquita.mcspsa.viewmodel.contracts.SetupContract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SetupViewModel extends CoreViewModel implements SetupContract.ViewModel {

    private SetupContract.View viewCallback;

    private SetupRepository repository;

    protected final MutableLiveData<ApiResponseSingle> mldCampaigns = new MutableLiveData<>();

    protected final MutableLiveData<ApiResponseSingle> mldCampaignsStores = new MutableLiveData<>();

    public SetupViewModel(Application app) {
        super(app);

        repository = SetupRepository.getInstance(app);
    }

    @Override
    public void onViewResumed() {

    }

    @Override
    public void onViewAttached(@NonNull Object viewCallback) {
        this.viewCallback = (SetupContract.View) viewCallback;
    }

    @Override
    public void onViewDetached() {
        this.viewCallback = null;
    }

    public SetupRepository getRepository() {
        return repository;
    }

    @Override
    public void showProgress(Boolean flag) {

    }

    @Override
    public void showProgressLockable(Boolean show) {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showError(Boolean show) {

    }

    @Override
    public void showAdvert(String message) {

    }

    @Override
    public void showLoginMessage() {

    }

    @Override
    public void getCampaigns(CoreTunnelTransform parameters) {
        showProgress(true);
        getCompositeDisposable().add(getRepository().getSetupApiDataSource().getCampaigns(parameters)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> mldCampaigns.setValue(ApiResponseSingle.loading()))
                .subscribe(
                        result -> mldCampaigns.setValue(ApiResponseSingle.success(Translate(result, parameters.getUser()))),
                        throwable -> mldCampaigns.setValue(ApiResponseSingle.error(throwable))
                ));
    }

    @Override
    public LiveData<ApiResponseSingle> getCampaigns() {
        return mldCampaigns;
    }

    private List<CampaignEntity> Translate(CampaignResp response, CoreUserEntity user) {
        List<CampaignEntity> translated = new ArrayList<>();
        CampaignEntity entity;
        if (response != null && response.getRows() != null && response.getRows().size() > 0) {
            for (CampaignResp.CampaignRowsResp item : response.getRows()) {
                entity = new CampaignEntity(true, user.getUsername(), user.getServerId(), item.getInstNum(), item.getEventoNum(), item.getDescr(), item.getCdStatus(), item.getDtInicProg(), item.getDtFinalProg());
                translated.add(entity);
            }
        }
        return translated;
    }

    @Override
    public void getStores(CoreTunnelTransform parameters) {
        showProgress(true);
        getCompositeDisposable().add(getRepository().getSetupApiDataSource().getStores(parameters)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> mldCampaignsStores.setValue(ApiResponseSingle.loading()))
                .subscribe(
                        result -> mldCampaignsStores.setValue(ApiResponseSingle.success(Translate(result, parameters.getUser()))),
                        throwable -> mldCampaignsStores.setValue(ApiResponseSingle.error(throwable))
                ));
    }

    @Override
    public LiveData<ApiResponseSingle> getStores() {
        return mldCampaignsStores;
    }

    private List<CampaignStoreEntity> Translate(CampaignStoreResp response, CoreUserEntity user) {
        List<CampaignStoreEntity> translated = new ArrayList<>();
        CampaignStoreEntity entity;
        if (response != null && response.getRows() != null && response.getRows().size() > 0) {
            for (CampaignStoreResp.CampaignStoreRowsResp item : response.getRows()) {
                entity = new CampaignStoreEntity(true, user.getUsername(), user.getServerId(), item.getInstNum(), item.getEventoNum(), item.getUpfjPfjNum(), item.getUpfjNum(), item.getNome(), item.getStatusScheduled());
                translated.add(entity);
            }
        }
        return translated;
    }
}
