Feature: Create checklists

  Background:
    Given I go to create checklist option

  Scenario: Basic checklist
    When I fill the checklist
    Then It should be created

  Scenario: Take and attach a picture to checklist
    When I attach a photo
    Then The photo should be attached to the note
