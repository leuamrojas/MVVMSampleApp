package com.chiquita.mcspsa.data.repository;

import android.app.Application;

/**
 * This class responsible for handling data operations. This is the mediator between different
 * data sources (persistent model, web service, cache, etc.)
 */
public class SecurityRepository extends CoreRepository {

    private static final Object LOCK = new Object();

    private static SecurityRepository sInstance;

    private SecurityRepository(Application app) {
        super(app);
    }

    public static SecurityRepository getInstance(Application app) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new SecurityRepository(app);
            }
        }
        return sInstance;
    }
}
