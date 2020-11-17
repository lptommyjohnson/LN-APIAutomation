Feature: Query Dynamic QR
  Scenario: Issuer is able to query the QR generated from the acquirer
    Given the issuer processing ID is "300001"
    When  the QR data is retrieved from the generated Dynamic QR
    Then  QR has been queried successfully

  Scenario: Issuer will not be able to query th qr with invalid ID
    Given the issuer processing ID "500" does not exists
    When  the QR data is retrieved from the generated Dynamic QR
    Then  the error message telling that the partner ID is invalid will appear

  Scenario: Issuer will not be able to query th qr with invalid qrPayload
    Given the qrPayload "ABC" does not exists
    Then the error message telling that the QR Payload is invalid will appear

  Scenario: Issuer will not be able to query th qr with invalid datetime format
    Given the datetime "ABC" is in the wrong format
    Then the error message telling that the datetime is invalid will appear

  Scenario: Issuer will not be able to query th qr with missing ID
    Given the issuer processing ID is unknown
    Then the error message telling that the partner ID is missing will appear

  Scenario: Issuer will not be able to query th qr with missing qrPayload
    Given the QR Payload is unknown
    Then the error message telling that the QR Payload is missing will appear

  Scenario: Issuer will not be able to query th qr with missing datetime
    Given the datetime is unknown
    Then the error message telling that the datetime is missing will appear