package com.auth0.android.jwt;

import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.Nullable;
import android.util.Base64;

import org.hamcrest.collection.IsEmptyCollection;
import org.hamcrest.core.IsCollectionContaining;
import org.junit.Assert;
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
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class JWTTest {

    private static final String CHARSET_UTF_8 = "UTF-8";

    @Rule
    public ExpectedException exception = ExpectedException.none();

    // Exceptions
    @Test
    public void shouldThrowIfLessThan3Parts() throws Exception {
        exception.expect(DecodeException.class);
        exception.expectMessage("The token was expected to have 3 parts, but got 2.");
        new JWT("two.parts");
    }

    @Test
    public void shouldThrowIfMoreThan3Parts() throws Exception {
        exception.expect(DecodeException.class);
        exception.expectMessage("The token was expected to have 3 parts, but got 4.");
        new JWT("this.has.four.parts");
    }

    @Test
    public void shouldThrowIfItsNotBase64Encoded() throws Exception {
        exception.expect(DecodeException.class);
        exception.expectMessage("Received bytes didn't correspond to a valid Base64 encoded string.");
        new JWT("thisIsNot.Base64_Enc.oded");
    }

    @Test
    public void shouldThrowIfPayloadHasInvalidJSONFormat() throws Exception {
        exception.expect(DecodeException.class);
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

    @Test
    public void shouldGetEmptySignature() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiJ9.e30.");
        assertThat(jwt, is(notNullValue()));
        assertThat(jwt.getSignature(), is(""));
    }

    // Public Claims

    @Test
    public void shouldGetIssuer() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJKb2huIERvZSJ9.SgXosfRR_IwCgHq5lF3tlM-JHtpucWCRSaVuoHTbWbQ");
        assertThat(jwt, is(notNullValue()));
        assertThat(jwt.getIssuer(), is("John Doe"));
    }

    @Test
    public void shouldGetNullIssuerIfMissing() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiJ9.e30.something");
        assertThat(jwt, is(notNullValue()));

        Assert.assertThat(jwt.getIssuer(), is(nullValue()));
    }

    @Test
    public void shouldGetSubject() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJUb2szbnMifQ.RudAxkslimoOY3BLl2Ghny3BrUKu9I1ZrXzCZGDJtNs");
        assertThat(jwt, is(notNullValue()));
        assertThat(jwt.getSubject(), is("Tok3ns"));
    }

    @Test
    public void shouldGetNullSubjectIfMissing() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiJ9.e30.something");
        assertThat(jwt, is(notNullValue()));

        Assert.assertThat(jwt.getSubject(), is(nullValue()));
    }

    @Test
    public void shouldGetArrayAudience() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOlsiSG9wZSIsIlRyYXZpcyIsIlNvbG9tb24iXX0.Tm4W8WnfPjlmHSmKFakdij0on2rWPETpoM7Sh0u6-S4");
        assertThat(jwt, is(notNullValue()));
        assertThat(jwt.getAudience(), is(hasSize(3)));
        assertThat(jwt.getAudience(), is(hasItems("Hope", "Travis", "Solomon")));
    }

    @Test
    public void shouldGetStringAudience() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJKYWNrIFJleWVzIn0.a4I9BBhPt1OB1GW67g2P1bEHgi6zgOjGUL4LvhE9Dgc");
        assertThat(jwt, is(notNullValue()));
        assertThat(jwt.getAudience(), is(hasSize(1)));
        assertThat(jwt.getAudience(), is(hasItems("Jack Reyes")));
    }

    @Test
    public void shouldGetEmptyListAudienceIfMissing() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiJ9.e30.something");
        assertThat(jwt, is(notNullValue()));

        Assert.assertThat(jwt.getAudience(), IsEmptyCollection.<String>empty());
    }

    @Test
    public void shouldDeserializeDatesUsingLong() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjIxNDc0OTM2NDcsIm5iZiI6MjE0NzQ5MzY0NywiZXhwIjoyMTQ3NDkzNjQ3LCJjdG0iOjIxNDc0OTM2NDd9.txmUJ0UCy2pqTFrEgj49eNDQCWUSW_XRMjMaRqcrgLg");
        assertThat(jwt, is(notNullValue()));

        long secs = Integer.MAX_VALUE + 10000L;
        Date expectedDate = new Date(secs * 1000);
        assertThat(jwt.getIssuedAt(), is(expectedDate));
        assertThat(jwt.getNotBefore(), is(expectedDate));
        assertThat(jwt.getExpiresAt(), is(expectedDate));
        assertThat(jwt.getClaim("ctm").asDate(), is(expectedDate));
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
    public void shouldGetNullExpirationTimeIfMissing() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiJ9.e30.something");
        assertThat(jwt, is(notNullValue()));

        Assert.assertThat(jwt.getExpiresAt(), is(nullValue()));
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
    public void shouldGetNullNotBeforeIfMissing() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiJ9.e30.something");
        assertThat(jwt, is(notNullValue()));

        Assert.assertThat(jwt.getNotBefore(), is(nullValue()));
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
    public void shouldGetNullIssuedAtIfMissing() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiJ9.e30.something");
        assertThat(jwt, is(notNullValue()));

        Assert.assertThat(jwt.getIssuedAt(), is(nullValue()));
    }

    @Test
    public void shouldGetId() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjM0NTY3ODkwIn0.m3zgEfVUFOd-CvL3xG5BuOWLzb0zMQZCqiVNQQOPOvA");
        assertThat(jwt, is(notNullValue()));
        assertThat(jwt.getId(), is("1234567890"));
    }

    @Test
    public void shouldGetNullIdIfMissing() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiJ9.e30.something");
        assertThat(jwt, is(notNullValue()));

        Assert.assertThat(jwt.getId(), is(nullValue()));
    }

    @Test
    public void shouldNotBeDeemedExpiredWithoutDateClaims() throws Exception {
        JWT jwt = customTimeJWT(null, null);
        assertThat(jwt.isExpired(0), is(false));
    }

    @Test
    public void shouldNotBeDeemedExpired() throws Exception {
        JWT jwt = customTimeJWT(null, new Date().getTime() + 2000);
        assertThat(jwt.isExpired(0), is(false));
    }

    @Test
    public void shouldBeDeemedExpired() throws Exception {
        JWT jwt = customTimeJWT(null, new Date().getTime() - 2000);
        assertThat(jwt.isExpired(0), is(true));
    }

    @Test
    public void shouldNotBeDeemedExpiredByLeeway() throws Exception {
        JWT jwt = customTimeJWT(null, new Date().getTime() - 1000);
        assertThat(jwt.isExpired(2), is(false));
    }

    @Test
    public void shouldBeDeemedExpiredByLeeway() throws Exception {
        JWT jwt = customTimeJWT(null, new Date().getTime() - 2000);
        assertThat(jwt.isExpired(1), is(true));
    }

    @Test
    public void shouldNotBeDeemedFutureIssued() throws Exception {
        JWT jwt = customTimeJWT(new Date().getTime() - 2000, null);
        assertThat(jwt.isExpired(0), is(false));
    }

    @Test
    public void shouldBeDeemedFutureIssued() throws Exception {
        JWT jwt = customTimeJWT(new Date().getTime() + 2000, null);
        assertThat(jwt.isExpired(0), is(true));
    }

    @Test
    public void shouldNotBeDeemedFutureIssuedByLeeway() throws Exception {
        JWT jwt = customTimeJWT(new Date().getTime() + 1000, null);
        assertThat(jwt.isExpired(2), is(false));
    }

    @Test
    public void shouldBeDeemedFutureIssuedByLeeway() throws Exception {
        JWT jwt = customTimeJWT(new Date().getTime() + 2000, null);
        assertThat(jwt.isExpired(1), is(true));
    }

    @Test
    public void shouldBeDeemedNotTimeValid() throws Exception {
        JWT jwt = customTimeJWT(new Date().getTime() + 1000, new Date().getTime() - 1000);
        assertThat(jwt.isExpired(0), is(true));
    }

    @Test
    public void shouldBeDeemedTimeValid() throws Exception {
        JWT jwt = customTimeJWT(new Date().getTime() - 1000, new Date().getTime() + 1000);
        assertThat(jwt.isExpired(0), is(false));
    }

    @Test
    public void shouldThrowIfLeewayIsNegative() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("The leeway must be a positive value.");
        JWT jwt = customTimeJWT(null, null);
        jwt.isExpired(-1);
    }

    @Test
    public void shouldNotRemoveKnownPublicClaimsFromTree() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJhdXRoMCIsInN1YiI6ImVtYWlscyIsImF1ZCI6InVzZXJzIiwiaWF0IjoxMDEwMTAxMCwiZXhwIjoxMTExMTExMSwibmJmIjoxMDEwMTAxMSwianRpIjoiaWRpZCIsInJvbGVzIjoiYWRtaW4ifQ.jCchxb-mdMTq5EpeVMSQyTp6zSwByKnfl9U-Zc9kg_w");

        assertThat(jwt, is(notNullValue()));
        assertThat(jwt.getIssuer(), is("auth0"));
        assertThat(jwt.getSubject(), is("emails"));
        assertThat(jwt.getAudience(), is(IsCollectionContaining.hasItem("users")));
        assertThat(jwt.getIssuedAt().getTime(), is(10101010L * 1000));
        assertThat(jwt.getExpiresAt().getTime(), is(11111111L * 1000));
        assertThat(jwt.getNotBefore().getTime(), is(10101011L * 1000));
        assertThat(jwt.getId(), is("idid"));

        assertThat(jwt.getClaim("roles").asString(), is("admin"));
        assertThat(jwt.getClaim("iss").asString(), is("auth0"));
        assertThat(jwt.getClaim("sub").asString(), is("emails"));
        assertThat(jwt.getClaim("aud").asString(), is("users"));
        assertThat(jwt.getClaim("iat").asDouble(), is(10101010D));
        assertThat(jwt.getClaim("exp").asDouble(), is(11111111D));
        assertThat(jwt.getClaim("nbf").asDouble(), is(10101011D));
        assertThat(jwt.getClaim("jti").asString(), is("idid"));
    }


    //Private Claims

    @Test
    public void shouldGetBaseClaimIfClaimIsMissing() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiJ9.e30.K17vlwhE8FCMShdl1_65jEYqsQqBOVMPUU9IgG-QlTM");
        assertThat(jwt, is(notNullValue()));
        assertThat(jwt.getClaim("notExisting"), is(notNullValue()));
        assertThat(jwt.getClaim("notExisting"), is(not(instanceOf(ClaimImpl.class))));
        assertThat(jwt.getClaim("notExisting"), is(instanceOf(BaseClaim.class)));
    }

    @Test
    public void shouldGetClaim() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiJ9.eyJvYmplY3QiOnsibmFtZSI6ImpvaG4ifX0.lrU1gZlOdlmTTeZwq0VI-pZx2iV46UWYd5-lCjy6-c4");
        assertThat(jwt, is(notNullValue()));
        assertThat(jwt.getClaim("object"), is(notNullValue()));
        assertThat(jwt.getClaim("object"), is(instanceOf(ClaimImpl.class)));
    }

    @Test
    public void shouldGetAllClaims() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiJ9.eyJvYmplY3QiOnsibmFtZSI6ImpvaG4ifSwic3ViIjoiYXV0aDAifQ.U20MgOAV81c54mRelwYDJiLllb5OVwUAtMGn-eUOpTA");
        assertThat(jwt, is(notNullValue()));
        Map<String, Claim> claims = jwt.getClaims();
        assertThat(claims, is(notNullValue()));
        Claim objectClaim = claims.get("object");
        assertThat(objectClaim, is(notNullValue()));
        assertThat(objectClaim, is(instanceOf(ClaimImpl.class)));
        Claim extraClaim = claims.get("sub");
        assertThat(extraClaim, is(notNullValue()));
        assertThat(extraClaim.asString(), is("auth0"));
    }

    @Test
    public void shouldGetEmptyAllClaims() throws Exception {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiJ9.e30.ZRrHA1JJJW8opsbCGfG_HACGpVUMN_a9IV7pAx_Zmeo");
        assertThat(jwt, is(notNullValue()));
        Map<String, Claim> claims = jwt.getClaims();
        assertThat(claims, is(notNullValue()));
        assertThat(claims.isEmpty(), is(true));
    }

    //Parcelable

    @Test
    public void shouldBeParceled() throws Exception {
        JWT jwtOrigin = new JWT("eyJhbGciOiJIUzI1NiJ9.e30.K17vlwhE8FCMShdl1_65jEYqsQqBOVMPUU9IgG-QlTM");
        assertThat(jwtOrigin, is(notNullValue()));

        Bundle bundleOrigin = new Bundle();
        bundleOrigin.putParcelable("jwt", jwtOrigin);
        Parcel parcel = Parcel.obtain();
        bundleOrigin.writeToParcel(parcel, 0);

        //Extract bundle from parcel
        parcel.setDataPosition(0);
        Bundle bundleDest = parcel.readBundle(JWT.class.getClassLoader());
        JWT jwtDest = bundleDest.getParcelable("jwt");

        assertThat(jwtDest, is(notNullValue()));
        assertThat(bundleOrigin, is(not(bundleDest)));
        assertThat(jwtOrigin, is(not(jwtDest)));
        assertThat(jwtOrigin.toString(), is(jwtDest.toString()));
    }


    //Helper Methods

    /**
     * Creates a new JWT with custom time claims.
     *
     * @param iatMs iat value in MILLISECONDS
     * @param expMs exp value in MILLISECONDS
     * @return a JWT
     */
    private JWT customTimeJWT(@Nullable Long iatMs, @Nullable Long expMs) {
        String header = encodeString("{}");
        StringBuilder bodyBuilder = new StringBuilder("{");
        if (iatMs != null) {
            long iatSeconds = iatMs / 1000;
            bodyBuilder.append("\"iat\":\"").append(iatSeconds).append("\"");
        }
        if (expMs != null) {
            if (iatMs != null) {
                bodyBuilder.append(",");
            }
            long expSeconds = expMs / 1000;
            bodyBuilder.append("\"exp\":\"").append(expSeconds).append("\"");
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