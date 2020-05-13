package com.chiquita.mcspsa.data.room;

import androidx.lifecycle.MutableLiveData;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import com.chiquita.mcspsa.BuildConfig;
import com.chiquita.mcspsa.core.CoreMobileExecutors;
import com.chiquita.mcspsa.data.model.CampaignEntity;
import com.chiquita.mcspsa.data.model.CampaignStoreEntity;
import com.chiquita.mcspsa.data.model.CoreMenuEntity;
import com.chiquita.mcspsa.data.model.CoreServerEntity;
import com.chiquita.mcspsa.data.model.CoreUserEntity;
import com.chiquita.mcspsa.data.model.CoreViajeEntity;
import com.chiquita.mcspsa.data.model.location.CoreLocationEntity;
import com.chiquita.mcspsa.data.model.location.CorePackerEntity;
import com.chiquita.mcspsa.data.room.dao.CoreLocationDao;
import com.chiquita.mcspsa.data.room.dao.CoreMenuDao;
import com.chiquita.mcspsa.data.room.dao.CorePackerDao;
import com.chiquita.mcspsa.data.room.dao.CoreServerDao;
import com.chiquita.mcspsa.data.room.dao.CoreUserDao;
import com.chiquita.mcspsa.data.room.dao.CoreViajeDao;
import com.chiquita.mcspsa.worker.SeedDatabaseWorker;

import java.util.Date;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

/**
 * Each entity with @Dao Notation must be added here
 * If any entity  is updated, the version must be increased.
 **/
@Database(entities = {CoreUserEntity.class,
        CoreServerEntity.class,
        CoreMenuEntity.class,
        CampaignEntity.class,
        CampaignStoreEntity.class,
        CoreLocationEntity.class,
        CoreViajeEntity.class,
        CorePackerEntity.class},
        version = 6, exportSchema = false)
@TypeConverters(MCSDatabase.DateConverter.class)
public abstract class MCSDatabase extends RoomDatabase {

    private static MCSDatabase sInstance;

    @VisibleForTesting
    public static final String DATABASE_NAME = BuildConfig.DB_NAME;

    public abstract CoreUserDao userDao();

    public abstract CoreServerDao coreServerDao();

    public abstract CoreMenuDao coreMenuDao();

    public abstract CoreLocationDao coreLocationDao();

    public abstract CorePackerDao corePackerDao();

    // added by asif
    public abstract CoreViajeDao coreViajeDao();

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static MCSDatabase getInstance(final Context context, final CoreMobileExecutors executors) {
        if (sInstance == null) {
            synchronized (MCSDatabase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context.getApplicationContext(), executors);
                    sInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    /**
     * Build the database. {@link Builder#build()} only sets up the database configuration and
     * creates a new instance of the database.
     * The SQLite database is only created when it's accessed for the first time.
     */
    private static MCSDatabase buildDatabase(final Context appContext,
                                             final CoreMobileExecutors executors) {
        return Room.databaseBuilder(appContext, MCSDatabase.class, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(SeedDatabaseWorker.class).build();
                        WorkManager.getInstance().enqueue(workRequest);
                    }
                }).build();
    }

    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true);
    }

    public static class DateConverter {
        @TypeConverter
        public static Date toDate(Long timestamp) {
            return timestamp == null ? null : new Date(timestamp);
        }

        @TypeConverter
        public static Long toTimestamp(Date date) {
            return date == null ? null : date.getTime();
        }
    }

}
