package com.company.project.tests.ios;

import com.company.project.page.ios.ActionSheetPage;
import com.company.project.utilities.AppiumUtil;
import com.company.project.utilities.RunOn;
import io.appium.java_client.AppiumDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;

public class ActionSheets {

  private Logger log = LogManager.getLogger();
  AppiumDriver driver = null;
  AppiumUtil appium = null;
  RunOn run_on = new RunOn();
  ActionSheetPage actionsheetpage = null;

  @BeforeMethod(alwaysRun = true)
  @Parameters({"runOn", "appName", "deviceName", "udid", "bundleId"})
  public void invokeApp(
      String runOn, String appName, String deviceName, String udid, String bundleId) {
    driver = run_on.run(runOn, appName, deviceName, udid, bundleId);
    log.info("--------------------------------------------------------------------------");
    log.info("Appium driver created for - " + runOn);
    log.info("Targeting app - " + appName);
    log.info("--------------------------------------------------------------------------");
    appium = new AppiumUtil(driver);
    actionsheetpage = new ActionSheetPage(driver);
  }

  @AfterMethod(alwaysRun = true)
  public void tearDown() {
    if (driver != null) {
      driver.quit();
    }
  }

  /*
  IOS click tests
   */
  // @Test(priority = 1)
  public void validateActionSheetsLinks() {
    log.info("Testing a - " + driver.getContext());
    actionsheetpage.clickOnActionSheets();
    actionsheetpage.clickOKCancelButton();
    actionsheetpage.clickOK();
    actionsheetpage.clickOKCancelButton();
    actionsheetpage.clickCancel();
    actionsheetpage.navigateToUICatalogHomePage();
    Assert.assertTrue(actionsheetpage.validateHomePageLanding());
  }

  /*
  IOS Scroll test
   */
  @Test(priority = 2)
  public void validateScrollcAction() {
    appium.ios_scrollUntilLabelIsFound("Search Bars",70,30,10);
    actionsheetpage.clickOnSearchBars();
    actionsheetpage.navigateToUICatalogHomePage();
    Assert.assertTrue(actionsheetpage.validateHomePageLanding());
  }
}
