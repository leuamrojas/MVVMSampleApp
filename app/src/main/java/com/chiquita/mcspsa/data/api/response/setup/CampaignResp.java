package com.chiquita.mcspsa.data.api.response.setup;

import com.chiquita.mcspsa.data.api.response.CoreCommonResp;
import com.chiquita.mcspsa.data.api.response.CoreCommonRowsResp;
import com.chiquita.mcspsa.data.api.response.CoreCommonRowsTypeResp;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CampaignResp extends CoreCommonResp<CampaignResp.CampaignRowsResp, CampaignResp.CampaignRowsTypeResp> {

    public static class CampaignRowsResp extends CoreCommonRowsResp {

        @SerializedName("instNum")
        @Expose
        private String instNum;
        @SerializedName("eventoNum")
        @Expose
        private String eventoNum;
        @SerializedName("descr")
        @Expose
        private String descr;
        @SerializedName("cdStatus")
        @Expose
        private String cdStatus;
        @SerializedName("dtInicProg")
        @Expose
        private String dtInicProg;
        @SerializedName("dtFinalProg")
        @Expose
        private String dtFinalProg;

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

    public static class CampaignRowsTypeResp extends CoreCommonRowsTypeResp {

        @SerializedName("dtFinalProg")
        @Expose
        private String dtFinalProg;
        @SerializedName("eventoNum")
        @Expose
        private String eventoNum;
        @SerializedName("cdStatus")
        @Expose
        private String cdStatus;
        @SerializedName("descr")
        @Expose
        private String descr;
        @SerializedName("instNum")
        @Expose
        private String instNum;
        @SerializedName("dtInicProg")
        @Expose
        private String dtInicProg;

        public String getDtFinalProg() {
            return dtFinalProg;
        }

        public void setDtFinalProg(String dtFinalProg) {
            this.dtFinalProg = dtFinalProg;
        }

        public String getEventoNum() {
            return eventoNum;
        }

        public void setEventoNum(String eventoNum) {
            this.eventoNum = eventoNum;
        }

        public String getCdStatus() {
            return cdStatus;
        }

        public void setCdStatus(String cdStatus) {
            this.cdStatus = cdStatus;
        }

        public String getDescr() {
            return descr;
        }

        public void setDescr(String descr) {
            this.descr = descr;
        }

        public String getInstNum() {
            return instNum;
        }

        public void setInstNum(String instNum) {
            this.instNum = instNum;
        }

        public String getDtInicProg() {
            return dtInicProg;
        }

        public void setDtInicProg(String dtInicProg) {
            this.dtInicProg = dtInicProg;
        }

    }
}
