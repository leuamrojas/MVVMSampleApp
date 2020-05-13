package com.chiquita.mcspsa.data.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;

@Entity(tableName = "example_campaign", foreignKeys = {
        @ForeignKey(entity = CoreUserEntity.class,
                parentColumns = {"username", "serverId"},
                childColumns = {"username", "serverId"},
                onDelete = ForeignKey.CASCADE)},
        primaryKeys = {"username", "serverId", "instNum", "eventoNum"},
        indices = {@Index(value = {"username", "serverId", "instNum", "eventoNum"}, unique = true)}, inheritSuperIndices = true)
public class CampaignEntity extends CoreBaseEntity {

    @NonNull
    private String instNum;

    @NonNull
    private String eventoNum;

    private String descr;

    private String cdStatus;

    private String dtInicProg;

    private String dtFinalProg;

    public CampaignEntity() {
    }

    @Ignore
    public CampaignEntity(@NonNull String instNum, @NonNull String eventoNum, String descr, String cdStatus, String dtInicProg, String dtFinalProg) {
        this.instNum = instNum;
        this.eventoNum = eventoNum;
        this.descr = descr;
        this.cdStatus = cdStatus;
        this.dtInicProg = dtInicProg;
        this.dtFinalProg = dtFinalProg;
    }

    @Ignore
    public CampaignEntity(boolean active, @NonNull String username, @NonNull Long serverId, @NonNull String instNum, @NonNull String eventoNum, String descr, String cdStatus, String dtInicProg, String dtFinalProg) {
        super(active, username, serverId);
        this.instNum = instNum;
        this.eventoNum = eventoNum;
        this.descr = descr;
        this.cdStatus = cdStatus;
        this.dtInicProg = dtInicProg;
        this.dtFinalProg = dtFinalProg;
    }

    @NonNull
    public String getInstNum() {
        return instNum;
    }

    public void setInstNum(@NonNull String instNum) {
        this.instNum = instNum;
    }

    @NonNull
    public String getEventoNum() {
        return eventoNum;
    }

    public void setEventoNum(@NonNull String eventoNum) {
        this.eventoNum = eventoNum;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getCdStatus() {
        return cdStatus;
    }

    public void setCdStatus(String cdStatus) {
        this.cdStatus = cdStatus;
    }

    public String getDtInicProg() {
        return dtInicProg;
    }

    public void setDtInicProg(String dtInicProg) {
        this.dtInicProg = dtInicProg;
    }

    public String getDtFinalProg() {
        return dtFinalProg;
    }

    public void setDtFinalProg(String dtFinalProg) {
        this.dtFinalProg = dtFinalProg;
    }
}
