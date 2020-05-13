package com.chiquita.mcspsa.data.model;

import androidx.annotation.NonNull;
import androidx.room.Ignore;

import java.io.Serializable;

public abstract class CoreBaseEntity implements Serializable {

    public boolean active;

    @NonNull
    public String username;

    @NonNull
    public Long serverId;

    @Ignore
    protected boolean isSelected = false;

    public CoreBaseEntity() {
    }

    public CoreBaseEntity( boolean active, @NonNull String username, @NonNull Long serverId) {
        this.active = active;
        this.username = username;
        this.serverId = serverId;
        this.isSelected = false;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
