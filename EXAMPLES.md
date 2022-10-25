# Examples using JWTDecode.Android

- [Examples using JWTDecode.Android](#examples-using-jwtdecodeandroid)
  - [Registered Claims](#registered-claims)
    - [Issuer ("iss")](#issuer-iss)
    - [Subject ("sub")](#subject-sub)
    - [Audience ("aud")](#audience-aud)
    - [Expiration Time ("exp")](#expiration-time-exp)
    - [Not Before ("nbf")](#not-before-nbf)
    - [Issued At ("iat")](#issued-at-iat)
    - [JWT ID ("jti")](#jwt-id-jti)
  - [Time Validation](#time-validation)
  - [Private Claims](#private-claims)
  - [Claim Class](#claim-class)
    - [Primitives](#primitives)
    - [Collections](#collections)
  - [Sharing the instance](#sharing-the-instance)
    - [Parcel](#parcel)
    - [toString](#tostring)

## Registered Claims

### Issuer ("iss")

Returns the Issuer value or null if it's not defined.

```java
String issuer = jwt.getIssuer();
```

### Subject ("sub")

Returns the Subject value or null if it's not defined.

```java
String subject = jwt.getSubject();
```

### Audience ("aud")

Returns the Audience value or an empty list if it's not defined.

```java
List<String> audience = jwt.getAudience();
```

### Expiration Time ("exp")

Returns the Expiration Time value or null if it's not defined.

```java
Date expiresAt = jwt.getExpiresAt();
```

### Not Before ("nbf")

Returns the Not Before value or null if it's not defined.

```java
Date notBefore = jwt.getNotBefore();
```

### Issued At ("iat")

Returns the Issued At value or null if it's not defined.

```java
Date issuedAt = jwt.getIssuedAt();
```

### JWT ID ("jti")

Returns the JWT ID value or null if it's not defined.

```java
String id = jwt.getId();
```

## Time Validation

The JWT token may include DateNumber fields that can be used to validate that the token was issued in a past date `"iat" < TODAY` and that the expiration date is in the future `"exp" > TODAY`. This library includes a method that checks both of this fields and returns the validity of the token. If any of the fields is missing they wont be considered. You must provide a positive amount of seconds as leeway to consider in the Date comparison.

```java
boolean isExpired = jwt.isExpired(10); // 10 seconds leeway
```


## Private Claims

Additional Claims defined in the token can be obtained by calling `getClaim` and passing the Claim name. If the claim can't be found, a BaseClaim will be returned. BaseClaim will return null on every method call except for the `asList` and `asArray`.

```java
Claim claim = jwt.getClaim("isAdmin");
```

You can also obtain all the claims at once by calling `getClaims`.

```java
Map<String, Claim> allClaims = jwt.getClaims();
```

## Claim Class
The Claim class is a wrapper for the Claim values. It allows you to get the Claim as different class types. The available helpers are:

### Primitives
* **asBoolean()**: Returns the Boolean value or null if it can't be converted.
* **asInt()**: Returns the Integer value or null if it can't be converted.
* **asLong()**: Returns the Long value or null if it can't be converted.
* **asDouble()**: Returns the Double value or null if it can't be converted.
* **asString()**: Returns the String value or null if it can't be converted.
* **asDate()**: Returns the Date value or null if it can't be converted. Note that the [JWT Standard](https://tools.ietf.org/html/rfc7519#section-2) specified that all the *NumericDate* values must be in seconds. (Epoch / Unix time)

### Collections
To obtain a Claim as a Collection you'll need to provide the **Class Type** of the contents to convert from.

* **asArray(class)**: Returns the value parsed as an Array of elements of type **Class Type**, or an empty Array if the value isn't an JSON Array.
* **asList(class)**: Returns the value parsed as a List of elements of type **Class Type**, or an empty List if the value isn't an JSON Array.

If the values inside the JSON Array can't be converted to the given **Class Type**, a `DecodeException` will raise.

## Sharing the instance

### Parcel

The `JWT` class implements **Parcelable** so you can send it inside a `Bundle` on any Android intent. i.e. using Android Activities:

```java
// In the first Activity
JWT jwt = new JWT("header.payload.signature");

Intent intent = new Intent(ProfileActivity.class, MainActivity.this);
intent.putExtra("jwt", jwt);
startActivity(intent);

// Then in another Activity
JWT jwt = (JWT) getIntent().getParcelableExtra("jwt");
```

### toString

You can also call at any time `jwt.toString()` to get the String representation of the token that has given instance to this JWT. This is useful for instance if you need to validate some claims when you get a response, and then send the token back in the request header.

```java
JWT jwt = new JWT(res.getHeader("Authorization"));
if (!jwt.isExpired(0) && "auth0".equals(jwt.getIssuer())){
    req.putHeader("Authorization", "Bearer " + jwt);
    return;
} else {
    // Get a fresh token
}
```