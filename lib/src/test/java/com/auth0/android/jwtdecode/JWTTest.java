package com.auth0.android.jwtdecode;

import android.support.annotation.Nullable;
import android.util.Base64;

import com.auth0.android.jwtdecode.exceptions.JWTException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
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
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class JWTTest {

    private static final String CHARSET_UTF_8 = "UTF-8";

    @Rule
    public ExpectedException exception = ExpectedException.none();

    // Exceptions
    @Test
    public void shouldThrowIfLessThan3Parts() throws Exception {
        exception.expect(JWTException.class);
        exception.expectMessage("The token was expected to have 3 parts, but got 2.");
        new JWT("two.parts");
    }

    @Test
    public void shouldThrowIfMoreThan3Parts() throws Exception {
        exception.expect(JWTException.class);
        exception.expectMessage("The token was expected to have 3 parts, but got 4.");
        new JWT("this.has.four.parts");
    }

    @Test
    public void shouldThrowIfItsNotBase64Encoded() throws Exception {
        exception.expect(JWTException.class);
        exception.expectMessage("Received bytes didn't correspond to a valid Base64 encoded string.");
        new JWT("thisIsNot.Base64_Enc.oded");
    }

    @Test
    public void shouldThrowIfPayloadHasInvalidJSONFormat() throws Exception {
        exception.expect(JWTException.class);
        exception.expectMessage("The token's payload had an invalid JSON format.");
        new JWT("eyJhbGciOiJIUzI1NiJ9.e30ijfe923.XmNK3GpH3Ys_7lyQ");
    }

    // toString
    @Test
    public void shouldGetStringToken() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiJ9.e30.XmNK3GpH3Ys_7wsYBfq4C3M6goz71I7dTgUkuIa5lyQ");
        assertThat(jwt, is(notNullValue()));
        assertThat(jwt.toString(), is(notNullValue()));
        assertThat(jwt.toString(), is("eyJhbGciOiJIUzI1NiJ9.e30.XmNK3GpH3Ys_7wsYBfq4C3M6goz71I7dTgUkuIa5lyQ"));
    }

    // Parts

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
    public void shouldGetExpirationTime() throws Exception {
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

    @Test
    public void shouldBeExpired() throws Exception {
        long pastSeconds = System.currentTimeMillis() / 1000;
        long futureSeconds = (System.currentTimeMillis() + 10000) / 1000;

        JWT issuedAndExpiresInTheFuture = customTimeJWT(futureSeconds, futureSeconds);
        assertTrue(issuedAndExpiresInTheFuture.isExpired());
        JWT issuedInTheFuture = customTimeJWT(futureSeconds, null);
        assertTrue(issuedInTheFuture.isExpired());

        JWT issuedAndExpiresInThePast = customTimeJWT(pastSeconds, pastSeconds);
        assertTrue(issuedAndExpiresInThePast.isExpired());
        JWT expiresInThePast = customTimeJWT(null, pastSeconds);
        assertTrue(expiresInThePast.isExpired());

        JWT issuedInTheFutureExpiresInThePast = customTimeJWT(futureSeconds, pastSeconds);
        assertTrue(issuedInTheFutureExpiresInThePast.isExpired());
    }

    @Test
    public void shouldNotBeExpired() throws Exception {
        long pastSeconds = System.currentTimeMillis() / 1000;
        long futureSeconds = (System.currentTimeMillis() + 10000) / 1000;

        JWT missingDates = customTimeJWT(null, null);
        assertFalse(missingDates.isExpired());

        JWT issuedInThePastExpiresInTheFuture = customTimeJWT(pastSeconds, futureSeconds);
        assertFalse(issuedInThePastExpiresInTheFuture.isExpired());

        JWT issuedInThePast = customTimeJWT(pastSeconds, null);
        assertFalse(issuedInThePast.isExpired());

        JWT expiresInTheFuture = customTimeJWT(null, futureSeconds);
        assertFalse(expiresInTheFuture.isExpired());
    }

    //Private Claims

    @Test
    public void shouldGetNullIfClaimIsMissing() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiJ9.e30.K17vlwhE8FCMShdl1_65jEYqsQqBOVMPUU9IgG-QlTM");
        assertThat(jwt, is(notNullValue()));
        assertThat(jwt.getClaim("notExisting"), is(nullValue()));
    }

    @Test
    public void shouldGetClaim() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiJ9.eyJvYmplY3QiOnsibmFtZSI6ImpvaG4ifX0.lrU1gZlOdlmTTeZwq0VI-pZx2iV46UWYd5-lCjy6-c4");
        assertThat(jwt, is(notNullValue()));
        assertThat(jwt.getClaim("object"), is(notNullValue()));
        assertThat(jwt.getClaim("object"), is(instanceOf(Claim.class)));
    }

    //Helper Methods

    private JWT customTimeJWT(@Nullable Long iat, @Nullable Long exp) {
        String header = encodeString("{}");
        StringBuilder bodyBuilder = new StringBuilder("{");
        if (iat != null) {
            bodyBuilder.append("\"iat\":\"").append(iat.longValue()).append("\"");
        }
        if (exp != null) {
            if (iat != null) {
                bodyBuilder.append(",");
            }
            bodyBuilder.append("\"exp\":\"").append(exp.longValue()).append("\"");
        }
        bodyBuilder.append("}");
        String body = encodeString(bodyBuilder.toString());
        String signature = "sign";
        return new JWT(String.format("%s.%s.%s", header, body, signature));
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