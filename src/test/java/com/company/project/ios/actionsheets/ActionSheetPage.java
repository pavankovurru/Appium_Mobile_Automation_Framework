package com.company.project.ios.actionsheets;

import com.company.project.utilities.appium.AppiumUserSimulations;
import io.appium.java_client.AppiumDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ActionSheetPage {

  private Logger log = LogManager.getLogger();
  AppiumDriver driver;
  AppiumUserSimulations appium = null;

  public ActionSheetPage(AppiumDriver driver) {
    this.driver = driver;
    appium = new AppiumUserSimulations(driver);
  }

  public void navigateToUICatalogHomePage() {
    appium.ios_GetMobileElementUsingAccessibilityId("UICatalog").click();
    log.info("click action on back button");
  }

  public void clickOnActionSheets() {
    appium.ios_GetMobileElementUsingAccessibilityId("Action Sheets").click();
    log.info("click action on Action Sheets");
  }

  public void clickOnSearchBars() {
    appium.ios_GetMobileElementUsingAccessibilityId("Search Bars").click();
    log.info("click action on Search Bars Sheets");
  }

  public void clickOKCancelButton() {
    appium.ios_GetMobileElementUsingAccessibilityId("Okay / Cancel").click();
    log.info("click action on Okay / Cancel");
  }

  public void clickOK() {
    appium.ios_GetMobileElementUsingName("OK").click();
    log.info("click action on OK button");
  }

  public void clickCancel() {
    appium.ios_GetMobileElementUsingName("Cancel").click();
    log.info("click action on Cancel button");
  }

  public boolean validateHomePageLanding() {
    return appium.ios_isElementPresentUsingXpath("//XCUIElementTypeApplication[@name=\"UICatalog\"]", 15);
  }
}
