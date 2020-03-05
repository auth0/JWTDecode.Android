package com.auth0.android.jwt;

import java.util.Objects;

class UserPojo {
    private String name;
    private int id;

    UserPojo(String name, int id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserPojo userPojo = (UserPojo) o;

        if (id != userPojo.id) return false;
        return Objects.equals(name, userPojo.name);
    }
}
