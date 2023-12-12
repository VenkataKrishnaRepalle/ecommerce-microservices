Feature: Authentication

  Background:
    * url AUTH_BASE_URL

  Scenario Outline: 01 - Password Login
    Given path 'password-login'
    And header Content-type = 'application/json'
    And request
    """
      {
        "userIdentifier": <userIdentifier>,
        "password": <password>
      }
    """
    When method POST
    Then status 200
    And match response == '#notnull'
    Examples:
      | userIdentifier | password   |
      | user10         | usertest10 |

  Scenario Outline: 02 - Otp Login
    Given path 'otp-login'
    And header Content-Type = 'application/json'
    And request
    """
      {
        "userIdentifier": <userIdentifier>
      }
    """
    When method POST
    Then status 200
    And match response == '#notnull'
    Examples:
      | userIdentifier |
      | user1          |

  Scenario Outline: 03 - Get UserId By UserIdentifier
    Given path 'get-userId-by-userIdentifier/<userIdentifier>'
    And header Content-Type = 'application/json'
    When method GET
    Then status 200
    And match response == '#string'
    Examples:
      | userIdentifier |
      | user1          |