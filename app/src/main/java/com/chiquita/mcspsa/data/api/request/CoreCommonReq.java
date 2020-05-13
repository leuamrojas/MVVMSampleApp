package com.chiquita.mcspsa.data.api.request;

import com.google.gson.annotations.Expose;

import java.util.LinkedHashMap;

/**
 *
 *
 * MCS Common Request
 */
public class CoreCommonReq {

    @Expose
    private LinkedHashMap<String, LinkedHashMap<String, Object>> properties = new LinkedHashMap<>();

    public CoreCommonReq() {
    }

    public LinkedHashMap<String, LinkedHashMap<String, Object>> getScript() {
        return properties;
    }

    public void setProperties(LinkedHashMap<String, LinkedHashMap<String, Object>> properties) {
        this.properties = properties;
    }

    public void addProperty(String key, Object value) {
        properties.put(key, (LinkedHashMap<String, Object>) value);
    }
}
