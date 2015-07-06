package com.nedap_bi.examples.server.authorizationcode;

import static spark.Spark.*;

import java.io.IOException;

import com.nedap_bi.examples.server.authorizationcode.controllers.HomeController;
import com.nedap_bi.examples.server.authorizationcode.controllers.MyAppController;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;

/**
 * Hello world!
 *
 */
public class App {
	/** Global instance of the HTTP transport. */
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY = new JacksonFactory();

	private static final java.io.File DATA_STORE_DIR = new java.io.File(
			System.getProperty("user.home"), ".store/token_sample");

	public static FileDataStoreFactory DATA_STORE_FACTORY;

	public static void main(String[] args) {
		try {
			// Data store for tokens
			DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// This sample allows only one user connecting to the website.
		// It
	
		new HomeController();
		new MyAppController(HTTP_TRANSPORT, JSON_FACTORY );
		new ApiRequestHandler(HTTP_TRANSPORT, JSON_FACTORY)
				.getDataFromApi();

	}
}
