package com.auth0.android.jwt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

final class JSONObjectClaim implements Claim {
    @NonNull
    private final String name;
    @NonNull
    private final JSONObject object;

    JSONObjectClaim(@NonNull JSONObject object, @NonNull String name) {
        this.object = object;
        this.name = name;
    }

    @Nullable
    @Override
    public Boolean asBoolean() {
        try {
            return object.getBoolean(name);
        } catch (JSONException exception) {
            return null;
        }
    }

    @Nullable
    @Override
    public Integer asInt() {
        try {
            return object.getInt(name);
        } catch (JSONException exception) {
            return null;
        }
    }

    @Nullable
    @Override
    public Long asLong() {
        try {
            return object.getLong(name);
        } catch (JSONException exception) {
            return null;
        }
    }

    @Nullable
    @Override
    public Double asDouble() {
        try {
            return object.getDouble(name);
        } catch (JSONException exception) {
            return null;
        }
    }

    @Nullable
    @Override
    public String asString() {
        if (object.isNull(name) || object.optJSONArray(name) != null || object.optJSONObject(name) != null) {
            return null;
        }

        try {
            return object.getString(name);
        } catch (JSONException exception) {
            return null;
        }
    }

    @Nullable
    @Override
    public Date asDate() {
        final Long value = asLong();
        if (value != null) {
            return new Date(value * 1000);
        }

        return null;
    }

    @Nullable
    @Override
    public JSONArray asArray() {
        return object.optJSONArray(name);
    }

    @Nullable
    @Override
    public JSONObject asObject() {
        return object.optJSONObject(name);
    }
}
