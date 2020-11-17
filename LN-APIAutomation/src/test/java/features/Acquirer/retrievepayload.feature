Feature:Retrieve supported QR payloads
  Scenario: Only supported QR is returned to acquirer
    Given I setup Acquirer Processor QR config
    Then I should see all the supported QR payloads

  Scenario: No supported QR is returned to acquirer
    Given I do not setup Acquirer Processor QR config
    Then I should not see any supported QR payloads
