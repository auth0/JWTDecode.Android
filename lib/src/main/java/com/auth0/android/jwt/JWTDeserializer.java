package com.auth0.android.jwt;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class JWTDeserializer implements JsonDeserializer<JWTPayload> {
    @Override
    public JWTPayload deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonNull() || !json.isJsonObject()) {
            throw new DecodeException("The token's payload had an invalid JSON format.");
        }

        JsonObject object = json.getAsJsonObject();

        //Public Claims
        String iss = removeString(object, "iss");
        String sub = removeString(object, "sub");
        Date exp = removeDate(object, "exp");
        Date nbf = removeDate(object, "nbf");
        Date iat = removeDate(object, "iat");
        String jti = removeString(object, "jti");
        List<String> aud = removeStringOrArray(object, "aud");

        //Private Claims
        Map<String, Claim> extra = new HashMap<>();
        for (Map.Entry<String, JsonElement> e : object.entrySet()) {
            extra.put(e.getKey(), new Claim(e.getValue()));
        }

        return new JWTPayload(iss, sub, exp, nbf, iat, jti, aud, extra);
    }

    private List<String> removeStringOrArray(JsonObject obj, String claimName) {
        List<String> list = Collections.emptyList();
        if (obj.has(claimName)) {
            JsonElement arrElement = obj.remove(claimName);
            if (arrElement.isJsonArray()) {
                JsonArray jsonArr = arrElement.getAsJsonArray();
                list = new ArrayList<>(jsonArr.size());
                for (int i = 0; i < jsonArr.size(); i++) {
                    list.add(jsonArr.get(i).getAsString());
                }
            } else {
                list = Collections.singletonList(arrElement.getAsString());
            }
        }
        return list;
    }

    private Date removeDate(JsonObject obj, String claimName) {
        if (!obj.has(claimName)) {
            return null;
        }
        long ms = obj.remove(claimName).getAsLong() * 1000;
        return new Date(ms);
    }

    private String removeString(JsonObject obj, String claimName) {
        if (!obj.has(claimName)) {
            return null;
        }
        return obj.remove(claimName).getAsString();
    }
}
