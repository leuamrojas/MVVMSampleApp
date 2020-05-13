package com.chiquita.mcspsa.data.repository;

import android.app.Application;

import com.chiquita.mcspsa.core.CoreMobileExecutors;
import com.chiquita.mcspsa.data.api.datasource.SecurityApiDS;
import com.chiquita.mcspsa.data.room.MCSDatabase;

/**
 * This class responsible for handling data operations. This is the mediator between different
 * data sources (persistent model, web service, cache, etc.)
 */
public class CoreRepository {

    private MCSDatabase database;

    private SecurityApiDS securityDataSource;

    private CoreMobileExecutors executor;

    public CoreRepository(Application app) {
        super();
        this.executor = CoreMobileExecutors.getInstance();
        this.database = MCSDatabase.getInstance(app, getExecutor());
        this.securityDataSource = SecurityApiDS.getInstance();
    }

    public CoreMobileExecutors getExecutor() {
        return executor;
    }

    public MCSDatabase getDatabase() {
        return database;
    }

    public SecurityApiDS getSecurityDataSource() {
        return securityDataSource;
    }
}
