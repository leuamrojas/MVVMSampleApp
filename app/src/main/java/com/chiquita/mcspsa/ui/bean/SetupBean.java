package com.chiquita.mcspsa.ui.bean;

import com.chiquita.mcspsa.data.model.CampaignEntity;
import com.chiquita.mcspsa.data.model.bean.CoreBean;

import java.util.Date;

public class SetupBean extends CoreBean {

    private String userName;

    private int eventNum;

    private int eventInst;

    private Date schDate;

    private int upfjNum;

    private int pfjNum;

    private CampaignEntity campaign;

    public SetupBean() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getEventNum() {
        return eventNum;
    }

    public void setEventNum(int eventNum) {
        this.eventNum = eventNum;
    }

    public int getEventInst() {
        return eventInst;
    }

    public void setEventInst(int eventInst) {
        this.eventInst = eventInst;
    }

    public Date getSchDate() {
        return schDate;
    }

    public void setSchDate(Date schDate) {
        this.schDate = schDate;
    }

    public int getUpfjNum() {
        return upfjNum;
    }

    public void setUpfjNum(int upfjNum) {
        this.upfjNum = upfjNum;
    }

    public int getPfjNum() {
        return pfjNum;
    }

    public void setPfjNum(int pfjNum) {
        this.pfjNum = pfjNum;
    }

    public CampaignEntity getCampaign() {
        return campaign;
    }

    public void setCampaign(CampaignEntity campaign) {
        this.campaign = campaign;
    }
}
