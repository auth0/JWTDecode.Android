package com.auth0.android.jwt;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.collection.IsArrayWithSize.emptyArray;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

public class BaseClaimTest {

    private BaseClaim claim;

    @Before
    public void setUp() throws Exception {
        claim = new BaseClaim();
    }

    @Test
    public void shouldGetAsBoolean() throws Exception {
        assertThat(claim.asBoolean(), is(nullValue()));
    }

    @Test
    public void shouldGetAsInt() throws Exception {
        assertThat(claim.asInt(), is(nullValue()));
    }

    @Test
    public void shouldGetAsLong() throws Exception {
        assertThat(claim.asLong(), is(nullValue()));
    }

    @Test
    public void shouldGetAsDouble() throws Exception {
        assertThat(claim.asDouble(), is(nullValue()));
    }

    @Test
    public void shouldGetAsString() throws Exception {
        assertThat(claim.asString(), is(nullValue()));
    }

    @Test
    public void shouldGetAsDate() throws Exception {
        assertThat(claim.asDate(), is(nullValue()));
    }

    @Test
    public void shouldGetAsArray() throws Exception {
        assertThat(claim.asArray(Object.class), is(notNullValue()));
        assertThat(claim.asArray(Object.class), is(emptyArray()));
    }

    @Test
    public void shouldGetAsList() throws Exception {
        assertThat(claim.asList(Object.class), is(notNullValue()));
        assertThat(claim.asList(Object.class), is(empty()));
    }

}