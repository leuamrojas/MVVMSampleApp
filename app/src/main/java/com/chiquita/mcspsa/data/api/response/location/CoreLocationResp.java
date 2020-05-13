package com.chiquita.mcspsa.data.api.response.location;

import com.chiquita.mcspsa.data.api.response.CoreCommonResp;
import com.chiquita.mcspsa.data.api.response.CoreCommonRowsResp;
import com.chiquita.mcspsa.data.api.response.CoreCommonRowsTypeResp;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CoreLocationResp extends CoreCommonResp<CoreLocationResp.CoreLocationRowsResp, CoreLocationResp.CoreLocationRowsTypeResp> {

    public static class CoreLocationRowsResp extends CoreCommonRowsResp {

        @SerializedName("CCR_BC")
        @Expose
        private String ccrBc;

        @SerializedName("CCR_BC_ALIAS")
        @Expose
        private String ccrBcAlias;

        @SerializedName("CCR_DESCRIPTION")
        @Expose
        private String ccrDescription;

        /**
         * No args constructor for use in serialization
         */
        public CoreLocationRowsResp() {
        }

        /**
         * @param ccrBc
         * @param ccrBcAlias
         * @param ccrDescription
         */

        public CoreLocationRowsResp(String ccrBc, String ccrBcAlias, String ccrDescription) {
            super();
            this.ccrBc = ccrBc;
            this.ccrBcAlias = ccrBcAlias;
            this.ccrDescription = ccrDescription;
        }

        public String getCcrBc() {
            return ccrBc;
        }

        public void setCcrBc(String ccrBc) {
            this.ccrBc = ccrBc;
        }

        public CoreLocationResp.CoreLocationRowsResp withCcrBc(String ccrBc) {
            this.ccrBc = ccrBc;
            return this;
        }

        public String getCcrBcAlias() {
            return ccrBcAlias;
        }

        public void setCcrBcAlias(String ccrBcAlias) {
            this.ccrBcAlias = ccrBcAlias;
        }

        public CoreLocationResp.CoreLocationRowsResp withCcrBcAlias(String ccrBcAlias) {
            this.ccrBcAlias = ccrBcAlias;
            return this;
        }

        public String getCcrDescription() {
            return ccrDescription;
        }

        public void setCcrDescription(String ccrDescription) {
            this.ccrDescription = ccrDescription;
        }

        public CoreLocationResp.CoreLocationRowsResp withCcrDescription(String ccrDescription) {
            this.ccrDescription = ccrDescription;
            return this;
        }
    }

    public static class CoreLocationRowsTypeResp extends CoreCommonRowsTypeResp {
        @SerializedName("CCR_BC")
        @Expose
        private String ccrBc;

        @SerializedName("CCR_BC_ALIAS")
        @Expose
        private String ccrBcAlias;

        @SerializedName("CCR_DESCRIPTION")
        @Expose
        private String ccrDescription;

        /**
         * No args constructor for use in serialization
         */
        public CoreLocationRowsTypeResp() {
        }

        /**
         * @param ccrBc
         * @param ccrBcAlias
         * @param ccrDescription
         */

        public CoreLocationRowsTypeResp(String ccrBc, String ccrBcAlias, String ccrDescription) {
            super();
            this.ccrBc = ccrBc;
            this.ccrBcAlias = ccrBcAlias;
            this.ccrDescription = ccrDescription;
        }

        public String getCcrBc() {
            return ccrBc;
        }

        public void setCcrBc(String ccrBc) {
            this.ccrBc = ccrBc;
        }

        public CoreLocationResp.CoreLocationRowsTypeResp withCcrBc(String ccrBc) {
            this.ccrBc = ccrBc;
            return this;
        }

        public String getCcrBcAlias() {
            return ccrBcAlias;
        }

        public void setCcrBcAlias(String ccrBcAlias) {
            this.ccrBcAlias = ccrBcAlias;
        }

        public CoreLocationResp.CoreLocationRowsTypeResp withCcrBcAlias(String ccrBcAlias) {
            this.ccrBcAlias = ccrBcAlias;
            return this;
        }

        public String getCcrDescription() {
            return ccrDescription;
        }

        public void setCcrDescription(String ccrDescription) {
            this.ccrDescription = ccrDescription;
        }

        public CoreLocationResp.CoreLocationRowsTypeResp withCcrDescription(String ccrDescription) {
            this.ccrDescription = ccrDescription;
            return this;
        }
    }
}
