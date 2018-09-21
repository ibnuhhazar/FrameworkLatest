@Registration
Feature: Registration

  @demo
  Scenario: Registration user with java programming language
  	Given i install Selendroid apps
    And i open selendroid app
    When i click button "startUserRegistrationCD"
    And i enter as username "Asterix" in id "inputUsername"
    And i enter as email "ibnuhazar@btpn.com" in id "inputEmail"
    And i enter as password "Obelix" in id "inputPassword"
    And i select as programming language "Java" in "input_preferedProgrammingLanguage"
    And i check checkbox accept adds ni "input_adds"
    And i click button by id "btnRegisterUser"
    Then i verify, so that id "label_preferedProgrammingLanguage_data" is "Java"