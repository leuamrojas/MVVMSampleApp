package com.chiquita.mcspsa.data.repository;

import android.app.Application;

import com.chiquita.mcspsa.data.api.datasource.MenuApiDS;
import com.chiquita.mcspsa.data.api.request.CoreTunnelTransform;
import com.chiquita.mcspsa.data.api.response.menu.CoreMenuResp;
import com.chiquita.mcspsa.data.model.CoreMenuEntity;
import java.util.ArrayList;
import java.util.List;

/**
 * This class responsible for handling data operations. This is the mediator between different
 * data sources (persistent model, web service, cache, etc.)
 */
public class MenuRepository extends CoreRepository {

    private static final Object LOCK = new Object();

    private MenuApiDS menuApiDS;

    private static MenuRepository sInstance;

    private MenuRepository(Application app) {
        super(app);

        this.menuApiDS = MenuApiDS.getInstance();
    }

    public static MenuRepository getInstance(Application app) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new MenuRepository(app);
            }
        }
        return sInstance;
    }

    public MenuApiDS getMenuApiDS() {
        return menuApiDS;
    }

    public List<CoreMenuEntity> transformTo(CoreMenuResp response, CoreTunnelTransform ireq) {
        List<CoreMenuEntity> transformed = new ArrayList<>();
        CoreMenuEntity entity;
        if (response != null && response.getRows() != null && response.getRows().size() > 0) {
            for (CoreMenuResp.CoreMenuRowsResp item : response.getRows()) {
                entity = new CoreMenuEntity(true, ireq.getUser().getUsername(), ireq.getUser().getServerId(), item.getObjectCode(), item.getObjectSmall(), item.getObjectAplication(), item.getObjectName());
                transformed.add(entity);
            }
        }
        return transformed;
    }
}
