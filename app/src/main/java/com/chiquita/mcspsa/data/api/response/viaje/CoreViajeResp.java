
package com.chiquita.mcspsa.data.api.response.viaje;

import com.chiquita.mcspsa.data.api.response.CoreCommonResp;
import com.chiquita.mcspsa.data.api.response.CoreCommonRowsResp;
import com.chiquita.mcspsa.data.api.response.CoreCommonRowsTypeResp;
import com.chiquita.mcspsa.data.api.response.menu.CoreMenuResp;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CoreViajeResp extends CoreCommonResp<CoreViajeResp.CoreViajeRowsResp, CoreViajeResp.CoreViajeRowsTypeResp> {




    public static class CoreViajeRowsResp extends CoreCommonRowsResp {

        @SerializedName("CCR_BC")
        @Expose
        private String cCRBC;
        @SerializedName("CCR_BC_ALIAS")
        @Expose
        private String cCRBCALIAS;
        @SerializedName("CCR_DESCRIPTION")
        @Expose
        private String cCRDESCRIPTION;

        public String getCCRBC() {
            return cCRBC;
        }

        public void setCCRBC(String cCRBC) {
            this.cCRBC = cCRBC;
        }

        public String getCCRBCALIAS() {
            return cCRBCALIAS;
        }

        public void setCCRBCALIAS(String cCRBCALIAS) {
            this.cCRBCALIAS = cCRBCALIAS;
        }

        public String getCCRDESCRIPTION() {
            return cCRDESCRIPTION;
        }

        public void setCCRDESCRIPTION(String cCRDESCRIPTION) {
            this.cCRDESCRIPTION = cCRDESCRIPTION;
        }

        /**
         * No args constructor for use in serialization
         */
        public CoreViajeRowsResp() {
        }

        /**
         *
         * @param cCRBC
         * @param cCRDESCRIPTION
         * @param cCRBCALIAS
         */
        public CoreViajeRowsResp(String cCRBC, String cCRBCALIAS, String cCRDESCRIPTION, String objectName) {
            super();
            this.cCRBC = cCRBC;
            this.cCRBCALIAS = cCRBCALIAS;
            this.cCRDESCRIPTION = cCRDESCRIPTION;
        }


        public CoreViajeRowsResp withcCRBC(String cCRBC) {
            this.cCRBC = cCRBC;
            return this;
        }



        public void setObjectSmall(String cCRBCALIAS) {
            this.cCRBCALIAS = cCRBCALIAS;
        }

        public CoreViajeRowsResp withcCRBCALIAS(String cCRBCALIAS) {
            this.cCRBCALIAS = cCRBCALIAS;
            return this;
        }



        public CoreViajeRowsResp withcCRDESCRIPTION(String cCRDESCRIPTION) {
            this.cCRDESCRIPTION = cCRDESCRIPTION;
            return this;
        }


    }

    public static class CoreViajeRowsTypeResp extends CoreCommonRowsTypeResp {

        @SerializedName("CCR_BC")
        @Expose
        private String cCRBC;
        @SerializedName("CCR_BC_ALIAS")
        @Expose
        private String cCRBCALIAS;
        @SerializedName("CCR_DESCRIPTION")
        @Expose
        private String cCRDESCRIPTION;

        public String getCCRBC() {
            return cCRBC;
        }

        public void setCCRBC(String cCRBC) {
            this.cCRBC = cCRBC;
        }

        public String getCCRBCALIAS() {
            return cCRBCALIAS;
        }

        public void setCCRBCALIAS(String cCRBCALIAS) {
            this.cCRBCALIAS = cCRBCALIAS;
        }

        public String getCCRDESCRIPTION() {
            return cCRDESCRIPTION;
        }

        public void setCCRDESCRIPTION(String cCRDESCRIPTION) {
            this.cCRDESCRIPTION = cCRDESCRIPTION;
        }

        /**
         * No args constructor for use in serialization
         */
        public CoreViajeRowsTypeResp() {
        }

        /**
         * @param cCRBCALIAS
         * @param cCRBC
         * @param cCRDESCRIPTION
         */
        public CoreViajeRowsTypeResp(String cCRBC, String cCRBCALIAS, String cCRDESCRIPTION) {
            super();
            this.cCRBC = cCRBC;
            this.cCRBCALIAS = cCRBCALIAS;
            this.cCRDESCRIPTION = cCRDESCRIPTION;
        }


        public CoreViajeRowsTypeResp withcCRBC(String cCRBC) {
            this.cCRBC = cCRBC;
            return this;
        }


        public CoreViajeRowsTypeResp withcCRBCALIAS(String cCRBCALIAS) {
            this.cCRBCALIAS = cCRBCALIAS;
            return this;
        }



        public CoreViajeRowsTypeResp withcCRDESCRIPTION(String cCRDESCRIPTION) {
            this.cCRDESCRIPTION = cCRDESCRIPTION;
            return this;
        }


    }

}
