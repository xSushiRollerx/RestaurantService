package com.xsushirollx.sushibyte.restaurantservice.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {
	
	private String SECRET_KEY = "sushibyte";
	
	public String extractUserId(String token) {
		return (extractClaim(token, Claims::getSubject));
	}
	
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	private String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 360 * 100 * 10))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
		
	}
	
	public String generateToken(String id) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, id);
	}
	
	public boolean validateToken(String token) {
//		final String userId = extractUserId(token);
		return (!isTokenExpired(token));
	}
	
}

