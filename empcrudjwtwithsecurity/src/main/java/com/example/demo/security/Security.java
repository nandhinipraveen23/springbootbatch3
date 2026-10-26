package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class Security {
	
	@Autowired
	JwtFilter jwtFilter;

	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http)
	{
		http.csrf(csrf-> csrf.disable())
		.authorizeHttpRequests(auth -> auth
				
				.requestMatchers("/employees/saveemp")
				.hasRole("MANAGER")
				
				
				.requestMatchers("/auth/**")
				.permitAll()
				
				
				.requestMatchers("/employees/update/**")
				.hasAnyRole("ADMIN","MANAGER")
				
				.requestMatchers("/employees/allemp")
				.hasAnyRole("ADMIN","MANAGER","USER")
				
				
				.anyRequest()
				.authenticated())
				
				
				.addFilterBefore(jwtFilter,  UsernamePasswordAuthenticationFilter.class   );
		        return http.build();
				
				
				
				
				
		
	}
}
