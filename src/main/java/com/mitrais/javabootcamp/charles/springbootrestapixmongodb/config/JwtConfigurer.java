package com.mitrais.javabootcamp.charles.springbootrestapixmongodb.config;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	private JwtTokenProvider jwtTokenProvider;
	
	public JwtConfigurer(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	//this method will check whether a token is still valid or not
	@Override
	public void configure(HttpSecurity builder) throws Exception {
		
		JwtTokenFilter customFilter = new JwtTokenFilter(jwtTokenProvider);
		builder.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
		
	}

}
