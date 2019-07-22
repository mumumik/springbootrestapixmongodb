package com.mitrais.javabootcamp.charles.springbootrestapixmongodb;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.web.client.HttpClientErrorException.BadRequest;

import com.mitrais.javabootcamp.charles.springbootrestapixmongodb.entity.User;
import com.mitrais.javabootcamp.charles.springbootrestapixmongodb.payload.PayloadUser;

import io.cucumber.java8.En;

public class SpringbootrestapixmongodbApplicationSteps extends SpringCucumberIntegrationTest implements En{
	
	public SpringbootrestapixmongodbApplicationSteps() {
		
		User user = new User();
		PayloadUser payloadUser = new PayloadUser();
		
		Given("i am on the Registration page", () -> {
			assertThat(getRegister().getBody()).isEqualTo("{\"message\":\"you are on the registration page\"}");
		});
		
		Given("i am on the Login page", () -> {
			assertThat(getLogin().getBody()).isEqualTo("{\"message\":\"you are on the login page\"}");
		});

		When("i submit a valid registration data", () -> {
			user.setEmail("charlesarnesus@gmail.com");
			user.setPassword("123");
			user.setFullname("Charles Arnesus");
			try {
				response = register(user).getBody();
			} catch(BadRequest e) {
				response = e.getResponseBodyAsString();
			}
		});
		
		When("i submit registration data with email that already exists", () -> {
			user.setEmail("arnesuscharles@gmail.com");
			user.setPassword("123");
			user.setFullname("Charles Arnesus");
			try {
				response = register(user).getBody();
			} catch(BadRequest e) {
				response = e.getResponseBodyAsString();
			}
		});
		
		When("i submit a valid credential", () -> {
			payloadUser.setEmail("arnesuscharles@gmail.com");
			payloadUser.setPassword("123");
			try {
				response = login(payloadUser).getStatusCode().toString();
			} catch(Exception e) {
				response = e.getMessage();
			}
		});
		
		When("i submit an invalid credential", () -> {
			payloadUser.setEmail("invalid@gmail.com");
			payloadUser.setPassword("invalid");
			try {
				response = login(payloadUser).getStatusCode().toString();
			} catch(Exception e) {
				response = e.getMessage();
			}
		});
		
		Then("my account is created", () -> {
			assertThat(response).isEqualTo("{\"message\":\"User registered succesfully\"}");
		});
		
		Then("my account is not created", () -> {
			assertThat(response).isEqualTo("{\"message\":\"User with email: "+user.getEmail()+" already exists\"}");
		});
		
		Then("i am successfully logged in", () -> {
			assertThat(response).isEqualTo("200 OK");
		});
		
		Then("i am not successfully logged in", () -> {
			assertThat(response).isEqualTo("403 null");
		});
		
	}
	
}
