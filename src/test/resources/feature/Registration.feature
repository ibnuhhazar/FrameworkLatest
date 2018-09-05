@FunctionalTest
Feature: Registration user

  Background: 
    i have opened a selendroid-test-app
    
  Scenario Outline: Registration user with Programming language is Java
    And i click button start register "startUserRegistration"
    And i enter as username "<username>" in "inputUsername"
    And i enter as email "ibnuhazar@btpn.com" in "inputEmail"
    And i enter as password "<password>" in "inputPassword"
    And i select as programming language "Java" in "input_preferedProgrammingLanguage"
    And i check checkbox accept adds in "input_adds"
    When i click button register "btnRegisterUser"
    Then verify user "Java" in "label_preferedProgrammingLanguage_data"
		
    Examples: 
      | username | password |
      | ibnuh    | jakob    |

	@Smoketest @EndtoEnd
  Scenario: Web View with dropdown say hello
    And i click button open chroom in "buttonStartWebview"
    And i click button spinner Webdriver Test Data in "spinner_webdriver_test_data"
    And i click as "formPage"
    When i click button "goBack"
    Then verify home page tittle is "selendroid-test-appa" in "title"