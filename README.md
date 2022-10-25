![JWTDecode.Android](https://cdn.auth0.com/website/sdks/banners/jwtdecode-android-banner.png)

[![CircleCI](https://img.shields.io/circleci/project/github/auth0/JWTDecode.Android.svg?style=flat-square)](https://circleci.com/gh/auth0/JWTDecode.Android/tree/master)
[![Maven Central](https://img.shields.io/maven-central/v/com.auth0.android/jwtdecode.svg?style=flat-square)](https://search.maven.org/artifact/com.auth0.android/jwtdecode)
[![codecov](https://codecov.io/gh/auth0/JWTDecode.android/branch/master/graph/badge.svg)](https://codecov.io/gh/auth0/JWTDecode.android)
[![javadoc](https://javadoc.io/badge2/com.auth0.android/jwtdecode/javadoc.svg)](https://javadoc.io/doc/com.auth0.android/jwtdecode)

📚 [Documentation](#documentation) • 🚀 [Getting Started](#getting-started) • 💬 [Feedback](#feedback)

## Documentation

- [Docs Site](https://auth0.github.io/react-native-auth0/)
- [API Reference](https://javadoc.io/doc/com.auth0.android/jwtdecode/latest/index.html)
- [Examples](https://github.com/auth0/JWTDecode.Android/blob/master/EXAMPLES.md)

## Getting Started

### Installation
The library is be available both in Maven Central and JCenter. To start using it add this line to your `build.gradle` dependencies file:

```groovy
implementation 'com.auth0.android:jwtdecode:2.0.1'
```

### Usage

Decode a JWT token

```java
String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9.TJVA95OrM7E2cBab30RMHrHDcEfxjoYZgeFONFh7HgQ";
JWT jwt = new JWT(token);

String issuer = jwt.getIssuer(); //get registered claims
String claim = jwt.getClaim("isAdmin").asString(); //get custom claims
boolean isExpired = jwt.isExpired(10); // Do time validation with 10 seconds leeway
```

A `DecodeException` will raise with a detailed message if the token has:
* An invalid part count.
* A part not encoded as Base64 + UTF-8.
* A Header or Payload without a valid JSON format.

Checkout [EXAMPLES](https://github.com/auth0/JWTDecode.Android/blob/master/EXAMPLES.md) for more details on how to use the library

## Feedback

### Contributing

We appreciate feedback and contribution to this repo! Before you get started, please see the following:

- [Auth0's general contribution guidelines](https://github.com/auth0/open-source-template/blob/master/GENERAL-CONTRIBUTING.md)
- [Auth0's code of conduct guidelines](https://github.com/auth0/open-source-template/blob/master/CODE-OF-CONDUCT.md)

### Raise an issue
To provide feedback or report a bug, [please raise an issue on our issue tracker](https://github.com/auth0/JWTDecode.Android/issues).

### Vulnerability Reporting
Please do not report security vulnerabilities on the public Github issue tracker. The [Responsible Disclosure Program](https://auth0.com/whitehat) details the procedure for disclosing security issues.

---

<p align="center">
  <picture>
    <source media="(prefers-color-scheme: light)" srcset="https://cdn.auth0.com/website/sdks/logos/auth0_light_mode"   width="150">
    <source media="(prefers-color-scheme: dark)" srcset="https://cdn.auth0.com/website/sdks/logos/auth0_dark_mode.png" width="150">
    <img alt="Auth0 Logo" src="https://cdn.auth0.com/website/sdks/logos/auth0_light_mode.png" width="150">
  </picture>
</p>
<p align="center">Auth0 is an easy to implement, adaptable authentication and authorization platform. To learn more checkout <a href="https://auth0.com/why-auth0">Why Auth0?</a></p>
<p align="center">
This project is licensed under the MIT license. See the <a href="https://github.com/auth0/JWTDecode.Android/blob/master/LICENSE"> LICENSE</a> file for more info.</p>