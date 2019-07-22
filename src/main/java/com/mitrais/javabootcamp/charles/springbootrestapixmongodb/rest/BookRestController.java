package com.mitrais.javabootcamp.charles.springbootrestapixmongodb.rest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//CORS configuration
//@CrossOrigin(origins = "http://mitraiscdcjavabootcampebcharles.ap-southeast-1.elasticbeanstalk.com/", maxAge = 3600)
@RestController
@RequestMapping(value="/api/books")
public class BookRestController {

	@GetMapping(value="/test")
	public String test() {
		return "test";
	}
	
}
