package com.company.project.ios.actionsheets;

import com.company.project.utilities.appium.AppiumDriverFactory;
import com.company.project.utilities.appium.AppiumUserSimulations;
import io.appium.java_client.AppiumDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;

public class ActionSheetsTests {

  private Logger log = LogManager.getLogger();
  AppiumDriver driver = null;
  AppiumUserSimulations appium = null;
  AppiumDriverFactory driverFactory = null;
  ActionSheetPage actionsheetpage = null;

  @BeforeMethod(alwaysRun = true)
  @Parameters({"runOn", "appName", "udid"})
  public void invokeApp(
      String runOn, String appName, String udid) {
    log.info("Trying to create appium driver for -" + runOn);
    driverFactory = new AppiumDriverFactory();

    if (runOn.equalsIgnoreCase("IOSSimulator")){
      driver = driverFactory.createLocalIOSDriverForSimulator(appName, udid, true);
    } else if(runOn.equalsIgnoreCase("IOSDevice")){
      driver = driverFactory.createLocalIOSDriverForPhysicalDevice(appName, udid,"", true);
    } else {
      log.info("Incorrect runOn parameter provided" + runOn);
    }

    log.info("--------------------------------------------------------------------------");
    log.info("Appium driver created for - " + runOn);
    log.info("Targeting app - " + appName);
    log.info("--------------------------------------------------------------------------");
    appium = new AppiumUserSimulations(driver);
    actionsheetpage = new ActionSheetPage(driver);
  }

  @AfterMethod(alwaysRun = true)
  public void tearDown() {
    if (driver != null) {
      driver.quit();
    }
  }

  // @Test(priority = 1)
  public void validateActionSheetsLinks() {
    actionsheetpage.clickOnActionSheets();
    actionsheetpage.clickOKCancelButton();
    actionsheetpage.clickOK();
    actionsheetpage.clickOKCancelButton();
    actionsheetpage.clickCancel();
    actionsheetpage.navigateToUICatalogHomePage();
    Assert.assertTrue(actionsheetpage.validateHomePageLanding());
  }
}
