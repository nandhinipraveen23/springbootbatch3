package com.example.demo.security;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	private String secret="ilovejavaiknowpythonilovejavaiknowpythonilovejavaiknowpythonilovejavaiknowpythonilovejavaiknowpythonilovejavaiknowpythonilovejavaiknowpythonilovejavaiknowpythonilovejavaiknowpythonilovejavaiknowpythonilovejavaiknowpython";

public String generateToken(String email,String role)
{
	SecretKey key = Keys.hmacShaKeyFor(secret.getBytes()); 
	return Jwts.builder()
			.setSubject(email)
			.claim("role",role)
			.setIssuedAt(new Date())
			.setExpiration(new Date(System.currentTimeMillis()+1000*60*60))
			.signWith(key)
			.compact();
} 
public boolean validateToken(String token){
	SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
	Jwts.parser()
	.verifyWith(key)
	.build()
	.parseSignedClaims(token);
	
	return true;
	 
}

public String getEmail(String token)
{
	SecretKey key = Keys.hmacShaKeyFor(secret.getBytes()); 
	
	return Jwts.parser()
			.verifyWith(key)
			.build()
			.parseSignedClaims(token)
			.getPayload()
			.getSubject();
}

public String getRole(String token)
{
	SecretKey key = Keys.hmacShaKeyFor(secret.getBytes()); 
	
	return Jwts.parser()
			.verifyWith(key)
			.build()
			.parseSignedClaims(token)
			.getPayload()
			.get("role",String.class);
}

}
