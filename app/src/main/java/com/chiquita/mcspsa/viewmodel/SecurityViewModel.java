package com.chiquita.mcspsa.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.chiquita.mcspsa.BuildConfig;
import com.chiquita.mcspsa.core.CoreMobileManager;
import com.chiquita.mcspsa.core.helper.api.ApiResponseSingle;
import com.chiquita.mcspsa.data.api.request.CoreApiUtil;
import com.chiquita.mcspsa.data.api.request.CoreCommonParameter;
import com.chiquita.mcspsa.data.api.request.CoreTunnelTransform;
import com.chiquita.mcspsa.data.api.response.security.CoreLoginResp;
import com.chiquita.mcspsa.data.model.CoreServerEntity;
import com.chiquita.mcspsa.data.model.CoreUserEntity;
import com.chiquita.mcspsa.data.repository.SecurityRepository;
import com.chiquita.mcspsa.viewmodel.contracts.SecurityContract;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class SecurityViewModel extends CoreViewModel implements SecurityContract.ViewModel {

    private SecurityContract.View viewCallback;

    private SecurityRepository securityRepository;

    public SecurityViewModel(Application app) {
        super(app);

        securityRepository = SecurityRepository.getInstance(app);
    }

    @Override
    public void onViewResumed() {

    }

    @Override
    public void onViewAttached(@NonNull Object viewCallback) {
        this.viewCallback = (SecurityContract.View) viewCallback;
    }

    @Override
    public void onViewDetached() {
        this.viewCallback = null;
    }

    public SecurityRepository getSecurityRepository() {
        return securityRepository;
    }

    /**
     * @Server Methods
     */
    public LiveData<List<CoreServerEntity>> loadServers() {
        if (BuildConfig.DEBUG) {
            return getSecurityRepository().getDatabase().coreServerDao().getAll(false);
        } else {
            return getSecurityRepository().getDatabase().coreServerDao().getAll(true);
        }
    }

    public void createServers(List<CoreServerEntity> servers) {
        getSecurityRepository().getExecutor().diskIO().execute(() -> getSecurityRepository().getDatabase().coreServerDao().insertAll(servers));
    }

    @Override
    public LiveData<ApiResponseSingle> doApplicationLogin() {
        return mldToken;
    }

    @Override
    public void doApplicationLogin(CoreTunnelTransform parameters) {
        showProgressLockable(true);
        getCompositeDisposable().add(getRepository().getSecurityDataSource().doApplicationLogin(parameters)
                .subscribeOn(Schedulers.io())
                .flatMap((Function<CoreLoginResp, ObservableSource<CoreLoginResp>>) loginResponse ->
                        {
                            if (loginResponse != null && loginResponse.getToken() != null && !loginResponse.getToken().isEmpty()) {
                                parameters.getUser().setToken(loginResponse.getToken());
                                return getSecurityRepository().getSecurityDataSource().doVerifyAccess(getIsAccessibleTransform(parameters.getUser()))
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .map(isAccessibleResp -> {
                                            loginResponse.setIsAccessibleResp(isAccessibleResp);
                                            return loginResponse;
                                        });
                            }
                            return Observable.just(loginResponse);
                        }
                )
                .flatMap((Function<CoreLoginResp, ObservableSource<CoreLoginResp>>) loginResponse -> {
                            if (loginResponse != null && loginResponse.getToken() != null && !loginResponse.getToken().isEmpty()) {
                                parameters.getUser().setToken(loginResponse.getToken());
                                return getSecurityRepository().getSecurityDataSource().doRegisterAccess(getRegisterAccessTransform(parameters.getUser()))
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .map(registerAccessResp -> {
                                            loginResponse.setRegisterAccessResp(registerAccessResp);
                                            return loginResponse;
                                        });
                            } else {
                                return Observable.just(loginResponse);
                            }
                        }
                )
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> mldToken.setValue(ApiResponseSingle.loading()))
                .subscribe(
                        result -> mldToken.setValue(ApiResponseSingle.success(result)),
                        throwable -> mldToken.setValue(ApiResponseSingle.error(throwable))
                ));
    }

    public CoreTunnelTransform getIsAccessibleTransform(CoreUserEntity user) {
        CoreCommonParameter p1, p2, p3;

        p1 = new CoreCommonParameter.CoreCommonParameterBuilder()
                .setName("p_obj_nome")
                .setDirection("IN")
                .setType("VARCHAR")
                .setValue("SIGC9000").createCommonRequestParameter();

        p2 = new CoreCommonParameter.CoreCommonParameterBuilder()
                .setName("p_user")
                .setDirection("IN")
                .setType("VARCHAR")
                .setValue(user.getUsername()).createCommonRequestParameter();

        p3 = new CoreCommonParameter.CoreCommonParameterBuilder()
                .setName("p_lista")
                .setDirection("OUT")
                .setType("CURSOR").createCommonRequestParameter();

        CoreTunnelTransform ir = new CoreTunnelTransform();
        ir.setUser(user);
        ir.setParameters(CoreApiUtil.createSingleRequestObject(user, "rec_acesso_obj", "inbd0492", p1, p2, p3));
//        ir.setParameters(CoreApiUtil.createSingleRequestObject(user, "inbd0492.rec_acesso_obj", "", p1, p2, p3));

        //Change by Ankit: Upper one was applied earlier..
        return ir;
    }

    public CoreTunnelTransform getRegisterAccessTransform(CoreUserEntity user) {

        CoreCommonParameter p1, p2, p3, p4, p5;

        p1 = new CoreCommonParameter.CoreCommonParameterBuilder()
                .setName("p_objeto")
                .setDirection("IN")
                .setType("VARCHAR")
                .setValue("SIGC9000").createCommonRequestParameter();

        p2 = new CoreCommonParameter.CoreCommonParameterBuilder()
                .setName("p_usuario")
                .setDirection("IN")
                .setType("VARCHAR")
                .setValue(user.getUsername()).createCommonRequestParameter();

        p3 = new CoreCommonParameter.CoreCommonParameterBuilder()
                .setName("p_ip_machine")
                .setDirection("IN")
                .setType("VARCHAR")
                .setValue(CoreMobileManager.getInstance().getLocalIpAddress()).createCommonRequestParameter();

        p4 = new CoreCommonParameter.CoreCommonParameterBuilder()
                .setName("p_net_server")
                .setDirection("IN")
                .setType("VARCHAR")
//                .setValue(user.getServerAddress().substring(0, 35)) //Change by Ankit, changed substring from 35 to 22.
                .setValue(user.getServerAddress().substring(0, 22)) //Change by Ankit, changed substring from 35 to 22.
                .createCommonRequestParameter();

        p5 = new CoreCommonParameter.CoreCommonParameterBuilder()
                .setName("p_retorno")
                .setDirection("OUT")
                .setType("VARCHAR").createCommonRequestParameter();

        CoreTunnelTransform ir = new CoreTunnelTransform();
        ir.setUser(user);
        ir.setParameters(CoreApiUtil.createSingleRequestObject(user, "insere_log_acesso", "inbd0309", p1, p2, p3, p4, p5));
//        ir.setParameters(CoreApiUtil.createSingleRequestObject(user, "inbd0309.insere_log_acesso", "", p1, p2, p3, p4, p5));

        return ir;
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
    public void showLoginMessage() {
        if (viewCallback != null)
            viewCallback.showLoginMessage();
    }

    @Override
    public void showError(Boolean show) {

    }

    @Override
    public void showAdvert(String message) {

    }
}
