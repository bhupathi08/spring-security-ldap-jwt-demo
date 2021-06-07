package gov.census.maftigerstats.entity;

import java.io.Serializable;
import java.util.List;

public class AuthenticationResponse implements Serializable {

	private static final long serialVersionUID = 4620827902882362185L;
	private final String accessToken;
	private final String refreshToken;
	private final List<String> roles;

	public AuthenticationResponse(String accessToken, String refreshToken, List<String> roles) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.roles = roles;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}
	
	public List<String> getRoles() {
		return roles;
	}
}