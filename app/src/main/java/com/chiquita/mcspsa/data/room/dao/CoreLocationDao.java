package com.chiquita.mcspsa.data.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.chiquita.mcspsa.data.model.location.CoreLocationEntity;

import java.util.List;

@Dao
public abstract class CoreLocationDao extends CoreBaseDao<CoreLocationEntity>  {

    @Query("DELETE from core_location where username = :userId and serverId = :serverId")
    public abstract void clear(String userId, Long serverId);

    @Query("SELECT * FROM core_location where username = :userId and serverId = :serverId")
    public abstract LiveData<List<CoreLocationEntity>> getAll(String userId, Long serverId);

    @Query("SELECT * FROM core_location WHERE active = :active and username = :userId and serverId = :serverId")
    public abstract LiveData<List<CoreLocationEntity>> getAll(int active, String userId, Long serverId);

    @Query("select * from core_location where CCR_BC = :ccrBc and username = :userId and serverId = :serverId")
    public abstract LiveData<CoreLocationEntity> get(String ccrBc, String userId, Long serverId);

    @Query("select * from core_location where CCR_BC = :ccrBc and username = :userId and serverId = :serverId")
    public abstract CoreLocationEntity Sync(String ccrBc, String userId, Long serverId);

    @Transaction
    public void updateData(List<CoreLocationEntity> objects, String userId, Long serverId) {
        clear(userId, serverId);
        insertAll(objects);
    }
}
