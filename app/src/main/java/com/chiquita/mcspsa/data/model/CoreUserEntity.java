package com.chiquita.mcspsa.data.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "core_user", primaryKeys = {"username", "serverId"},
        indices = {@Index(value = {"username", "serverId"}, unique = true),
                @Index({"username"})}, inheritSuperIndices = true)
public class CoreUserEntity implements Serializable {

    @NonNull
    private String username;

    private String password;

    private String token;

    private Date lastRefresh;

    private String lang;

    /**
     * Room doesn't support one-to-one relationship at the moment..
     * So we will apply temporary solution for the Server-User relationship
     */
    @NonNull
    private Long serverId;

    private String serverName;

    private String serverAddress;

    /**
     * Clone support
     */
    private long rowId;

    public boolean active;

    public CoreUserEntity() {
    }

    @Ignore
    public CoreUserEntity(long id, @NonNull String username, String password, String token, Date lastRefresh, boolean active, String lang, @NonNull Long serverId, String serverName, String serverAddress) {
        this.rowId = id;
        this.username = username;
        this.password = password;
        this.token = token;
        this.lastRefresh = lastRefresh;
        this.active = active;
        this.lang = lang;
        this.serverId = serverId;
        this.serverName = serverName;
        this.serverAddress = serverAddress;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
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

    public void setLastRefresh(Date lastRefresh) {
        this.lastRefresh = lastRefresh;
    }

    public Date getLastRefresh() {
        return lastRefresh;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public long getRowId() {
        return rowId;
    }

    public void setRowId(long rowId) {
        this.rowId = rowId;
    }

    public static CoreUserEntity clone(CoreUserEntity from, long id) {
        return new CoreUserEntity(id, from.username, from.password, from.token, from.lastRefresh, from.active, from.lang, from.serverId, from.serverName, from.serverAddress);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}