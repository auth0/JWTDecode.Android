package com.auth0.android.jwt;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.hamcrest.collection.IsArrayWithSize;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.collection.IsArrayContainingInOrder.arrayContaining;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class ClaimImplTest {

    Gson gson;
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        gson = new Gson();
    }

    @Test
    public void shouldGetBooleanValue() throws Exception {
        JsonElement value = gson.toJsonTree(true);
        ClaimImpl claim = new ClaimImpl(value);

        assertThat(claim.asBoolean(), is(notNullValue()));
        assertThat(claim.asBoolean(), is(true));
    }

    @Test
    public void shouldGetNullBooleanIfNotPrimitiveValue() throws Exception {
        JsonElement value = gson.toJsonTree(new Object());
        ClaimImpl claim = new ClaimImpl(value);

        assertThat(claim.asBoolean(), is(nullValue()));
    }

    @Test
    public void shouldGetIntValue() throws Exception {
        JsonElement value = gson.toJsonTree(123);
        ClaimImpl claim = new ClaimImpl(value);

        assertThat(claim.asInt(), is(notNullValue()));
        assertThat(claim.asInt(), is(123));
    }

    @Test
    public void shouldGetNullIntIfNotPrimitiveValue() throws Exception {
        JsonElement value = gson.toJsonTree(new Object());
        ClaimImpl claim = new ClaimImpl(value);

        assertThat(claim.asInt(), is(nullValue()));
    }

    @Test
    public void shouldGetDoubleValue() throws Exception {
        JsonElement value = gson.toJsonTree(1.5);
        ClaimImpl claim = new ClaimImpl(value);

        assertThat(claim.asDouble(), is(notNullValue()));
        assertThat(claim.asDouble(), is(1.5));
    }

    @Test
    public void shouldGetNullDoubleIfNotPrimitiveValue() throws Exception {
        JsonElement value = gson.toJsonTree(new Object());
        ClaimImpl claim = new ClaimImpl(value);

        assertThat(claim.asDouble(), is(nullValue()));
    }

    @Test
    public void shouldGetLargeDateValue() throws Exception {
        long seconds = Integer.MAX_VALUE + 10000L;
        JsonElement value = gson.toJsonTree(seconds);
        ClaimImpl claim = new ClaimImpl(value);

        Date date = claim.asDate();
        assertThat(date, is(notNullValue()));
        assertThat(date.getTime(), is(seconds * 1000));
        assertThat(date.getTime(), is(2147493647L * 1000));
    }

    @Test
    public void shouldGetDateValue() throws Exception {
        JsonElement value = gson.toJsonTree("1476824844");
        ClaimImpl claim = new ClaimImpl(value);

        assertThat(claim.asDate(), is(notNullValue()));
        assertThat(claim.asDate(), is(new Date(1476824844L * 1000)));
    }

    @Test
    public void shouldGetNullDateIfNotPrimitiveValue() throws Exception {
        JsonElement value = gson.toJsonTree(new Object());
        ClaimImpl claim = new ClaimImpl(value);

        assertThat(claim.asDate(), is(nullValue()));
    }

    @Test
    public void shouldGetStringValue() throws Exception {
        JsonElement value = gson.toJsonTree("string");
        ClaimImpl claim = new ClaimImpl(value);

        assertThat(claim.asString(), is(notNullValue()));
        assertThat(claim.asString(), is("string"));
    }

    @Test
    public void shouldGetNullStringIfNotPrimitiveValue() throws Exception {
        JsonElement value = gson.toJsonTree(new Object());
        ClaimImpl claim = new ClaimImpl(value);

        assertThat(claim.asString(), is(nullValue()));
    }

    @Test
    public void shouldGetArrayValueOfCustomClass() throws Exception {
        JsonElement value = gson.toJsonTree(new UserPojo[]{new UserPojo("George", 1), new UserPojo("Mark", 2)});
        ClaimImpl claim = new ClaimImpl(value);

        assertThat(claim.asArray(UserPojo.class), is(notNullValue()));
        assertThat(claim.asArray(UserPojo.class), is(arrayContaining(new UserPojo("George", 1), new UserPojo("Mark", 2))));
    }

    @Test
    public void shouldGetArrayValue() throws Exception {
        JsonElement value = gson.toJsonTree(new String[]{"string1", "string2"});
        ClaimImpl claim = new ClaimImpl(value);

        assertThat(claim.asArray(String.class), is(notNullValue()));
        assertThat(claim.asArray(String.class), is(arrayContaining("string1", "string2")));
    }

    @Test
    public void shouldGetEmptyArrayIfNullValue() throws Exception {
        JsonElement value = gson.toJsonTree(null);
        ClaimImpl claim = new ClaimImpl(value);

        assertThat(claim.asArray(String.class), is(notNullValue()));
        assertThat(claim.asArray(String.class), is(IsArrayWithSize.<String>emptyArray()));
    }

    @Test
    public void shouldGetEmptyArrayIfNonArrayValue() throws Exception {
        JsonElement value = gson.toJsonTree(1);
        ClaimImpl claim = new ClaimImpl(value);

        assertThat(claim.asArray(String.class), is(notNullValue()));
        assertThat(claim.asArray(String.class), is(IsArrayWithSize.<String>emptyArray()));
    }

    @Test
    public void shouldThrowIfArrayClassMismatch() throws Exception {
        JsonElement value = gson.toJsonTree(new String[]{"keys", "values"});
        ClaimImpl claim = new ClaimImpl(value);

        exception.expect(DecodeException.class);
        claim.asArray(UserPojo.class);
    }

    @Test
    public void shouldGetListValueOfCustomClass() throws Exception {
        JsonElement value = gson.toJsonTree(Arrays.asList(new UserPojo("George", 1), new UserPojo("Mark", 2)));
        ClaimImpl claim = new ClaimImpl(value);

        assertThat(claim.asList(UserPojo.class), is(notNullValue()));
        assertThat(claim.asList(UserPojo.class), is(hasItems(new UserPojo("George", 1), new UserPojo("Mark", 2))));
    }

    @Test
    public void shouldGetListValue() throws Exception {
        JsonElement value = gson.toJsonTree(Arrays.asList("string1", "string2"));
        ClaimImpl claim = new ClaimImpl(value);

        assertThat(claim.asList(String.class), is(notNullValue()));
        assertThat(claim.asList(String.class), is(hasItems("string1", "string2")));
    }

    @Test
    public void shouldGetEmptyListIfNullValue() throws Exception {
        JsonElement value = gson.toJsonTree(null);
        ClaimImpl claim = new ClaimImpl(value);

        assertThat(claim.asList(String.class), is(notNullValue()));
        assertThat(claim.asList(String.class), is(IsEmptyCollection.emptyCollectionOf(String.class)));
    }

    @Test
    public void shouldGetEmptyListIfNonArrayValue() throws Exception {
        JsonElement value = gson.toJsonTree(1);
        ClaimImpl claim = new ClaimImpl(value);

        assertThat(claim.asList(String.class), is(notNullValue()));
        assertThat(claim.asList(String.class), is(IsEmptyCollection.emptyCollectionOf(String.class)));
    }

    @Test
    public void shouldThrowIfListClassMismatch() throws Exception {
        JsonElement value = gson.toJsonTree(new String[]{"keys", "values"});
        ClaimImpl claim = new ClaimImpl(value);

        exception.expect(DecodeException.class);
        claim.asList(UserPojo.class);
    }

    @Test
    public void shouldGetSubClaimsValue() throws Exception {
        Map<String, Object> valueMap = new HashMap<>();
        valueMap.put("key1", "value1");
        valueMap.put("key2", "value2");
        valueMap.put("key3", 1234567);

        Map<String, Object> subValueMap = new HashMap<>();
        subValueMap.put("key4-1", "value4-1");
        subValueMap.put("key4-2", "value4-2");
        subValueMap.put("key4-3", 7654321);

        valueMap.put("key4", subValueMap);

        JsonElement value = gson.toJsonTree(valueMap);
        ClaimImpl claim = new ClaimImpl(value);

        assertThat(claim.getSubClaims(), is(notNullValue()));

        assertThat(claim.getSubClaim("key1").asString(), is("value1"));
        assertThat(claim.getSubClaim("key2").asString(), is("value2"));
        assertThat(claim.getSubClaim("key3").asInt(), is(1234567));

        assertThat(claim.getSubClaim("key4").getSubClaim("key4-1").asString(), is("value4-1"));
        assertThat(claim.getSubClaim("key4").getSubClaim("key4-2").asString(), is("value4-2"));
        assertThat(claim.getSubClaim("key4").getSubClaim("key4-3").asInt(), is(7654321));
        assertThat(claim.getSubClaim("key4").getSubClaim("key4-noExistKey"), is(nullValue()));

        assertThat(claim.getSubClaim("noExistKey"), is(nullValue()));
    }

    @Test
    public void shouldGetEmptySubClaimsIfStringValue() throws Exception {
        JsonElement value = gson.toJsonTree("string1");
        ClaimImpl claim = new ClaimImpl(value);

        assertThat(claim.getSubClaims(), is(nullValue()));
        assertThat(claim.getSubClaim("string1"), is(nullValue()));
    }

    @Test
    public void shouldGetEmptySubClaimsIfIntValue() throws Exception {
        JsonElement value = gson.toJsonTree(1234567);
        ClaimImpl claim = new ClaimImpl(value);

        assertThat(claim.getSubClaims(), is(nullValue()));
        assertThat(claim.getSubClaim("key1"), is(nullValue()));
    }

    @Test
    public void shouldGetEmptySubClaimsIfArrayValue() throws Exception {
        JsonElement value = gson.toJsonTree(new String[] { "string1", "string2" });
        ClaimImpl claim = new ClaimImpl(value);

        assertThat(claim.getSubClaims(), is(nullValue()));
        assertThat(claim.getSubClaim("string1"), is(nullValue()));
        assertThat(claim.getSubClaim("string2"), is(nullValue()));
    }
}
