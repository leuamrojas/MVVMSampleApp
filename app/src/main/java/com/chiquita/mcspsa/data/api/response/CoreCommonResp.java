package com.chiquita.mcspsa.data.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public abstract class CoreCommonResp<R extends CoreCommonRowsResp, T extends CoreCommonRowsTypeResp>{

    @SerializedName("script1")
    @Expose
    public List<R> rows = null;

    @SerializedName("dictionary")
    @Expose
    public CoreCommonDictionary<T> dictionary;

    @SerializedName("messages")
    @Expose
    public Object messages;

    @SerializedName("error_message")
    @Expose
    public String errorMessage;

    public CoreCommonResp() {
    }

    public CoreCommonResp(List<R> rows, CoreCommonDictionary<T> dictionary) {
        this.rows = rows;
        this.dictionary = dictionary;
    }

    public List<R> getRows() {
        return rows;
    }

    public void setRows(List<R> rows) {
        this.rows = rows;
    }

    public CoreCommonDictionary<T> getDictionary() {
        return dictionary;
    }

    public void setDictionary(CoreCommonDictionary<T> dictionary) {
        this.dictionary = dictionary;
    }

    public Object getMessages() {
        return messages;
    }

    public void setMessages(Object messages) {
        this.messages = messages;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public static class CoreCommonDictionary<T> {

        @SerializedName("script1")
        @Expose
        private T type;

        /**
         * No args constructor for use in serialization
         */
        public CoreCommonDictionary() {
        }

        public CoreCommonDictionary(T type) {
            this.type = type;
        }

        public T getType() {
            return type;
        }

        public void setType(T type) {
            this.type = type;
        }
    }
}
