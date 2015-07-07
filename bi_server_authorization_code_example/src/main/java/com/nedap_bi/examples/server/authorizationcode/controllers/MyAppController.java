package com.nedap_bi.examples.server.authorizationcode.controllers;

import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.halt;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;


import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.mustache.MustacheTemplateEngine;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.nedap_bi.examples.server.authorizationcode.OAuth2ClientSettings;
import com.nedap_bi.examples.server.authorizationcode.helpers.OAuthHelper;
import com.nedap_bi.examples.server.authorizationcode.helpers.SessionHelpers;
import com.nedap_bi.examples.server.authorizationcode.models.User;

/**
 * Controller that handles the OAuth authorization and Callbacks.
 * 
 * Endpoints are:
 *   /myapp/home - Initializes the authorization flow if no token is stored, otherwise shows token information.
 *   /myapp/tokens/callback - Callback endpoint for the application. It handles successful and unsuccessful OAuth authorization requests.
 *   /myapp/tokens/revoke - Revokes the OAuth token, so we can test the authorization flow again.
 *   
 */
public class MyAppController {

	/** Global instance of the HTTP transport. */
	private static HttpTransport HTTP_TRANSPORT;

	/** Global instance of the JSON factory. */
	private static JsonFactory JSON_FACTORY;

	private User user;

	public MyAppController(HttpTransport httpTransport,
			JsonFactory jsonFactory) {
		this.HTTP_TRANSPORT = httpTransport;
		this.JSON_FACTORY = jsonFactory;

		/**
		 * Authentication login!
		 */
		before("/myapp/*", (request, response) -> {
			String userName = request.cookie(SessionHelpers.COOKIE_USER);
			if (userName == null || userName.length() == 0) {
				halt(401, "Go away, this is an restricted area!");
			}			
			user = SessionHelpers.buildUser(request);			
		});

		/**
		 * This view shows an example that the user can link/unlink with business insight.
		 * 
		 * 
		 */
		get("/myapp/home",
				(request, response) -> {
					Map<String, Object> map = new HashMap<String, Object>();

					// Making a new authorization code request.
					AuthorizationCodeFlow flow = OAuthHelper.initializeFlow(
							HTTP_TRANSPORT, JSON_FACTORY);
					map.put("auth_url", flow.newAuthorizationUrl()
							.setRedirectUri(OAuth2ClientSettings.CALLBACK_URL)
							.build());					
			
					// view
					map.put("user", user);
					return new ModelAndView(map, "myapp/home.mustache");
				}, new MustacheTemplateEngine());

		/**
		 * Authorization code flow callback. This is where the user will be
		 * redirected to after the access grant. It handles token errors and
		 * requests an request token from an authorization grant.
		 */
		get("/myapp/tokens/callback", (request, response) -> {

			if (request.queryMap().toMap().containsKey("code")) {
				String authToken = request.queryParams("code");
				handleTokenRequest(authToken, user, request, response);
			} else if (request.queryMap().toMap().containsKey("error")) {
				String errorType = request.queryParams("error");
				handleAccessGrantError(errorType, request, response);

			} else {
				// Not a correct callback...
			}
			response.redirect("/myapp/home");
			return "";
		});

		/**
		 * This method simulates an revoke method.
		 */
		get("/myapp/tokens/revoke", (request, response) -> {
			SessionHelpers.destroyToken(response);
			response.redirect("/myapp/home");
			return "";
		});
	}

	private void handleAccessGrantError(String errorType, Request request,
			Response response) {
		// handle errors here, access denied errors.
		// Dummy method.
	}

	private void handleTokenRequest(String authToken, User user,
			Request request, Response response) {
		AuthorizationCodeFlow flow = OAuthHelper.initializeFlow(HTTP_TRANSPORT,
				JSON_FACTORY);
		
		TokenResponse tokenResponse = null;
		try {
			tokenResponse = flow.newTokenRequest(authToken)
					.setRedirectUri(OAuth2ClientSettings.CALLBACK_URL)
					.execute();
			// store token in the database for example.
			SessionHelpers.storeToken(response, user,
					tokenResponse.getAccessToken(),
					tokenResponse.getRefreshToken(),
					tokenResponse.getExpiresInSeconds(),
					System.currentTimeMillis(), tokenResponse.getScope());
			flow.createAndStoreCredential(tokenResponse, user.getName());
		} catch (IOException ioe) {
			System.out.println("exception");
			// handle exception when server does not respond.
		}
	}
}
