package com.chiquita.mcspsa.data.api.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * MCS Common Request Parameters
 */
public class CoreCommonParameter {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("direction")
    @Expose
    private String direction;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("value")
    @Expose
    private String value;

    /**
     * No args constructor for use in serialization
     */
    public CoreCommonParameter() {
    }

    /**
     * @param direction
     * @param name
     * @param value
     * @param type
     */
    public CoreCommonParameter(String name, String direction, String type, String value) {
        super();
        this.name = name;
        this.direction = direction;
        this.type = type;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Builder
     */
    public static class CoreCommonParameterBuilder {
        private String name;
        private String direction;
        private String type;
        private String value;

        public CoreCommonParameterBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public CoreCommonParameterBuilder setDirection(String direction) {
            this.direction = direction;
            return this;
        }

        public CoreCommonParameterBuilder setType(String type) {
            this.type = type;
            return this;
        }

        public CoreCommonParameterBuilder setValue(String value) {
            this.value = value;
            return this;
        }

        public CoreCommonParameter createCommonRequestParameter() {
            return new CoreCommonParameter(name, direction, type, value);
        }
    }
}
