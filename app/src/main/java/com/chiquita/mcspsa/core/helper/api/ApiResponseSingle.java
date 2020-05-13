package com.chiquita.mcspsa.core.helper.api;

import com.chiquita.mcspsa.data.api.response.DefaultResp;
import com.google.gson.JsonElement;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class ApiResponseSingle<T> {

    public final Status status;

    @Nullable
    public final T data;

    @Nullable
    public final Throwable error;

    private ApiResponseSingle(Status status, @Nullable T data, @Nullable Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static ApiResponseSingle loading() {
        return new ApiResponseSingle(Status.LOADING, null, null);
    }

    public static ApiResponseSingle error(@NonNull Throwable error) {
        return new ApiResponseSingle(Status.ERROR, null, error);
    }

    public static ApiResponseSingle success(@NonNull JsonElement data) {
        return new ApiResponseSingle(Status.SUCCESS, data, null);
    }

    public static ApiResponseSingle success() {
        return new ApiResponseSingle(Status.SUCCESS, null, null);
    }

    public static ApiResponseSingle success(Object data) {
        return new ApiResponseSingle(Status.SUCCESS, data, null);
    }

    public static ApiResponseSingle success(Response<JsonElement> data) {
        return new ApiResponseSingle(Status.SUCCESS, data, null);
    }

    public static ApiResponseSingle success(ResponseBody data) {
        return new ApiResponseSingle(Status.SUCCESS, data, null);
    }

    public static ApiResponseSingle success(DefaultResp data) {
        return new ApiResponseSingle(Status.SUCCESS, data, null);
    }

    public enum Status {
        LOADING,
        SUCCESS,
        ERROR
    }
}
