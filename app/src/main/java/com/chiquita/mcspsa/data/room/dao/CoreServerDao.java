package com.chiquita.mcspsa.data.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.chiquita.mcspsa.data.model.CoreServerEntity;

import java.util.List;
import io.reactivex.Flowable;

@Dao
public abstract class CoreServerDao extends CoreBaseDao<CoreServerEntity> {

    @Query("SELECT * FROM core_server where active = 1 and isProduction = :isProduction")
    public abstract LiveData<List<CoreServerEntity>> getAll(boolean isProduction);

    @Query("select * from core_server where serverId = :id")
    public abstract LiveData<CoreServerEntity> get(long id);

    @Query("select * from core_server where serverName = :serverName")
    public abstract LiveData<CoreServerEntity> get(String serverName);

    @Query("SELECT * FROM core_server where active = 1")
    public abstract Flowable<List<CoreServerEntity>> get();

    @Query("select * from core_server where serverId = :id")
    public abstract CoreServerEntity Sync(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertAll(List<CoreServerEntity> objects);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insertIgnore(List<CoreServerEntity> objects);

}
