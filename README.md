# JWTDecode.Android

Java library with focus on Android that provides Json Web Token (JWT) decoding.

## Install
The library *will* be available both in Maven Central and JCenter. To start using it add this line to your `build.gradle` dependencies file:

```groovy
compile 'com.auth0.android:jwtdecode:1.0.0'
```

## Usage

Decode a JWT token

```java
String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9.TJVA95OrM7E2cBab30RMHrHDcEfxjoYZgeFONFh7HgQ";
JWT jwt = new JWT(token);
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

Returns the Audience value or null if it's not defined.

```java
String audience = jwt.getAudience();
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


#### Private Claims

Additional Claims defined in the token can be obtained by calling `getClaim` and passing the Claim name. If the claim can't be found, null will be returned.

```java
Claim claim = jwt.getClaim("isAdmin");
```

### Claim Class
The Claim class is a wrapper for the Claim values. It allows you to get the Claim as different class types. The available helpers are:

* **asBoolean**: Returns the Boolean value or null if it can't be converted.
* **asInt**: Returns the Integer value or null if it can't be converted.
* **asDouble**: Returns the Double value or null if it can't be converted.
* **asString**: Returns the String value or null if it can't be converted.
* **asDate**: Returns the Date value or null if it can't be converted. Note that the [JWT Standard](https://tools.ietf.org/html/rfc7519#section-2) specified that all the *NumericDate* values must be in seconds.
* **asArray**: Returns the value parsed as an Array, or an empty Array if it can't be converted.
* **asList**: Returns the value parsed as a List, or an empty List if it can't be converted.


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

[Auth0](auth0.com)

## License

This project is licensed under the MIT license. See the [LICENSE](LICENSE) file for more info.