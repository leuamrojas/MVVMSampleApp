package com.chiquita.mcspsa.data.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;

@Entity(tableName = "example_campaign_store", foreignKeys = {
        @ForeignKey(entity = CoreUserEntity.class,
                parentColumns = {"username", "serverId"},
                childColumns = {"username", "serverId"},
                onDelete = ForeignKey.CASCADE)},
        primaryKeys = {"username", "serverId", "instNum", "eventoNum", "upfjPfjNum", "upfjNum"},
        indices = {@Index(value = {"username", "serverId", "instNum", "eventoNum", "upfjPfjNum", "upfjNum"}, unique = true)}, inheritSuperIndices = true)
public class CampaignStoreEntity extends CoreBaseEntity {

    @NonNull
    private String instNum;

    @NonNull
    private String eventoNum;

    @NonNull
    private String upfjPfjNum;

    @NonNull
    private String upfjNum;

    private String nome;

    private String statusScheduled;

    public CampaignStoreEntity() {
    }

    @Ignore
    public CampaignStoreEntity(boolean active, @NonNull String username, @NonNull Long serverId) {
        super(active, username, serverId);
    }

    @Ignore
    public CampaignStoreEntity(@NonNull String instNum, @NonNull String eventoNum, @NonNull String upfjPfjNum, @NonNull String upfjNum, String nome, String statusScheduled) {
        this.instNum = instNum;
        this.eventoNum = eventoNum;
        this.upfjPfjNum = upfjPfjNum;
        this.upfjNum = upfjNum;
        this.nome = nome;
        this.statusScheduled = statusScheduled;
    }

    @Ignore
    public CampaignStoreEntity(boolean active, @NonNull String username, @NonNull Long serverId, @NonNull String instNum, @NonNull String eventoNum, @NonNull String upfjPfjNum, @NonNull String upfjNum, String nome, String statusScheduled) {
        super(active, username, serverId);
        this.instNum = instNum;
        this.eventoNum = eventoNum;
        this.upfjPfjNum = upfjPfjNum;
        this.upfjNum = upfjNum;
        this.nome = nome;
        this.statusScheduled = statusScheduled;
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

    @NonNull
    public String getUpfjPfjNum() {
        return upfjPfjNum;
    }

    public void setUpfjPfjNum(@NonNull String upfjPfjNum) {
        this.upfjPfjNum = upfjPfjNum;
    }

    @NonNull
    public String getUpfjNum() {
        return upfjNum;
    }

    public void setUpfjNum(@NonNull String upfjNum) {
        this.upfjNum = upfjNum;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getStatusScheduled() {
        return statusScheduled;
    }

    public void setStatusScheduled(String statusScheduled) {
        this.statusScheduled = statusScheduled;
    }
}
