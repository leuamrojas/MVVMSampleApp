package com.chiquita.mcspsa.data.api.request;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 *
 *
 * MCS Json Serializer
 **/
public class CoreCommonJsonSerializer implements JsonSerializer<Object> {

    @Override
    public JsonElement serialize(Object cr, Type type, JsonSerializationContext context) {
        CoreCommonReq mcc = (CoreCommonReq) cr;
        return context.serialize(mcc.getScript());
    }
}
