package com.auth0.android.jwtdecode;

import android.util.Base64;

import org.hamcrest.collection.IsArrayWithSize;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsArrayContainingInOrder.arrayContaining;
import static org.hamcrest.collection.IsArrayWithSize.arrayWithSize;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class JWTTest {

    private static final String CHARSET_UTF_8 = "UTF-8";

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void shouldGetHeader() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiJ9.e30.XmNK3GpH3Ys_7wsYBfq4C3M6goz71I7dTgUkuIa5lyQ");
        assertThat(jwt, is(notNullValue()));
        assertThat(jwt.getHeader(), is(instanceOf(Map.class)));
        assertThat(jwt.getHeader(), is(hasEntry("alg", "HS256")));
    }

    @Test
    public void shouldGetSignature() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiJ9.e30.XmNK3GpH3Ys_7wsYBfq4C3M6goz71I7dTgUkuIa5lyQ");
        assertThat(jwt, is(notNullValue()));
        assertThat(jwt.getSignature(), is("XmNK3GpH3Ys_7wsYBfq4C3M6goz71I7dTgUkuIa5lyQ"));
    }

    // Public Claims

    @Test
    public void shouldGetIssuer() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJKb2huIERvZSJ9.SgXosfRR_IwCgHq5lF3tlM-JHtpucWCRSaVuoHTbWbQ");
        assertThat(jwt, is(notNullValue()));
        assertThat(jwt.getIssuer(), is("John Doe"));
    }

    @Test
    public void shouldGetSubject() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJUb2szbnMifQ.RudAxkslimoOY3BLl2Ghny3BrUKu9I1ZrXzCZGDJtNs");
        assertThat(jwt, is(notNullValue()));
        assertThat(jwt.getSubject(), is("Tok3ns"));
    }

    @Test
    public void shouldGetArrayAudience() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOlsiSG9wZSIsIlRyYXZpcyIsIlNvbG9tb24iXX0.Tm4W8WnfPjlmHSmKFakdij0on2rWPETpoM7Sh0u6-S4");
        assertThat(jwt, is(notNullValue()));
        assertThat(jwt.getAudience(), is(arrayWithSize(3)));
        assertThat(jwt.getAudience(), is(arrayContaining("Hope", "Travis", "Solomon")));
    }

    @Test
    public void shouldGetStringAudience() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJKYWNrIFJleWVzIn0.a4I9BBhPt1OB1GW67g2P1bEHgi6zgOjGUL4LvhE9Dgc");
        assertThat(jwt, is(notNullValue()));
        assertThat(jwt.getAudience(), is(arrayWithSize(1)));
        assertThat(jwt.getAudience(), is(arrayContaining("Jack Reyes")));
    }

    @Test
    public void shouldGetExpiresAt() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiJ9.eyJleHAiOiIxNDc2NzI3MDg2In0.XwZztHlQwnAgmnQvrcWXJloLOUaLZGiY0HOXJCKRaks");
        assertThat(jwt, is(notNullValue()));
        assertThat(jwt.getExpiresAt(), is(instanceOf(Date.class)));
        long ms = 1476727086L * 1000;
        Date expectedDate = new Date(ms);
        assertThat(jwt.getExpiresAt(), is(notNullValue()));
        assertThat(jwt.getExpiresAt(), is(equalTo(expectedDate)));
    }

    @Test
    public void shouldGetNotBefore() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiJ9.eyJuYmYiOiIxNDc2NzI3MDg2In0.pi3Fi3oFiXk5A5AetDdL0hjVx_rt6F5r_YiG6HoCYDw");
        assertThat(jwt, is(notNullValue()));
        assertThat(jwt.getNotBefore(), is(instanceOf(Date.class)));
        long ms = 1476727086L * 1000;
        Date expectedDate = new Date(ms);
        assertThat(jwt.getNotBefore(), is(notNullValue()));
        assertThat(jwt.getNotBefore(), is(equalTo(expectedDate)));
    }

    @Test
    public void shouldGetIssuedAt() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOiIxNDc2NzI3MDg2In0.u6BxwrO7S0sqDY8-1cUOLzU2uejAJBzQQF8g_o5BAgo");
        assertThat(jwt, is(notNullValue()));
        assertThat(jwt.getIssuedAt(), is(instanceOf(Date.class)));
        long ms = 1476727086L * 1000;
        Date expectedDate = new Date(ms);
        assertThat(jwt.getIssuedAt(), is(notNullValue()));
        assertThat(jwt.getIssuedAt(), is(equalTo(expectedDate)));
    }

    @Test
    public void shouldGetId() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjM0NTY3ODkwIn0.m3zgEfVUFOd-CvL3xG5BuOWLzb0zMQZCqiVNQQOPOvA");
        assertThat(jwt, is(notNullValue()));
        assertThat(jwt.getId(), is("1234567890"));
    }

    //Private Claims

    @Test
    public void shouldGetNullIfClaimIsMissing() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiJ9.e30.K17vlwhE8FCMShdl1_65jEYqsQqBOVMPUU9IgG-QlTM");
        assertThat(jwt, is(notNullValue()));
        assertThat(jwt.getClaim("notExisting"), is(nullValue()));
    }

    @Test
    public void shouldGetNullIntegerIfClaimClassMismatch() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiJ9.eyJvYmplY3QiOnsibmFtZSI6ImpvaG4ifX0.lrU1gZlOdlmTTeZwq0VI-pZx2iV46UWYd5-lCjy6-c4");
        assertThat(jwt, is(notNullValue()));
        assertThat(jwt.getClaim("object").asInt(), is(nullValue()));
    }

    @Test
    public void shouldGetNullDoubleIfClaimClassMismatch() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiJ9.eyJvYmplY3QiOnsibmFtZSI6ImpvaG4ifX0.lrU1gZlOdlmTTeZwq0VI-pZx2iV46UWYd5-lCjy6-c4");
        assertThat(jwt, is(notNullValue()));
        assertThat(jwt.getClaim("object").asDouble(), is(nullValue()));
    }

    @Test
    public void shouldGetNullStringIfClaimClassMismatch() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiJ9.eyJvYmplY3QiOnsibmFtZSI6ImpvaG4ifX0.lrU1gZlOdlmTTeZwq0VI-pZx2iV46UWYd5-lCjy6-c4");
        assertThat(jwt, is(notNullValue()));
        assertThat(jwt.getClaim("object").asString(), is(nullValue()));
    }

    @Test
    public void shouldGetNullDateIfClaimClassMismatch() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiJ9.eyJvYmplY3QiOnsibmFtZSI6ImpvaG4ifX0.lrU1gZlOdlmTTeZwq0VI-pZx2iV46UWYd5-lCjy6-c4");
        assertThat(jwt, is(notNullValue()));
        assertThat(jwt.getClaim("object").asString(), is(nullValue()));
    }

    @Test
    public void getDateClaim() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiJ9.eyJtaWxsaXMiOjE0NzY4MTU2NDF9.i6gXnaIMC31aCeKy4CYt8X72VwyBBNuAfCEtqthYkOA");
        assertThat(jwt, is(notNullValue()));
        assertThat(jwt.getClaim("millis").asDate(), is(new Date(1476815641L * 1000)));
    }

    @Test
    public void shouldGetDoubleClaim() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiJ9.eyJwZXJjZW50IjoyMy41Nn0.KjBMiUGXy73Hz68DuCtqoju459sVZdH0RupHlEe1-Wc");
        assertThat(jwt, is(notNullValue()));
        assertThat(jwt.getClaim("percent").asDouble(), is(23.56));
    }

    @Test
    public void shouldGetArrayOfCustomClassClaim() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiJ9.eyJ1c2VycyI6W3sibmFtZSI6Ikdlb3JnZSIsImlkIjoxfSx7Im5hbWUiOiJNYXJrIiwiaWQiOjJ9XX0.CLzNVzzyi3eTC8RAoyOgFWnC1rEXEPE0TazAwOsowGE");
        assertThat(jwt, is(notNullValue()));
        assertThat(jwt.getClaim("users").asArray(UserPojo.class), is(arrayContaining(new UserPojo("George", 1), new UserPojo("Mark", 2))));
    }

    @Test
    public void shouldGetArrayOfPrimitiveClaim() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiJ9.eyJ0ZXh0IjpbInNvbWUiLCJ3b3JkcyJdfQ.5BCCZYrzdIwp1I6PFC46OXMf6TgbASuPRTTrBN-JkBM");
        assertThat(jwt, is(notNullValue()));
        assertThat(jwt.getClaim("text").asArray(String.class), is(arrayContaining("some", "words")));
    }

    @Test
    public void shouldGetNullArrayIfClaimIsNotArray() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiSm9obiBEb2UifQ.LlTGHPZRXbci-y349jXXN0byQniQQqwKGybzQCFIgY0");
        assertThat(jwt, is(notNullValue()));
        assertThat(jwt.getClaim("name").asArray(Integer.class), is(IsArrayWithSize.<Integer>emptyArray()));
    }

    @Test
    public void shouldGetEmptyArrayIfClaimClassMismatch() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiSm9obiBEb2UifQ.LlTGHPZRXbci-y349jXXN0byQniQQqwKGybzQCFIgY0");
        assertThat(jwt, is(notNullValue()));
        assertThat(jwt.getClaim("name").asArray(Integer.class), is(IsArrayWithSize.<Integer>emptyArray()));
    }

    @Test
    public void shouldGetListOfCustomClassClaim() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiJ9.eyJ1c2VycyI6W3sibmFtZSI6Ikdlb3JnZSIsImlkIjoxfSx7Im5hbWUiOiJNYXJrIiwiaWQiOjJ9XX0.CLzNVzzyi3eTC8RAoyOgFWnC1rEXEPE0TazAwOsowGE");
        assertThat(jwt, is(notNullValue()));
        assertThat(jwt.getClaim("users").asList(UserPojo.class), is(hasItems(new UserPojo("George", 1), new UserPojo("Mark", 2))));
    }

    @Test
    public void shouldGetListOfPrimitiveClaim() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiJ9.eyJ0ZXh0IjpbInNvbWUiLCJ3b3JkcyJdfQ.5BCCZYrzdIwp1I6PFC46OXMf6TgbASuPRTTrBN-JkBM");
        assertThat(jwt, is(notNullValue()));
        assertThat(jwt.getClaim("text").asList(String.class), is(hasItems("some", "words")));
    }

    @Test
    public void shouldGetNullListIfClaimIsNotArray() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiSm9obiBEb2UifQ.LlTGHPZRXbci-y349jXXN0byQniQQqwKGybzQCFIgY0");
        assertThat(jwt, is(notNullValue()));
        assertThat(jwt.getClaim("name").asList(Integer.class), is(IsEmptyCollection.emptyCollectionOf(Integer.class)));
    }

    @Test
    public void shouldGetEmptyListIfClaimClassMismatch() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiSm9obiBEb2UifQ.LlTGHPZRXbci-y349jXXN0byQniQQqwKGybzQCFIgY0");
        assertThat(jwt, is(notNullValue()));
        assertThat(jwt.getClaim("name").asList(Integer.class), is(IsEmptyCollection.emptyCollectionOf(Integer.class)));
    }

    //Helper Methods

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