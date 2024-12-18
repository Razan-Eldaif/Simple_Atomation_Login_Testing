Feature: Reset Password Feature

  Scenario: Send reset OTP to a registered phone number
    Given I navigate to the reset password page
    When I enter a registered phone number "1234567890"
    Then I should receive an OTP for password reset

  Scenario: Validate the error message for an unregistered phone number
    Given I navigate to the reset password page
    When I enter an unregistered phone number "0000000000"
    Then I should see an error message "Phone number not registered"

  Scenario: Attempt to reset password with an invalid new password
    Given I navigate to the reset password page
    When I enter a registered phone number "1234567890"
    And I receive an OTP
    And I enter the OTP "123456"
    And I enter a new password "short"
    Then I should see an error message "Password must be at least 8 characters long with at least one special character"

  Scenario: Reset password with a valid new password
    Given I navigate to the reset password page
    When I enter a registered phone number "1234567890"
    And I receive an OTP
    And I enter the OTP "123456"
    And I enter a valid new password "Pass@new456"
    Then I should see a success message "Password reset successfully"