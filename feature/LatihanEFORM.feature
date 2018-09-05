@Registration
Feature: Registration

  @demo
  Scenario: Registration user with java programming language
  	Given i install EFORM apps
    And i open selendroid app
    When i click button start register "startUserRegistration"
    And i enter as username "Asterix" in "inputUsername"
    And i enter as email "ibnuhazar@btpn.com" in "inputEmail"
    And i enter as password "Obelix" in "inputPassword"
    And i select as programming language "Java" in "input_preferedProgrammingLanguage"
    And i check checkbox accept adds ni "input_adds"
    And i click button register "btnRegisterUser"
    Then i verify, so that "label_preferedProgrammingLanguage_data" is "Java"