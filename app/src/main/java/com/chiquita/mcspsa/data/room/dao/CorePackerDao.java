package com.chiquita.mcspsa.data.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.chiquita.mcspsa.data.model.location.CorePackerEntity;

import java.util.List;

@Dao
public abstract class CorePackerDao extends CoreBaseDao<CorePackerEntity> {

    @Query("DELETE from core_packer where username = :userId and serverId = :serverId")
    public abstract void clear(String userId, Long serverId);

    @Query("SELECT * FROM core_packer where username = :userId and serverId = :serverId")
    public abstract LiveData<List<CorePackerEntity>> getAll(String userId, Long serverId);

    @Query("SELECT * FROM core_packer WHERE active = :active and username = :userId and serverId = :serverId")
    public abstract LiveData<List<CorePackerEntity>> getAll(int active, String userId, Long serverId);

    @Query("select * from core_packer where CCR_BC = :ccrBc and username = :userId and serverId = :serverId")
    public abstract LiveData<CorePackerEntity> get(String ccrBc, String userId, Long serverId);

    @Query("select * from core_packer where CCR_BC = :ccrBc and username = :userId and serverId = :serverId")
    public abstract CorePackerEntity Sync(String ccrBc, String userId, Long serverId);

    @Transaction
    public void updateData(List<CorePackerEntity> objects, String userId, Long serverId) {
        clear(userId, serverId);
        insertAll(objects);
    }
}
