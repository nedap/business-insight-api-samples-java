package com.nedap_bi.examples.server.authorizationcode.helpers;

import java.io.IOException;
import java.util.Arrays;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.nedap_bi.examples.server.authorizationcode.App;
import com.nedap_bi.examples.server.authorizationcode.OAuth2ClientSettings;

public class OAuthHelper {
		
	public static AuthorizationCodeFlow initializeFlow(
			HttpTransport httpTransport, JsonFactory jsonFactory) {
		try {
			// Builds an authorization code flow to use for authorization with the oauth server.
			// We use the Datastore factory to store tokens. You can use your custom implementation.
			return new AuthorizationCodeFlow.Builder(
					BearerToken.authorizationHeaderAccessMethod(), httpTransport,
					jsonFactory, new GenericUrl(
							OAuth2ClientSettings.TOKEN_SERVER_URL),
					new ClientParametersAuthentication(
							OAuth2ClientSettings.API_KEY,
							OAuth2ClientSettings.API_SECRET),
					OAuth2ClientSettings.API_KEY,
					OAuth2ClientSettings.AUTHORIZATION_SERVER_URL).setScopes(
					Arrays.asList(OAuth2ClientSettings.SCOPE)).setDataStoreFactory(App.DATA_STORE_FACTORY).build();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
}
