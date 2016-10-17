package com.auth0.android.jwtdecode;

import java.util.Date;

public class JWTPayload {

    String iss;
    String sub;
    Date exp;
    Date nbf;
    Date iat;
    String jti;
    String[] aud;

    public JWTPayload(String iss, String sub, Date exp, Date nbf, Date iat, String jti, String[] aud) {
        this.iss = iss;
        this.sub = sub;
        this.exp = exp;
        this.nbf = nbf;
        this.iat = iat;
        this.jti = jti;
        this.aud = aud;
    }

}
