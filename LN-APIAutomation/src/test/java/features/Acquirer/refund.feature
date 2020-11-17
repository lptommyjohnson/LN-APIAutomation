Feature: Refund a payment
  Scenario: Merchant will be able to make a full refund on the same day
    Given the QR has been generated
    And   the push payment has been made
    When  the refund details are filled in correctly
    When  the refund amount is initiated
    Then  the full amount is refunded successfully

  Scenario: Merchant will be able to make a full refund with a refund reason
    Given the QR has been generated
    And   the push payment has been made
    When  the refund details are filled in correctly with a reason
    When  the refund amount is initiated
    Then  the full amount is refunded successfully

  Scenario: Merchant will be able to make a partial refund on the same day
    Given the QR has been generated
    And   the push payment has been made
    When  the refund details are filled in correctly with partial amount "100.00"
    When  the refund amount is initiated
    Then  the partial amount is refunded successfully

  Scenario: Merchant will be able to make a full refund on the next day
    Given the QR has been generated
    And   the push payment has been made on the previous day
    When  the refund details are filled in correctly
    When  the refund amount is initiated
    Then  the full amount is refunded successfully

  Scenario: Merchant will be able to make a partial refund on the next day
    Given the QR has been generated
    And   the push payment has been made on the previous day
    When  the refund details are filled in correctly with partial amount "100.00"
    When  the refund amount is initiated
    Then  the partial amount is refunded successfully

  Scenario: Merchant will not be able to make refund with excess amount
    Given the QR has been generated
    And   the push payment has been made
    When  the refund details are filled in correctly with amount "1000.00" larger than transaction amount
    When  the refund amount is initiated
    Then  An error message should be displayed to tell that refund amount has exceeded the maximum amount refundable

  Scenario: Merchant will not be able to make refund twice
    Given the QR has been generated
    And   the push payment has been made
    When  the refund details are filled in correctly
    When  the refund amount is initiated
    When  the refund amount is initiated again
    Then  An error message should be displayed to tell that refund amount has exceeded the maximum amount refundable

  Scenario: Merchant will not be able to make refund without partner ID
    Given the QR has been generated
    And   the push payment has been made
    When  the refund details are filled in correctly without partnerID
    When  the refund amount is initiated
    Then  An error message should be displayed to tell that partner ID is mandatory

  Scenario: Merchant will not be able to make refund without transaction ID
    Given the QR has been generated
    And   the push payment has been made
    When  the refund details are filled in correctly without transaction ID
    When  the refund amount is initiated
    Then  An error message should be displayed to tell that transaction ID is mandatory

  Scenario: Merchant will not be able to make refund without currency code
    Given the QR has been generated
    And   the push payment has been made
    When  the refund details are filled in correctly without currency code
    When  the refund amount is initiated
    Then  An error message should be displayed to tell that currency code is mandatory

  Scenario: Merchant will not be able to make refund without amount
    Given the QR has been generated
    And   the push payment has been made
    When  the refund details are filled in correctly without amount
    When  the refund amount is initiated
    Then  An error message should be displayed to tell that amount is mandatory

  Scenario: Merchant will not be able to make refund with invalid transaction ID
    Given the QR has been generated
    And   the push payment has been made
    When  the refund details are filled in correctly with invalid transaction ID
    When  the refund amount is initiated
    Then  An error message should be displayed to tell that invalid transaction ID

  Scenario: Merchant will not be able to make refund with invalid partner ID
    Given the QR has been generated
    And   the push payment has been made
    When  the refund details are filled in correctly with invalid partner ID
    When  the refund amount is initiated
    Then  An error message should be displayed to tell that invalid partner ID

  Scenario: Merchant will not be able to make refund with invalid currency code
    Given the QR has been generated
    And   the push payment has been made
    When  the refund details are filled in correctly with invalid currency code
    When  the refund amount is initiated
    Then  An error message should be displayed to tell that invalid currency code

  Scenario: Merchant will not be able to make refund with invalid amount
    Given the QR has been generated
    And   the push payment has been made
    When  the refund details are filled in correctly with invalid amount
    When  the refund amount is initiated
    Then  An error message should be displayed to tell that invalid amount

  Scenario: Merchant will not be able to make refund with a different transaction currency code
    Given the QR has been generated
    And   the push payment has been made
    When  the refund details are filled in correctly with a different transaction currency code
    When  the refund amount is initiated
    Then  An error message should be displayed to tell that refund currency must be the same as transaction currency

  Scenario: Merchant will be able to make refund for a cross border payment
    Given the QR has been generated
    And   the QR has been queried
    And   the cross border push payment has been made
    When  the refund details are filled in correctly
    When  the refund amount is initiated
    Then  the full amount is refunded successfully



