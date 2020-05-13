package com.chiquita.mcspsa.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.chiquita.mcspsa.core.CoreMobileManager;
import com.chiquita.mcspsa.core.helper.api.ApiResponseSingle;
import com.chiquita.mcspsa.core.helper.log.LogManager;
import com.chiquita.mcspsa.data.model.CoreUserEntity;
import com.chiquita.mcspsa.data.model.bean.CoreBean;
import com.chiquita.mcspsa.data.repository.CoreRepository;
import com.chiquita.mcspsa.viewmodel.contracts.CoreContract;
import com.orhanobut.hawk.Hawk;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.reactivex.disposables.CompositeDisposable;

public abstract class CoreViewModel extends AndroidViewModel implements CoreContract.ViewModel {

    protected CoreRepository coreRepository;

    protected final CompositeDisposable compositeDisposable = new CompositeDisposable();

    protected final MutableLiveData<ApiResponseSingle> mldToken = new MutableLiveData<>();

    protected final MutableLiveData<CoreBean> bean = new MutableLiveData<>();

    protected Executor executor;

    protected WorkManager mWorkManager;

    public CoreViewModel(Application app) {
        super(app);

        this.mWorkManager = WorkManager.getInstance();

        this.coreRepository = new CoreRepository(app);

        this.executor = Executors.newFixedThreadPool(5);
    }

    public Context getApplicationContext(){
        return getApplication();
    }

    public CoreRepository getRepository() {
        return coreRepository;
    }

    public LiveData<CoreBean> getBusinessBean() {
        return bean;
    }

    public void setBusinessBean(CoreBean business) {
        this.bean.postValue(business);
    }

    public LiveData<CoreUserEntity> getUser(String username) {
        return loadUser(username);
    }

    @Override
    public void saveUser(CoreUserEntity user) {
        try {
            create(user);
            Hawk.put(CoreMobileManager.getInstance().getHawkKeys(1), user.getUsername());
        } catch (Exception ex) {
            LogManager.getInstance().error(getClass().getCanonicalName(), ex.getLocalizedMessage());
        }
    }

    public LiveData<List<WorkInfo>> getWorkStatusLD(String workTag) {
        return mWorkManager.getWorkInfosByTagLiveData(workTag);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    protected void create(CoreUserEntity item) {
        getRepository().getExecutor().diskIO().execute(() -> getRepository().getDatabase().userDao().insert(item));
    }

    protected LiveData<CoreUserEntity> loadUser(String userName) {
        return getRepository().getDatabase().userDao().get(userName);
    }

    public abstract void showProgress(Boolean flag);

    public abstract void showProgressLockable(Boolean show);

    public abstract void showMessage(String message);

    public abstract void showError(Boolean show);

    public abstract void showAdvert(String message);

    public abstract void showLoginMessage();

    /**
     *
     * ~This methods will be removed from here~!
     * @param status
     * @return
     */
    public boolean shouldDoLogin(String status) {
        if (status != null) {
            if (status.equals("EXPIRADO"))
                return true;
            else
                return false;
        } else
            return false;
    }


}
