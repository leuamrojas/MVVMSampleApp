package com.chiquita.mcspsa.data.api.request;

import com.chiquita.mcspsa.BuildConfig;
import com.chiquita.mcspsa.core.helper.log.LogManager;
import com.chiquita.mcspsa.data.model.CoreUserEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class CoreApiUtil {

    public static CoreTunnelTransform createLoginRequest(CoreUserEntity user) {

        Map<String, String> params = new HashMap<>();
        params.put("user", user.getUsername());
        params.put("password", user.getPassword());
        params.put("obs", "MCSAPP");

        CoreTunnelTransform ir = new CoreTunnelTransform();
        ir.setUser(user);
        ir.setParameters(params);

        return ir;
    }

    public static Map<String, String> createSingleRequestObject(CoreUserEntity user, String function, String object, CoreCommonParameter... Ps) {

        LinkedHashMap<String, LinkedHashMap<String, Object>> localMap = new LinkedHashMap<>();
        LinkedHashMap<String, CoreCommonParameter> parameters = new LinkedHashMap<>();
        LinkedHashMap<String, Object> single = new LinkedHashMap<>();

        int i = 0;
        for (CoreCommonParameter arg : Ps) {
            parameters.put("p".concat(String.valueOf(i++)), arg);
        }

        single.put("parameters", parameters);
        single.put("function", function);

        if (object != null && !object.isEmpty())
            single.put("object", object);

        localMap.put("script1", single);
        CoreCommonReq request = new CoreCommonReq();
        request.setProperties(localMap);
        Gson gson = new GsonBuilder().registerTypeAdapter(CoreCommonReq.class, new CoreCommonJsonSerializer()).create();
        String json = gson.toJson(request);

        if (BuildConfig.DEBUG)
            LogManager.getInstance().info("Single-Request", json);

        Map<String, String> params = new HashMap<>();
        params.put("strJson", json);
        params.put("user", user != null ? user.getUsername() : "");
        params.put("token", user != null ? user.getToken() : "");

        return params;

    }

    public static LinkedHashMap<String, Object> createRequestMap(String function, String object, CoreCommonParameter... Ps) {
        LinkedHashMap<String, CoreCommonParameter> parameters = new LinkedHashMap<>();
        LinkedHashMap<String, Object> single = new LinkedHashMap<>();

        int i = 0;
        for (CoreCommonParameter arg : Ps) {
            parameters.put("p".concat(String.valueOf(i++)), arg);
        }

        single.put("parameters", parameters);
        single.put("function", function);

        if (object != null && !object.isEmpty())
            single.put("object", object);

        return single;
    }

    public static Map<String, String> createCompoundRequestObject(CoreUserEntity user, LinkedHashMap<String, Object>... requestMaps) {

        LinkedHashMap<String, LinkedHashMap<String, Object>> localMap = new LinkedHashMap<>();

        int i = 0;
        for (LinkedHashMap<String, Object> arg : requestMaps) {
            localMap.put(String.format("script%s", String.valueOf(i++)), arg);
        }

        CoreCommonReq request = new CoreCommonReq();
        request.setProperties(localMap);
        Gson gson = new GsonBuilder().registerTypeAdapter(CoreCommonReq.class, new CoreCommonJsonSerializer()).create();
        String json = gson.toJson(request);

        if (BuildConfig.DEBUG)
            LogManager.getInstance().info("Compound-Request", json);

        Map<String, String> params = new HashMap<>();
        params.put("strJson", json);
        params.put("user", user != null ? user.getUsername() : "");
        params.put("token", user != null ? user.getToken() : "");

        return params;
    }
}
