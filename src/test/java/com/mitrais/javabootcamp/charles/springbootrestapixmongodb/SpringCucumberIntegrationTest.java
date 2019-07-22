package com.mitrais.javabootcamp.charles.springbootrestapixmongodb;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.mitrais.javabootcamp.charles.springbootrestapixmongodb.entity.User;
import com.mitrais.javabootcamp.charles.springbootrestapixmongodb.payload.PayloadUser;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Ignore
public class SpringCucumberIntegrationTest {
	
	private final String SERVER_URL = "http://localhost";
	private final String RESTAPI_ENDPOINT = "/api";	
	private final String AUTH_ENDPOINT = "/auth";
	private final String REGISTER_ENDPOINT = "/register";
	private final String LOGIN_ENDPOINT = "/login";
	
	private RestTemplate restTemplate;
	
	@LocalServerPort
	protected int port;
	
	String response;
	
	public SpringCucumberIntegrationTest() {
		this.restTemplate = new RestTemplate();
	}
	
	protected String apiEndpoint() {
		return SERVER_URL + ":" + "5000" + RESTAPI_ENDPOINT;
	}
	
	ResponseEntity<String> register(User user){
		return restTemplate.postForEntity(apiEndpoint()+AUTH_ENDPOINT+REGISTER_ENDPOINT, user, String.class);
	}
	
	ResponseEntity<String> getRegister() {
		return restTemplate.getForEntity(apiEndpoint()+AUTH_ENDPOINT+REGISTER_ENDPOINT, String.class);
	}
	
	ResponseEntity<String> login(PayloadUser payloadUser){
		return restTemplate.postForEntity(apiEndpoint()+AUTH_ENDPOINT+LOGIN_ENDPOINT, payloadUser, String.class);
	}
	
	ResponseEntity<String> getLogin() {
		return restTemplate.getForEntity(apiEndpoint()+AUTH_ENDPOINT+LOGIN_ENDPOINT, String.class);
	}

}
