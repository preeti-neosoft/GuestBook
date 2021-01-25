package com.boot.util;


import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component

public class JwtUtil {
	
	@Value("${app.secret}")
	private String secret;
	
	
	
	
	public boolean validateToken(String token,String userName/*database username*/) {
		String TokenUserName=getUserName(token);//this user name comes from token that we have generate
		return (userName.equalsIgnoreCase(TokenUserName) && !isTokenExpired(token));
	}
	
	
	
	//5.validate expireDate
	public boolean isTokenExpired(String token) {
	Date expDate=getExpDate(token);
	return expDate.before(new Date(System.currentTimeMillis()));
	}
	
	//4.read subject/username
	public String getUserName(String token) {
		return getClaims(token).getSubject();
	}
	
	//3.read expiry date from givin token from given claims
	public Date getExpDate(String token) {
		return getClaims(token).getExpiration();
	}
	
	
	
	public Claims getClaims(String token) {
		return Jwts.parser().setSigningKey(secret.getBytes())
				.parseClaimsJws(token)
				.getBody();
		
		
		
	}
	
	
	
    //1. generating token
	
	public String generateToken(String subject)
	{
		return Jwts.builder()
				.setSubject(subject)
				.setIssuer("neoSoft")
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+TimeUnit.MINUTES.toMillis(15)))
				.signWith(SignatureAlgorithm.HS256, secret.getBytes())
				.compact();
		
	}

	
	

}

