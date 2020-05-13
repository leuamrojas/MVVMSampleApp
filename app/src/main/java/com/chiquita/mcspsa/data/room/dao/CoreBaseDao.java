package com.chiquita.mcspsa.data.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import java.util.List;

@Dao
public abstract class CoreBaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract Long insert(T type);

    @Update
    public abstract void update(T type);

    @Delete
    public abstract void delete(T type);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertAll(List<T> objects);
}