# Change Log

## [1.3.0](https://github.com/auth0/jwtdecode.android/tree/1.3.0) (2019-04-17)
[Full Changelog](https://github.com/auth0/jwtdecode.android/compare/1.2.0...1.3.0)

**Added**
- Add Claim asLong support. [\#35](https://github.com/auth0/JWTDecode.Android/pull/35) ([chenjiancan](https://github.com/chenjiancan))

## [1.2.0](https://github.com/auth0/jwtdecode.android/tree/1.2.0) (2018-11-21)
[Full Changelog](https://github.com/auth0/jwtdecode.android/compare/1.1.1...1.2.0)

**Added**
- Allow to obtain the Map of Claims [\#31](https://github.com/auth0/JWTDecode.Android/pull/31) ([lbalmaceda](https://github.com/lbalmaceda))

**Changed**
- Target SDK 28. Update Wrapper and OSS plugin [\#30](https://github.com/auth0/JWTDecode.Android/pull/30) ([lbalmaceda](https://github.com/lbalmaceda))

**Fixed**
- Allow JWT with empty Signatures [\#15](https://github.com/auth0/JWTDecode.Android/pull/15) ([oliverspryn](https://github.com/oliverspryn))

## [1.1.1](https://github.com/auth0/jwtdecode.android/tree/1.1.1) (2017-04-27)
[Full Changelog](https://github.com/auth0/jwtdecode.android/compare/1.1.0...1.1.1)

**Fixed**
- Change getClaim annotation to nonNull [\#9](https://github.com/auth0/JWTDecode.Android/pull/9) ([lbalmaceda](https://github.com/lbalmaceda))

## [1.1.0](https://github.com/auth0/jwtdecode.android/tree/1.1.0) (2016-12-05)
[Full Changelog](https://github.com/auth0/jwtdecode.android/compare/1.0.0...1.1.0)

**Changed**
- Keep public claims instead of removing them [\#7](https://github.com/auth0/JWTDecode.Android/pull/7) ([lbalmaceda](https://github.com/lbalmaceda))
- Return BaseClaim if custom Claim not found [\#4](https://github.com/auth0/JWTDecode.Android/pull/4) ([lbalmaceda](https://github.com/lbalmaceda))

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
