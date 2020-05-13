package com.chiquita.mcspsa.data.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public abstract class CoreCommonRowsResp {

    //Changes by Ankit: previously it was status and now STATUS
    @SerializedName("status")
    @Expose
    public String status;

    public CoreCommonRowsResp() {
    }

    public CoreCommonRowsResp(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
