Feature: Password Reset Functionality

  @ResetPassword
  Scenario: User enters an unregistered Phone number
    Given User on Password Reset page
    When User enters an unregistered phone number "123456789"
    Then An error message for wrong phone appears

  @ResetPassword
  Scenario: User enters a registered phone number
    Given User on Password Reset page
    When User enters a registered phone number "521000400"
    Then User should receive an OTP
    When User enters the OTP and an invalid password
    Then User should see error messages for invalid password
    When User enters valid password "Pass@new456"
    Then User should be able to reset the password successfully



