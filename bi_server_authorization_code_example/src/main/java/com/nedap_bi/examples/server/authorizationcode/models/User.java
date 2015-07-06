package com.nedap_bi.examples.server.authorizationcode.models;

public class User {

	private String name;
	private OAuthToken token;

	
	/**
	 * Dummy data store model for an User.
	 */
	public User() {
		this.name = null;
		this.token = new OAuthToken();
	}

	public User(String name, OAuthToken token) {
		this.name = name;
		this.token = token;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public OAuthToken getToken() {
		return token;
	}

	public void setToken(OAuthToken token) {
		this.token = token;
	}

	public void destroy() {
		this.token = new OAuthToken();
		this.name = null;		
	}
}
