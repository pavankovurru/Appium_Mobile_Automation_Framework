package com.company.project.utilities.appium;

import io.appium.java_client.*;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/** Created by pavankovurru on 1/29/17. */
public class AppiumUserSimulations {

  private static Logger log = LogManager.getLogger();
  private AppiumDriver driver;
  private String parentHandle;
  private Alert alert;
  private WebDriverWait wait;

  public AppiumUserSimulations(AppiumDriver driver) {
    this.driver = driver;
  }

  // ******* GENERIC IOS & ANDROID ******** //

  public void click(By locator) {
    log.info("Trying to click element with locator - " + locator);
    wait_until_MobileElementIs_Clickable(driver, locator).click();
  }

  public void sendKeys(By locator, String text) {
    log.info("Trying to send keys  - " + text);
    log.info("To locator - " + locator);
    wait_until_MobileElementIs_Clickable(driver, locator).sendKeys(text);
  }

  public String getText(By locator) {
    log.info("Trying to get text from element with locator - " + locator);
    return wait_until_MobileElementIs_Visible(driver, locator).getText();
  }

  public boolean isDisplayed(By locator) {
    log.info("Trying to check if element is displayed with locator - " + locator);
    try {
      return wait_until_MobileElementIs_Visible(driver, locator).isDisplayed();
    } catch (Exception e) {
      return false;
    }
  }

  public void clear(By locator) {
    log.info("Trying to clear element contents from element with locator - " + locator);
    wait_until_MobileElementIs_Clickable(driver, locator).clear();
  }

  public void tapTouchAction(By locator) {
    new TouchAction((PerformsTouchActions) driver)
        .tap(ElementOption.element(wait_until_MobileElementIs_Clickable(driver, locator)))
        .perform();
  }

  // Long Press Action
  public void longPressTouchAction(By locator, int durationInSeconds) {
    new TouchAction((PerformsTouchActions) driver)
        .longPress(ElementOption.element(wait_until_MobileElementIs_Clickable(driver, locator)))
        .release()
        .perform();
  }

  // Change Device Orientation
  public void changeDeviceOrientation(ScreenOrientation orientation) {
    log.info("Rotating screen to " + orientation + " mode");
    if (driver instanceof AndroidDriver) {
      ((AndroidDriver) driver).rotate(orientation);
    } else if (driver instanceof IOSDriver) {
      ((IOSDriver) driver).rotate(orientation);
    }
  }

  // Swipe Actions
  public void swipeHorizontal(
      double startPercentage, double endPercentage, int durationInMilliSeconds) {
    Dimension size = driver.manage().window().getSize();
    int startx = (int) (size.width * startPercentage);
    int endx = (int) (size.width * endPercentage);
    int y = size.height / 2;
    new TouchAction((PerformsTouchActions) driver)
        .press(PointOption.point(startx, y))
        .waitAction(WaitOptions.waitOptions(Duration.ofMillis(durationInMilliSeconds)))
        .moveTo(PointOption.point(endx, y))
        .release()
        .perform();
  }

  public void swipeVertical(
      double startPercentage, double endPercentage, int durationInMilliSeconds) {
    Dimension size = driver.manage().window().getSize();
    int x = size.width / 2;
    int starty = (int) (size.height * startPercentage);
    int endy = (int) (size.height * endPercentage);
    new TouchAction((PerformsTouchActions) driver)
        .press(PointOption.point(x, starty))
        .waitAction(WaitOptions.waitOptions(Duration.ofMillis(durationInMilliSeconds)))
        .moveTo(PointOption.point(x, endy))
        .release()
        .perform();
  }

  // ******* ANDROID ONLY ******** //

  // ANDROID KEY EVENTS
  public void android_HomeKeyEvent() {
    log.info("android Home event");
    ((AndroidDriver) driver)
        .pressKey(new io.appium.java_client.android.nativekey.KeyEvent(AndroidKey.HOME));
  }

  public void android_BackKeyEvent() {
    log.info("android back event");
    ((AndroidDriver) driver)
        .pressKey(new io.appium.java_client.android.nativekey.KeyEvent(AndroidKey.BACK));
  }

  public void android_Enter() {
    log.info("android Enter event");
    ((AndroidDriver) driver)
        .pressKey(new io.appium.java_client.android.nativekey.KeyEvent(AndroidKey.ENTER));
  }

