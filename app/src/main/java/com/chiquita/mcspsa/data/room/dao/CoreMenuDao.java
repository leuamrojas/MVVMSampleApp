package com.chiquita.mcspsa.data.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.chiquita.mcspsa.data.model.CoreMenuEntity;

import java.util.List;

@Dao
public abstract class CoreMenuDao extends CoreBaseDao<CoreMenuEntity> {

    @Query("DELETE from core_menu where username = :userId and serverId = :serverId")
    public abstract void clear(String userId, Long serverId);

    @Query("SELECT * FROM core_menu where username = :userId and serverId = :serverId")
    public abstract LiveData<List<CoreMenuEntity>> getAll(String userId, Long serverId);

    @Query("SELECT * FROM core_menu WHERE active = :active and username = :userId and serverId = :serverId")
    public abstract LiveData<List<CoreMenuEntity>> getAll(int active, String userId, Long serverId);

    @Query("select * from core_menu where objectCode = :objectCode and username = :userId and serverId = :serverId")
    public abstract LiveData<CoreMenuEntity> get(String objectCode, String userId, Long serverId);

    @Query("select * from core_menu where objectCode = :objectCode and username = :userId and serverId = :serverId")
    public abstract CoreMenuEntity Sync(String objectCode, String userId, Long serverId);

    @Transaction
    public void updateData(List<CoreMenuEntity> objects, String userId, Long serverId) {
        clear(userId, serverId);
        insertAll(objects);
    }
}
