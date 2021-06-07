package gov.census.maftigerstats.service;

import java.util.Date;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import gov.census.maftigerstats.dao.RefreshTokenRepository;
import gov.census.maftigerstats.entity.RefreshToken;
import gov.census.maftigerstats.util.TokenRefreshException;

@Service
public class RefreshTokenService {

	@Autowired
	private RefreshTokenRepository refreshTokenRepository;
	
	public RefreshToken findByToken(String token) {
		return refreshTokenRepository.getRefreshTokenByToken(token);
	}

	public RefreshToken createRefreshToken(UserDetails userDetails) {
		RefreshToken refreshToken = refreshTokenRepository.getRefreshTokenByUsername(userDetails.getUsername());
		if(refreshToken != null) {
			refreshTokenRepository.delete(refreshToken);	
		}
		refreshToken = new RefreshToken();
		refreshToken.setUserName(userDetails.getUsername());
		refreshToken.setExpiryDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24));
		refreshToken.setToken(UUID.randomUUID().toString());
		refreshToken = refreshTokenRepository.save(refreshToken);
		return refreshToken;
	}

	public boolean verifyExpiration(RefreshToken token) {
		if (token.getExpiryDate().compareTo(new Date(System.currentTimeMillis())) < 0) {
			refreshTokenRepository.delete(token);
			throw new TokenRefreshException(token.getToken(),
					"Refresh token was expired. Please make a new signin request");
		}
		return true;
	}

}
