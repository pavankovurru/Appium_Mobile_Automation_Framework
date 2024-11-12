package com.company.project.utilities.appium;

import static java.time.Duration.ofSeconds;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.*;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.SupportsContextSwitching;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
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

  public void clear(By locator) {
    log.info("Trying to clear element contents from element with locator - " + locator);
    wait_until_MobileElementIs_Clickable(driver, locator).clear();
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

  public boolean isDisplayed(By locator) {
    log.info("Trying to check if element is displayed with locator - " + locator);
    boolean isDisplayed = true;
    WebDriverWait wait = new WebDriverWait(driver, ofSeconds(10));
    wait.pollingEvery(Duration.ofMillis(50));
    try {
      wait.until(ExpectedConditions.visibilityOf(driver.findElement(locator)));
      log.info("Element found");
    } catch (Exception e) {
      isDisplayed = false;
      log.info("Element not found 😱");
    }
    return isDisplayed;
  }

  public boolean isDisplayed(By locator, int timeout) {
    log.info("Trying to check if element is displayed with locator - " + locator);
    boolean isDisplayed = true;
    WebDriverWait wait = new WebDriverWait(driver, ofSeconds(timeout));
    wait.pollingEvery(Duration.ofMillis(50));
    try {
      wait.until(ExpectedConditions.visibilityOf(driver.findElement(locator)));
      log.info("Element found");
    } catch (Exception e) {
      isDisplayed = false;
      log.info("Element not found 😱");
    }
    return isDisplayed;
  }

  public boolean isiOSPlatform(AppiumDriver driver) {
    Object platform = driver.getCapabilities().getCapability("platformName");
    return ((Platform) platform).name().equalsIgnoreCase("ios");
  }

  public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT
  }

  public void swipeUntilElementIsVisible(By locator, Direction direction, int MAX_SWIPE_ATTEMPTS) {
    int swipeAttempts = 1;
    while (swipeAttempts < MAX_SWIPE_ATTEMPTS) {
      if (isDisplayed(locator, 1)) {
        return;
      } else {
        log.info("Trying to swipe, Attempt -" + swipeAttempts);
        swipe(direction);
        swipeAttempts++;
      }
    }
    throw new RuntimeException("Element not found after swiping" + MAX_SWIPE_ATTEMPTS + " times.");
  }

  private void swipe(Direction direction) {
    int height = driver.manage().window().getSize().height;
    int width = driver.manage().window().getSize().width;

    int startX, startY, endX, endY;

    // Determine the start and end coordinates for each direction
    switch (direction) {
      case UP:
        startX = width / 2;
        startY = (int) (height * 0.8);
        endX = width / 2;
        endY = (int) (height * 0.2);
        break;

      case DOWN:
        startX = width / 2;
        startY = (int) (height * 0.2);
        endX = width / 2;
        endY = (int) (height * 0.8);
        break;

      case LEFT:
        startX = (int) (width * 0.8);
        startY = height / 2;
        endX = (int) (width * 0.2);
        endY = height / 2;
        break;

      case RIGHT:
        startX = (int) (width * 0.2);
        startY = height / 2;
        endX = (int) (width * 0.8);
        endY = height / 2;
        break;

      default:
        throw new IllegalArgumentException("Invalid swipe direction");
    }

    PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
    Sequence swipe = new Sequence(finger, 1);

    // Add actions to perform the swipe
    swipe.addAction(
        finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
    swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
    swipe.addAction(
        finger.createPointerMove(
            Duration.ofMillis(600), PointerInput.Origin.viewport(), endX, endY));
    swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

    driver.perform(Arrays.asList(swipe));
  }

  // Context Switching using executeScript
  public List<String> getAllContextsJS() {
    @SuppressWarnings("unchecked")
    List<String> contexts = (List<String>) driver.executeScript("mobile: getContexts");
    log.info("Available contexts: " + contexts);
    return contexts;
  }

  public void switchToContextJS(String context) {
    log.info("Trying to switch to context - " + context);
    driver.executeScript("mobile: setContext", ImmutableMap.of("name", context));
  }

  public String getCurrentContextJS(String context) {
    log.info("Trying to get current context");
    return (String) driver.executeScript("mobile: Get Current Context");
  }

  // Context Switching using SupportsContextSwitching
  public void switchToContext(String contextName) {
    ((SupportsContextSwitching) driver).context(contextName);
  }

  public Set<String> getAllContexts() {
    return ((SupportsContextSwitching) driver).getContextHandles();
  }

  public String getCurrentContext() {
    return ((SupportsContextSwitching) driver).getContext();
  }

  // ******* ANDROID ONLY ******** //

  // Android EXECUTE METHODS -
  // https://github.com/appium/appium-uiautomator2-driver/blob/master/docs/android-mobile-gestures.md

  public void validateSwipePercentage(int percent) {
    if (percent > 1 || percent < 0) {
      log.error("Incorrect swipe percent provided: value needs to be between 0 & 1 ");
      throw new IllegalArgumentException("Invalid percent: " + percent);
    }
  }

  public void swipeOnElementAndroid(By locator, String direction, int percent) {
    validateInputDirection(direction);
    validateSwipePercentage(percent);
    log.info("Attempting to swipe " + direction + " on an element located by: " + locator);

    try {
      ((JavascriptExecutor) driver)
          .executeScript(
              "mobile: swipeGesture",
              ImmutableMap.of(
                  "direction", direction.toString(),
                  "elementId", driver.findElement(locator).getAttribute("UID"),
                  "percent", percent));
      log.info("Swipe " + direction + " performed successfully on element: " + locator);
    } catch (NoSuchElementException e) {
      log.error("Element not found with locator: " + locator, e);
      throw e;
    } catch (Exception e) {
      log.error("Failed to perform swipe " + direction.toString() + " on element: " + locator, e);
      throw e;
    }
  }

  public void scrollToElementAndroid(By locator, String direction, int percent) {
    validateInputDirection(direction);
    validateSwipePercentage(percent);
    log.info("Attempting to swipe " + direction + " on an element located by: " + locator);

    try {
      ((JavascriptExecutor) driver)
          .executeScript(
              "mobile: scrollGesture",
              ImmutableMap.of(
                  "direction", direction.toLowerCase(),
                  "elementId", driver.findElement(locator).getAttribute("UID"),
                  "percent", percent));
      log.info("Swipe " + direction + " performed successfully on element: " + locator);
    } catch (NoSuchElementException e) {
      log.error("Element not found with locator: " + locator, e);
      throw e;
    } catch (Exception e) {
      log.error("Failed to perform swipe " + direction + " on element: " + locator, e);
      throw e;
    }
  }

  public void doubleTapOnElementAndroid(By locator) {
    try {
      driver.executeScript(
          "mobile: doubleClickGesture",
          ImmutableMap.of("elementId ", driver.findElement(locator).getAttribute("UID")));
      log.info("Double tap performed successfully on element: " + locator);
    } catch (NoSuchElementException e) {
      log.error("Element not found with locator: " + locator, e);
      throw e;
    } catch (Exception e) {
      log.error("Failed to perform double tap gesture on element : " + locator, e);
      throw e;
    }
  }

  public void pressAndHoldElementAndroid(By locator, int timeToHoldInMilliSeconds) {
    try {
      driver.executeScript(
          "mobile: longClickGesture",
          ImmutableMap.of(
              "elementId ",
              driver.findElement(locator).getAttribute("UID"),
              "duration",
              timeToHoldInMilliSeconds));
      log.info("press and hold performed successfully on element: " + locator);
    } catch (NoSuchElementException e) {
      log.error("Element not found with locator: " + locator, e);
      throw e;
    } catch (Exception e) {
      log.error("Failed to perform press and hold gesture on element : " + locator, e);
      throw e;
    }
  }

  public void tapOnElementAndroid(By locator, int xOffset, int yOffset) {
    try {
      driver.executeScript(
          "mobile: clickGesture",
          ImmutableMap.of(
              "elementId ", driver.findElement(locator).getAttribute("UID"),
              "x", xOffset,
              "y", yOffset));
      log.info("Tap action performed successfully on element: " + locator);
    } catch (NoSuchElementException e) {
      log.error("Element not found with locator: " + locator, e);
      throw e;
    } catch (Exception e) {
      log.error("Failed to perform tap action on element : " + locator, e);
      throw e;
    }
  }

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

  // Notifications Android
  public void openNotificationsAndroid() {
    log.info("Opening notifications page");
    ((AndroidDriver) driver).openNotifications();
  }

  // ************ IOS ONLY  *************** //

  // EXECUTE METHODS - https://www.youtube.com/watch?v=oAJ7jwMNFVU
  // https://github.com/appium/appium-xcuitest-driver/blob/master/docs/reference/execute-methods.md

  public void validateInputDirection(String direction) {
    Set<String> validDirections = Set.of("up", "down", "left", "right");
    if (!validDirections.contains(direction.toLowerCase())) {
      log.error(
          "Incorrect direction provided: "
              + direction
              + ". Valid directions are: "
              + validDirections);
      throw new IllegalArgumentException("Invalid direction: " + direction);
    }
  }

  public void swipeOnElementIOS(By locator, String direction) {
    validateInputDirection(direction);
    log.info("Attempting to swipe " + direction + " on an element located by: " + locator);

    try {
      WebElement element = driver.findElement(locator);
      driver.executeScript(
          "mobile: swipe",
          ImmutableMap.of(
              "direction", direction.toLowerCase(),
              "elementId", element.getAttribute("UID")));
      log.info("Swipe " + direction + " performed successfully on element: " + locator);
    } catch (NoSuchElementException e) {
      log.error("Element not found with locator: " + locator, e);
      throw e;
    } catch (Exception e) {
      log.error("Failed to perform swipe " + direction + " on element: " + locator, e);
      throw e;
    }
  }

  // NOTE : This auto scrolls to element inside a container, element has to be hittable
  public void scrollToElementIOS(By locator) {
    try {
      WebElement element = driver.findElement(locator);
      driver.executeScript(
          "mobile: scrollToElement", ImmutableMap.of("elementId", element.getAttribute("UID")));
      log.info("scroll performed successfully on element with locator : " + locator);
    } catch (NoSuchElementException e) {
      log.error("Element not found with locator: " + locator, e);
      throw e;
    } catch (Exception e) {
      log.error("Failed to perform scroll on element with locator: " + locator, e);
      throw e;
    }
  }

  public void scrollToElementWithPredicateIOS(String predicate, String direction) {
    validateInputDirection(direction);
    log.info(
        "Attempting to swipe "
            + direction
            + " on an element located by predicate String: "
            + predicate);

    try {
      WebElement element = driver.findElement(AppiumBy.iOSNsPredicateString(predicate));
      driver.executeScript(
          "mobile: scroll",
          ImmutableMap.of("direction", direction.toLowerCase(), "predicateString", element));
      log.info(
          "scroll "
              + direction
              + " performed successfully on element: by predicate String: "
              + predicate);
    } catch (NoSuchElementException e) {
      log.error("Element not found with locator with predicate: " + predicate, e);
      throw e;
    } catch (Exception e) {
      log.error(
          "Failed to perform scroll " + direction + " on element with predicate: " + predicate, e);
      throw e;
    }
  }

  public void scrollToElementWithPredicateOnContainerIOS(
      String predicate, By container, String direction) {
    validateInputDirection(direction);
    log.info(
        "Attempting to swipe "
            + direction
            + " on an element located by predicate String: "
            + predicate);

    try {
      WebElement element =
          driver.findElement(AppiumBy.iOSNsPredicateString(predicate)); // scroll to element
      WebElement containerElement = driver.findElement(container); // scroll in container

      driver.executeScript(
          "mobile: scroll",
          ImmutableMap.of(
              "direction", direction.toLowerCase(),
              "elementId ", containerElement.getAttribute("UID"),
              "predicateString", element));
      log.info(
          "scroll "
              + direction
              + " performed successfully on element: by predicate String: "
              + predicate);
    } catch (NoSuchElementException e) {
      log.error("Element not found with locator with predicate: " + predicate, e);
      throw e;
    } catch (Exception e) {
      log.error(
          "Failed to perform scroll " + direction + " on element with predicate: " + predicate, e);
      throw e;
    }
  }

  public void doubleTapOnElementIOS(By locator) {
    WebElement element = driver.findElement(locator);
    try {
      driver.executeScript(
          "mobile: doubleTap", ImmutableMap.of("elementId ", element.getAttribute("UID")));
      log.info("Double tap performed successfully on element: " + element);
    } catch (NoSuchElementException e) {
      log.error("Element not found with locator: " + element, e);
      throw e;
    } catch (Exception e) {
      log.error("Failed to perform double tap gesture on element : " + element, e);
      throw e;
    }
  }

  public void pressAndHoldElementIOS(By container, int timeToHoldInSeconds) {
    WebElement element = driver.findElement(container);
    try {
      driver.executeScript(
          "mobile: touchAndHold",
          ImmutableMap.of(
              "elementId ", element.getAttribute("UID"), "duration", timeToHoldInSeconds));
      log.info("press and hold performed successfully on element: " + element);
    } catch (NoSuchElementException e) {
      log.error("Element not found with locator: " + element, e);
      throw e;
    } catch (Exception e) {
      log.error("Failed to perform press and hold gesture on element : " + element, e);
      throw e;
    }
  }

  public void tapOnElementIOS(By locator, int xOffset, int yOffset) {
    WebElement element = driver.findElement(locator);
    try {
      driver.executeScript(
          "mobile: tap",
          ImmutableMap.of(
              "elementId ", element.getAttribute("UID"),
              "x", xOffset,
              "y", yOffset));
      log.info("Tap action performed successfully on element: " + element);
    } catch (NoSuchElementException e) {
      log.error("Element not found with locator: " + element, e);
      throw e;
    } catch (Exception e) {
      log.error("Failed to perform tap action on element : " + element, e);
      throw e;
    }
  }

  // ******* EXPLICIT WAITS ON SINGLE ELEMENT ******************//
  // ************************************************************

  // WAIT FOR MAX TIME 15 SECS TILL THE ELEMENT IS CLICKABLE - DISPLAYED AND ENABLED
  public WebElement wait_until_MobileElementIs_Clickable(WebDriver driver, By locator) {
    log.info("15 secs - Waiting for element using -" + locator);
    wait = new WebDriverWait(driver, ofSeconds(15));
    return wait.until(ExpectedConditions.elementToBeClickable(locator));
  }

  // WAIT FOR MAX TIME 15 SECS TILL THE ELEMENT IS VISIBLE
  public WebElement wait_until_MobileElementIs_Visible(WebDriver driver, By locator) {
    log.info("15 secs - Waiting for element using -" + locator);
    wait = new WebDriverWait(driver, ofSeconds(15));
    return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
  }

  // WAIT FOR MAX TIME 15 SECS TILL THE ELEMENT IS PRESENT
  public WebElement wait_until_MobileElementIs_Present(WebDriver driver, By locator) {
    log.info("15 secs - Waiting for element using -" + locator);
    wait = new WebDriverWait(driver, ofSeconds(15));
    return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
  }

  // ************* EXPLICIT WAITS ON MULTIPLE ELEMENTS ***************//
  // ************************************************************

  // WAIT FOR MAX TIME 15 SECS TILL THE ELEMENT IS PRESENT
  public List<WebElement> wait_until_MobileElementsAre_Present(WebDriver driver, By locator) {
    log.info("5 secs - Waiting for elements using -" + locator);
    wait = new WebDriverWait(driver, ofSeconds(15));
    return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
  }

  // WAIT FOR MAX TIME 5 SECS TILL THE ELEMENT IS VISIBLE
  public List<WebElement> wait_until_MobileElementsAre_Visible(WebDriver driver, By locator) {
    log.info("5 secs - Waiting for elements using -" + locator);
    wait = new WebDriverWait(driver, ofSeconds(15));
    return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
  }

  public List<WebElement> wait_until_MobileElementsAre_Visible(
      WebDriver driver, By locator, int timeInSeconds) {
    log.info("5 secs - Waiting for elements using -" + locator);
    wait = new WebDriverWait(driver, ofSeconds(timeInSeconds));
    return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
  }

  // ******** EXPLICIT WAITS ON PAGE TITLE,URL AND ELEMENT_NOT_PRESENT ************//
  public boolean is_MobileElement_NotPresent(WebDriver driver, By locator) {
    log.info("5 secs - checking for element presence using -" + locator);
    wait = new WebDriverWait(driver, ofSeconds(15));
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