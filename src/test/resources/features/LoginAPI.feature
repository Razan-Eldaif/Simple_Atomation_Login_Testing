Feature: API Test

  Scenario: Verify the status of the API
    Given the API endpoint is "dev-v3.aqar.fm/graphql"
    When
    Then the response status code should be 200
    And the response body should contain "expectedValue"