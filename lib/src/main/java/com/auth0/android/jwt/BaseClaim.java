package com.auth0.android.jwt;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;

/**
 * The BaseClaim class is a Claim implementation that returns null when any of it's methods it's called.
 */
class BaseClaim implements Claim {

    @Nullable
    @Override
    public Boolean asBoolean() {
        return null;
    }

    @Nullable
    @Override
    public Integer asInt() {
        return null;
    }

    @Nullable
    @Override
    public Long asLong() { return null; }

    @Nullable
    @Override
    public Double asDouble() {
        return null;
    }

    @Nullable
    @Override
    public String asString() {
        return null;
    }

    @Nullable
    @Override
    public Date asDate() {
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public JSONArray asArray() {
        return null;
    }

    @Nullable
    @Override
    public JSONObject asObject() {
        return null;
    }
}
