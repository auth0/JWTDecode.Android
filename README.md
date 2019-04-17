# JWTDecode.Android

[![CircleCI](https://img.shields.io/circleci/project/github/auth0/JWTDecode.Android.svg?style=flat-square)](https://circleci.com/gh/auth0/JWTDecode.Android/tree/master)
[![codecov](https://codecov.io/gh/auth0/JWTDecode.android/branch/master/graph/badge.svg)](https://codecov.io/gh/auth0/JWTDecode.android)
[![Download](https://api.bintray.com/packages/auth0/android/jwtdecode/images/download.svg)](https://bintray.com/auth0/android/jwtdecode/_latestVersion)

Java library with focus on Android that provides Json Web Token (JWT) decoding.

## Install
The library is be available both in Maven Central and JCenter. To start using it add this line to your `build.gradle` dependencies file:

```groovy
implementation 'com.auth0.android:jwtdecode:1.3.0'
```

## Usage

Decode a JWT token

```java
String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9.TJVA95OrM7E2cBab30RMHrHDcEfxjoYZgeFONFh7HgQ";
JWT jwt = new JWT(token);
```

A `DecodeException` will raise with a detailed message if the token has:
* An invalid part count.
* A part not encoded as Base64 + UTF-8.
* A Header or Payload without a valid JSON format.


#### Android SDK Versions Troubleshooting
Those using this library from version `1.2.0` and up should start targeting latest android SDK versions, as [recommended by Google](https://developer.android.com/distribute/best-practices/develop/target-sdk). Those running into conflicts because of different `com.android.support` libraries versions can choose to use latest release `28.0.0` or exclude the ones required by this library and require a different version in their app's `build.gradle` file as shown below:

 e.g. if choosing an older version such as `25.4.0`

```groovy
apply plugin: 'com.android.application'
 android {
    //...
 }
 dependencies {
    implementation ('com.auth0.android:jwtdecode:1.2.0'){
        exclude group: 'com.android.support', module: 'appcompat-v7'
    }
    implementation 'com.android.support:appcompat-v7:25.4.0'
    //...
}
```

### Registered Claims

#### Issuer ("iss")

Returns the Issuer value or null if it's not defined.

```java
String issuer = jwt.getIssuer();
```

#### Subject ("sub")

Returns the Subject value or null if it's not defined.

```java
String subject = jwt.getSubject();
```

#### Audience ("aud")

Returns the Audience value or an empty list if it's not defined.

```java
List<String> audience = jwt.getAudience();
```

#### Expiration Time ("exp")

Returns the Expiration Time value or null if it's not defined.

```java
Date expiresAt = jwt.getExpiresAt();
```

#### Not Before ("nbf")

Returns the Not Before value or null if it's not defined.

```java
Date notBefore = jwt.getNotBefore();
```

#### Issued At ("iat")

Returns the Issued At value or null if it's not defined.

```java
Date issuedAt = jwt.getIssuedAt();
```

#### JWT ID ("jti")

Returns the JWT ID value or null if it's not defined.

```java
String id = jwt.getId();
```

#### Time Validation

The JWT token may include DateNumber fields that can be used to validate that the token was issued in a past date `"iat" < TODAY` and that the expiration date is in the future `"exp" > TODAY`. This library includes a method that checks both of this fields and returns the validity of the token. If any of the fields is missing they wont be considered. You must provide a positive amount of seconds as leeway to consider in the Date comparison.

```java
boolean isExpired = jwt.isExpired(10); // 10 seconds leeway
```

### Private Claims

Additional Claims defined in the token can be obtained by calling `getClaim` and passing the Claim name. If the claim can't be found, a BaseClaim will be returned. BaseClaim will return null on every method call except for the `asList` and `asArray`.

```java
Claim claim = jwt.getClaim("isAdmin");
```

You can also obtain all the claims at once by calling `getClaims`.

```java
Map<String, Claim> allClaims = jwt.getClaims();
```

### Claim Class
The Claim class is a wrapper for the Claim values. It allows you to get the Claim as different class types. The available helpers are:

#### Primitives
* **asBoolean()**: Returns the Boolean value or null if it can't be converted.
* **asInt()**: Returns the Integer value or null if it can't be converted.
* **asLong()**: Returns the Long value or null if it can't be converted.
* **asDouble()**: Returns the Double value or null if it can't be converted.
* **asString()**: Returns the String value or null if it can't be converted.
* **asDate()**: Returns the Date value or null if it can't be converted. Note that the [JWT Standard](https://tools.ietf.org/html/rfc7519#section-2) specified that all the *NumericDate* values must be in seconds. (Epoch / Unix time)

#### Collections
To obtain a Claim as a Collection you'll need to provide the **Class Type** of the contents to convert from.

* **asArray(class)**: Returns the value parsed as an Array of elements of type **Class Type**, or an empty Array if the value isn't an JSON Array.
* **asList(class)**: Returns the value parsed as a List of elements of type **Class Type**, or an empty List if the value isn't an JSON Array.

If the values inside the JSON Array can't be converted to the given **Class Type**, a `DecodeException` will raise.

### Sharing the instance

#### Parcel

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

#### toString

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


## What is Auth0?

Auth0 helps you to:

* Add authentication with [multiple authentication sources](https://docs.auth0.com/identityproviders), either social like **Google, Facebook, Microsoft Account, LinkedIn, GitHub, Twitter, Box, Salesforce, among others**, or enterprise identity systems like **Windows Azure AD, Google Apps, Active Directory, ADFS or any SAML Identity Provider**.
* Add authentication through more traditional **[username/password databases](https://docs.auth0.com/mysql-connection-tutorial)**.
* Add support for **[linking different user accounts](https://docs.auth0.com/link-accounts)** with the same user.
* Support for generating signed [Json Web Tokens](https://docs.auth0.com/jwt) to call your APIs and **flow the user identity** securely.
* Analytics of how, when and where users are logging in.
* Pull data from other sources and add it to the user profile, through [JavaScript rules](https://docs.auth0.com/rules).

## Create a free account in Auth0

1. Go to [Auth0](https://auth0.com) and click Sign Up.
2. Use Google, GitHub or Microsoft Account to login.

## Issue Reporting

If you have found a bug or if you have a feature request, please report them at this repository issues section. Please do not report security vulnerabilities on the public GitHub issue tracker. The [Responsible Disclosure Program](https://auth0.com/whitehat) details the procedure for disclosing security issues.

## Author

[Auth0](https://auth0.com)

## License

This project is licensed under the MIT license. See the [LICENSE](LICENSE) file for more info.
