//package com.example.demo.security;
//
//import java.io.IOException;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//@Component
//public class JwtFilter extends OncePerRequestFilter {
//
//	@Autowired
//	JwtUtil jwtUtil;
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//		 
//	String path=	request.getRequestURI();
//		
//	if(path.startsWith("/auth"))
//	{
//		filterChain.doFilter(request, response);
//		return;
//	}
//	
//	String authHeader= request.getHeader("Authorization");
//	System.out.println("auth  header: "+authHeader);
//	// 01234567
//	// Bearer kjdfsklfjsdfkjsdfksdjfksdfj3483294cnmx,mnvxncvm,xcnvx,mcvnx,cmvnxc,mvncxvmnxv,mxnv,mcxv
//		
//	
//	if( authHeader==null || !authHeader.startsWith("Bearer ") )
//	{
//		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//		return;
//	}
//	
//	
//	String  token= authHeader.substring(7);
//	 
//	       
// 
//	
//	String email = jwtUtil.getEmail(token);
//	String role = jwtUtil.getRole(token);
//
// 
//	List<GrantedAuthority> authorities =
//	        List.of(new SimpleGrantedAuthority("ROLE_" + role));
//
//	System.out.println("Authorities: " + authorities);
//
//	UsernamePasswordAuthenticationToken auth =
//	        new UsernamePasswordAuthenticationToken(
//	                email, null, authorities);
//
//	SecurityContextHolder.getContext().setAuthentication(auth);
//
//	System.out.println("Authenticated: " +
//	        SecurityContextHolder.getContext()
//	                .getAuthentication().isAuthenticated());
//
//	filterChain.doFilter(request, response);
//	}
//
//}




package com.example.demo.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        // Case 1: No token provided
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract the token
        String token = authHeader.substring(7);

        try {
            // Validate the token before extracting claims
            if (!jwtUtil.validateToken(token)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            String email = jwtUtil.getEmail(token);
            String role = jwtUtil.getRole(token);

            // Set authentication only if not already authenticated
            if (SecurityContextHolder.getContext().getAuthentication() == null) {

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                email,
                                null,
                                Collections.singletonList(
                                        new SimpleGrantedAuthority("ROLE_" + role)
                                )
                        );

                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
            }

            // Continue to the controller
            filterChain.doFilter(request, response);

        } catch (JwtException | IllegalArgumentException ex) {
            // Malformed, expired, or otherwise invalid token
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
