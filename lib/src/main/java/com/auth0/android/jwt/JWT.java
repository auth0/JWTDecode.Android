package com.auth0.android.jwt;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Wrapper class for values contained inside a Json Web Token (JWT).
 */
@SuppressWarnings("ALL")
public class JWT implements Parcelable {

    private static final String TAG = JWT.class.getSimpleName();
    private static final String ENCODING_UTF_8 = "UTF-8";
    private final String token;

    private Map<String, String> header;
    private JWTPayload payload;
    private String signature;

    /**
     * Decode a given string JWT token.
     *
     * @param token the string JWT token.
     * @throws DecodeException if the token cannot be decoded
     */
    public JWT(@NonNull String token) {
        decode(token);
        this.token = token;
    }

    /**
     * Get the Header values from this JWT as a Map of Strings.
     *
     * @return the Header values of the JWT.
     */
    @NonNull
    public Map<String, String> getHeader() {
        return header;
    }

    /**
     * Get the Signature from this JWT as a Base64 encoded String.
     *
     * @return the Signature of the JWT.
     */
    @NonNull
    public String getSignature() {
        return signature;
    }

    /**
     * Get the value of the "iss" claim, or null if it's not available.
     *
     * @return the Issuer value or null.
     */
    @Nullable
    public String getIssuer() {
        return payload.iss;
    }

    /**
     * Get the value of the "sub" claim, or null if it's not available.
     *
     * @return the Subject value or null.
     */
    @Nullable
    public String getSubject() {
        return payload.sub;
    }

    /**
     * Get the value of the "aud" claim, or an empty list if it's not available.
     *
     * @return the Audience value or an empty list.
     */
    @Nullable
    public List<String> getAudience() {
        return payload.aud;
    }

    /**
     * Get the value of the "exp" claim, or null if it's not available.
     *
     * @return the Expiration Time value or null.
     */
    @Nullable
    public Date getExpiresAt() {
        return payload.exp;
    }

    /**
     * Get the value of the "nbf" claim, or null if it's not available.
     *
     * @return the Not Before value or null.
     */
    @Nullable
    public Date getNotBefore() {
        return payload.nbf;
    }

    /**
     * Get the value of the "iat" claim, or null if it's not available.
     *
     * @return the Issued At value or null.
     */
    @Nullable
    public Date getIssuedAt() {
        return payload.iat;
    }

    /**
     * Get the value of the "jti" claim, or null if it's not available.
     *
     * @return the JWT ID value or null.
     */
    @Nullable
    public String getId() {
        return payload.jti;
    }

    /**
     * Get a Claim given it's name. If the Claim wasn't specified in the JWT payload, a BaseClaim will be returned.
     *
     * @param name the name of the Claim to retrieve.
     * @return a valid Claim.
     */
    @NonNull
    public Claim getClaim(@NonNull String name) {
        return payload.claimForName(name);
    }

    /**
     * Get all the Claims.
     *
     * @return a valid Map of Claims.
     */
    @NonNull
    public Map<String, Claim> getClaims() {
        return payload.tree;
    }

    /**
     * Validates that this JWT was issued in the past and hasn't expired yet.
     *
     * @param leeway the time leeway in seconds in which the token should still be considered valid.
     * @return if this JWT has already expired or not.
     */
    public boolean isExpired(long leeway) {
        if (leeway < 0) {
            throw new IllegalArgumentException("The leeway must be a positive value.");
        }
        long todayTime = (long) (Math.floor(new Date().getTime() / 1000) * 1000); //truncate millis
        Date futureToday = new Date((todayTime + leeway * 1000));
        Date pastToday = new Date((todayTime - leeway * 1000));
        boolean expValid = payload.exp == null || !pastToday.after(payload.exp);
        boolean iatValid = payload.iat == null || !futureToday.before(payload.iat);
        return !expValid || !iatValid;
    }

    /**
     * Returns the String representation of this JWT.
     *
     * @return the String Token.
     */
    @Override
    public String toString() {
        return token;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(token);
    }

    public static final Creator<JWT> CREATOR = new Creator<JWT>() {
        @Override
        public JWT createFromParcel(Parcel in) {
            return new JWT(in.readString());
        }

        @Override
        public JWT[] newArray(int size) {
            return new JWT[size];
        }
    };

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
        if (parts.length == 2 && token.endsWith(".")) {
            //Tokens with alg='none' have empty String as Signature.
            parts = new String[]{parts[0], parts[1], ""};
        }
        if (parts.length != 3) {
            throw new DecodeException(String.format("The token was expected to have 3 parts, but got %s.", parts.length));
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
            throw new DecodeException("Received bytes didn't correspond to a valid Base64 encoded string.", e);
        } catch (UnsupportedEncodingException e) {
            throw new DecodeException("Device doesn't support UTF-8 charset encoding.", e);
        }
        return decoded;
    }

    private <T> T parseJson(String json, Type typeOfT) {
        T payload;
        try {
            payload = getGson().fromJson(json, typeOfT);
        } catch (Exception e) {
            throw new DecodeException("The token's payload had an invalid JSON format.", e);
        }
        return payload;
    }

    static Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(JWTPayload.class, new JWTDeserializer())
                .create();
    }
}
