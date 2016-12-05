# Change Log

## [1.0.0](https://github.com/auth0/lock/tree/1.0.0) (2016-10-25)
[Full Changelog](https://github.com/auth0/lock/tree/1.0.0)

Java library with focus on Android that provides Json Web Token (JWT) decoding.

## Install
The library is be available both in Maven Central and JCenter. To start using it add this line to your `build.gradle` dependencies file:

```groovy
compile 'com.auth0.android:jwtdecode:1.0.0'
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