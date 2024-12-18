Feature: Login Functionality

  Scenario: Verify the presence of the login form
    Given User on the login page
    Then The login form with all its feilds and button appear


  Scenario: Verify Login with incorrect Password
    Given User on the login page
    When User enters valid Phone and incorrect password
    Then An error message should apper on the screen

  Scenario: Verify Login with incorrect Phone
    Given User on the login page
    When User enters valid password and incorrect phone number
    Then An error message should apper on the screen

  Scenario: Verify Login with blank password and Phone
    Given User on the login page
    When User enter login buttoun with empty feilds
    Then An error message should apper on the screen

  Scenario: Verify Login with valid Phone and blank password
    Given User on the login page
    When User enters valid phone and leave password feild empty
    Then An error message should apper on the screen

  Scenario: Verify Login with valid password and blank phone
    Given User on the login page
    When User enters valid password and leave phone feild empty
    Then An error message should apper on the screen

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
    When User enters valid Phone and password
    Then User should be redirect to the dashboard


