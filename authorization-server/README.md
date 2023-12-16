## Authentication Server or Identity Provider:

1. ID Token:
    - Issuer: Authentication server or identity provider.
    - Purpose: In the context of OIDC, the ID token is used to carry information about the authenticated user. It
      provides claims about the user's identity, such as their subject identifier (sub), username, and possibly other
      attributes. It is a JWT (JSON Web Token) and is intended to be consumed by the client application.
2. Authentication Token/Session Token:
    - Issuer: Authentication server or identity provider.
    - Purpose: After successful authentication, an authentication token or session token is often issued. This token
      represents the authenticated session and is used by the client to make subsequent requests without
      re-authenticating for a certain period.

## Authorization Server:

1. Access Token:
    - Issuer: Authorization server.
    - Purpose: The access token is the primary token used by the client application to access protected resources on
      behalf of the user. It represents the authorization granted by the user. The format of the access token may vary (
      e.g., JWT or opaque token) based on the token endpoint configuration.
2. Refresh Token:
    - Issuer: Authorization server.
    - Purpose: A refresh token is often issued alongside the access token. It allows the client to obtain a new access
      token without requiring the user to re-authenticate. The refresh token is typically long-lived compared to the
      access token, and its use is subject to security considerations.