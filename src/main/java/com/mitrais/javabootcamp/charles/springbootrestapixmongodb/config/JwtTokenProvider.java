package com.mitrais.javabootcamp.charles.springbootrestapixmongodb.config;

import java.util.Base64;
import java.util.Date;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.mitrais.javabootcamp.charles.springbootrestapixmongodb.entity.Role;
import com.mitrais.javabootcamp.charles.springbootrestapixmongodb.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {

	@Value("${security.jwt.token.secret-key:secret}")
	private String secretKey = "secret";
	
	@Value("${security.jwt.token.expire-length:3600000}")
	private long validityInMilliseconds = 3600000; // 1h
	
	@Autowired
	private UserService userService;
	
	//used to encode the secretKey to Base64 encoding after the start of the application
	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}
	
	//used to create token when user is login to the application
	public String createToken(String username, Set<Role> roles) {
		System.out.println(secretKey);
		Claims claims = Jwts.claims().setSubject(username);
		claims.put("roles", roles);
		Date now = new Date();
		Date validity = new Date(now.getTime() + validityInMilliseconds);
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(validity)
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact();
	}
	
	//used in JwtTokenFilter.java to create an User authentication when the user is already login to the application
	public Authentication getAuthentication(String token) {
		UserDetails userDetails = this.userService.loadUserByUsername(getUsername(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}
	
	//used in JwtTokenProvider.java, getAuthentication() method
	//used to get the username/email/subject from the token
	public String getUsername(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}
	
	//used in JwtTokenFilter.java to make sure that the token starts with "Bearer "
	public String resolveToken(HttpServletRequest req) {
		String bearerToken = req.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}
	
	//used in JwtTokenFilter.java used to make sure that the token is not expired or invalid
	public boolean validateToken(String token) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			if(claims.getBody().getExpiration().before(new Date())) {
				return false;
			}
			return true;
		}catch(JwtException | IllegalArgumentException e) {
			throw new JwtException("Expired or invalid JWT Token");
		}
	}
	
}
