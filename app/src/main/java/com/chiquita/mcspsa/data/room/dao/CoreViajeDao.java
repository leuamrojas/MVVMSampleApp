package com.chiquita.mcspsa.data.room.dao;

import com.chiquita.mcspsa.data.model.CoreViajeEntity;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

@Dao
public abstract class CoreViajeDao extends CoreBaseDao<CoreViajeEntity> {

    @Query("DELETE from core_vaije where username = :userId and serverId = :serverId")
    public abstract void clear(String userId, Long serverId);

    @Query("SELECT * FROM core_vaije where username = :userId and serverId = :serverId")
    public abstract LiveData<List<CoreViajeEntity>> getAll(String userId, Long serverId);

    @Query("SELECT * FROM core_vaije WHERE active = :active and username = :userId and serverId = :serverId")
    public abstract LiveData<List<CoreViajeEntity>> getAll(int active, String userId, Long serverId);


    @Transaction
    public void updateData(List<CoreViajeEntity> objects, String userId, Long serverId) {
        clear(userId, serverId);
        insertAll(objects);
    }
}
