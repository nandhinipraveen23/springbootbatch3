package com.example.gateway.security;
import java.nio.charset.StandardCharsets; import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value; import org.springframework.cloud.gateway.filter.GatewayFilterChain; import org.springframework.cloud.gateway.filter.GlobalFilter; import org.springframework.core.Ordered; import org.springframework.http.HttpHeaders; import org.springframework.http.HttpStatus; import org.springframework.http.MediaType; import org.springframework.stereotype.Component; import org.springframework.web.server.ServerWebExchange; import reactor.core.publisher.Mono;
import io.jsonwebtoken.Claims; import io.jsonwebtoken.Jwts; import io.jsonwebtoken.JwtException; import io.jsonwebtoken.security.Keys;
@Component public class JwtGatewayFilter implements GlobalFilter, Ordered {
	private final SecretKey key;
	public JwtGatewayFilter(@Value("${jwt.secret}") String secret)
	{
		byte[] bytes=secret.getBytes(StandardCharsets.UTF_8);
		if(bytes.length<32)
			throw new IllegalArgumentException("JWT_SECRET must be at least 32 bytes");
		key=Keys.hmacShaKeyFor(bytes);
		}
	@Override public Mono<Void> filter(ServerWebExchange exchange,GatewayFilterChain chain)
	{
		String path=exchange.getRequest().getURI().getPath();
		String method=exchange.getRequest().getMethod()==null?"":exchange.getRequest().getMethod().name();
		if(path.equals("/api/auth/login")&&method.equals("POST"))
			return chain.filter(exchange);
		String auth=exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
		if(auth==null||!auth.startsWith("Bearer "))
			return unauthorized(exchange,"Missing or invalid Authorization header");
		String token=auth.substring(7).trim();
		try{
			Claims claims=Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
			if(claims.getSubject()==null||claims.getSubject().isBlank())
				return unauthorized(exchange,"Token subject is missing");
			return chain.filter(exchange.mutate().request(exchange.getRequest().mutate().header("X-Authenticated-User",claims.getSubject()).build()).build());
			}
		catch(JwtException|IllegalArgumentException ex)
		{
			return unauthorized(exchange,"Invalid or expired JWT");
			}}
	
	

private Mono<Void> unauthorized(
        ServerWebExchange ex, String message) {

    ex.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
    ex.getResponse().getHeaders()
            .setContentType(MediaType.APPLICATION_JSON);

    String body = "{\"status\":401,"
            + "\"error\":\"Unauthorized\","
            + "\"message\":\"" + message + "\"}";

    byte[] bytes = body.getBytes(StandardCharsets.UTF_8);

    return ex.getResponse().writeWith(
            Mono.just(
                ex.getResponse()
                        .bufferFactory()
                        .wrap(bytes)
            )
    );
}



	@Override public int getOrder(){
		return -1;
		}
}
