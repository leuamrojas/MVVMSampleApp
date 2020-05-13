package com.chiquita.mcspsa.data.api.response.menu;

import com.chiquita.mcspsa.data.api.response.CoreCommonResp;
import com.chiquita.mcspsa.data.api.response.CoreCommonRowsResp;
import com.chiquita.mcspsa.data.api.response.CoreCommonRowsTypeResp;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CoreMenuResp extends CoreCommonResp<CoreMenuResp.CoreMenuRowsResp, CoreMenuResp.CoreMenuRowsTypeResp> {

    public static class CoreMenuRowsResp extends CoreCommonRowsResp {

        @SerializedName("objectCode")
        @Expose
        private String objectCode;
        @SerializedName("objectSmall")
        @Expose
        private String objectSmall;
        @SerializedName("objectAplication")
        @Expose
        private String objectAplication;
        @SerializedName("objectName")
        @Expose
        private String objectName;

        /**
         * No args constructor for use in serialization
         */
        public CoreMenuRowsResp() {
        }

        /**
         * @param objectName
         * @param objectCode
         * @param objectAplication
         * @param objectSmall
         */
        public CoreMenuRowsResp(String objectCode, String objectSmall, String objectAplication, String objectName) {
            super();
            this.objectCode = objectCode;
            this.objectSmall = objectSmall;
            this.objectAplication = objectAplication;
            this.objectName = objectName;
        }

        public String getObjectCode() {
            return objectCode;
        }

        public void setObjectCode(String objectCode) {
            this.objectCode = objectCode;
        }

        public CoreMenuRowsResp withObjectCode(String objectCode) {
            this.objectCode = objectCode;
            return this;
        }

        public String getObjectSmall() {
            return objectSmall;
        }

        public void setObjectSmall(String objectSmall) {
            this.objectSmall = objectSmall;
        }

        public CoreMenuRowsResp withObjectSmall(String objectSmall) {
            this.objectSmall = objectSmall;
            return this;
        }

        public String getObjectAplication() {
            return objectAplication;
        }

        public void setObjectAplication(String objectAplication) {
            this.objectAplication = objectAplication;
        }

        public CoreMenuRowsResp withObjectAplication(String objectAplication) {
            this.objectAplication = objectAplication;
            return this;
        }

        public String getObjectName() {
            return objectName;
        }

        public void setObjectName(String objectName) {
            this.objectName = objectName;
        }

        public CoreMenuRowsResp withObjectName(String objectName) {
            this.objectName = objectName;
            return this;
        }

    }

    public static class CoreMenuRowsTypeResp extends CoreCommonRowsTypeResp {

        @SerializedName("objectName")
        @Expose
        private String objectName;
        @SerializedName("objectCode")
        @Expose
        private String objectCode;
        @SerializedName("objectAplication")
        @Expose
        private String objectAplication;
        @SerializedName("objectSmall")
        @Expose
        private String objectSmall;

        /**
         * No args constructor for use in serialization
         */
        public CoreMenuRowsTypeResp() {
        }

        /**
         * @param objectCode
         * @param objectName
         * @param objectSmall
         * @param objectAplication
         */
        public CoreMenuRowsTypeResp(String objectName, String objectCode, String objectAplication, String objectSmall) {
            super();
            this.objectName = objectName;
            this.objectCode = objectCode;
            this.objectAplication = objectAplication;
            this.objectSmall = objectSmall;
        }

        public String getObjectName() {
            return objectName;
        }

        public void setObjectName(String objectName) {
            this.objectName = objectName;
        }

        public CoreMenuRowsTypeResp withObjectName(String objectName) {
            this.objectName = objectName;
            return this;
        }

        public String getObjectCode() {
            return objectCode;
        }

        public void setObjectCode(String objectCode) {
            this.objectCode = objectCode;
        }

        public CoreMenuRowsTypeResp withObjectCode(String objectCode) {
            this.objectCode = objectCode;
            return this;
        }

        public String getObjectAplication() {
            return objectAplication;
        }

        public void setObjectAplication(String objectAplication) {
            this.objectAplication = objectAplication;
        }

        public CoreMenuRowsTypeResp withObjectAplication(String objectAplication) {
            this.objectAplication = objectAplication;
            return this;
        }

        public String getObjectSmall() {
            return objectSmall;
        }

        public void setObjectSmall(String objectSmall) {
            this.objectSmall = objectSmall;
        }

        public CoreMenuRowsTypeResp withObjectSmall(String objectSmall) {
            this.objectSmall = objectSmall;
            return this;
        }

    }
}
