package com.chiquita.mcspsa.data.repository;

import android.app.Application;

/**
 * This class responsible for handling data operations. This is the mediator between different
 * data sources (persistent model, web service, cache, etc.)
 */
public class MainRepository extends CoreRepository {

    private static final Object LOCK = new Object();

    private static MainRepository sInstance;

    private MainRepository(Application app) {
        super(app);
    }

    public static MainRepository getInstance(Application app) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new MainRepository(app);
            }
        }
        return sInstance;
    }
}
