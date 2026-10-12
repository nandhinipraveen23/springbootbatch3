	package com.example.employee.security;
	import java.nio.charset.StandardCharsets; import java.util.Date; import javax.crypto.SecretKey;
	import org.springframework.beans.factory.annotation.Value; import org.springframework.stereotype.Service;
	import io.jsonwebtoken.Jwts; import io.jsonwebtoken.security.Keys;
	@Service public class JwtService {
	 private final SecretKey key; private final long expirationMs;
	 public JwtService(@Value("${jwt.secret}") String secret,@Value("${jwt.expiration-ms:3600000}") long expirationMs){if(secret.getBytes(StandardCharsets.UTF_8).length<32)throw new IllegalArgumentException("JWT_SECRET must be at least 32 bytes");this.key=Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));this.expirationMs=expirationMs;}
	 public String generateToken(String email){Date now=new Date();return Jwts.builder().subject(email).issuedAt(now).expiration(new Date(now.getTime()+expirationMs)).signWith(key).compact();}
	}
