package com.mitrais.javabootcamp.charles.springbootrestapixmongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mitrais.javabootcamp.charles.springbootrestapixmongodb.entity.Role;

public interface RoleRepository extends MongoRepository<Role, String> {
	
	Role findByRole(String role);

}
