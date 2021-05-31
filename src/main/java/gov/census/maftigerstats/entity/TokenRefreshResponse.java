package gov.census.maftigerstats.entity;

public class TokenRefreshResponse {

	private final String accessToken;
	private final String refreshToken;

	public TokenRefreshResponse(String accessToken, String refreshToken) {
		super();
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

}
