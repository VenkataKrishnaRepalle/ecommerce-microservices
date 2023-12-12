Feature: User

  Background:
    * url AUTH_BASE_URL

  Scenario Outline: Register
    Given path '/register'
    And header Content-type = 'application/json'
    And request
    """
      {
        "username": '<username>',
        "fullName": '<fullName>',
        "phoneNumber": '<phoneNumber>',
        "email": '<email>',
        "password": '<password>',
        "confirmPassword": '<password>'
      }
    """
    When method POST
    Then status 200
    Examples:
      | username | fullName | phoneNumber | email            | password     |
      | user11   | user 11  | 6000000011  | user11@gmail.com | testPassword |