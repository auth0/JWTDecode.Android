package com.auth0.android.jwt;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Date;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 23)
public class ClaimImplTest {
    private Claim createClaim(Object value) throws JSONException {
        final JSONObject object = new JSONObject();
        object.putOpt("name", value);
        return new JSONObjectClaim(object, "name");
    }

    @Test
    public void shouldGetBooleanValue() throws JSONException {
        final Claim claim = createClaim(true);

        assertThat(claim.asBoolean(), is(notNullValue()));
        assertThat(claim.asBoolean(), is(true));
    }

    @Test
    public void shouldGetNullBooleanIfNotPrimitiveValue() throws JSONException {
        Claim claim = createClaim(new JSONObject());

        assertThat(claim.asBoolean(), is(nullValue()));
    }

    @Test
    public void shouldGetIntValue() throws JSONException {
        Claim claim = createClaim(123);

        assertThat(claim.asInt(), is(notNullValue()));
        assertThat(claim.asInt(), is(123));
    }


    @Test
    public void shouldGetLongValue() throws JSONException {
        Claim claim = createClaim(123L);

        assertThat(claim.asLong(), is(notNullValue()));
        assertThat(claim.asLong(), is(123L));
    }

    @Test
    public void shouldGetNullIntIfNotPrimitiveValue() throws JSONException {
        Claim claim = createClaim(new JSONObject());

        assertThat(claim.asInt(), is(nullValue()));
    }

    @Test
    public void shouldGetNullLongIfNotPrimitiveValue() throws JSONException {
        Claim claim = createClaim(new JSONObject());

        assertThat(claim.asLong(), is(nullValue()));
    }

    @Test
    public void shouldGetDoubleValue() throws JSONException {
        Claim claim = createClaim(1.5);

        assertThat(claim.asDouble(), is(notNullValue()));
        assertThat(claim.asDouble(), is(1.5));
    }

    @Test
    public void shouldGetNullDoubleIfNotPrimitiveValue() throws JSONException {
        Claim claim = createClaim(new JSONObject());

        assertThat(claim.asDouble(), is(nullValue()));
    }

    @Test
    public void shouldGetLargeDateValue() throws JSONException {
        long seconds = Integer.MAX_VALUE + 10000L;
        Claim claim = createClaim(seconds);

        Date date = claim.asDate();
        assertThat(date, is(notNullValue()));
        assertThat(date.getTime(), is(seconds * 1000));
        assertThat(date.getTime(), is(2147493647L * 1000));
    }

    @Test
    public void shouldGetDateValue() throws JSONException {
        Claim claim = createClaim("1476824844");

        assertThat(claim.asDate(), is(notNullValue()));
        assertThat(claim.asDate(), is(new Date(1476824844L * 1000)));
    }

    @Test
    public void shouldGetNullDateIfNotPrimitiveValue() throws JSONException {
        Claim claim = createClaim(new JSONObject());

        assertThat(claim.asDate(), is(nullValue()));
    }

    @Test
    public void shouldGetStringValue() throws JSONException {
        Claim claim = createClaim("string");

        assertThat(claim.asString(), is(notNullValue()));
        assertThat(claim.asString(), is("string"));
    }

    @Test
    public void shouldGetNullStringIfNotPrimitiveValue() throws JSONException {
        Claim claim = createClaim(new JSONObject());

        assertThat(claim.asString(), is(nullValue()));
    }

    @Test
    public void shouldGetArrayValueOfCustomClass() {
        // JsonElement value = gson.toJsonTree(new UserPojo[]{new UserPojo("George", 1), new UserPojo("Mark", 2)});
        // ClaimImpl claim = new ClaimImpl(value);

        // assertThat(claim.asArray(UserPojo.class), is(notNullValue()));
        // assertThat(claim.asArray(UserPojo.class), is(arrayContaining(new UserPojo("George", 1), new UserPojo("Mark", 2))));
        // TODO
    }

    @Test
    public void shouldGetArrayValue() {
        // JsonElement value = gson.toJsonTree(new String[]{"string1", "string2"});
        // ClaimImpl claim = new ClaimImpl(value);

        // assertThat(claim.asArray(String.class), is(notNullValue()));
        // assertThat(claim.asArray(String.class), is(arrayContaining("string1", "string2")));
        // TODO
    }

    @Test
    public void shouldGetEmptyArrayIfNullValue() {
        // JsonElement value = gson.toJsonTree(null);
        // ClaimImpl claim = new ClaimImpl(value);

        // assertThat(claim.asArray(String.class), is(notNullValue()));
        // assertThat(claim.asArray(String.class), is(IsArrayWithSize.<String>emptyArray()));
        // TODO: asArray() now returns null if it's not a JSONArray
    }

