package com.chiquita.mcspsa.data.api;

import androidx.lifecycle.LiveData;

import com.chiquita.mcspsa.core.helper.api.ApiResponsePaged;
import com.chiquita.mcspsa.data.api.response.DefaultResp;
import com.chiquita.mcspsa.data.api.response.location.CoreLocationResp;
import com.chiquita.mcspsa.data.api.response.location.CorePackerResp;
import com.chiquita.mcspsa.data.api.response.menu.CoreMenuResp;
import com.chiquita.mcspsa.data.api.response.security.CoreLoginResp;
import com.chiquita.mcspsa.data.api.response.setup.CampaignResp;
import com.chiquita.mcspsa.data.api.response.setup.CampaignStoreResp;
import com.chiquita.mcspsa.data.api.response.viaje.CoreViajeResp;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface EndPoints {

    @FormUrlEncoded
    @POST("login")
    Observable<CoreLoginResp> doApplicationLogin(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("tunel/")
    Observable<DefaultResp> doVerifyAccess(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("tunel/")
    Observable<DefaultResp> doRegisterAccess(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("tunel/")
    LiveData<ApiResponsePaged<CoreMenuResp>> loadMenu(@FieldMap Map<String, String> params);

    // added by asif
    @FormUrlEncoded
    @POST("tunel/")
    LiveData<ApiResponsePaged<CoreViajeResp>> getViaje(@FieldMap Map<String, String> params);
    //end

    @FormUrlEncoded
    @POST("tunel/")
    Observable<CampaignResp> getCampaigns(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("tunel/")
    Observable<CampaignStoreResp> getStores(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("tunel/")
    Observable<DefaultResp> getSurveys(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("tunel/")
    Observable<DefaultResp> postSchedule(@FieldMap Map<String, String> params);

    //Location APIs

    @FormUrlEncoded
    @POST("tunel/")
    LiveData<ApiResponsePaged<CoreLocationResp>> loadLocations(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("tunel/")
    LiveData<ApiResponsePaged<CorePackerResp>> loadPackers(@FieldMap Map<String, String> params);
}