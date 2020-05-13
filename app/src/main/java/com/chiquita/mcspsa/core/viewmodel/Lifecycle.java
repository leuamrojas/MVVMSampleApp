package com.chiquita.mcspsa.core.viewmodel;

import androidx.annotation.NonNull;


public interface Lifecycle {

    interface View {
    }

    interface ViewModel<T> {

        void onViewResumed();

        void onViewAttached(@NonNull T viewCallback);

        void onViewDetached();
    }
}
