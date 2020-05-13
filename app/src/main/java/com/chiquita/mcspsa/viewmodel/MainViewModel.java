package com.chiquita.mcspsa.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import com.chiquita.mcspsa.data.repository.MainRepository;
import com.chiquita.mcspsa.viewmodel.contracts.MainContract;

public class MainViewModel extends CoreViewModel implements MainContract.ViewModel {

    private MainContract.View viewCallback;

    private MainRepository repository;

    public MainViewModel(Application app) {
        super(app);
        repository = MainRepository.getInstance(app);
    }

    @Override
    public void onViewResumed() {

    }

    @Override
    public void onViewAttached(@NonNull Object viewCallback) {
        this.viewCallback = (MainContract.View) viewCallback;
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
