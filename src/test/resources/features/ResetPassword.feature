Feature: Password Reset Functionality

  Scenario: User enters an unrigesterd Phone number
    Given User on Password Reset page
    When User enters an unregistered phone number "512345678"
    Then An error message for wrong phone appears

  Scenario: User enters a registered phone number
    Given User on Password Reset page
    When User enters a registered phone number "521000400"
    Then User should receive an OTP

  Scenario: User enters an incorrect OTP and a short password
    Given User on Password Reset page
    When User enters wrong OTP "8765432" and an invalid password
    Then User should see error messages for invalid OTP and invalid password

  Scenario: User successfully resets the password
    Given User on Password Reset page
    When User enters valid phone and correct OTP and valid password "Pass@new456"
    Then User should be able to reset the password successfully



