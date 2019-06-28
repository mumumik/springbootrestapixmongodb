package com.mitrais.javabootcamp.charles.springbootrestapixmongodb;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.mitrais.javabootcamp.charles.springbootrestapixmongodb.entity.Role;
import com.mitrais.javabootcamp.charles.springbootrestapixmongodb.repository.RoleRepository;

@SpringBootApplication
public class SpringbootrestapixmongodbApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootrestapixmongodbApplication.class, args);
	}
	
	@Bean
	CommandLineRunner init(RoleRepository roleRepository) {
		
		return args ->{
			Role adminRole = roleRepository.findByRole("ADMIN");
			if(adminRole == null) {
				Role newAdminRole = new Role();
				newAdminRole.setRole("ADMIN");
				roleRepository.save(newAdminRole);
			}
		};
		
	}

}
