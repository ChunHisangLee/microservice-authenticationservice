package com.jack.authservice.constants;

public class SecurityConstants {
    private SecurityConstants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static final String SECRET = "MySecretKeyForJWTGeneration";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/auth/register";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    private static final String[] PUBLIC_URLS = {
            "/api/auth/**",
            "/public/**",
            "/h2-console/**",
            "/swagger-ui.html",
            "/swagger-resources/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/"
    };

    public static String[] getPublicUrls() {
        return PUBLIC_URLS.clone();
    }
}
