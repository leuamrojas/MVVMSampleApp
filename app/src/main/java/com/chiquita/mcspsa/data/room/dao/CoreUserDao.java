package com.chiquita.mcspsa.data.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import com.chiquita.mcspsa.data.model.CoreUserEntity;

@Dao
public abstract class CoreUserDao extends CoreBaseDao<CoreUserEntity> {

    @Query("SELECT * FROM core_user WHERE active = 1 LIMIT 1")
    public abstract CoreUserEntity get();

    @Query("SELECT * FROM core_user WHERE username = :username and active = 1 LIMIT 1")
    public abstract LiveData<CoreUserEntity> get(String username);

    @Query("SELECT * FROM core_user WHERE active = 1 LIMIT 1")
    public abstract CoreUserEntity getConnected();

}