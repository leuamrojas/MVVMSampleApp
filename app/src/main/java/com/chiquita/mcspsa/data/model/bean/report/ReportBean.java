package com.chiquita.mcspsa.data.model.bean.report;

import com.chiquita.mcspsa.data.model.CoreUserEntity;

import java.io.Serializable;

public class ReportBean implements Serializable {

    private CoreUserEntity user;

    private String workerTag;

    private String reportURL;

    private String reportName;

    private Boolean remoteRegistered;

    public String getWorkerTag() {
        return workerTag;
    }

    public void setWorkerTag(String workerTag) {
        this.workerTag = workerTag;
    }

    public CoreUserEntity getUser() {
        return user;
    }

    public void setUser(CoreUserEntity user) {
        this.user = user;
    }

    public String getReportURL() {
        return reportURL;
    }

    public void setReportURL(String reportURL) {
        this.reportURL = reportURL;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public Boolean getRemoteRegistered() {
        return remoteRegistered;
    }

    public void setRemoteRegistered(Boolean remoteRegistered) {
        this.remoteRegistered = remoteRegistered;
    }
}
