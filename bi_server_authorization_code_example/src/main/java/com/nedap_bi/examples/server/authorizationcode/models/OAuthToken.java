package com.nedap_bi.examples.server.authorizationcode.models;

public class OAuthToken {

	private String refreshToken;
	private String authToken;
	private Long expiresIn;
	private Long createdAt;
	private String scope;

	/**
	 * Dummy data store model for an OAuthToken.
	 */
	
	public OAuthToken(String authToken, String refreshToken, Long expiresIn,
			Long createdAt, String scope) {
		this.refreshToken = refreshToken;
		this.authToken = authToken;
		this.expiresIn = expiresIn;
		this.createdAt = createdAt;
		this.scope = scope;
	}

	public OAuthToken() {
		this.refreshToken = null;
		this.authToken = null;
		this.expiresIn = null;
		this.createdAt = null;
		this.scope = null;
	}

	public void setAuthToken(String token) {
		this.authToken = token;
	}

	public String getAuthToken() {
		return this.authToken;
	}

	public void setRefrshToken(String token) {
		this.refreshToken = token;
	}

	public String getRefreshToken() {
		return this.refreshToken;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public Long getExpiresIn() {
		return this.expiresIn;
	}

	public void setExpiresIn(Long expiresIn) {
		this.expiresIn = expiresIn;
	}

	public Long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(long createdAt) {
		this.createdAt = createdAt;
	}

	public long expiresAt() {
		return createdAt + (expiresIn * 1000);
	}

	public boolean isExpired() {
		return (expiresAt() < System.currentTimeMillis());
	}

	public boolean isValid() {
		return this.authToken != null && this.refreshToken != null
				&& this.expiresIn != null && this.createdAt != null;
	}

	public void destroy() {
		this.refreshToken = null;
		this.authToken = null;
		this.expiresIn = null;
		this.createdAt = null;
		this.scope = null;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		String NEW_LINE = System.getProperty("line.separator");
		result.append("AuthToken : " + authToken + NEW_LINE);
		result.append("RefreshToken : " + refreshToken + NEW_LINE);
		result.append("ExpiresIn : " + expiresIn + NEW_LINE);
		result.append("CreatedAt : " + createdAt + NEW_LINE);
		result.append("Scope : " + scope + NEW_LINE);

		return result.toString();

	}
}