  public void android_Tab() {
    log.info("android Tab event");
    ((AndroidDriver) driver)
        .pressKey(new io.appium.java_client.android.nativekey.KeyEvent(AndroidKey.TAB));
  }

  public void android_MultiTaskingKeyEvent() {
    ((AndroidDriver) driver)
        .pressKey(new io.appium.java_client.android.nativekey.KeyEvent(AndroidKey.APP_SWITCH));
  }

  // **** SCROLL FUNCTIONS (SCROLL'S ON ENTIRE PAGE) *****//
  // ************************************************************

  public WebElement android_ScrollToText(String text) {
    log.info("Trying to scroll to element with text  - " + text);

    WebElement el =
        wait_until_MobileElementIs_Visible(
            driver,
            AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector()).scrollIntoView(text(\"" + text + "\"));"));
    return el;
  }

  public WebElement android_ScrollToContentDesc(String contentDesc) {
    log.info("Trying to scroll to element with content desc - " + contentDesc);
    WebElement el =
        wait_until_MobileElementIs_Visible(
            driver,
            AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector()).scrollIntoView(description(\""
                    + contentDesc
                    + "\"));"));
    return el;
  }

  public WebElement android_scrollToID(String id) {
    log.info("Trying to scroll to android element with ID - " + id);

    WebElement el =
        wait_until_MobileElementIs_Visible(
            driver,
            AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector()).scrollIntoView(resourceId(\"" + id + "\"));"));
    return el;
  }

  // **** SCROLL FUNCTIONS (SCROLL'S INSIDE PARTICULAR ELEMENT) *****//
  // Other Supported Functions available here -
  // https://developer.android.com/reference/android/support/test/uiautomator/UiSelector

