package com.chiquita.mcspsa.data.api.response.security;

import androidx.room.Ignore;

import com.chiquita.mcspsa.data.api.response.DefaultResp;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CoreLoginResp {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("user")
    @Expose
    private String user;

    @Ignore
    private DefaultResp isAccessibleResp;

    @Ignore
    private DefaultResp registerAccessResp;

    /**
     * No args constructor for use in serialization
     */
    public CoreLoginResp() {
    }

    public CoreLoginResp(String status, String token, String user) {
        this.status = status;
        this.token = token;
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CoreLoginResp withStatus(String status) {
        this.status = status;
        return this;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public CoreLoginResp withToken(String token) {
        this.token = token;
        return this;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public DefaultResp getIsAccessibleResp() {
        return isAccessibleResp;
    }

    public void setIsAccessibleResp(DefaultResp isAccessibleResp) {
        this.isAccessibleResp = isAccessibleResp;
    }

    public DefaultResp getRegisterAccessResp() {
        return registerAccessResp;
    }

    public void setRegisterAccessResp(DefaultResp registerAccessResp) {
        this.registerAccessResp = registerAccessResp;
    }
}
