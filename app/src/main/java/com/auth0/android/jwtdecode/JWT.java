package com.auth0.android.jwtdecode;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;

import com.auth0.android.jwtdecode.exceptions.InvalidJsonException;
import com.auth0.android.jwtdecode.exceptions.JWTException;
import com.auth0.android.jwtdecode.exceptions.MalformedJWTException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.UnsupportedEncodingException;
import java.util.Date;

public class JWT {

    private static final String TAG = JWT.class.getSimpleName();
    private static final String ENCODING_UTF_8 = "UTF-8";

    private String header;
    private JWTPayload payload;
    private String signature;

    public JWT(@NonNull String token) {
        decode(token);
    }

    @Nullable
    public String getHeader() {
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

    // =====================================
    // ===========Private Methods===========
    // =====================================

    private void decode(String token) {
        final String[] parts = splitToken(token);
        header = base64Decode(parts[0]);
        payload = parsePayload(base64Decode(parts[1]));
        signature = parts[2];
    }

    private String[] splitToken(String token) {
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            throw new MalformedJWTException(String.format("The token was expected to have 3 parts, but got %s.", parts.length));
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
            throw new JWTException("Received bytes weren't UTF-8 encoded.", e);
        }
        return decoded;
    }

    private JWTPayload parsePayload(String json) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(JWTPayload.class, new JwtDeserializer())
                .create();

        JWTPayload payload;
        try {
            payload = gson.fromJson(json, JWTPayload.class);
        } catch (Exception e) {
            throw new InvalidJsonException("The token's payload had an invalid JSON format.", e);
        }
        return payload;
    }
}
