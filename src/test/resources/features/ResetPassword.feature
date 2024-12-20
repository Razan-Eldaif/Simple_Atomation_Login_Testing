Feature: Password Reset Functionality

Scenario: User enters an unrigesterd Phone number
  When User enters an unregistered phone number "123454362"
  Then An error message for wrong phone appears

  Scenario: User enters a registered phone number
    When User clicks on forgot password
    And I enter a registered phone number "521000400"
    Then User should receive an OTP

  Scenario: User enters an incorrect OTP and a short password
    When User clicks on forgot password
    And I enter a registered phone number "521000400"
    And User enters wrong OTP "8765432"
    And User enters a new password "9876" and repeats it as "9876
    Then User should see an error message for invalid OTP
    And User should see an error message for invalid password

  Scenario: User enters an invalid password
    When User clicks on forgot password
    And I enter a registered phone number "521000400"
    And User enters a valid OTP
    And User enters an invalid password "password" and repeats it as "password"
    Then User should see an error message for invalid password

  Scenario: User successfully resets the password

    When User clicks on forgot password
    And I enter a registered phone number "521000400"
    And User enters a valid OTP
    And User enters a valid password "Pass@new456" and repeats it as "Pass@new456"
    Then User should be able to reset the password successfully

