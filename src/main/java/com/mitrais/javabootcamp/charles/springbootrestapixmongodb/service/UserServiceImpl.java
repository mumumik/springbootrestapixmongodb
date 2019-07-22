package com.mitrais.javabootcamp.charles.springbootrestapixmongodb.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mitrais.javabootcamp.charles.springbootrestapixmongodb.entity.Role;
import com.mitrais.javabootcamp.charles.springbootrestapixmongodb.entity.User;
import com.mitrais.javabootcamp.charles.springbootrestapixmongodb.repository.RoleRepository;
import com.mitrais.javabootcamp.charles.springbootrestapixmongodb.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Optional<User> result = userRepository.findByEmail(email);
		User user = null;
		if(result.isPresent()) {
			user = result.get();
		}
		
		if(user == null) {
			throw new UsernameNotFoundException("Invalid username or password");
		}
		List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
		return buildUserForAuthentication(user, authorities);
	}

	@Override
	public void save(User user) {
		
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setEnabled(true);
		Role userRole = roleRepository.findByRole("ADMIN");
		user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		userRepository.save(user);

	}
	
	public User findByEmail(String email) {
		
		Optional<User> result = userRepository.findByEmail(email);
		User user = null;
		
		if(result.isPresent()) {
			user = result.get();
		}
		
		return user;
	}
	
	private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles){
		Set<GrantedAuthority> roles = new HashSet<>();
		userRoles.forEach((role)->{
			roles.add(new SimpleGrantedAuthority(role.getRole()));
		});
		
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
		
		return grantedAuthorities;
	}
	
	private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
	}

}
