Feature: Generate Dynamic QR

  Scenario: Acquirer is able to generate the dynamic QR
    Given I provide the correct test data to the endpoint
    Then Dynamic QR is generated successfully

  Scenario: Dynamic QR details (data, id) will be updated when merchant info is updated
    Given I provide "Singapore Merchant" as the merchant name for the first time
    And I provide "SG Merchant" as the merchant name for the second time
    Then the QR details will be updated

  Scenario: Generate a dynamic QR with invalid payload code will trigger an error message
    Given  Given that the acquirer do not accept "Load" as payload code
    Then An error message stating that payload code is invalid will appear

  Scenario: Generate a dynamic QR with invalid partner id will trigger an error message
    Given The partner id "500" does not exists
    Then An error message stating that partner ID  is invalid will appear

  Scenario: Generate a dynamic QR with invalid payee will trigger an error message
    Given The payee "ABC" does not exists
    Then An error message stating that payee is invalid will appear

  Scenario: Generate a dynamic QR with invalid currency code will trigger an error message
    Given The currency code "ABC" does not exists
    Then An error message stating that currency code is invalid will appear

  Scenario: Generate a dynamic QR with invalid amount will trigger an error message
    Given The amount "ABC" is not in the right format
    Then An error message stating that amount is invalid will appear

  Scenario: Generate a dynamic QR with invalid service code will trigger an error message
    Given The service code "ABC" is not in the right format
    Then An error message stating that service code is invalid will appear

  Scenario: Generate a dynamic QR with invalid merchant country code will trigger an error message
    Given The merchant country code "ABC" is not in the right format
    Then An error message stating that merchant country code is invalid will appear

  Scenario: Generate a dynamic QR with invalid mcc will trigger an error message
    Given The mcc "ABC" does not exists
    Then An error message stating that mcc is invalid will appear

  Scenario: Generate a dynamic QR with missing payload code will trigger an error message
    Given  Given that the payload code is empty
    Then An error message stating that payload code is mandatory will appear

  Scenario: Generate a dynamic QR with missing partner ID will trigger an error message
    Given  Given that the partner ID is empty
    Then An error message stating that partner ID is mandatory will appear

  Scenario: Generate a dynamic QR with missing payee will trigger an error message
    Given  Given that the payee is empty
    Then An error message stating that payee is mandatory will appear

  Scenario: Generate a dynamic QR with missing currency code will trigger an error message
    Given  Given that the currency code is empty
    Then An error message stating that currency code is mandatory will appear

  Scenario: Generate a dynamic QR with missing amount will trigger an error message
    Given  Given that the amount is empty
    Then An error message stating that amount is mandatory will appear

  Scenario: Generate a dynamic QR with missing service code will trigger an error message
    Given  Given that the service code is empty
    Then An error message stating that service code is mandatory will appear

  Scenario: Generate a dynamic QR with missing merchant country code will trigger an error message
    Given  Given that the merchant country code is empty
    Then An error message stating that merchant country code is mandatory will appear

  Scenario: Generate a dynamic QR with missing mcc will trigger an error message
    Given  Given that the mcc is empty
    Then An error message stating that mcc is mandatory will appear

  Scenario: Acquirer is not able to generate the dynamic QR with the different merchant country code in which it is located at
    Given I provide "US" as the merchant country code when it is located in SG
    Then An error message stating that merchant country code is invalid will appear

