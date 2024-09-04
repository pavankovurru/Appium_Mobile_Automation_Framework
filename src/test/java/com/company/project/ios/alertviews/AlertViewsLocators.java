package com.company.project.ios.alertviews;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

public class AlertViewsLocators {

  // Alert Views locators
  public static final By alertViewsScreenTitle =
      AppiumBy.iOSNsPredicateString(
          "name == \"Alert Views\" AND type == \"XCUIElementTypeNavigationBar\"");

  // Alert Styles
  public static final By alertViewsSimpleAlert = AppiumBy.accessibilityId("Simple");
  public static final By alertViewsOkayCancelAlert = AppiumBy.accessibilityId("Okay / Cancel");
  public static final By alertViewsOtherAlert = AppiumBy.accessibilityId("Other");
  public static final By alertViewsTextEntryAlert = AppiumBy.accessibilityId("Text Entry");
  public static final By alertViewsSecureTextEntryAlert =
      AppiumBy.accessibilityId("Secure Text Entry");

  // Simple Alert
  public static final By alertViewsSimpleAlertContainer =
      AppiumBy.iOSNsPredicateString(
          "name == \"A Short Title Is Best\" AND label == \"A Short Title Is Best\" AND type == \"XCUIElementTypeAlert\"");
  public static final By alertViewsSimpleAlertTitle =
      AppiumBy.xpath("//XCUIElementTypeStaticText[@name=\"A Short Title Is Best\"]");
  public static final By alertViewsSimpleAlertMessage =
      AppiumBy.xpath(
          "//XCUIElementTypeStaticText[@name=\"A message should be a short, complete sentence.\"]");
  public static final By alertViewsSimpleAlertOKButton =
      AppiumBy.xpath("//XCUIElementTypeButton[@name=\"OK\"]");

  // Navigate back to landing screen
  public static final By navigateBackFromAlertViewsToLandingScreenButton =
      AppiumBy.xpath("//XCUIElementTypeButton[@name=\"UIKitCatalog\"]");
}
