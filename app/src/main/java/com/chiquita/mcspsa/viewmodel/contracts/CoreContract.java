package com.chiquita.mcspsa.viewmodel.contracts;

import androidx.lifecycle.LiveData;
import androidx.work.WorkInfo;
import com.chiquita.mcspsa.core.viewmodel.Lifecycle;
import com.chiquita.mcspsa.data.model.CoreUserEntity;
import com.chiquita.mcspsa.data.model.bean.CoreBean;

import java.util.List;

public interface CoreContract {

    interface View extends Lifecycle.View {

        void showProgress(boolean show);

        void showMessage(String message);

        void showAdvert(String message);

        void showLoginMessage();

        void showProgressLockable(boolean show);

        void showEmpty(boolean show, android.view.View contentView);

        void showError(boolean show, android.view.View contentView);
    }

    interface ViewModel extends Lifecycle.ViewModel {

        /**
         * @User Room
         */
        void saveUser(CoreUserEntity user);

        LiveData<CoreUserEntity> getUser(String username);

        /**
         * @Business Bean
         */
        LiveData<CoreBean> getBusinessBean();

        void setBusinessBean(CoreBean business);

        /**
         * @Workers
         */
        LiveData<List<WorkInfo>> getWorkStatusLD(String workTag);


    }
}
