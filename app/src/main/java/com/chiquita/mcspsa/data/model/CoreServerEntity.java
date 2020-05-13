package com.chiquita.mcspsa.data.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;

import java.io.Serializable;

@Entity(tableName = "core_server",
        primaryKeys = {"serverId"},
        indices = {@Index(value = {"serverId"}, unique = true)}, inheritSuperIndices = true)
public class CoreServerEntity implements Serializable {

    @NonNull
    private Long serverId;

    private String serverName;

    private String serverAddress;

    private boolean isProduction;

    public boolean active;

    protected boolean isSelected = false;

    /**
     * Clone support
     */
    private long rowId;

    @Ignore
    public CoreServerEntity(@NonNull Long serverId, String serverName, String serverAddress, boolean active, boolean isProduction, long rowId) {
        this.serverId = serverId;
        this.serverName = serverName;
        this.serverAddress = serverAddress;
        this.active = active;
        this.isProduction = isProduction;
        this.rowId = rowId;
    }

    @Override
    public String toString() {
        return getServerName();
    }

    public CoreServerEntity() {
    }

    public boolean isProduction() {
        return isProduction;
    }

    public void setProduction(boolean production) {
        isProduction = production;
    }

    @NonNull
    public Long getServerId() {
        return serverId;
    }

    public void setServerId(@NonNull Long serverId) {
        this.serverId = serverId;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public long getRowId() {
        return rowId;
    }

    public void setRowId(long rowId) {
        this.rowId = rowId;
    }

    public static CoreServerEntity clone(CoreServerEntity item, long rowId) {
        return new CoreServerEntity(item.serverId, item.serverName, item.serverAddress, item.active, item.isProduction, rowId);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