    @Test
    public void shouldGetEmptyArrayIfNonArrayValue() {
        // JsonElement value = gson.toJsonTree(1);
        // ClaimImpl claim = new ClaimImpl(value);

        // assertThat(claim.asArray(String.class), is(notNullValue()));
        // assertThat(claim.asArray(String.class), is(IsArrayWithSize.<String>emptyArray()));
        // TODO: asArray() now returns null if it's not a JSONArray
    }

    @Test
    public void shouldThrowIfArrayClassMismatch() {
        // JsonElement value = gson.toJsonTree(new String[]{"keys", "values"});
        // ClaimImpl claim = new ClaimImpl(value);

        // exception.expect(DecodeException.class);
        // claim.asArray(UserPojo.class);
        // TODO:
    }

    @Test
    public void shouldGetListValueOfCustomClass() {
        // JsonElement value = gson.toJsonTree(Arrays.asList(new UserPojo("George", 1), new UserPojo("Mark", 2)));
        // ClaimImpl claim = new ClaimImpl(value);

        // assertThat(claim.asList(UserPojo.class), is(notNullValue()));
        // assertThat(claim.asList(UserPojo.class), is(hasItems(new UserPojo("George", 1), new UserPojo("Mark", 2))));
        // TODO
    }

    @Test
    public void shouldGetListValue() {
        // JsonElement value = gson.toJsonTree(Arrays.asList("string1", "string2"));
        // ClaimImpl claim = new ClaimImpl(value);

        // assertThat(claim.asList(String.class), is(notNullValue()));
        // assertThat(claim.asList(String.class), is(hasItems("string1", "string2")));
    }

    @Test
    public void shouldGetEmptyListIfNullValue() {
        // JsonElement value = gson.toJsonTree(null);
        // ClaimImpl claim = new ClaimImpl(value);
        //
        // assertThat(claim.asList(String.class), is(notNullValue()));
        // assertThat(claim.asList(String.class), is(IsEmptyCollection.emptyCollectionOf(String.class)));
        // TODO
    }

    @Test
    public void shouldGetEmptyListIfNonArrayValue() {
        // JsonElement value = gson.toJsonTree(1);
        // ClaimImpl claim = new ClaimImpl(value);
        //
        // assertThat(claim.asList(String.class), is(notNullValue()));
        // assertThat(claim.asList(String.class), is(IsEmptyCollection.emptyCollectionOf(String.class)));
        // TODO
    }

    @Test
    public void shouldThrowIfListClassMismatch() {
        // JsonElement value = gson.toJsonTree(new String[]{"keys", "values"});
        // ClaimImpl claim = new ClaimImpl(value);
        //
        // exception.expect(DecodeException.class);
        // claim.asList(UserPojo.class);
        // TODO:
    }

    @Test
    public void shouldGetAsObject() {
        // UserPojo data = new UserPojo("George", 1);
        // JsonElement userValue = gson.toJsonTree(data);
        // ClaimImpl userClaim = new ClaimImpl(userValue);
        //
        // JsonElement intValue = gson.toJsonTree(1);
        // ClaimImpl intClaim = new ClaimImpl(intValue);
        //
        // JsonElement booleanValue = gson.toJsonTree(true);
        // ClaimImpl booleanClaim = new ClaimImpl(booleanValue);
        //
        // assertThat(userClaim.asObject(UserPojo.class), is(notNullValue()));
        // assertThat(userClaim.asObject(UserPojo.class), is(new UserPojo("George", 1)));
        //
        // assertThat(intClaim.asObject(Integer.class), is(notNullValue()));
        // assertThat(intClaim.asObject(Integer.class), is(1));
        //
        // assertThat(booleanClaim.asObject(Boolean.class), is(notNullValue()));
        // assertThat(booleanClaim.asObject(Boolean.class), is(true));
        // TODO
    }

    @Test
    public void shouldGetNullObjectIfNullValue() {
        // JsonElement value = gson.toJsonTree(null);
        // ClaimImpl claim = new ClaimImpl(value);
        //
        // assertThat(claim.asObject(UserPojo.class), is(nullValue()));
        // TODO
    }

    @Test
    public void shouldThrowIfObjectClassMismatch() {
        // JsonElement value = gson.toJsonTree(1);
        // ClaimImpl claim = new ClaimImpl(value);
        //
        // exception.expect(DecodeException.class);
        // claim.asObject(UserPojo.class);
        // TODO
    }
}
