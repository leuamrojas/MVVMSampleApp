package com.chiquita.mcspsa.viewmodel;

import android.app.Application;

import com.chiquita.mcspsa.data.repository.MainRepository;
import com.chiquita.mcspsa.viewmodel.contracts.EvaluationContract;


import androidx.annotation.NonNull;

public class EvaluationViewModel extends CoreViewModel implements EvaluationContract.ViewModel {

    private EvaluationContract.View viewCallback;

    private MainRepository repository;

    public EvaluationViewModel(Application app) {
        super(app);
        repository = MainRepository.getInstance(app);
    }

    @Override
    public void onViewResumed() {

    }

    @Override
    public void onViewAttached(@NonNull Object viewCallback) {
        this.viewCallback = (EvaluationContract.View) viewCallback;
    }

    @Override
    public void onViewDetached() {
        this.viewCallback = null;
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
