package com.chiquita.mcspsa.data.repository;

import android.app.Application;

import com.chiquita.mcspsa.data.api.datasource.SetupApiDS;

/**
 * This class responsible for handling data operations. This is the mediator between different
 * data sources (persistent model, web service, cache, etc.)
 */
public class SetupRepository extends CoreRepository {

    private static final Object LOCK = new Object();

    private static SetupRepository sInstance;

    private SetupApiDS setupApiDS;

    private SetupRepository(Application app) {
        super(app);

        setupApiDS = SetupApiDS.getInstance();
    }

    public static SetupRepository getInstance(Application app) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new SetupRepository(app);
            }
        }
        return sInstance;
    }

    public SetupApiDS getSetupApiDataSource() {
        return setupApiDS;
    }
}