  public WebElement android_scrollToTextInsideElementWithResourceID(
      String resourceID, String text) {
    log.info("Trying to scroll to android element with text - " + text);
    // make sure u give the resouce ID of the complete list of elements here as parameter

    WebElement el =
        wait_until_MobileElementIs_Visible(
            driver,
            AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector()"
                    + ".resourceId(\""
                    + resourceID
                    + "\")).scrollIntoView("
                    + "new UiSelector().text(\""
                    + text
                    + "\"));"));
    return el;
  }

  public WebElement android_scrollToTextInsideElementWithContentDesc(
      String contentDesc, String text) {
    log.info("Trying to scroll to android element with contentDesc - " + contentDesc);
    // make sure u give the resouce ID of the complete list of elements here as parameter

    WebElement el =
        wait_until_MobileElementIs_Visible(
            driver,
            AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector()"
                    + ".description(\""
                    + contentDesc
                    + "\")).scrollIntoView("
                    + "new UiSelector().text(\""
                    + text
                    + "\"));"));
    return el;
  }

  public WebElement android_scrollToTextInsideElementWithID(String id, String text) {
    log.info("Trying to scroll to android element with ID - " + id);
    // make sure u give the resouce ID of the complete list of elements here as parameter

    WebElement el =
        wait_until_MobileElementIs_Visible(
            driver,
            AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector()"
                    + ".resourceId(\""
                    + id
                    + "\")).scrollIntoView("
                    + "new UiSelector().text(\""
                    + text
                    + "\"));"));
    return el;
  }

  // Notifications Android
  public void openNotificationsAndroid() {
    log.info("Opening notifications page");
    ((AndroidDriver) driver).openNotifications();
  }

  public void clearNotificationsAndroid() {
    // Attempt to find a "Clear all" button by various possible text labels
    List<String> clearAllLabels =
        Arrays.asList("CLEAR ALL", "Clear all", "clear all", "Dismiss all");
    for (String label : clearAllLabels) {
      if (android_isMobileElementPresentUsingText(label)) {
        wait_until_MobileElementIs_Clickable(
                driver, AppiumBy.androidUIAutomator("new UiSelector().text(\"" + label + "\")"))
            .click();
        log.info("Existing notifications Cleared");
        return; // Exit the method after successfully clearing
      }
    }

    // If no "Clear all" button found, try swiping down to reveal more options
    Dimension size = driver.manage().window().getSize();
    int startX = size.width / 2;
    int startY = 0; // Start from the very top
    int endY = size.height / 2; // Swipe halfway down the screen

    new TouchAction((PerformsTouchActions) driver)
        .press(PointOption.point(startX, startY))
        .waitAction(WaitOptions.waitOptions(Duration.ofMillis(500))) // Adjust duration as needed
        .moveTo(PointOption.point(startX, endY))
        .release()
        .perform();

    // Retry finding a "Clear all" button after the swipe
    for (String label : clearAllLabels) {
      if (android_isMobileElementPresentUsingText(label)) {
        wait_until_MobileElementIs_Clickable(
                driver, AppiumBy.androidUIAutomator("new UiSelector().text(\"" + label + "\")"))
            .click();
        log.info("Existing notifications Cleared");
        return;
      }
    }
    log.info("Could not find a clear notifications option. Tried multiple labels and swiping.");
  }

  // ********** CHECK FOR PRESENCE OF MOBILE ELEMENT ANDROID **************//
  // ************************************************************

  public boolean android_isMobileElementPresentUsingText(String text) {

    try {
      log.info("Trying to find element with text - " + text);
      if (!wait_until_MobileElementsAre_Visible(
              driver, (AppiumBy.androidUIAutomator("new UiSelector().text(\"" + text + "\")")), 5)
          .isEmpty()) {
        log.info("element found");
      }
    } catch (Exception e) {
      log.info("element not found");
      return false;
    }
    return true;
  }

  public boolean android_isMobileElementPresentUsingID(String id) {
    log.info("Trying to find element with ID - " + id);

    try {
      if (!wait_until_MobileElementsAre_Visible(
              driver,
              (AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"" + id + "\")")),
              5)
          .isEmpty()) {
        log.info("Found element having id -" + id);
      }
    } catch (Exception e) {
      log.info("element not found");
      return false;
    }
    return true;
  }

  public boolean android_isMobileElementPresentUsingContentDesc(String contentDesc) {
    try {
      log.info("Trying to locate element using content-desc - " + contentDesc);
      if (!wait_until_MobileElementsAre_Visible(
              driver,
              (AppiumBy.androidUIAutomator(
                  "new UiSelector().description(\"" + contentDesc + "\")")),
              5)
          .isEmpty()) {
        log.info("Found element having content desc - " + contentDesc);
      }
    } catch (Exception e) {
      log.info("element not found");
      return false;
    }
    return true;
  }

  public boolean android_isMobileElementPresentUsingXpath(String xPath) {
    try {
      log.info("Trying to locate element using content-desc - " + xPath);
      if (wait_until_MobileElementsAre_Visible(driver, (AppiumBy.xpath(xPath)), 5).size() >= 1) {
        log.info("Found element having content desc - " + xPath);
      }
    } catch (Exception e) {
      log.info("element not found");
      return false;
    }
    return false;
  }

  // ********** SCROLL AND CHECK FOR PRESENCE OF MOBILE ELEMENT ANDROID **************//
  // ************************************************************

  public boolean android_isMobileElementPresentUsingTextAfterScroll(String text) {
    log.info("Trying to find element with text - " + text);
    if (!wait_until_MobileElementsAre_Present(
            driver,
            (AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector()).scrollIntoView(text(\"" + text + "\"));")))
        .isEmpty()) {
      log.info("element found");
      return true;
    } else {
      log.info("element not found");
      return false;
    }
  }

  public boolean android_isMobileElementPresentUsingIDAfterScroll(String id) {
    log.info("Trying to find element with ID - " + id);
    if (!wait_until_MobileElementsAre_Present(
            driver,
            (AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector()).scrollIntoView(resourceId(\"" + id + "\"));")))
        .isEmpty()) return true;
    else return false;
  }

  public boolean android_isMobileElementPresentUsingContentDescAfterScroll(String contentDesc) {
    log.info("Trying to locate element using content-desc - " + contentDesc);
    if (!wait_until_MobileElementsAre_Present(
            driver,
            (AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector()).scrollIntoView(description(\""
                    + contentDesc
                    + "\"));")))
        .isEmpty()) {
      log.info("Found element having content desc -" + contentDesc);
      return true;
    } else return false;
  }

  // GET MOBILE ELEMENT ANDROID
  public WebElement androidGetMobileElementPresentUsingText(String text) {
    log.info("Trying to find element with text - " + text);
    return wait_until_MobileElementIs_Visible(
        driver, AppiumBy.androidUIAutomator("new UiSelector().text(\"" + text + "\")"));
  }

  public WebElement androidGetMobileElementPresentUsingID(String id) {
    log.info("Trying to find element with ID - " + id);
    return wait_until_MobileElementIs_Visible(
        driver, AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"" + id + "\")"));
  }

  public WebElement androidGetMobileElementPresentUsingContentDesc(String contentDesc) {
    log.info("Trying to locate element using content-desc - " + contentDesc);
    return wait_until_MobileElementIs_Visible(
        driver,
        AppiumBy.androidUIAutomator("new UiSelector().description(\"" + contentDesc + "\")"));
  }

  public WebElement androidGetMobileElementPresentUsingXPath(String xpath) {
    log.info("Trying to locate element using x-path - " + xpath);
    return wait_until_MobileElementIs_Visible(driver, AppiumBy.xpath(xpath));
  }

  public WebElement androidGetMobileElementPresentUsingClassName(String className) {
    log.info("Trying to locate elements using className - " + className);
    return wait_until_MobileElementIs_Visible(driver, AppiumBy.className(className));
  }

  // GET MOBILE ELEMENTS ANDROID
  public List<WebElement> androidGetMobileElementsPresentUsingXPath(String xpath) {
    log.info("Trying to locate elements using x-path - " + xpath);
    return wait_until_MobileElementsAre_Visible(driver, AppiumBy.xpath(xpath));
  }

  public List<WebElement> androidGetMobileElementsPresentUsingClassName(String className) {
    log.info("Trying to locate elements using className - " + className);
    return wait_until_MobileElementsAre_Visible(driver, AppiumBy.className(className));
  }

  // ***************************************** IOS ONLY
  // ************************************************ //

  // Get MOBILE ELEMENT
  public WebElement ios_GetMobileElementUsingAccessibilityId(String accessibilityId) {
    log.info("Trying to find element with id - " + accessibilityId);
    return wait_until_MobileElementIs_Visible(driver, AppiumBy.accessibilityId(accessibilityId));
  }

  public WebElement ios_GetMobileElementUsingName(String name) {
    log.info("Trying to find element with name - " + name);
    return wait_until_MobileElementIs_Visible(driver, AppiumBy.name(name));
  }

  public WebElement ios_GetMobileElementUsingClassName(String className) {
    log.info("Trying to find element with class name - " + className);
    return wait_until_MobileElementIs_Visible(driver, AppiumBy.className(className));
  }

  public WebElement ios_GetMobileElementUsingXpath(String xPath) {
    log.info("Trying to find element with xPath - " + xPath);
    return wait_until_MobileElementIs_Visible(driver, AppiumBy.xpath(xPath));
  }

  public WebElement ios_GetMobileElementUsingPredicateString(String predicateString) {
    log.info("Trying to find element with predicateString - " + predicateString);
    return wait_until_MobileElementIs_Visible(
        driver, AppiumBy.iOSNsPredicateString(predicateString));
  }

  public WebElement ios_GetMobileElementUsingClassChain(String classChain) {
    log.info("Trying to find element with classChain - " + classChain);
    return wait_until_MobileElementIs_Visible(driver, AppiumBy.iOSClassChain(classChain));
  }

  // ***** PRESENCE OF MOBILE ELEMENT ****** //
  public boolean ios_isElementPresentUsingAccessibilityId(
      String acessibilityId, int timeinSeconds) {
    try {
      log.info("Trying to find element with AcessibilityId - " + acessibilityId);
      if (!wait_until_MobileElementsAre_Visible(
              driver, (AppiumBy.accessibilityId(acessibilityId)), timeinSeconds)
          .isEmpty()) {
        log.info("element found");
        return true;
      }
    } catch (Exception e) {
      log.info("element not found");
      return false;
    }
    return false;
  }

  public boolean ios_isElementPresentUsingName(String name, int timeinSeconds) {
    try {
      log.info("Trying to find element with name - " + name);
      if (!wait_until_MobileElementsAre_Visible(driver, (AppiumBy.name(name)), timeinSeconds)
          .isEmpty()) {
        log.info("element found");
        return true;
      }
    } catch (Exception e) {
      log.info("element not found");
      return false;
    }
    return false;
  }

  public boolean ios_isElementPresentUsingXpath(String xPath, int timeinSeconds) {
    try {
      log.info("Trying to find element with xPath - " + xPath);
      if (!wait_until_MobileElementsAre_Visible(driver, (AppiumBy.xpath(xPath)), timeinSeconds)
          .isEmpty()) {
        log.info("element found");
        return true;
      }
    } catch (Exception e) {
      log.info("element not found");
      return false;
    }
    return false;
  }

  public boolean ios_isElementPresentUsingPredicateString(
      String predicateString, int timeinSeconds) {

    try {
      log.info("Trying to find element with predicateString - " + predicateString);
      if (!wait_until_MobileElementsAre_Visible(
              driver, (AppiumBy.iOSNsPredicateString(predicateString)), timeinSeconds)
          .isEmpty()) {
        log.info("element found");
        return true;
      }
    } catch (Exception e) {
      log.info("element not found");
      return false;
    }
    return false;
  }

  public boolean ios_isElementPresentUsingClassChain(String classChain, int timeinSeconds) {
    try {
      log.info("Trying to find element with classChain - " + classChain);
      if (!wait_until_MobileElementsAre_Visible(
              driver, (AppiumBy.iOSClassChain(classChain)), timeinSeconds)
          .isEmpty()) {
        log.info("element found");
        return true;
      }
    } catch (Exception e) {
      log.info("element not found");
      return false;
    }
    return false;
  }

  // ******* EXPLICIT WAITS ON SINGLE ELEMENT ******************//
  // ************************************************************

  // WAIT FOR MAX TIME 15 SECS TILL THE ELEMENT IS CLICKABLE - DISPLAYED AND ENABLED
  public WebElement wait_until_MobileElementIs_Clickable(WebDriver driver, By locator) {
    log.info("15 secs - Waiting for element using -" + locator);
    wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    return wait.until(ExpectedConditions.elementToBeClickable(locator));
  }

  // WAIT FOR MAX TIME 15 SECS TILL THE ELEMENT IS VISIBLE
  public WebElement wait_until_MobileElementIs_Visible(WebDriver driver, By locator) {
    log.info("15 secs - Waiting for element using -" + locator);
    wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
  }

  // WAIT FOR MAX TIME 15 SECS TILL THE ELEMENT IS PRESENT
  public WebElement wait_until_MobileElementIs_Present(WebDriver driver, By locator) {
    log.info("15 secs - Waiting for element using -" + locator);
    wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
  }

  // ************* EXPLICIT WAITS ON MULTIPLE ELEMENTS ***************//
  // ************************************************************

  // WAIT FOR MAX TIME 15 SECS TILL THE ELEMENT IS PRESENT
  public List<WebElement> wait_until_MobileElementsAre_Present(WebDriver driver, By locator) {
    log.info("5 secs - Waiting for elements using -" + locator);
    wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
  }

  // WAIT FOR MAX TIME 5 SECS TILL THE ELEMENT IS VISIBLE
  public List<WebElement> wait_until_MobileElementsAre_Visible(WebDriver driver, By locator) {
    log.info("5 secs - Waiting for elements using -" + locator);
    wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
  }

  public List<WebElement> wait_until_MobileElementsAre_Visible(
      WebDriver driver, By locator, int timeInSeconds) {
    log.info("5 secs - Waiting for elements using -" + locator);
    wait = new WebDriverWait(driver, Duration.ofSeconds(timeInSeconds));
    return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
  }

  // ******** EXPLICIT WAITS ON PAGE TITLE,URL AND ELEMENT_NOT_PRESENT ************//
  public boolean is_MobileElement_NotPresent(WebDriver driver, By locator) {
    log.info("5 secs - checking for element presence using -" + locator);
    wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
  }

  // EXTRAS
  public String generateUniqueStringLarge() {
    // Get the current date and time
    LocalDateTime now = LocalDateTime.now();

    // Format the date and time to a readable string
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
    String dateTimeString = now.format(formatter);

    // Generate a random UUID
    String uuid = UUID.randomUUID().toString().replace("-", "");

    // Combine date-time string with the UUID
    return dateTimeString + "_" + uuid;
  }

  public String generateUniqueString() {
    return UUID.randomUUID().toString().replace("-", "");
  }
}
