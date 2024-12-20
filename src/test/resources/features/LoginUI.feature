Feature: Login Functionality

  Scenario: Verify the presence of the login form
    Given User on the login page
    Then The login form with all its fields and button appear

  Scenario: Verify Login with incorrect Password
    Given User on the login page
    When User enters valid Phone and incorrect password
    Then An error message should apper on the screen

  Scenario: Verify Login with incorrect Phone
    Given User on the login page
    When User enters valid password and incorrect phone number
    Then Invalid phone number error message should appear

  Scenario: Verify Login with unregisterd Phone
    Given User on the login page
    When User enters valid password and unregistered phone number
    Then unregisterd phone number error message should appear

  Scenario: Verify Login with blank password and Phone
    Given User on the login page
    When User enter login buttoun with empty feilds
    Then two error messages should apper on the screen

  Scenario: Verify Login with valid Phone and blank password
    Given User on the login page
    When User enters valid phone and leave password feild empty
    Then An error message for empty password should apper on the screen

  Scenario: Verify Login with valid password and blank phone
    Given User on the login page
    When User enters valid password and leave phone feild empty
    Then An error message for empty phone should apper on the screen

  Scenario: Verify phone number feild validation
    Given User on the login page
    When User enters chracters on phone feild
    Then Any type of chracters should be blocked

  Scenario: Verify password hidden
    Given User on the login page
    When User enters chracters on password feild and the content is hiden
    And click on unhide password icon
    Then The text on password field unhiden




  Scenario: Verify Login with valid Phone number and Password
    Given User on the login page
    When User enters correct Phone and correct password
    Then User should be redirected to the dashboard