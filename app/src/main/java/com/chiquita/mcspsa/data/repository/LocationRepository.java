package com.chiquita.mcspsa.data.repository;

import android.app.Application;

import com.chiquita.mcspsa.data.api.datasource.LocationApiDS;
import com.chiquita.mcspsa.data.api.request.CoreTunnelTransform;
import com.chiquita.mcspsa.data.api.response.location.CoreLocationResp;
import com.chiquita.mcspsa.data.api.response.location.CorePackerResp;
import com.chiquita.mcspsa.data.model.location.CoreLocationEntity;
import com.chiquita.mcspsa.data.model.location.CorePackerEntity;

import java.util.ArrayList;
import java.util.List;

public class LocationRepository extends CoreRepository {

    private static final Object LOCK = new Object();

    private static LocationRepository sInstance;

    private LocationApiDS locationApiDS;

    private LocationRepository(Application app) {
        super(app);

        locationApiDS = LocationApiDS.getInstance();
    }

    public static LocationRepository getInstance(Application app) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new LocationRepository(app);
            }
        }
        return sInstance;
    }


    public LocationApiDS getLocationApiDS() {
        return locationApiDS;
    }

    public List<CoreLocationEntity> transformTo(CoreLocationResp response, CoreTunnelTransform ireq) {
        List<CoreLocationEntity> transformed = new ArrayList<>();
        CoreLocationEntity entity;
        if (response != null && response.getRows() != null && response.getRows().size() > 0) {
            for (CoreLocationResp.CoreLocationRowsResp item : response.getRows()) {
                entity = new CoreLocationEntity(true, ireq.getUser().getUsername(), ireq.getUser().getServerId(),
                        item.getCcrBc(), item.getCcrBcAlias(), item.getCcrDescription());
                transformed.add(entity);
            }
        }
        return transformed;
    }

    public List<CorePackerEntity> transformToPacker(CorePackerResp response, CoreTunnelTransform ireq) {
        List<CorePackerEntity> transformed = new ArrayList<>();
        CorePackerEntity entity;
        if (response != null && response.getRows() != null && response.getRows().size() > 0) {
            for (CorePackerResp.CorePackerRowsResp item : response.getRows()) {
                entity = new CorePackerEntity(true, ireq.getUser().getUsername(), ireq.getUser().getServerId(),
                        item.getCcrBc(), item.getCcrBcAlias(), item.getCcrDescription(), item.getCcrSource());
                transformed.add(entity);
            }
        }
        return transformed;
    }
}
