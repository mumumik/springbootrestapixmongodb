package com.mitrais.javabootcamp.charles.springbootrestapixmongodb.entity;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="user")
public class User {

	@Id
	private String id;
	private String email;
	private String password;
	private String fullname;
	private boolean enabled;
	@DBRef
	private Set<Role> roles;
	
	public User() {
		
	}

	public User(String id, String email, String password, String fullname, boolean enabled, Set<Role> roles) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.fullname = fullname;
		this.enabled = enabled;
		this.roles = roles;
	}

	public User(String email, String password, String fullname, boolean enabled, Set<Role> roles) {
		this.email = email;
		this.password = password;
		this.fullname = fullname;
		this.enabled = enabled;
		this.roles = roles;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
}
