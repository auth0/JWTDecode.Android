package com.auth0.android.jwt;

public class UserPojo {
    String name;
    int id;

    public UserPojo(String name, int id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserPojo userPojo = (UserPojo) o;

        if (id != userPojo.id) return false;
        return name != null ? name.equals(userPojo.name) : userPojo.name == null;
    }
}
