package com.hamter.util;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;
import java.security.Key;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwTokenUtil {

	@Value("${jwt.secret}")
    private String jwtSecret;

	@Value("${jwt.expiration}")
	private long jwtExpiration;

	public String generateToken(Long userId, List<String> roles) {
		Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
		String token = Jwts.builder().setSubject(String.valueOf(userId))
				.claim("roles", roles) 
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
		
		return token;
	}

	public Long extractUserId(String token) {
		Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
		String userIdStr = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
		return Long.parseLong(userIdStr);
	}

	public List<String> extractRoles(String token) {
	    Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
	    Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	    Object rolesClaim = claims.get("roles");
	    if (rolesClaim instanceof List) {
	        return (List<String>) rolesClaim;
	    }
	    return List.of();
	}

	public boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getExpiration();
	}

	public boolean validateToken(String token, String username) {
		try {
			extractUserId(token); 
			return !isTokenExpired(token);
		} catch (Exception e) {
			System.out.println("Lỗi khi xác thực token: " + e.getMessage());
			return false;
		}
	}
}
