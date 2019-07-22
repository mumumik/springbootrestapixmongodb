Feature: Login

	As a user I want to log in to the application
	
	Scenario: Login with a valid credentials
		Given i am on the Login page
		When i submit a valid credential
		Then i am successfully logged in
		
	Scenario: Login with an invalid credentials
		Given i am on the Login page
		When i submit an invalid credential
		Then i am not successfully logged in
		
	