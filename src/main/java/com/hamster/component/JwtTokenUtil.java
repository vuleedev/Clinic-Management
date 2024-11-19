package com.hamster.component;

import java.security.Key;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.hamster.model.User;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {
	private List<String> blacklist = new ArrayList<>();
	@org.springframework.beans.factory.annotation.Value("${jwt.expiration}")
	private int expiration;
	@org.springframework.beans.factory.annotation.Value("${jwt.secret-key}")
	private String secretKey;
	public String generateToken(User user) throws Exception{
		Map<String, Object> claims = new HashMap<>();
		claims.put("email", user.getUsername());
		try {
			String token = Jwts.builder()
					.setClaims(claims)
					.setSubject(user.getUsername())
					.setExpiration(new Date(System.currentTimeMillis() + expiration * 1000L))
					.signWith(getSignInKey(),SignatureAlgorithm.HS256)
					.compact();
			return token;
		} catch (Exception e) {
			throw new Exception("Cannot create jwt token, error: " + e.getMessage());
		}
	}
	private Key getSignInKey() {
        byte[] bytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(bytes);
    }
	private String generateSecretKey() {
        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[32];
        random.nextBytes(keyBytes);
        String secretKey = Encoders.BASE64.encode(keyBytes);
        return secretKey;
    }
	private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = this.extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // check expiration
    public boolean isTokenExpired(String token) {
        Date expirationDate = this.extractClaim(token, Claims::getExpiration);
        return expirationDate.before(new Date());
    }
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String email = extractEmail(token);
        return (email.equals(userDetails.getUsername()))
                && !isTokenExpired(token);
    }
    
    public void invalidateToken(String token) {
    	blacklist.add(token);
    }
    
    public boolean isTokenInvalidated(String token) {
    	return blacklist.contains(token);
    }
}
