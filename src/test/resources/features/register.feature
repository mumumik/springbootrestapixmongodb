Feature: Register/Signup

  As a user I want to register / signup to the Application
    
  Scenario: Register a user
  	Given i am on the Registration page
    When i submit a valid registration data 
    Then my account is created
    
  Scenario: Register a user with email that already exists
  	Given i am on the Registration page
  	When i submit registration data with email that already exists
  	Then my account is not created