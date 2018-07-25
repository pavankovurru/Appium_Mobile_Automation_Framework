package com.company.project.page.android;

import com.company.project.utilities.AppiumUtil;
import io.appium.java_client.AppiumDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Set;

/** Created by pavankovurru on 7/17/17. */
public class WebViewPage {

  private final Logger log = LogManager.getLogger();
  AppiumUtil appium = null;
  AppiumDriver driver;

  public WebViewPage(AppiumDriver driver) {
    this.driver = driver;
    appium = new AppiumUtil(driver);
  }

  public void clickViews() {
    appium.scrollToTextAndroid("Views").click();
  }

  public void clickWebView() {
    appium.scrollToTextAndroid("WebView").click();
    appium.sleep(2);
  }

  public void switchToWebView() {

    Set<String> handles = driver.getContextHandles();
    log.info("Context's available  - " + handles.size());

    for (String handle : handles) {
      log.info("Trying with context - " + handle);
      if (handle.matches(".*?WEBVIEW.*?")) {
        log.info("Trying to switch Context to - " + handle);
        driver.context(handle);
        log.info("Current context - " + driver.getContext());
        break;
      }
    }
  }

  public void switchToNative() {
    Set<String> handles = driver.getContextHandles();
    log.info("Context's available  - " + handles.size());

    for (String handle : handles) {
      log.info("Trying with context - " + handle);
      if (handle.matches("NATIVE_APP")) {
        log.info("Trying to switch Context to - " + handle);
        driver.context(handle);
        log.info("Current context - " + driver.getContext());
        break;
      }
    }
  }

  public String getText() {
    appium.sleep(5);
    return driver.findElement(By.xpath("//body/a")).getText();
  }
}
