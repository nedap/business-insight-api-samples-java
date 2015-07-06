package com.nedap_bi.examples.server.authorizationcode;

public final class OAuth2ClientSettings {
	// Your API KEY
	public static final String API_KEY = "";
	
		// Your API Secret
	public static final String API_SECRET = "";
	
		/// Your sever callback url use "urn:ietf:wg:oauth:2.0:oob" for local tests only.
	public static final String CALLBACK_URL = "http://localhost:4567/myapp/tokens/callback";
	
	// Scope
	public static final String SCOPE = "account";
	
	public static final String TOKEN_SERVER_URL = "https://nedap-bi.com/oauth/token";
	public static final String AUTHORIZATION_SERVER_URL = "https:/nedap-bi.com/oauth/authorize";		
}
