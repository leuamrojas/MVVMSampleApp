package com.chiquita.mcspsa.data.api.response.setup;

import com.chiquita.mcspsa.data.api.response.CoreCommonResp;
import com.chiquita.mcspsa.data.api.response.CoreCommonRowsResp;
import com.chiquita.mcspsa.data.api.response.CoreCommonRowsTypeResp;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CampaignStoreResp extends CoreCommonResp<CampaignStoreResp.CampaignStoreRowsResp, CampaignStoreResp.CampaignStoreRowsTypeResp> {

    public static class CampaignStoreRowsResp extends CoreCommonRowsResp {

        @SerializedName("instNum")
        @Expose
        private String instNum;
        @SerializedName("eventoNum")
        @Expose
        private String eventoNum;
        @SerializedName("upfjPfjNum")
        @Expose
        private String upfjPfjNum;
        @SerializedName("upfjNum")
        @Expose
        private String upfjNum;
        @SerializedName("nome")
        @Expose
        private String nome;
        @SerializedName("statusScheduled")
        @Expose
        private String statusScheduled;

        public String getInstNum() {
            return instNum;
        }

        public void setInstNum(String instNum) {
            this.instNum = instNum;
        }

        public String getEventoNum() {
            return eventoNum;
        }

        public void setEventoNum(String eventoNum) {
            this.eventoNum = eventoNum;
        }

        public String getUpfjPfjNum() {
            return upfjPfjNum;
        }

        public void setUpfjPfjNum(String upfjPfjNum) {
            this.upfjPfjNum = upfjPfjNum;
        }

        public String getUpfjNum() {
            return upfjNum;
        }

        public void setUpfjNum(String upfjNum) {
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

    public static class CampaignStoreRowsTypeResp extends CoreCommonRowsTypeResp {

        @SerializedName("upfjNum")
        @Expose
        private String upfjNum;
        @SerializedName("eventoNum")
        @Expose
        private String eventoNum;
        @SerializedName("statusScheduled")
        @Expose
        private String statusScheduled;
        @SerializedName("upfjPfjNum")
        @Expose
        private String upfjPfjNum;
        @SerializedName("nome")
        @Expose
        private String nome;
        @SerializedName("instNum")
        @Expose
        private String instNum;

        public String getUpfjNum() {
            return upfjNum;
        }

        public void setUpfjNum(String upfjNum) {
            this.upfjNum = upfjNum;
        }

        public String getEventoNum() {
            return eventoNum;
        }

        public void setEventoNum(String eventoNum) {
            this.eventoNum = eventoNum;
        }

        public String getStatusScheduled() {
            return statusScheduled;
        }

        public void setStatusScheduled(String statusScheduled) {
            this.statusScheduled = statusScheduled;
        }

        public String getUpfjPfjNum() {
            return upfjPfjNum;
        }

        public void setUpfjPfjNum(String upfjPfjNum) {
            this.upfjPfjNum = upfjPfjNum;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getInstNum() {
            return instNum;
        }

        public void setInstNum(String instNum) {
            this.instNum = instNum;
        }

    }
}
