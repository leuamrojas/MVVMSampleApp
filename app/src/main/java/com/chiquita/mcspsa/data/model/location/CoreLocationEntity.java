package com.chiquita.mcspsa.data.model.location;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.chiquita.mcspsa.data.model.CoreBaseEntity;
import com.chiquita.mcspsa.data.model.CoreUserEntity;

import java.io.Serializable;

@Entity(tableName = "core_location" /*,foreignKeys = {
        @ForeignKey(entity = CoreUserEntity.class,
                parentColumns = {"username", "serverId"},
                childColumns = {"username", "serverId"},
                onDelete = ForeignKey.CASCADE)}*//*,
        indices = {@Index(value = {"username", "serverId", "objectCode"}), @Index("serverId"), @Index({"username", "serverId"})},
        primaryKeys = {"objectCode", "username", "serverId"}, inheritSuperIndices = true*/)
public class CoreLocationEntity extends CoreBaseEntity implements Serializable {

    @NonNull
    @PrimaryKey
    private String CCR_BC;

    private String CCR_BC_ALIAS;

    private String CCR_DESCRIPTION;


    public CoreLocationEntity() {
    }

    @Ignore
    public CoreLocationEntity(@NonNull  String CCR_BC, String CCR_BC_ALIAS, String CCR_DESCRIPTION) {
        this.CCR_BC = CCR_BC;
        this.CCR_BC_ALIAS = CCR_BC_ALIAS;
        this.CCR_DESCRIPTION = CCR_DESCRIPTION;
    }

    @Ignore
    public CoreLocationEntity(boolean active, @NonNull String username, @NonNull Long serverId,
                              @NonNull  String CCR_BC, String CCR_BC_ALIAS, String CCR_DESCRIPTION) {
        super(active, username, serverId);
        this.CCR_BC = CCR_BC;
        this.CCR_BC_ALIAS = CCR_BC_ALIAS;
        this.CCR_DESCRIPTION = CCR_DESCRIPTION;
    }


    public String getCCR_BC_ALIAS() {
        return CCR_BC_ALIAS;
    }

    public void setCCR_BC_ALIAS(String CCR_BC_ALIAS) {
        this.CCR_BC_ALIAS = CCR_BC_ALIAS;
    }

    public String getCCR_BC() {
        return CCR_BC;
    }

    public void setCCR_BC(String CCR_BC) {
        this.CCR_BC = CCR_BC;
    }

    public String getCCR_DESCRIPTION() {
        return CCR_DESCRIPTION;
    }

    public void setCCR_DESCRIPTION(String CCR_DESCRIPTION) {
        this.CCR_DESCRIPTION = CCR_DESCRIPTION;
    }
}