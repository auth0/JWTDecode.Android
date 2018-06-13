package com.auth0.android.jwt;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The ClaimImpl class implements the Claim interface.
 */
@SuppressWarnings("WeakerAccess")
class ClaimImpl extends BaseClaim {

    private final JsonElement value;
    private final Map<String, Claim> subClaims;

    ClaimImpl(@NonNull JsonElement value) {
        this.value = value;

        subClaims = parseSubClaims();
    }

    private Map<String, Claim> parseSubClaims() {
        if (!value.isJsonObject()) {
            return null;
        }

        JsonObject jsonObject = value.getAsJsonObject();
        Map<String, Claim> subClaimMap = new HashMap<>();
        for (Map.Entry<String, JsonElement> e : jsonObject.entrySet()) {
            subClaimMap.put(e.getKey(), new ClaimImpl(e.getValue()));
        }
        return subClaimMap;
    }

    @Override
    @Nullable
    public Boolean asBoolean() {
        if (!value.isJsonPrimitive()) {
            return null;
        }
        return value.getAsBoolean();
    }

    @Override
    @Nullable
    public Integer asInt() {
        if (!value.isJsonPrimitive()) {
            return null;
        }
        return value.getAsInt();
    }

    @Override
    @Nullable
    public Double asDouble() {
        if (!value.isJsonPrimitive()) {
            return null;
        }
        return value.getAsDouble();
    }

    @Override
    @Nullable
    public String asString() {
        if (!value.isJsonPrimitive()) {
            return null;
        }
        return value.getAsString();
    }

    @Override
    @Nullable
    public Date asDate() {
        if (!value.isJsonPrimitive()) {
            return null;
        }
        long ms = Long.parseLong(value.getAsString()) * 1000;
        return new Date(ms);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] asArray(Class<T> tClazz) throws DecodeException {
        try {
            if (!value.isJsonArray() || value.isJsonNull()) {
                return (T[]) Array.newInstance(tClazz, 0);
            }
            Gson gson = new Gson();
            JsonArray jsonArr = value.getAsJsonArray();
            T[] arr = (T[]) Array.newInstance(tClazz, jsonArr.size());
            for (int i = 0; i < jsonArr.size(); i++) {
                arr[i] = gson.fromJson(jsonArr.get(i), tClazz);
            }
            return arr;
        } catch (JsonSyntaxException e) {
            throw new DecodeException("Failed to decode claim as array", e);
        }
    }

    @Override
    public <T> List<T> asList(Class<T> tClazz) throws DecodeException {
        try {
            if (!value.isJsonArray() || value.isJsonNull()) {
                return new ArrayList<>();
            }
            Gson gson = new Gson();
            JsonArray jsonArr = value.getAsJsonArray();
            List<T> list = new ArrayList<>();
            for (int i = 0; i < jsonArr.size(); i++) {
                list.add(gson.fromJson(jsonArr.get(i), tClazz));
            }
            return list;
        } catch (JsonSyntaxException e) {
            throw new DecodeException("Failed to decode claim as list", e);
        }
    }

    @Nullable
    @Override
    public Map<String, Claim> getSubClaims() {
        return subClaims;
    }

    @Nullable
    @Override
    public Claim getSubClaim(String name) {
        if (subClaims == null) {
            return null;
        } else {
            return subClaims.get(name);
        }
    }
}
