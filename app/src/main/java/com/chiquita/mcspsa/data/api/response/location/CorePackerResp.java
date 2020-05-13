package com.chiquita.mcspsa.data.api.response.location;

import com.chiquita.mcspsa.data.api.response.CoreCommonResp;
import com.chiquita.mcspsa.data.api.response.CoreCommonRowsResp;
import com.chiquita.mcspsa.data.api.response.CoreCommonRowsTypeResp;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CorePackerResp extends CoreCommonResp<CorePackerResp.CorePackerRowsResp, CorePackerResp.CorePackerRowsTypeResp> {

    public static class CorePackerRowsResp extends CoreCommonRowsResp {

        @SerializedName("CCR_BC")
        @Expose
        private String ccrBc;

        @SerializedName("CCR_BC_ALIAS")
        @Expose
        private String ccrBcAlias;

        @SerializedName("CCR_DESCRIPTION")
        @Expose
        private String ccrDescription;

        @SerializedName("CCR_SOURCE")
        @Expose
        private String ccrSource;


        /**
         * No args constructor for use in serialization
         */
        public CorePackerRowsResp() {
        }

        /**
         * @param ccrBc
         * @param ccrBcAlias
         * @param ccrDescription
         * @param ccrSource
         */

        public CorePackerRowsResp(String ccrBc, String ccrBcAlias, String ccrDescription, String ccrSource) {
            super();
            this.ccrBc = ccrBc;
            this.ccrBcAlias = ccrBcAlias;
            this.ccrDescription = ccrDescription;
            this.ccrSource = ccrSource;
        }

        public String getCcrBc() {
            return ccrBc;
        }

        public void setCcrBc(String ccrBc) {
            this.ccrBc = ccrBc;
        }

        public CorePackerResp.CorePackerRowsResp withCcrBc(String ccrBc) {
            this.ccrBc = ccrBc;
            return this;
        }

        public String getCcrBcAlias() {
            return ccrBcAlias;
        }

        public void setCcrBcAlias(String ccrBcAlias) {
            this.ccrBcAlias = ccrBcAlias;
        }

        public CorePackerResp.CorePackerRowsResp withCcrBcAlias(String ccrBcAlias) {
            this.ccrBcAlias = ccrBcAlias;
            return this;
        }

        public String getCcrDescription() {
            return ccrDescription;
        }

        public void setCcrDescription(String ccrDescription) {
            this.ccrDescription = ccrDescription;
        }

        public CorePackerResp.CorePackerRowsResp withCcrDescription(String ccrDescription) {
            this.ccrDescription = ccrDescription;
            return this;
        }

        public String getCcrSource() {
            return ccrSource;
        }

        public void setCcrSource(String ccrSource) {
            this.ccrSource = ccrSource;
        }

        public CorePackerResp.CorePackerRowsResp withCcrSource(String ccrSource) {
            this.ccrSource = ccrSource;
            return this;
        }
    }

    public static class CorePackerRowsTypeResp extends CoreCommonRowsTypeResp {
        @SerializedName("CCR_BC")
        @Expose
        private String ccrBc;

        @SerializedName("CCR_BC_ALIAS")
        @Expose
        private String ccrBcAlias;

        @SerializedName("CCR_DESCRIPTION")
        @Expose
        private String ccrDescription;


        @SerializedName("CCR_SOURCE")
        @Expose
        private String ccrSource;

        /**
         * No args constructor for use in serialization
         */
        public CorePackerRowsTypeResp() {
        }

        /**
         * @param ccrBc
         * @param ccrBcAlias
         * @param ccrDescription
         * @param ccrSource
         */

        public CorePackerRowsTypeResp(String ccrBc, String ccrBcAlias, String ccrDescription, String ccrSource) {
            super();
            this.ccrBc = ccrBc;
            this.ccrBcAlias = ccrBcAlias;
            this.ccrDescription = ccrDescription;
            this.ccrSource = ccrSource;
        }

        public String getCcrBc() {
            return ccrBc;
        }

        public void setCcrBc(String ccrBc) {
            this.ccrBc = ccrBc;
        }

        public CorePackerResp.CorePackerRowsTypeResp withCcrBc(String ccrBc) {
            this.ccrBc = ccrBc;
            return this;
        }

        public String getCcrBcAlias() {
            return ccrBcAlias;
        }

        public void setCcrBcAlias(String ccrBcAlias) {
            this.ccrBcAlias = ccrBcAlias;
        }

        public CorePackerResp.CorePackerRowsTypeResp withCcrBcAlias(String ccrBcAlias) {
            this.ccrBcAlias = ccrBcAlias;
            return this;
        }

        public String getCcrDescription() {
            return ccrDescription;
        }

        public void setCcrDescription(String ccrDescription) {
            this.ccrDescription = ccrDescription;
        }

        public CorePackerResp.CorePackerRowsTypeResp withCcrDescription(String ccrDescription) {
            this.ccrDescription = ccrDescription;
            return this;
        }

        public String getCcrSource() {
            return ccrSource;
        }

        public void setCcrSource(String ccrSource) {
            this.ccrSource = ccrSource;
        }

        public CorePackerResp.CorePackerRowsTypeResp withCcrSource(String ccrSource) {
            this.ccrSource = ccrSource;
            return this;
        }
    }
}
