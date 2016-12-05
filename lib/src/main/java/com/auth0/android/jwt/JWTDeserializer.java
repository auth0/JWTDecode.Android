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
        String iss = getString(object, "iss");
        String sub = getString(object, "sub");
        Date exp = getDate(object, "exp");
        Date nbf = getDate(object, "nbf");
        Date iat = getDate(object, "iat");
        String jti = getString(object, "jti");
        List<String> aud = getStringOrArray(object, "aud");

        //Private Claims
        Map<String, Claim> extra = new HashMap<>();
        for (Map.Entry<String, JsonElement> e : object.entrySet()) {
            extra.put(e.getKey(), new ClaimImpl(e.getValue()));
        }

        return new JWTPayload(iss, sub, exp, nbf, iat, jti, aud, extra);
    }

    private List<String> getStringOrArray(JsonObject obj, String claimName) {
        List<String> list = Collections.emptyList();
        if (obj.has(claimName)) {
            JsonElement arrElement = obj.get(claimName);
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

    private Date getDate(JsonObject obj, String claimName) {
        if (!obj.has(claimName)) {
            return null;
        }
        long ms = obj.get(claimName).getAsLong() * 1000;
        return new Date(ms);
    }

    private String getString(JsonObject obj, String claimName) {
        if (!obj.has(claimName)) {
            return null;
        }
        return obj.get(claimName).getAsString();
    }
}
