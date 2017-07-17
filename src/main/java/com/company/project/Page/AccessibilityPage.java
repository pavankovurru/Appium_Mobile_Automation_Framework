package com.company.project.Page;

import com.company.project.utilities.AppiumUtil;
import io.appium.java_client.AppiumDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/** Created by pavankovurru on 7/17/17. */
public class AccessibilityPage {

  private static final Logger log = LogManager.getLogger();
  AppiumDriver driver;

  public AccessibilityPage(AppiumDriver driver) {
    this.driver = driver;
  }

  public void clickAccessibility() {
    AppiumUtil.androidScrollToText("Accessibility").click();
  }


  //Accessibility Node Provider
  public void clickAccessibilityNodeProvider() {
    AppiumUtil.androidScrollToText("Accessibility Node Provider").click();
  }

  public String getAccessibilityNodeProviderText() {
     return  driver.findElementByXPath("//android.widget.LinearLayout/android.widget.TextView").getText();
  }



  //Accessibility Node Querying
  public void clickAccessibilityNodeQuerying() {
    AppiumUtil.androidScrollToText("Accessibility Node Querying").click();
  }




  //Accessibility Service
  public void clickAccessibilityService() {
    AppiumUtil.androidScrollToText("Accessibility Service").click();
  }




  //Custom View
  public void clickCustomView() {
    AppiumUtil.androidScrollToText("Custom View").click();
  }
}
