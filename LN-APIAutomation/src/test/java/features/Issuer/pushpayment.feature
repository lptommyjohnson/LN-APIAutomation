Feature: Making a push payment
  Scenario: Customer is able to make payment
    Given   The dynamic QR has been generated
    When     The mandatory fields are filled in correctly
    When     The partner id is "300001"
    When     The push payment is initiated
    Then    Payment is successfully made

  Scenario: Customer is not able to make payment with invalid partnerID
    Given   The dynamic QR has been generated
    When    The mandatory fields are filled in correctly
    When    The partner id is "500"
    When    The push payment is initiated
    Then    An error message should be displayed to tell the invalid partnerID`

  Scenario: Customer is not able to make payment with invalid transactionDateTime
    Given   The dynamic QR has been generated
    When    The mandatory fields are filled in correctly
    When    The transactionDateTime is "ABC"
    When    The push payment is initiated
    Then    An error message should be displayed to tell the invalid transactionDateTime

  Scenario: Customer is not able to make payment with invalid payee
    Given   The dynamic QR has been generated
    When    The mandatory fields are filled in correctly
    When    The payee is "ABC"
    When    The push payment is initiated
    Then    An error message should be displayed to tell the invalid payee

  Scenario: Customer is not able to make payment with invalid service code
    Given   The dynamic QR has been generated
    When    The mandatory fields are filled in correctly
    When    The service code is "ABC"
    When    The push payment is initiated
    Then    An error message should be displayed to tell the invalid service code

  Scenario: Customer is not able to make payment with invalid order ID
    Given   The dynamic QR has been generated
    When    The mandatory fields are filled in correctly
    When    The order ID is "ABC"
    When    The push payment is initiated
    Then    An error message should be displayed to tell the invalid order ID

  Scenario: Customer is not able to make payment with invalid channel
    Given   The dynamic QR has been generated
    When     The mandatory fields are filled in correctly
    When     The channel is "ABC"
    When     The push payment is initiated
    Then    An error message should be displayed to tell the invalid channel

  Scenario: Customer is not able to make payment with invalid format customer currency code
    Given   The dynamic QR has been generated
    When    The mandatory fields are filled in correctly
    When    The customer currency code is "ABC"
    When    The push payment is initiated
    Then    An error message should be displayed to tell the invalid customer currency code

  Scenario: Customer is not able to make payment with invalid format of customer amount
    Given   The dynamic QR has been generated
    When    The mandatory fields are filled in correctly
    When    The customer amount is "ABC"
    When    The push payment is initiated
    Then    An error message should be displayed to tell the invalid customer amount

  Scenario: Customer is not able to make payment with invalid format merchant currency code
    Given   The dynamic QR has been generated
    When    The mandatory fields are filled in correctly
    When    The merchant currency code is "ABC"
    When    The push payment is initiated
    Then    An error message should be displayed to tell the invalid merchant currency code

  Scenario: Customer is not able to make payment with invalid format of merchant amount
    Given   The dynamic QR has been generated
    When    The mandatory fields are filled in correctly
    When    The merchant amount is "ABC"
    When    The push payment is initiated
    Then    An error message should be displayed to tell the invalid merchant amount


  Scenario: Customer is not able to make payment with missing partnerID
    Given   The dynamic QR has been generated
    When    The mandatory fields are filled in correctly
    When    The partner id is ""
    When    The push payment is initiated
    Then    An error message should be displayed to tell the missing partnerID`

  Scenario: Customer is not able to make payment with missing transactionDateTime
    Given   The dynamic QR has been generated
    When    The mandatory fields are filled in correctly
    When    The transactionDateTime is ""
    When    The push payment is initiated
    Then    An error message should be displayed to tell the missing transactionDateTime

  Scenario: Customer is not able to make payment with missing payee
    Given   The dynamic QR has been generated
    When    The mandatory fields are filled in correctly
    When    The payee is ""
    When    The push payment is initiated
    Then    An error message should be displayed to tell the missing payee

  Scenario: Customer is not able to make payment with missing service code
    Given   The dynamic QR has been generated
    When    The mandatory fields are filled in correctly
    When    The service code is ""
    When    The push payment is initiated
    Then    An error message should be displayed to tell the missing service code

  Scenario: Customer is not able to make payment with missing order ID
    Given   The dynamic QR has been generated
    When    The mandatory fields are filled in correctly
    When    The order ID is ""
    When    The push payment is initiated
    Then    An error message should be displayed to tell the missing order ID

  Scenario: Customer is not able to make payment with missing channel
    Given   The dynamic QR has been generated
    When    The mandatory fields are filled in correctly
    When    The channel is ""
    When    The push payment is initiated
    Then    An error message should be displayed to tell the missing channel

  Scenario: Customer is not able to make payment with missing format customer currency code
    Given   The dynamic QR has been generated
    When    The mandatory fields are filled in correctly
    When    The customer currency code is ""
    When    The push payment is initiated
    Then    An error message should be displayed to tell the missing customer currency code

  Scenario: Customer is not able to make payment with missing format of customer amount
    Given   The dynamic QR has been generated
    When    The mandatory fields are filled in correctly
    When    The customer amount is ""
    When    The push payment is initiated
    Then    An error message should be displayed to tell the missing customer amount

  Scenario: Customer is not able to make payment with missing format merchant currency code
    Given   The dynamic QR has been generated
    When    The mandatory fields are filled in correctly
    When    The merchant currency code is ""
    When    The push payment is initiated
    Then    An error message should be displayed to tell the missing merchant currency code

  Scenario: Customer is not able to make payment with missing format of merchant amount
    Given   The dynamic QR has been generated
    When    The mandatory fields are filled in correctly
    When    The merchant amount is ""
    When    The push payment is initiated
    Then    An error message should be displayed to tell the missing merchant amount

  Scenario: Customer is not able to make payment when the merchant amount is larger than the transaction amount
    Given   The dynamic QR has been generated
    When    The mandatory fields are filled in correctly
    When    The merchant amount is $ "1000.00" when the transaction amount is $ "888.00"
    When    The push payment is initiated
    Then    An error message should be displayed to tell the merchant amount does not match the amount in the order

  Scenario: Customer is not able to make payment when the merchant amount is smaller than the transaction amount
    Given   The dynamic QR has been generated
    When    The mandatory fields are filled in correctly
    When    The merchant amount is $ "700.00" when the transaction amount is $ "888.00"
    When    The push payment is initiated
    Then    An error message should be displayed to tell the merchant amount does not match the amount in the order

  Scenario: Customer is able to make payment with cross border wallet
    Given   The dynamic QR has been generated successfully
    And     The Query has been retrieved
    When    The cross border mandatory fields are filled in correctly
    When    The cross border push payment is initiated
    Then    Cross border Payment is successfully made

  Scenario: Customer is not able to make cross border payment twice
    Given   The dynamic QR has been generated successfully
    And     The Query has been retrieved
    When    The cross border mandatory fields are filled in correctly
    When    The cross border push payment is initiated
    When    The payment is initiated again with the same QR
    Then    An error message to display that the order is not in a state that can be paid will appear