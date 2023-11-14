package com.auth0.android.jwt;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.nullValue;

import org.junit.Before;
import org.junit.Test;

public class BaseClaimTest {

    private BaseClaim claim;

    @Before
    public void setUp() {
        claim = new BaseClaim();
    }

    @Test
    public void shouldGetAsBoolean() {
        assertThat(claim.asBoolean(), is(nullValue()));
    }

    @Test
    public void shouldGetAsInt() {
        assertThat(claim.asInt(), is(nullValue()));
    }

    @Test
    public void shouldGetAsLong() {
        assertThat(claim.asLong(), is(nullValue()));
    }

    @Test
    public void shouldGetAsDouble() {
        assertThat(claim.asDouble(), is(nullValue()));
    }

    @Test
    public void shouldGetAsString() {
        assertThat(claim.asString(), is(nullValue()));
    }

    @Test
    public void shouldGetAsDate() {
        assertThat(claim.asDate(), is(nullValue()));
    }

    @Test
    public void shouldGetAsArray() {
        assertThat(claim.asArray(), is(nullValue()));
    }

    @Test
    public void shouldGetAsObject() {
        assertThat(claim.asObject(), is(nullValue()));
    }
}