Feature: Create text note

  Background:
    Given I go to create text note option

  Scenario: Basic text note
    When I fill the text note
    Then It should be created

  Scenario: Take and attach a picture to text note
    When I attach a photo
    Then The photo should be attached to the text note
