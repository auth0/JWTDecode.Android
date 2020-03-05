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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsArrayContainingInOrder.arrayContaining;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 23)
public class ClaimImplTest {

    private Gson gson;
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
        initMocks(this);
        gson = new Gson();
    }

    @Test
    public void shouldGetBooleanValue() {
        JsonElement value = gson.toJsonTree(true);
        ClaimImpl claim = new ClaimImpl(value);

        assertThat(claim.asBoolean(), is(notNullValue()));
        assertThat(claim.asBoolean(), is(true));
    }

    @Test
    public void shouldGetNullBooleanIfNotPrimitiveValue() {
        JsonElement value = gson.toJsonTree(new Object());
        ClaimImpl claim = new ClaimImpl(value);

        assertThat(claim.asBoolean(), is(nullValue()));
    }

    @Test
    public void shouldGetIntValue() {
        JsonElement value = gson.toJsonTree(123);
        ClaimImpl claim = new ClaimImpl(value);

        assertThat(claim.asInt(), is(notNullValue()));
        assertThat(claim.asInt(), is(123));
    }

    @Test
    public void shouldGetLongValue() {
        JsonElement value = gson.toJsonTree(123L);
        ClaimImpl claim = new ClaimImpl(value);

        assertThat(claim.asLong(), is(notNullValue()));
        assertThat(claim.asLong(), is(123L));
    }

    @Test
    public void shouldGetNullIntIfNotPrimitiveValue() {
        JsonElement value = gson.toJsonTree(new Object());
        ClaimImpl claim = new ClaimImpl(value);

        assertThat(claim.asInt(), is(nullValue()));
    }

    @Test
    public void shouldGetNullLongIfNotPrimitiveValue() {
        JsonElement value = gson.toJsonTree(new Object());
        ClaimImpl claim = new ClaimImpl(value);

        assertThat(claim.asLong(), is(nullValue()));
    }

    @Test
    public void shouldGetDoubleValue() {
        JsonElement value = gson.toJsonTree(1.5);
        ClaimImpl claim = new ClaimImpl(value);

        assertThat(claim.asDouble(), is(notNullValue()));
        assertThat(claim.asDouble(), is(1.5));
    }

    @Test
    public void shouldGetNullDoubleIfNotPrimitiveValue() {
        JsonElement value = gson.toJsonTree(new Object());
        ClaimImpl claim = new ClaimImpl(value);

        assertThat(claim.asDouble(), is(nullValue()));
    }

    @Test
    public void shouldGetLargeDateValue() {
        long seconds = Integer.MAX_VALUE + 10000L;
        JsonElement value = gson.toJsonTree(seconds);
        ClaimImpl claim = new ClaimImpl(value);

        Date date = claim.asDate();
        assertThat(date, is(notNullValue()));
        assertThat(date.getTime(), is(seconds * 1000));
        assertThat(date.getTime(), is(2147493647L * 1000));
    }

    @Test
    public void shouldGetDateValue() {
        JsonElement value = gson.toJsonTree("1476824844");
        ClaimImpl claim = new ClaimImpl(value);

        assertThat(claim.asDate(), is(notNullValue()));
        assertThat(claim.asDate(), is(new Date(1476824844L * 1000)));
    }

    @Test
    public void shouldGetNullDateIfNotPrimitiveValue() {
        JsonElement value = gson.toJsonTree(new Object());
        ClaimImpl claim = new ClaimImpl(value);

        assertThat(claim.asDate(), is(nullValue()));
    }

    @Test
    public void shouldGetStringValue() {
        JsonElement value = gson.toJsonTree("string");
        ClaimImpl claim = new ClaimImpl(value);

        assertThat(claim.asString(), is(notNullValue()));
        assertThat(claim.asString(), is("string"));
    }

    @Test
    public void shouldGetNullStringIfNotPrimitiveValue() {
        JsonElement value = gson.toJsonTree(new Object());
        ClaimImpl claim = new ClaimImpl(value);

        assertThat(claim.asString(), is(nullValue()));
    }

    @Test
    public void shouldGetArrayValueOfCustomClass() {
        JsonElement value = gson.toJsonTree(new UserPojo[]{new UserPojo("George", 1), new UserPojo("Mark", 2)});
        ClaimImpl claim = new ClaimImpl(value);

        assertThat(claim.asArray(UserPojo.class), is(notNullValue()));
        assertThat(claim.asArray(UserPojo.class), is(arrayContaining(new UserPojo("George", 1), new UserPojo("Mark", 2))));
    }

    @Test
    public void shouldGetArrayValue() {
        JsonElement value = gson.toJsonTree(new String[]{"string1", "string2"});
        ClaimImpl claim = new ClaimImpl(value);

        assertThat(claim.asArray(String.class), is(notNullValue()));
        assertThat(claim.asArray(String.class), is(arrayContaining("string1", "string2")));
    }

    @Test
    public void shouldGetEmptyArrayIfNullValue() {
        JsonElement value = gson.toJsonTree(null);
        ClaimImpl claim = new ClaimImpl(value);

        assertThat(claim.asArray(String.class), is(notNullValue()));
        assertThat(claim.asArray(String.class), is(IsArrayWithSize.<String>emptyArray()));
    }

    @Test
    public void shouldGetEmptyArrayIfNonArrayValue() {
        JsonElement value = gson.toJsonTree(1);
        ClaimImpl claim = new ClaimImpl(value);

        assertThat(claim.asArray(String.class), is(notNullValue()));
        assertThat(claim.asArray(String.class), is(IsArrayWithSize.<String>emptyArray()));
    }

    @Test
    public void shouldThrowIfArrayClassMismatch() {
        JsonElement value = gson.toJsonTree(new String[]{"keys", "values"});
        ClaimImpl claim = new ClaimImpl(value);

        exception.expect(DecodeException.class);
        claim.asArray(UserPojo.class);
    }

    @Test
    public void shouldGetListValueOfCustomClass() {
        JsonElement value = gson.toJsonTree(Arrays.asList(new UserPojo("George", 1), new UserPojo("Mark", 2)));
        ClaimImpl claim = new ClaimImpl(value);

        assertThat(claim.asList(UserPojo.class), is(notNullValue()));
        assertThat(claim.asList(UserPojo.class), is(hasItems(new UserPojo("George", 1), new UserPojo("Mark", 2))));
    }

    @Test
    public void shouldGetListValue() {
        JsonElement value = gson.toJsonTree(Arrays.asList("string1", "string2"));
        ClaimImpl claim = new ClaimImpl(value);

        assertThat(claim.asList(String.class), is(notNullValue()));
        assertThat(claim.asList(String.class), is(hasItems("string1", "string2")));
    }

    @Test
    public void shouldGetEmptyListIfNullValue() {
        JsonElement value = gson.toJsonTree(null);
        ClaimImpl claim = new ClaimImpl(value);

        assertThat(claim.asList(String.class), is(notNullValue()));
        assertThat(claim.asList(String.class), is(IsEmptyCollection.emptyCollectionOf(String.class)));
    }

    @Test
    public void shouldGetEmptyListIfNonArrayValue() {
        JsonElement value = gson.toJsonTree(1);
        ClaimImpl claim = new ClaimImpl(value);

        assertThat(claim.asList(String.class), is(notNullValue()));
        assertThat(claim.asList(String.class), is(IsEmptyCollection.emptyCollectionOf(String.class)));
    }

    @Test
    public void shouldThrowIfListClassMismatch() {
        JsonElement value = gson.toJsonTree(new String[]{"keys", "values"});
        ClaimImpl claim = new ClaimImpl(value);

        exception.expect(DecodeException.class);
        claim.asList(UserPojo.class);
    }

    @Test
    public void shouldGetAsObject() {
        UserPojo data = new UserPojo("George", 1);
        JsonElement userValue = gson.toJsonTree(data);
        ClaimImpl userClaim = new ClaimImpl(userValue);

        JsonElement intValue = gson.toJsonTree(1);
        ClaimImpl intClaim = new ClaimImpl(intValue);

        JsonElement booleanValue = gson.toJsonTree(true);
        ClaimImpl booleanClaim = new ClaimImpl(booleanValue);

        assertThat(userClaim.asObject(UserPojo.class), is(notNullValue()));
        assertThat(userClaim.asObject(UserPojo.class), is(new UserPojo("George", 1)));

        assertThat(intClaim.asObject(Integer.class), is(notNullValue()));
        assertThat(intClaim.asObject(Integer.class), is(1));

        assertThat(booleanClaim.asObject(Boolean.class), is(notNullValue()));
        assertThat(booleanClaim.asObject(Boolean.class), is(true));
    }

    @Test
    public void shouldGetNullObjectIfNullValue() {
        JsonElement value = gson.toJsonTree(null);
        ClaimImpl claim = new ClaimImpl(value);

        assertThat(claim.asObject(UserPojo.class), is(nullValue()));
    }

    @Test
    public void shouldThrowIfObjectClassMismatch() {
        JsonElement value = gson.toJsonTree(1);
        ClaimImpl claim = new ClaimImpl(value);

        exception.expect(DecodeException.class);
        claim.asObject(UserPojo.class);
    }
}
