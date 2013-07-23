package org.apache.amber.authserver.common;

public class Common{
	private Common() {
    }
    public static final String CLIENT_ID = "test_id";
    public static final String CLIENT_SECRET = "test_secret";
    public static final String USERNAME = "test_username";
    public static final String PASSWORD = "test_password";
   // public static final String HEADER_AUTHORIZATION = "Authorization";
    
    public static final String AUTHORIZATION_CODE = "12345";
    public static final String AUTH_URI="http://localhost:8080/AmberOauthAuthServer/authorise";
    public static final String TOKEN_URI="http://localhost:8080/AmberOauthAuthServer/token";
}
