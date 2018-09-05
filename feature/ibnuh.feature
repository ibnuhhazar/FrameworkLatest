@Registration
Feature: Test

  @demo
  Scenario: Test Login EForm
  	Given i install EFORM apps
    Given i open EForm Apps
    When i enter as Nomor HP "08990677550" in "login_phone_number_edittext"
    And i enter as Pin "112234" in "login_pin_edittext"