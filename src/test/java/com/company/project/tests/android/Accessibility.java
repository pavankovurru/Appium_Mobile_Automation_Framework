package com.company.project.tests.android;

import com.company.project.page.android.AccessibilityPage;
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

/** Created by pavankovurru on 7/14/17. */
public class Accessibility {

  public static Logger log = LogManager.getLogger();
  static AppiumDriver driver = null;
  AccessibilityPage accessibilitypage = null;

  @BeforeClass(alwaysRun = true)
  @Parameters({"runOn", "appName"})
  public void invokeApp(String runOn, String appName) {
    driver = RunOn.run(runOn, appName);
    log.info("--------------------------------------------------------------------------");
    log.info("Appium driver created for - " + runOn);
    log.info("Targeting app - " + appName);
    log.info("--------------------------------------------------------------------------");
    accessibilitypage = new AccessibilityPage(driver);
  }

  @AfterClass
  public void tearDown() {
    if (driver != null) {
      driver.quit();
    }
  }

  @Test(priority = 1)
  public void validateAccessibilityLink() {
    log.info("Testing a - " + driver.getContext());
    accessibilitypage.clickAccessibility();
  }

  @Test(priority = 2)
  public void validateAccessibilitySubLinks() {
    accessibilitypage.clickAccessibilityNodeProvider();
    AppiumUtil.androidBackKeyEvent();
    accessibilitypage.clickAccessibilityNodeQuerying();
    AppiumUtil.androidBackKeyEvent();
    accessibilitypage.clickAccessibilityService();
    AppiumUtil.androidBackKeyEvent();
    accessibilitypage.clickCustomView();
    AppiumUtil.androidBackKeyEvent();
  }

  @Test(priority = 3)
  public void validateAccessibilityNodeProvider() {
    accessibilitypage.clickAccessibilityNodeProvider();

    Assert.assertEquals(
        accessibilitypage.getAccessibilityNodeProviderText(),
        "Enable TalkBack and Explore-by-touch from accessibility settings. Then touch the colored squares.");

    // TODO validate background color of this element -->
    // driver.findElementsByXPath("//android.widget.LinearLayout/android.view.View");
    // APPIUM DOES NOT ALLOW YOU TO DO THIS - 'java-client', version: '4.1.2'

  }

  @Test(priority = 4)
  public void validateAccessibilityNodeQuerying() {
    accessibilitypage.clickAccessibilityNodeQuerying();
    accessibilitypage.checkAllUncheckedTextBoxesInAccessibilityNodeQuerying();
    Assert.assertTrue(accessibilitypage.areAllCheckBoxesSelected());
    accessibilitypage.unCheckAllcheckedTextBoxesInAccessibilityNodeQuerying();
    Assert.assertFalse(accessibilitypage.areAllCheckBoxesSelected());
  }

  @Test(priority = 5)
  public void validateAccessibilityService() {
    accessibilitypage.clickAccessibilityService();
    driver.findElementById("io.appium.android.apis:id/button").click();
    Assert.assertTrue(
        AppiumUtil.androidScrollToText("Accessibility").getText().equals("Accessibility"));
  }
}
