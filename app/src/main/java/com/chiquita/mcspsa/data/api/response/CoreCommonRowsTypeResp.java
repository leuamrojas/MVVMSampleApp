package com.chiquita.mcspsa.data.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public abstract class CoreCommonRowsTypeResp {

    @SerializedName("status")
    @Expose
    public String status;

    public CoreCommonRowsTypeResp() {
    }

    public CoreCommonRowsTypeResp(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
