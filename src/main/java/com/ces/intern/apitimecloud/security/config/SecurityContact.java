package com.ces.intern.apitimecloud.security.config;

public class SecurityContact {

    public static final long EXPIRATION_TIME = 864000000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_IN_URL = "/login";
    public static final String SIGN_UP_URL = "/users";
    public static final String TOKEN_SECRET = "TimeCloudTimeCloudTimeCloudTimeCloud";
    public static final String HEADER_USERID = "UserId";
}
