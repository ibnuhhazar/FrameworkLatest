@demos
Feature: Verify disabled button

	@verifybutton
	Scenario: Test to verify button is disabled
	Given I open APIDemos APK
	When I verify "API Demos" is display by text
	And I swipe to text "Security"
	And I click by text "Security"
	And I verify "KeyStore" is display by text
	And I click by text "KeyStore"
	And I verify "Security/KeyStore" is display by text
	Then I verify "sign_button" is disable by id