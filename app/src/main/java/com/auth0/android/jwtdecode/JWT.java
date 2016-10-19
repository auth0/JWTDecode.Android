package com.auth0.android.jwtdecode;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;

import com.auth0.android.jwtdecode.exceptions.JWTException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.Map;

public class JWT {

    private static final String TAG = JWT.class.getSimpleName();
    private static final String ENCODING_UTF_8 = "UTF-8";

    private Map<String, String> header;
    private JWTPayload payload;
    private String signature;

    public JWT(@NonNull String token) {
        decode(token);
    }

    @Nullable
    public Map<String, String> getHeader() {
        return header;
    }

    @Nullable
    public String getSignature() {
        return signature;
    }

    @Nullable
    public String getIssuer() {
        return payload.iss;
    }

    @Nullable
    public String getSubject() {
        return payload.sub;
    }

    @Nullable
    public String[] getAudience() {
        return payload.aud;
    }

    @Nullable
    public Date getExpiresAt() {
        return payload.exp;
    }

    @Nullable
    public Date getNotBefore() {
        return payload.nbf;
    }

    @Nullable
    public Date getIssuedAt() {
        return payload.iat;
    }

    @Nullable
    public String getId() {
        return payload.jti;
    }

    @Nullable
    public Claim getClaim(@NonNull String name) {
        return payload.extra.get(name);
    }

    public boolean isExpired() {
        final Date today = new Date();
        boolean issuedInTheFuture = payload.iat != null && payload.iat.after(today);
        boolean expired = payload.exp != null && payload.exp.before(today);
        return issuedInTheFuture || expired;
    }


    // =====================================
    // ===========Private Methods===========
    // =====================================

    private void decode(String token) {
        final String[] parts = splitToken(token);
        Type mapType = new TypeToken<Map<String, String>>() {
        }.getType();
        header = parseJson(base64Decode(parts[0]), mapType);
        payload = parseJson(base64Decode(parts[1]), JWTPayload.class);
        signature = parts[2];
    }

    private String[] splitToken(String token) {
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            throw new JWTException(String.format("The token was expected to have 3 parts, but got %s.", parts.length));
        }
        return parts;
    }

    @Nullable
    private String base64Decode(String string) {
        String decoded;
        try {
            byte[] bytes = Base64.decode(string, Base64.URL_SAFE | Base64.NO_WRAP | Base64.NO_PADDING);
            decoded = new String(bytes, ENCODING_UTF_8);
        } catch (IllegalArgumentException e) {
            throw new JWTException("Received bytes didn't correspond to a valid Base64 encoded string.", e);
        } catch (UnsupportedEncodingException e) {
            throw new JWTException("Device doesn't support UTF-8 charset encoding.", e);
        }
        return decoded;
    }

    private <T> T parseJson(String json, Type typeOfT) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(JWTPayload.class, new JWTDeserializer())
                .create();
        T payload;
        try {
            payload = gson.fromJson(json, typeOfT);
        } catch (Exception e) {
            throw new JWTException("The token's payload had an invalid JSON format.", e);
        }
        return payload;
    }
}
