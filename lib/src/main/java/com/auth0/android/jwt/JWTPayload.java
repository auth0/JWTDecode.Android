package com.auth0.android.jwt;

import java.util.Date;
import java.util.List;
import java.util.Map;

class JWTPayload {

    String iss;
    String sub;
    Date exp;
    Date nbf;
    Date iat;
    String jti;
    List<String> aud;
    Map<String, Claim> tree;

    JWTPayload(String iss, String sub, Date exp, Date nbf, Date iat, String jti, List<String> aud, Map<String, Claim> tree) {
        this.iss = iss;
        this.sub = sub;
        this.exp = exp;
        this.nbf = nbf;
        this.iat = iat;
        this.jti = jti;
        this.aud = aud;
        this.tree = tree;
    }

}
