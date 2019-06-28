package com.mitrais.javabootcamp.charles.springbootrestapixmongodb.rest;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mitrais.javabootcamp.charles.springbootrestapixmongodb.config.JwtTokenProvider;
import com.mitrais.javabootcamp.charles.springbootrestapixmongodb.entity.User;
import com.mitrais.javabootcamp.charles.springbootrestapixmongodb.payload.PayloadUser;
import com.mitrais.javabootcamp.charles.springbootrestapixmongodb.repository.UserRepository;
import com.mitrais.javabootcamp.charles.springbootrestapixmongodb.service.UserService;

@RestController
@RequestMapping(value="/api/auth")
public class UserRestController {

	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	@Autowired
	UserRepository userRepository;
	@Autowired
	private UserService userService;
	
	@SuppressWarnings("rawtypes")
	@PostMapping("/login")
	public ResponseEntity login(@RequestBody PayloadUser loginData) {
		
		try {
			String username = loginData.getEmail();
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, loginData.getPassword()));
			String token = jwtTokenProvider.createToken(username, this.userRepository.findByEmail(username).get().getRoles());
			Map<Object, Object> model = new HashMap<>();
			model.put("username", username);
			model.put("token", token);
			return ok(model);
		}catch(AuthenticationException e){
			throw new BadCredentialsException("Invalid email/password supplied");
		}
		
	}
	
	
	@SuppressWarnings("rawtypes")
	@PostMapping("/register")
	public ResponseEntity register(@RequestBody User user){
		User userExists = userService.findByEmail(user.getEmail());
		
		if(userExists != null) {
			throw new BadCredentialsException("User with email: "+ user.getEmail() + " already exists");
		}
		
		userService.save(user);
		Map<Object, Object> model = new HashMap<>();
		model.put("message", "User registered succesfully");
		return ok(model);
	}
	
}
