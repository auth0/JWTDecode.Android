package com.auth0.android.jwtdecode;

import com.auth0.android.jwtdecode.exceptions.JWTException;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

class JWTDeserializer implements JsonDeserializer<JWTPayload> {
    @Override
    public JWTPayload deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonNull() || !json.isJsonObject()) {
            throw new JWTException("The token's payload had an invalid JSON format.");
        }

        JsonObject object = json.getAsJsonObject();

        //Public Claims
        String iss = removeString(object, "iss");
        String sub = removeString(object, "sub");
        Date exp = removeDate(object, "exp");
        Date nbf = removeDate(object, "nbf");
        Date iat = removeDate(object, "iat");
        String jti = removeString(object, "jti");
        String[] aud = removeStringOrArray(object, "aud");

        //Private Claims
        Map<String, Claim> extra = new HashMap<>();
        for (Map.Entry<String, JsonElement> e : object.entrySet()) {
            extra.put(e.getKey(), new Claim(e.getValue()));
        }

        return new JWTPayload(iss, sub, exp, nbf, iat, jti, aud, extra);
    }

    private String[] removeStringOrArray(JsonObject obj, String claimName) {
        String[] arr = null;
        if (obj.has(claimName)) {
            JsonElement arrElement = obj.remove(claimName);
            if (arrElement.isJsonArray()) {
                JsonArray jsonArr = arrElement.getAsJsonArray();
                arr = new String[jsonArr.size()];
                for (int i = 0; i < jsonArr.size(); i++) {
                    arr[i] = jsonArr.get(i).getAsString();
                }
            } else {
                arr = new String[]{arrElement.getAsString()};
            }
        }
        return arr;
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
