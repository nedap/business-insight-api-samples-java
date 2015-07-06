package com.nedap_bi.examples.server.authorizationcode.helpers;

import java.util.Map;

import com.nedap_bi.examples.server.authorizationcode.models.OAuthToken;
import com.nedap_bi.examples.server.authorizationcode.models.User;

import spark.Request;
import spark.Response;

/*
 * Helper class for our user sessions.
 */
public class SessionHelpers {
	
	public static final String COOKIE_USER = "user";
	public static final String COOKIE_TOKEN_SCOPES = "tokenscopes";
	public static final String COOKIE_TOKEN_CREATED_AT = "tokencreatedat";
	public static final String COOKIE_TOKEN_EXPIRES_IN_SECONDS = "tokenexpiresinseconds";
	public static final String COOKIE_TOKEN_REFRESH_TOKEN = "tokenrefreshtoken";
	public static final String COOKIE_TOKEN_OAUTH_TOKEN = "token_auth_token";
	
	/**
	 * Stores the token somewhere in a cookie, this should be an database.
	 * @param user 
	 */
	public static void storeToken(Response response, User user, String authToken,
			String refreshToken, Long expiresIn, long createdAt,
			String scope) {
		user.getToken().setAuthToken(authToken);
		user.getToken().setRefrshToken(refreshToken);
		user.getToken().setCreatedAt(createdAt);
		user.getToken().setExpiresIn(expiresIn);
		user.getToken().setScope(scope);
		
		response.cookie("/", COOKIE_TOKEN_OAUTH_TOKEN, authToken, -1, false);
		response.cookie("/", COOKIE_TOKEN_REFRESH_TOKEN, refreshToken, -1, false);
		response.cookie("/", COOKIE_TOKEN_EXPIRES_IN_SECONDS, String.valueOf(expiresIn), -1, false);
		response.cookie("/", COOKIE_TOKEN_CREATED_AT,  String.valueOf(createdAt), -1, false);
		response.cookie("/", COOKIE_TOKEN_SCOPES, scope, -1, false);
	}

	public static void destroySession(Response response) {
		destroyToken(response);		
		response.removeCookie(COOKIE_USER);		
	}

	public static void destroyToken(Response response) {
		response.cookie("/", COOKIE_TOKEN_OAUTH_TOKEN, "", -1, false);
		response.cookie("/", COOKIE_TOKEN_REFRESH_TOKEN, "", -1, false);
		response.cookie("/", COOKIE_TOKEN_EXPIRES_IN_SECONDS, "", -1, false);
		response.cookie("/", COOKIE_TOKEN_CREATED_AT, "", -1, false);
		response.cookie("/", COOKIE_TOKEN_SCOPES, "", -1, false);		
	}

	/**
	 * Token and user should be somewhere stored in the database. For this
	 * sample we store it in a cookie.
	 */
	public static User buildUser(Request request) {
		User user = new User();
		Map<String, String> cookies = request.cookies();
		String authToken = cookies.get(COOKIE_TOKEN_OAUTH_TOKEN);
		String refreshToken = cookies.get(COOKIE_TOKEN_REFRESH_TOKEN);
		String expiresIn = cookies.get(COOKIE_TOKEN_EXPIRES_IN_SECONDS);
		String createdAt = cookies.get(COOKIE_TOKEN_CREATED_AT);
		String scopes = cookies.get(COOKIE_TOKEN_SCOPES);
		OAuthToken token = user.getToken();
		if (!StringHelpers.isBlank(authToken)
				&& !StringHelpers.isBlank(refreshToken)
				&& !StringHelpers.isBlank(expiresIn)
				&& !StringHelpers.isBlank(createdAt)
				&& !StringHelpers.isBlank(scopes)) {
			token.setAuthToken(authToken);
			token.setRefrshToken(refreshToken);
			token.setCreatedAt(Long.valueOf(createdAt));
			token.setExpiresIn(Long.valueOf(expiresIn));
			token.setScope(scopes);
		}
		user.setName(request.cookie(COOKIE_USER));
		user.setToken(token);
		return user;
	}

}