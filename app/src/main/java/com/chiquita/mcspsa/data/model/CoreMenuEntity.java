package com.chiquita.mcspsa.data.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import java.io.Serializable;

@Entity(tableName = "core_menu", foreignKeys = {
        @ForeignKey(entity = CoreUserEntity.class,
                parentColumns = {"username", "serverId"},
                childColumns = {"username", "serverId"},
                onDelete = ForeignKey.CASCADE)},
        indices = {@Index(value = {"username", "serverId", "objectCode"}), @Index("serverId"), @Index({"username", "serverId"})},
        primaryKeys = {"objectCode", "username", "serverId"}, inheritSuperIndices = true)
public class CoreMenuEntity extends CoreBaseEntity implements Serializable {

    @NonNull
    private String objectCode;

    private String objectSmall;

    private String objectAplication;

    private String objectName;

    public CoreMenuEntity() {
    }

    @Ignore
    public CoreMenuEntity(@NonNull String objectCode) {
        this.objectCode = objectCode;
    }

    @Ignore
    public CoreMenuEntity(@NonNull String objectCode, String objectSmall, String objectAplication, String objectName) {
        this.objectCode = objectCode;
        this.objectSmall = objectSmall;
        this.objectAplication = objectAplication;
        this.objectName = objectName;
    }

    @Ignore
    public CoreMenuEntity(boolean active, @NonNull String username, @NonNull Long serverId, @NonNull String objectCode, String objectSmall, String objectAplication, String objectName) {
        super(active, username, serverId);
        this.objectCode = objectCode;
        this.objectSmall = objectSmall;
        this.objectAplication = objectAplication;
        this.objectName = objectName;
    }

    @NonNull
    public String getObjectCode() {
        return objectCode;
    }

    public void setObjectCode(@NonNull String objectCode) {
        this.objectCode = objectCode;
    }

    public String getObjectSmall() {
        return objectSmall;
    }

    public void setObjectSmall(String objectSmall) {
        this.objectSmall = objectSmall;
    }

    public String getObjectAplication() {
        return objectAplication;
    }

    public void setObjectAplication(String objectAplication) {
        this.objectAplication = objectAplication;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }
}