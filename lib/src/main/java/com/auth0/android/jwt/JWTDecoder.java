package com.auth0.android.jwt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

class JWTDecoder {
    public Map<String, String> decodeHeader(@NonNull String json) {
        final JSONObject object;
        try {
            object = new JSONObject(json);
        } catch (JSONException exception) {
            throw new DecodeException("The token's payload had an invalid JSON format.", exception);
        }

        final Map<String, String> map = new HashMap<>();
        for (Iterator<String> it = object.keys(); it.hasNext(); ) {
            String key = it.next();
            String value = getString(object, key);
            if (value != null) {
                map.put(key, value);
            }
        }

        return map;
    }

    public JWTPayload decodePayload(@NonNull String json) {

        final JSONObject object;
        try {
            object = new JSONObject(json);
        } catch (JSONException exception) {
            throw new DecodeException("The token's payload had an invalid JSON format.", exception);
        }

        final String iss = getString(object, "iss");
        final String sub = getString(object, "sub");
        final Date exp = getDate(object, "exp");
        final Date nbf = getDate(object, "nbf");
        final Date iat = getDate(object, "iat");
        final String jti = getString(object, "jti");
        final List<String> aud = getStringOrArray(object, "aud");

        final Map<String, Claim> extra = new HashMap<>();
        for (Iterator<String> it = object.keys(); it.hasNext(); ) {
            String key = it.next();
            extra.put(key, new JSONObjectClaim(object, key));
        }

        return new JWTPayload(
            iss,
            sub,
            exp,
            nbf,
            iat,
            jti,
            aud,
            extra
        );
    }

    @Nullable
    private String getString(@NonNull JSONObject json, @NonNull String key) {
        if (!json.has(key) || json.isNull(key)) {
            return null;
        }

        try {
            return json.getString(key);
        } catch (JSONException exception) {
            return null;
        }
    }

    @Nullable
    private Long getLong(@NonNull JSONObject json, @NonNull String key) {
        if (!json.has(key) || json.isNull(key)) {
            return null;
        }

        try {
            return json.getLong(key);
        } catch (JSONException exception) {
            return null;
        }
    }

    @Nullable
    private Date getDate(@NonNull JSONObject json, @NonNull String key) {
        final Long seconds = getLong(json, key);
        if (seconds == null) {
            return null;
        }

        return new Date(seconds * 1000);
    }

    private List<String> getStringOrArray(@NonNull JSONObject json, @NonNull String key) {
        final JSONArray jsonArray = json.optJSONArray(key);
        if (jsonArray != null) {
            final List<String> list = new ArrayList<>(jsonArray.length());
            for (int idx = 0; idx < jsonArray.length(); idx += 1) {
                try {
                    list.add(jsonArray.getString(idx));
                } catch (JSONException exception) {
                    // This can only happen if I messed up the for loop
                }
            }
            return list;
        } else {
            final String single = getString(json, key);
            if (single != null) {
                return Collections.singletonList(single);
            }
        }

        return Collections.emptyList();
    }
}
