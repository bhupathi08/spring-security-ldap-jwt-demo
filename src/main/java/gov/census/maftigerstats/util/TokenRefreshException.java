package gov.census.maftigerstats.util;

public class TokenRefreshException extends RuntimeException {

	private static final long serialVersionUID = 6396718836176790429L;

	public TokenRefreshException(String token, String message) {
		super("Failed for " + token + ":" + message);
	}
}
