package com.company.project.tests.android;

import com.company.project.page.android.WebViewPage;
import com.company.project.utilities.RunOn;
import io.appium.java_client.AppiumDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/** Created by pavankovurru on 7/14/17. */
public class Webview {

  public static Logger log = LogManager.getLogger();
  static AppiumDriver driver = null;
  RunOn run_on = new RunOn();
  WebViewPage webviewpage = null;

  @BeforeClass(alwaysRun = true)
  @Parameters({"runOn", "appName"})
  public void invokeApp(String runOn, String appName) {
    driver = run_on.run(runOn, appName);
    log.info("--------------------------------------------------------------------------");
    log.info("Appium driver created for - " + runOn);
    log.info("Targeting app - " + appName);
    log.info("--------------------------------------------------------------------------");
    webviewpage = new WebViewPage(driver);
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
    webviewpage.clickViews();
    webviewpage.clickWebView();
    webviewpage.switchtoWebView();
    log.info(webviewpage.gettext());
    webviewpage.switchToNative();
  }
}
