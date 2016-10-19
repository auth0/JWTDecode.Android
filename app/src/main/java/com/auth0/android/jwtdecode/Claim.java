package com.auth0.android.jwtdecode;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A Claim is a value contained inside the JWT Payload.
 */
@SuppressWarnings("WeakerAccess")
public class Claim {

    private final JsonElement value;

    Claim(@NonNull JsonElement value) {
        this.value = value;
    }


    /**
     * Get this Claim as a Boolean.
     * If the value isn't of type Boolean or it can't be converted to a Boolean, null will be returned.
     *
     * @return the value as a Boolean or null.
     */
    @Nullable
    public Boolean asBoolean() {
        if (!value.isJsonPrimitive()) {
            return null;
        }
        return value.getAsBoolean();
    }

    /**
     * Get this Claim as an Integer.
     * If the value isn't of type Integer or it can't be converted to an Integer, null will be returned.
     *
     * @return the value as an Integer or null.
     */
    @Nullable
    public Integer asInt() {
        if (!value.isJsonPrimitive()) {
            return null;
        }
        return value.getAsInt();
    }

    /**
     * Get this Claim as a Double.
     * If the value isn't of type Double or it can't be converted to a Double, null will be returned.
     *
     * @return the value as a Double or null.
     */
    @Nullable
    public Double asDouble() {
        if (!value.isJsonPrimitive()) {
            return null;
        }
        return value.getAsDouble();
    }

    /**
     * Get this Claim as a String.
     * If the value isn't of type String or it can't be converted to a String, null will be returned.
     *
     * @return the value as a String or null.
     */
    @Nullable
    public String asString() {
        if (!value.isJsonPrimitive()) {
            return null;
        }
        return value.getAsString();
    }

    /**
     * Get this Claim as a Date.
     * If the value can't be converted to a Date, null will be returned.
     *
     * @return the value as a Date or null.
     */
    @Nullable
    public Date asDate() {
        if (!value.isJsonPrimitive()) {
            return null;
        }
        long ms = Long.parseLong(value.getAsString()) * 1000;
        return new Date(ms);
    }

    /**
     * Get this Claim as an Array of type T.
     * If the value isn't an Array, an empty Array will be returned.
     *
     * @return the value as an Array or an empty Array.
     * @throws JsonSyntaxException if the values inside the Array can't be converted to a class T.
     */
    @SuppressWarnings("unchecked")
    public <T> T[] asArray(Class<T> tClazz) throws JsonSyntaxException {
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
    }

    /**
     * Get this Claim as a List of type T.
     * If the value isn't an Array, an empty List will be returned.
     *
     * @return the value as a List or an empty List.
     * @throws JsonSyntaxException if the values inside the List can't be converted to a class T.
     */
    public <T> List<T> asList(Class<T> tClazz) throws JsonSyntaxException {
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
    }
}
