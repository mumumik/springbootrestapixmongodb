package com.mitrais.javabootcamp.charles.springbootrestapixmongodb.rest;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.badRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mitrais.javabootcamp.charles.springbootrestapixmongodb.config.JwtTokenProvider;
import com.mitrais.javabootcamp.charles.springbootrestapixmongodb.entity.User;
import com.mitrais.javabootcamp.charles.springbootrestapixmongodb.payload.PayloadUser;
import com.mitrais.javabootcamp.charles.springbootrestapixmongodb.repository.UserRepository;
import com.mitrais.javabootcamp.charles.springbootrestapixmongodb.service.UserService;

//CORS configuration
//@CrossOrigin(origins = "http://mitraiscdcjavabootcampebcharles.ap-southeast-1.elasticbeanstalk.com/", maxAge = 3600)
@RestController
@RequestMapping(value="/api")
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
	@PostMapping("/auth/login")
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
			Map<Object, Object> model = new HashMap<>();
			model.put("message", "Invalid username/password");
			return ResponseEntity.status(403).body(model);
		}
		
	}
	
	
	@SuppressWarnings("rawtypes")
	@PostMapping("/auth/register")
	public ResponseEntity register(@RequestBody User user){
		User userExists = userService.findByEmail(user.getEmail());
		Map<Object, Object> model = new HashMap<>();
		if(userExists != null) {
			model.put("message", "User with email: "+ user.getEmail() + " already exists");
			return badRequest().body(model);
		} else {
			userService.save(user);
			model.put("message", "User registered succesfully");
			return ok(model);
		}
		
	}
	
	@SuppressWarnings("rawtypes")
	@GetMapping("/auth/register")
	public ResponseEntity getRegisterPage() {
		Map<Object, Object> model = new HashMap<>();
		model.put("message", "you are on the registration page");
		return ok(model);
	}
	
	@SuppressWarnings("rawtypes")
	@GetMapping("/auth/login")
	public ResponseEntity getLoginPage() {
		Map<Object, Object> model = new HashMap<>();
		model.put("message", "you are on the login page");
		return ok(model);
	}
	
	@GetMapping("/user/{email}")
	public User getUserbyEmail(@PathVariable String email) {
		return userService.findByEmail(email);
	}
	
}
