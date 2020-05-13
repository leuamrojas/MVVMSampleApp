package com.chiquita.mcspsa.data.repository;

import android.app.Application;

import com.chiquita.mcspsa.data.api.datasource.MenuApiDS;
import com.chiquita.mcspsa.data.api.datasource.ViajeApiDS;
import com.chiquita.mcspsa.data.api.request.CoreTunnelTransform;
import com.chiquita.mcspsa.data.api.response.menu.CoreMenuResp;
import com.chiquita.mcspsa.data.api.response.viaje.CoreViajeResp;
import com.chiquita.mcspsa.data.model.CoreMenuEntity;
import com.chiquita.mcspsa.data.model.CoreViajeEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * This class responsible for handling data operations. This is the mediator between different
 * data sources (persistent model, web service, cache, etc.)
 */
public class ViajeRepository extends CoreRepository {

    private static final Object LOCK = new Object();

    private ViajeApiDS menuApiDS;

    private static ViajeRepository sInstance;

    private ViajeRepository(Application app) {
        super(app);

        this.menuApiDS = ViajeApiDS.getInstance();
    }

    public static ViajeRepository getInstance(Application app) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new ViajeRepository(app);
            }
        }
        return sInstance;
    }

    public ViajeApiDS getViajeApiDS() {
        return menuApiDS;
    }

    public List<CoreViajeEntity> transformTo(CoreViajeResp response, CoreTunnelTransform ireq) {
        List<CoreViajeEntity> transformed = new ArrayList<>();
        CoreViajeEntity entity;
        if (response != null && response.getRows() != null && response.getRows().size() > 0) {
            for (CoreViajeResp.CoreViajeRowsResp item : response.getRows()) {
                entity = new CoreViajeEntity(true, ireq.getUser().getUsername(), ireq.getUser().getServerId(), item.getCCRBC(), item.getCCRBCALIAS(), item.getCCRDESCRIPTION());
                transformed.add(entity);
            }
        }
        return transformed;
    }
}
