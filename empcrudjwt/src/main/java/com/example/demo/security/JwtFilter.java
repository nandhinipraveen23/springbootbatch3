package com.example.demo.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	JwtUtil jwtUtil;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		 
	String path=	request.getRequestURI();
		
	if(path.equals("/auth/login") || path.equals("/employees/saveemp"))
	{
		filterChain.doFilter(request, response);
		return;
	}
	
	String authHeader= request.getHeader("Authorization");
	System.out.println("auth  header: "+authHeader);
	// 01234567
	// Bearer kjdfsklfjsdfkjsdfksdjfksdfj3483294cnmx,mnvxncvm,xcnvx,mcvnx,cmvnxc,mvncxvmnxv,mxnv,mcxv
		
	
	if( authHeader==null || !authHeader.startsWith("Bearer ") )
	{
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		return;
	}
	
	
	String  token= authHeader.substring(7);
	System.out.println("my token is: "+ token);
	       
	if(!jwtUtil.validateToken(token))
	{
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		return;
	}
	
	filterChain.doFilter(request, response);
	}

}
