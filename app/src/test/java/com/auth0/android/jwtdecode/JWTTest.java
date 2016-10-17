package com.auth0.android.jwtdecode;

import android.util.Base64;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.Date;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsArrayContainingInOrder.arrayContaining;
import static org.hamcrest.collection.IsArrayWithSize.arrayWithSize;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class JWTTest {

    private static final String CHARSET_UTF_8 = "UTF-8";

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getHeader() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiJ9.e30.XmNK3GpH3Ys_7wsYBfq4C3M6goz71I7dTgUkuIa5lyQ");
        assertThat(jwt, is(notNullValue()));
        assertThat(jwt.getHeader(), is("{\"alg\":\"HS256\"}"));
    }

    @Test
    public void getSignature() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiJ9.e30.XmNK3GpH3Ys_7wsYBfq4C3M6goz71I7dTgUkuIa5lyQ");
        assertThat(jwt, is(notNullValue()));
        assertThat(jwt.getSignature(), is("XmNK3GpH3Ys_7wsYBfq4C3M6goz71I7dTgUkuIa5lyQ"));
    }

    @Test
    public void getIssuer() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJKb2huIERvZSJ9.SgXosfRR_IwCgHq5lF3tlM-JHtpucWCRSaVuoHTbWbQ");
        assertThat(jwt, is(notNullValue()));
        assertThat(jwt.getIssuer(), is("John Doe"));
    }

    @Test
    public void getSubject() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJUb2szbnMifQ.RudAxkslimoOY3BLl2Ghny3BrUKu9I1ZrXzCZGDJtNs");
        assertThat(jwt, is(notNullValue()));
        assertThat(jwt.getSubject(), is("Tok3ns"));
    }

    @Test
    public void getArrayAudience() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOlsiSG9wZSIsIlRyYXZpcyIsIlNvbG9tb24iXX0.Tm4W8WnfPjlmHSmKFakdij0on2rWPETpoM7Sh0u6-S4");
        assertThat(jwt, is(notNullValue()));
        assertThat(jwt.getAudience(), is(arrayWithSize(3)));
        assertThat(jwt.getAudience(), is(arrayContaining("Hope", "Travis", "Solomon")));
    }

    @Test
    public void getStringAudience() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJKYWNrIFJleWVzIn0.a4I9BBhPt1OB1GW67g2P1bEHgi6zgOjGUL4LvhE9Dgc");
        assertThat(jwt, is(notNullValue()));
        assertThat(jwt.getAudience(), is(arrayWithSize(1)));
        assertThat(jwt.getAudience(), is(arrayContaining("Jack Reyes")));
    }

    @Test
    public void getExpiresAt() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiJ9.eyJleHAiOiIxNDc2NzI3MDg2In0.XwZztHlQwnAgmnQvrcWXJloLOUaLZGiY0HOXJCKRaks");
        assertThat(jwt, is(notNullValue()));
        assertThat(jwt.getExpiresAt(), is(instanceOf(Date.class)));
        long ms = 1476727086L * 1000;
        Date expectedDate = new Date(ms);
        assertThat(jwt.getExpiresAt(), is(notNullValue()));
        assertThat(jwt.getExpiresAt(), is(equalTo(expectedDate)));
    }

    @Test
    public void getNotBefore() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiJ9.eyJuYmYiOiIxNDc2NzI3MDg2In0.pi3Fi3oFiXk5A5AetDdL0hjVx_rt6F5r_YiG6HoCYDw");
        assertThat(jwt, is(notNullValue()));
        assertThat(jwt.getNotBefore(), is(instanceOf(Date.class)));
        long ms = 1476727086L * 1000;
        Date expectedDate = new Date(ms);
        assertThat(jwt.getNotBefore(), is(notNullValue()));
        assertThat(jwt.getNotBefore(), is(equalTo(expectedDate)));
    }

    @Test
    public void getIssuedAt() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOiIxNDc2NzI3MDg2In0.u6BxwrO7S0sqDY8-1cUOLzU2uejAJBzQQF8g_o5BAgo");
        assertThat(jwt, is(notNullValue()));
        assertThat(jwt.getIssuedAt(), is(instanceOf(Date.class)));
        long ms = 1476727086L * 1000;
        Date expectedDate = new Date(ms);
        assertThat(jwt.getIssuedAt(), is(notNullValue()));
        assertThat(jwt.getIssuedAt(), is(equalTo(expectedDate)));
    }

    @Test
    public void getId() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjM0NTY3ODkwIn0.m3zgEfVUFOd-CvL3xG5BuOWLzb0zMQZCqiVNQQOPOvA");
        assertThat(jwt, is(Matchers.notNullValue()));
        assertThat(jwt.getId(), is("1234567890"));
    }

    private String generateToken(String jsonHeader, String jsonBody, String signature) {
        String header = encodeString(jsonHeader);
        String body = encodeString(jsonBody);
        return String.format("%s.%s.%s", header, body, signature);
    }

    private String encodeString(String source) {
        byte[] bytes = Base64.encode(source.getBytes(), Base64.URL_SAFE | Base64.NO_WRAP | Base64.NO_PADDING);
        String res = "";
        try {
            res = new String(bytes, CHARSET_UTF_8);
        } catch (UnsupportedEncodingException ignored) {
        }
        return res;
    }

}