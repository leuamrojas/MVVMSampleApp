package com.chiquita.mcspsa.data.api.request;

import com.chiquita.mcspsa.data.model.CoreUserEntity;

import java.util.Map;

/**
 *
 *
 * MCS Common Request Transformation
 */
public class CoreTunnelTransform {

    CoreUserEntity user;
    Map<String, String> parameters;

    public CoreTunnelTransform() {
    }

    public CoreTunnelTransform(CoreUserEntity user, Map<String, String> params) {
        this.user = user;
        this.parameters = params;
    }

    public CoreUserEntity getUser() {
        return user;
    }

    public void setUser(CoreUserEntity user) {
        this.user = user;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

}
