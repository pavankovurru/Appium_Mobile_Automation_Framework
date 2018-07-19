package com.company.project.tests.ios;

import com.company.project.page.ios.ActionSheetPage;
import com.company.project.utilities.AppiumUtil;
import com.company.project.utilities.RunOn;
import io.appium.java_client.AppiumDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ActionSheets {

  private Logger log = LogManager.getLogger();
  AppiumDriver driver = null;
  AppiumUtil appium = null;
  RunOn run_on = new RunOn();
  ActionSheetPage actionsheetpage = null;

  @BeforeClass(alwaysRun = true)
  @Parameters({"runOn", "appName"})
  public void invokeApp(String runOn, String appName) {
    driver = run_on.run(runOn, appName);
    log.info("--------------------------------------------------------------------------");
    log.info("Appium driver created for - " + runOn);
    log.info("Targeting app - " + appName);
    log.info("--------------------------------------------------------------------------");
    appium = new AppiumUtil(driver);
    actionsheetpage = new ActionSheetPage(driver);
  }

  @AfterClass
  public void tearDown() {
    if (driver != null) {
      driver.quit();
    }
  }

  @Test(priority = 1)
  public void validateActionSheetsLink() {
    log.info("Testing a - " + driver.getContext());
    actionsheetpage.clickOnActionSheets();
    actionsheetpage.clickOKCancelButton();
    actionsheetpage.clickOK();
    actionsheetpage.clickOKCancelButton();
    actionsheetpage.clickCancel();
    actionsheetpage.clickBackButton();
    Assert.assertTrue(actionsheetpage.validatePageLanding("UICatalog"));
  }

  @Test(priority = 2)
  public void validateScrollUsingAccessibilityID() {
    appium.scroll_Ios_Up_UntilElementWithAccessibilityIDIsFound("Search Bars").click();
    Assert.assertTrue(actionsheetpage.validatePageLanding("Search Bars"));
    actionsheetpage.clickBackButton();
    Assert.assertTrue(actionsheetpage.validatePageLanding("UICatalog"));
  }
}
