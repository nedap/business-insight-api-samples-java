package com.nedap_bi.examples.server.authorizationcode;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.nedap_bi.examples.server.authorizationcode.helpers.OAuthHelper;
import com.nedap_bi.examples.server.authorizationcode.models.User;

public class ApiRequestHandler {

	private final ScheduledExecutorService scheduler = Executors
			.newScheduledThreadPool(1);

	private static HttpTransport httpTransport;

	private static JsonFactory jsonFactory;

	private static HttpResponse executeGet(HttpTransport transport,
			JsonFactory jsonFactory, Credential credential, GenericUrl url)
			throws IOException {
		HttpRequestFactory requestFactory = transport
				.createRequestFactory(credential);
		return requestFactory.buildGetRequest(url).execute();
	}

	public ApiRequestHandler(HttpTransport httpTransport,
			JsonFactory jsonFactory) {

		this.httpTransport = httpTransport;
		this.jsonFactory = jsonFactory;
	}

	/**
	 * This simulates an background job where data is extracted from the server
	 * with a valid token.
	 * 
	 */
	public void getDataFromApi() {

		final Runnable apiRequester = new Runnable() {
			public void run() {
				try {
					// Get stored token, refresh token and expiration for the
					// user.
					// check for token validation, refresh if the token is not
					// valid.
					// Call the endpoint with the valid token.

					AuthorizationCodeFlow flow = OAuthHelper.initializeFlow(
							httpTransport, jsonFactory);
					/*
					 * Build your credentials from the stored token information.
					 */
					// Credential credential = flow.loadCredential(--your user
					// id--);

					/*
					 * Execute queries on the api with the credentials.
					 */

					// executeGet(httpTransport,jsonFactory, credential,new
					// GenericUrl("https://api.nedap-bi.com/v1/installations"));

				} catch (Exception e) {
					e.printStackTrace();
					// catch all errors.
				}
			}
		};

		// Schedule once an hour.
		final ScheduledFuture<?> apiHandler = scheduler.scheduleAtFixedRate(
				apiRequester, 2, 60, TimeUnit.MINUTES);
	}
}
