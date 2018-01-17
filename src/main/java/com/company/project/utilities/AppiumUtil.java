package com.company.project.utilities;

import io.appium.java_client.*;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by pavankovurru on 1/29/17.
 */

public class AppiumUtil {

  public static final Logger LOG = LogManager.getLogger();
  //  static PropertiesLoader environmentProperties =
  //      new PropertiesLoader(GlobalConstants.ENVIRONMENT_PROPERTY_PATH);
  public static AppiumDriver driver;
  static String parentHandle;
  static Alert alert;
  static WebDriverWait wait;
  static DesiredCapabilities cap;

  // ******** LOCAL RUN ********//

  // Android

  public static AppiumDriver createLocalAndroidDriver_For_Emulator(String appName) {

    cap = new DesiredCapabilities();
    cap.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
    cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
    cap.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
    cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "60");
    cap.setCapability(MobileCapabilityType.APP, appName);

    try {
      driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), cap);
      LOG.info("Android Driver object created for emulator");
      // driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
      return driver;
    } catch (MalformedURLException ex) {
      ex.printStackTrace();
    }
    return driver;
  }

  public static AppiumDriver createLocalAndroidDriver_For_RealDevice(String appName) {

    cap = new DesiredCapabilities();
    cap.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
    cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
    cap.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Device");
    cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "60");
    cap.setCapability(MobileCapabilityType.APP, appName);

    try {
      driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), cap);
      LOG.info("Android Driver object created for Real android Device");
      // driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
      return driver;
    } catch (MalformedURLException ex) {
      ex.printStackTrace();
    }

    return driver;
  }

  public static AppiumDriver createLocalAndroidDriver_For_RealDeviceUsing_AppPackageActivity(
          String appName, String appPackage, String appActivity, String udid) {

    cap = new DesiredCapabilities();
    cap.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
    cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
    cap.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Device");
    cap.setCapability(MobileCapabilityType.UDID,udid);
//    cap.setCapability("systemPort", port);
    cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "60");
    cap.setCapability("appPackage", appPackage);
    cap.setCapability("appActivity", appActivity);
    //cap.setCapability("app", appName);


    try {
      driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), cap);
      LOG.info("Android Driver object created for Real android Device");
      // driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
      return driver;
    } catch (MalformedURLException ex) {
      ex.printStackTrace();
    }

    return driver;
  }

  public static AppiumDriver createLocalAndroidDriver_For_WebApp_In_Emulator(String browserName) {

    cap = new DesiredCapabilities();
    cap.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
    cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
    cap.setCapability(MobileCapabilityType.BROWSER_NAME, browserName);
    cap.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
    cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "60");

    try {
      driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), cap);
      LOG.info("Android Driver object created for Web App in  android Emulator");
      // driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
      return driver;
    } catch (MalformedURLException ex) {
      ex.printStackTrace();
    }

    return driver;
  }

  public static AppiumDriver createLocalAndroidDriver_For_WebApp_In_RealDevice(String browserName) {

    cap = new DesiredCapabilities();
    cap.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
    cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
    cap.setCapability(MobileCapabilityType.BROWSER_NAME, browserName);
    cap.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Device");
    cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "60");

    try {
      driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), cap);
      LOG.info("Android Driver object created for Web App in  android Device");
      // driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
      return driver;
    } catch (MalformedURLException ex) {
      ex.printStackTrace();
    }

    return driver;
  }

  // ******** IOS **********//

  public static AppiumDriver createLocalIOSDriver_For_NativeApp_In_Simulator(String appName) {

    cap = new DesiredCapabilities();
    cap.setCapability(MobileCapabilityType.BROWSER_NAME, "");
    cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
    cap.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
    cap.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone Simulator");
    cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "60");
    cap.setCapability(IOSMobileCapabilityType.LAUNCH_TIMEOUT, 500000);
    cap.setCapability(MobileCapabilityType.APP, appName);

    try {
      driver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), cap);
      LOG.info("IOS Driver object created for Simulator");
      // driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
      return driver;
    } catch (MalformedURLException ex) {
      ex.printStackTrace();
    }
    return driver;
  }

  public static AppiumDriver createLocalIOSDriver_For_NativeApp_In_IOSDEVICE(
          String appName, String deviceName, String udid, String bundleID) {

    cap = new DesiredCapabilities();
    // cap.setCapability(MobileCapabilityType.BROWSER_NAME, "");
    cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
    cap.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
    cap.setCapability(MobileCapabilityType.APP, appName);
    cap.setCapability("xcodeOrgId", "84CK77G588");
    cap.setCapability("xcodeSigningId", "iPhone Developer");
    cap.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
    cap.setCapability(MobileCapabilityType.UDID, udid);
    cap.setCapability("updatedWDABundleId", bundleID);

    try {
      driver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), cap);
      LOG.info("IOS Driver object created for IOS DEVICE");
      // driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
      return driver;
    } catch (MalformedURLException ex) {
      ex.printStackTrace();
    }
    return driver;
  }

  public static AppiumDriver createLocalIOSDriver_For_WebApp_In_Simulator(String browserName) {

    cap = new DesiredCapabilities();
    cap.setCapability(MobileCapabilityType.BROWSER_NAME, browserName);
    cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
    cap.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
    cap.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone Simulator");
    cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "60");
    cap.setCapability(IOSMobileCapabilityType.LAUNCH_TIMEOUT, 500000);

    try {
      driver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), cap);
      LOG.info("IOS Driver object created for Simulator");
      // driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
      return driver;
    } catch (MalformedURLException ex) {
      ex.printStackTrace();
    }
    return driver;
  }

  public static AppiumDriver createLocalIOSDriver_For_WebApp_In_IOSDEVICE(
          String browserName, String deviceName, String orgID) {

    cap = new DesiredCapabilities();
    cap.setCapability(MobileCapabilityType.BROWSER_NAME, browserName);
    cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
    cap.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
    cap.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
    cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "60");
    cap.setCapability(IOSMobileCapabilityType.LAUNCH_TIMEOUT, 500000);
    cap.setCapability("xcodeOrgId", orgID);
    cap.setCapability("xcodeSigningId", "iPhone Developer");

    try {
      driver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), cap);
      LOG.info("IOS Driver object created for IOS DEVICE");
      // driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
      return driver;
    } catch (MalformedURLException ex) {
      ex.printStackTrace();
    }
    return driver;
  }

  // **** REMOTE RUN (BROWSER-STACK ,SAUCE-LABS , AWS ...ETC ) ****//
  // *** AWS DEVICE FARM RUN ***//

  public static AppiumDriver createAndroidDriver_AWS_Device_Farm() {
    DesiredCapabilities capabilities = new DesiredCapabilities();
    URL url = null;
    try {
      url = new URL("http://127.0.0.1:4723/wd/hub");
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    LOG.info("Android Driver created for AWS Device farm");

    driver = new AndroidDriver<MobileElement>(url, capabilities);

    // Use a higher value if your mobile elements take time to show up
    // driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

    return driver;
  }

  // ******* UTILITIES FUNCTIONS *********//
  // ****** APPIUM FUNCTIONS ************//

  // open notifications Android
  public static void openNotificationsAndroid() {
    LOG.info("Trying to open notifications");
    ((AndroidDriver) driver).openNotifications();
  }

  // clear notifications
  public static void clearNotificationsAndroid() {

    // google pixel related
    if (isMobileElementPresentUsingContentDescAndroid("Clear all notifications.")) {
      driver
              .findElement(
                      MobileBy.AndroidUIAutomator(
                              "new UiSelector().description(\"Clear all notifications.\")"))
              .click();
      LOG.info("Existing notifications Cleared");

    } else {
      LOG.info("Could not find clear existing notifications option in the notifications page");
    }

    // TODO update clear notifications present
  }

  // Set location Android
  public static void setLocationAndroid(double latitude, double longitude) {
    LOG.info("Trying to mock location --> Lat:" + latitude + " Long: " + longitude);
    Location loc = new Location(latitude, longitude, 1000); // latitude, longitude, altitude
    driver.setLocation(loc);
    LOG.info("Location set to --> Latitude: " + latitude + " Longitude: " + longitude);
  }

  // presence of mobile element

  public static boolean isMobileElementPresentUsingTextAndroid(String text) {
    LOG.info("Trying to find element with text - " + text);
    if (driver
            .findElements(MobileBy.AndroidUIAutomator("new UiSelector().text(\"" + text + "\")"))
            .size()
            >= 1) {
      LOG.info("element found");
      return true;
    }
    else {
      LOG.info("element not found");
      return false;
    }
  }

  public static boolean isMobileElementPresentUsingIDAndroid(String id) {
    LOG.info("Trying to find element with ID - " + id);

    if (driver
            .findElements(
                    MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"" + id + "\")"))
            .size()
            >= 1) {
      LOG.info("Found element having id -" + id);
      return true;
    }
    else return false;
  }

  public static boolean isMobileElementPresentUsingContentDescAndroid(String contentDesc) {
    LOG.info("Trying to locate element using content-desc - " + contentDesc);
    if (driver
            .findElements(
                    MobileBy.AndroidUIAutomator(
                            "new UiSelector().description(\"" + contentDesc + "\")"))
            .size()
            >= 1) {
      LOG.info("Found element having content desc -" + contentDesc);
      return true;
    } else return false;
  }

  public static boolean isMobileElementPresentUsingText_ScrollAndroid(String text) {
    LOG.info("Trying to find element with text - " + text);
    if (driver
            .findElements(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"" + text + "\"));"))
            .size()
            >= 1) {
      LOG.info("element found");
      return true;
    }
    else {
      LOG.info("element not found");
      return false;
    }
  }

  public static boolean isMobileElementPresentUsingID_ScrollAndroid(String id) {
    LOG.info("Trying to find element with ID - " + id);
    if (driver
            .findElements(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(resourceId(\"" + id + "\"));"))
            .size()
            >= 1) return true;
    else return false;
  }

  public static boolean isMobileElementPresentUsingContentDesc_ScrollAndroid(String contentDesc) {
    LOG.info("Trying to locate element using content-desc - " + contentDesc);
    if (driver.findElements(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(description(\"" + contentDesc + "\"));"))
            .size()
            >= 1) {
      LOG.info("Found element having content desc -" + contentDesc);
      return true;
    } else return false;
  }

  public static Set<String> switchToWebView() {
    LOG.info("Trying to switch to webview");
    Set<String> availableContexts = driver.getContextHandles();
    LOG.info("No of Contexts Found  = " + availableContexts.size());
    for (String context : availableContexts) {
      LOG.info("Context - " + context);
      if (context.matches(".*?WEBVIEW.*?")) {
        LOG.info("Switching to context " + context);
        driver.context(context);
        LOG.info("Context switched to -" + driver.getContext());
        break;
      }
    }
    return availableContexts;
  }

  public static void switchToNativeContext(Set<String> availableContexts) {
    for (String context : availableContexts) {
      if (context.contains("NATIVE")) {
        LOG.info("Trying to switch to native context");
        driver.context(context);
        LOG.info("Switched to Context" + context);
      }
    }
  }

  // **** SCROLL FUNCTIONS *****//

  public static MobileElement scrollAndroid(String resourceID, String text) {
    LOG.info("Trying to scroll to android element with text - " + text);
    // make sure u give the resouce ID of the complete list of elements here as parameter

    MobileElement el =
            (MobileElement)
                    driver.findElement(
                            MobileBy.AndroidUIAutomator(
                                    "new UiScrollable(new UiSelector()"
                                            + ".resourceId(\""
                                            + resourceID
                                            + "\")).scrollIntoView("
                                            + "new UiSelector().text(\""
                                            + text
                                            + "\"));"));
    return el;
  }

  public static MobileElement scrollAndroidUsingindex(String resourceID, int index) {
    LOG.info("Trying to scroll to android element with index - " + index);

    // make sure u give the resouce ID of the complete list of elements here as parameter

    MobileElement el =
            (MobileElement)
                    driver.findElement(
                            MobileBy.AndroidUIAutomator(
                                    "new UiScrollable(new UiSelector()"
                                            + ".resourceId(\""
                                            + resourceID
                                            + "\")).scrollIntoView("
                                            + "new UiSelector().index(\""
                                            + index
                                            + "\"));"));
    return el;
  }

  public static MobileElement androidScrollToText(String text) {
    LOG.info("Trying to scroll to element with text  - " + text);

    MobileElement el =
            (MobileElement)
                    driver.findElement(
                            MobileBy.AndroidUIAutomator(
                                    "new UiScrollable(new UiSelector()).scrollIntoView(text(\"" + text + "\"));"));
    return el;
  }

  public static MobileElement androidScrollToContentDesc(String contentDesc) {
    LOG.info("Trying to scroll to element with content desc - " + contentDesc);
    MobileElement el =
            (MobileElement)
                    driver.findElement(
                            MobileBy.AndroidUIAutomator(
                                    "new UiScrollable(new UiSelector()).scrollIntoView(description(\""
                                            + contentDesc
                                            + "\"));"));
    return el;
  }

  public static MobileElement androidScrollToID(String id) {
    LOG.info("Trying to scroll to android element with ID - " + id);

    MobileElement el =
            (MobileElement)
                    driver.findElement(
                            MobileBy.AndroidUIAutomator(
                                    "new UiScrollable(new UiSelector()).scrollIntoView(resourceId(\""
                                            + id
                                            + "\"));"));
    return el;
  }



  // ANDROID KEY EVENT FUNCTIONS

  public static void androidHomeKeyEvent() {
    ((AndroidDriver) driver).pressKeyCode(AndroidKeyCode.HOME);
    AppiumUtil.sleep(2);
  }

  public static void androidBackKeyEvent() {
    LOG.info("android back event");
    ((AndroidDriver) driver).pressKeyCode(AndroidKeyCode.BACK);
    AppiumUtil.sleep(2);
  }

  public static void androidEnter() {
    LOG.info("android Enter event");
    ((AndroidDriver) driver).pressKeyCode(AndroidKeyCode.ENTER);
    AppiumUtil.sleep(2);
  }

  public static void androidtab() {
    LOG.info("android Tab event");
    // ((AndroidDriver) driver).pressKeyCode(AndroidKeyCode.KEYCODE_DPAD_DOWN);
    ((AndroidDriver) driver).pressKeyCode(AndroidKeyCode.KEYCODE_TAB);

    AppiumUtil.sleep(2);
  }

  public static void androidMultiTaskingKeyEvent() {
    ((AndroidDriver) driver).pressKeyCode(AndroidKeyCode.KEYCODE_APP_SWITCH);
    AppiumUtil.sleep(2);
  }

  // ********** TOUCH ACTIONS **************//

  public static void basicSwipe(WebElement element1, WebElement element2) {
    TouchAction touch = new TouchAction(driver);
    touch.press(element1).moveTo(element2).release().perform();
  }

  public static void dragDrop(WebElement element1, WebElement element2) {
    TouchAction touch = new TouchAction(driver);
    touch.longPress(element1).moveTo(element2).release().perform();
  }

  public static void pressAndHold(WebElement element1, int timeInSeconds) {
    TouchAction touch = new TouchAction(driver);
    touch.longPress(element1, Duration.ofSeconds(timeInSeconds)).release().perform();
  }

  public static void press_Hold_Moveto_NewElement(WebElement element1, WebElement element2) {
    TouchAction touch = new TouchAction(driver);
    touch.press(element1).waitAction(Duration.ofSeconds(2)).moveTo(element2).release().perform();
  }

  public static void tap(WebElement element) {
    TouchAction touch = new TouchAction(driver);
    touch.moveTo(element).tap(element).perform();
  }

  // ***** IOS FUNCTIONS ****** //

  public static boolean isElementPresentUsingAccessibilityId(String id){
    return driver.findElementsByAccessibilityId(id).size()>=1;
  }

  public static boolean isElementPresentUsingName(String name){
    return driver.findElementsByName(name).size()>=1;
  }

  public static boolean isElementPresentUsingXPath(String xpath){
    return driver.findElementsByXPath(xpath).size()>=1;
  }



  public static void scrollDown_InIosUsingLabel( String label) {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    HashMap scrollObject = new HashMap();
    scrollObject.put("direction", "down");
    scrollObject.put("predicateString", "label == '"+label+"'");
    js.executeScript("mobile: scroll", scrollObject);
  }

  public static void scrollUp_InIosUsingLabel( String label) {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    HashMap scrollObject = new HashMap();
    scrollObject.put("direction", "up");
    scrollObject.put("predicateString", "label == '"+label+"'");
    js.executeScript("mobile: scroll", scrollObject);
  }

  public static void scrollDown_InIosUsingElement(  RemoteWebElement element) {
    String elementID = element.getId();
    JavascriptExecutor js = (JavascriptExecutor) driver;
    HashMap scrollObject = new HashMap();
    scrollObject.put("element", elementID); // Only for ‘scroll in element’
    scrollObject.put("direction", "down");
    js.executeScript("mobile: scroll", scrollObject);
  }

  public static void scrollUp_InIosUsingElement(  RemoteWebElement element) {
    String elementID = element.getId();
    JavascriptExecutor js = (JavascriptExecutor) driver;
    HashMap scrollObject = new HashMap();
    scrollObject.put("element", elementID); // Only for ‘scroll in element’
    scrollObject.put("direction", "up");
    js.executeScript("mobile: scroll", scrollObject);
  }


  public static WebElement scroll_Ios_Up_UntilElementWithAccessibilityIDIsFound(String accessibilityID) {
    Boolean cont = true;
    WebElement el = null;

    while (cont) {
      try {
        el = driver.findElementByAccessibilityId(accessibilityID);
        if (el.isDisplayed()) {
          LOG.info("element with accessibilityID " + accessibilityID + " is Visible");
          return el;
        } else scrollIOS_up();
      } catch (NoSuchElementException e) {
        scrollIOS_up();
      }
    }
    return el;
  }

  public static WebElement scroll_Ios_Down_UntilElementWithAccessibilityIDIsFound(String accessibilityID) {
    Boolean cont = true;
    WebElement el = null;

    while (cont) {
      try {
        el = driver.findElementByAccessibilityId(accessibilityID);
        if (el.isDisplayed()) {
          LOG.info("element with accessibilityID " + accessibilityID + " is Visible");
          return el;
        } else scrollIOS_down();
      } catch (NoSuchElementException e) {
        scrollIOS_up();
      }
    }
    return el;
  }


  public static void scrollIOS_up() {
    Dimension size = driver.manage().window().getSize();
    int x = size.getWidth() / 2;
    int start_y = (int) (size.getHeight() * 0.60);
    int end_y = (int) (size.getHeight() * 0.30);
    new TouchAction(driver).press(x,start_y).moveTo(x,end_y).release().perform();
  }

  public static void scrollIOS_down() {
    Dimension size = driver.manage().window().getSize();
    int x = size.getWidth() / 2;
    int start_y = (int) (size.getHeight() * 0.30);
    int end_y = (int) (size.getHeight() * 0.60);
    new TouchAction(driver).press(x,start_y).moveTo(x,end_y).release().perform();
  }


  // Swiping element

  public static void swipeElementToTheLeft(WebElement el) {

    LOG.info("Trying to swipe element to the left");
    int x = el.getLocation().getX();
    int y = el.getLocation().getY();
    // elements x location is the beginning point of the element , multiplying with 4 to get to the
    // middle part of the element
    new TouchAction(driver)
            .press(x * 4, y)
            .waitAction(Duration.ofSeconds(2))
            .moveTo(x, y)
            .release()
            .perform();
    LOG.info("Element swipe to the left performed");
  }

  public static void swipeElementToTheRight(WebElement el) {

    LOG.info("Trying to swipe element to the left");
    int x = el.getLocation().getX();
    int y = el.getLocation().getY();
    // elements x location is the beginning point of the element , multiplying with 4 to get to the
    // middle part of the element
    new TouchAction(driver)
            .press(x, y)
            .waitAction(Duration.ofSeconds(2))
            .moveTo(x * 4, y)
            .release()
            .perform();
    LOG.info("Element flicked to the left");
  }

  public static void horizontalSwipe(String mainContainerElement, String classNameOFSubElements) {

    AndroidElement mainContainer = (AndroidElement) driver.findElementById(mainContainerElement);
    List<MobileElement> images = mainContainer.findElementsByClassName(classNameOFSubElements);
    int originalImageCount = images.size();
    Point location = mainContainer.getLocation();
    Point center = mainContainer.getCenter();
    TouchAction swipe =
            new TouchAction(driver)
                    .press(images.get(2), -10, center.y - location.y)
                    .waitAction(Duration.ofSeconds(2))
                    .moveTo(mainContainer, 10, center.y - location.y)
                    .release();
    swipe.perform();
  }

  public static void horizontalSwipeLeft(WebElement el) {
    int x = (int) (driver.manage().window().getSize().width * 0.01);
    // replace 2 with 3 or 4  if this does not work due to presence of element in the top position
    int y = (int) (driver.manage().window().getSize().height / 2);
    TouchAction action = new TouchAction((MobileDriver) driver);
    action.longPress(el).moveTo(x, y).release().perform();
  }

  public static void horizontalSwipeRight(WebElement el) {
    int x = (int) (driver.manage().window().getSize().width * 0.80);
    // replace 2 with 3 or 4  if this does not work due to presence of element in the top position
    int y = (int) (driver.manage().window().getSize().height / 2);
    TouchAction action = new TouchAction((MobileDriver) driver);
    action.longPress(el).moveTo(x, y).release().perform();
  }

  public static void verticalSwipeUp(WebElement el) {
    int y = (int) (driver.manage().window().getSize().height * 0.01);
    // replace 2 with 3 or 4  if this does not work due to presence of element in the top position
    int x = (int) (driver.manage().window().getSize().width / 2);
    TouchAction action = new TouchAction((MobileDriver) driver);
    action.longPress(el).moveTo(x, y).release().perform();
  }

  public static void verticalSwipeDown(WebElement el) {
    int y = (int) (driver.manage().window().getSize().height * 0.80);
    // replace 2 with 3 or 4  if this does not work due to presence of element in the top position
    int x = (int) (driver.manage().window().getSize().width / 2);
    TouchAction action = new TouchAction((MobileDriver) driver);
    action.longPress(el).moveTo(x, y).release().perform();
  }

  // ************************************************************* MULTI TOUCH ACTIONS
  // ****************************************************************//

  public static void zoom_Using_MultiTouchActions() {

    int width = driver.manage().window().getSize().getWidth();
    int height = driver.manage().window().getSize().getHeight();

    int halfWidth = width / 2;
    int halfHeight = height / 2;

    MultiTouchAction multiTouch = new MultiTouchAction(driver);
    TouchAction touch1 = new TouchAction(driver);
    TouchAction touch2 = new TouchAction(driver);

    touch1.press(halfHeight, halfHeight).waitAction(Duration.ofSeconds(1)).moveTo(0, 60).release();
    touch2
            .press(halfHeight, halfHeight + 40)
            .waitAction(Duration.ofSeconds(1))
            .moveTo(0, 80)
            .release();

    multiTouch.add(touch1).add(touch2);
    multiTouch.perform();
  }

  // ****************************  SELENIUM FUNCTIONS *****************************//

  // ************************************************************* NORMAL CLICK
  // ****************************************************************//

  public static void click(WebDriver driver, By locator) {
    AppiumUtil.wait_until_ElementIs_Present(driver, locator).click();
    AppiumUtil.waitForPageToLoad(driver);
  }

  // ************************************************************* ACTIONS
  // ****************************************************************//

  // Hover over an element
  public static void hover(WebDriver driver, By locator) {
    Actions action = new Actions(driver);
    action
            .moveToElement(AppiumUtil.wait_until_ElementIs_Visible(driver, locator))
            .build()
            .perform();
    LOG.info("Hover action done on element -" + locator);
    AppiumUtil.waitForPageToLoad(driver);
  }

  // Hover over an element and click
  public static void hoverAndClick(WebDriver driver, By locator) {
    Actions action = new Actions(driver);
    action
            .moveToElement(AppiumUtil.wait_until_ElementIs_Visible(driver, locator))
            .click()
            .build()
            .perform();
    LOG.info("click action on -" + locator + " performed");
    AppiumUtil.waitForPageToLoad(driver);
  }

  // Hover over an element and click
  public static void hoverAndClick(WebDriver driver, WebElement el) {
    Actions action = new Actions(driver);
    action.moveToElement(el).click().build().perform();
    LOG.info("click action on -" + el + " performed");
    AppiumUtil.waitForPageToLoad(driver);
  }

  public static void hoverAndClear(WebDriver driver, WebElement el) {
    Actions action = new Actions(driver);
    action.moveToElement(el).click().build().perform();
    el.clear();
    LOG.info("click and clear actions on -" + el + " performed");
    AppiumUtil.waitForPageToLoad(driver);
  }

  // Hover over an element click and press enter
  public static void hoverClickAndPressEnter(WebDriver driver, By locator) {
    Actions action = new Actions(driver);
    action
            .moveToElement(AppiumUtil.wait_until_ElementIs_Visible(driver, locator))
            .click()
            .sendKeys(Keys.ENTER)
            .build()
            .perform();
    AppiumUtil.waitForPageToLoad(driver);
  }

  // Hover over an element click and send data
  public static void hoverClickAndSendData(WebDriver driver, By locator, String data) {
    Actions action = new Actions(driver);
    action
            .moveToElement(AppiumUtil.wait_until_ElementIs_Visible(driver, locator))
            .click()
            .sendKeys(data)
            .build()
            .perform();
    AppiumUtil.waitForPageToLoad(driver);
  }

  // Hover over an element click, send data and press enter
  public static void hoverClickSendDataAndPressEnter(WebDriver driver, By locator, String data) {
    Actions action = new Actions(driver);
    action
            .moveToElement(AppiumUtil.wait_until_ElementIs_Visible(driver, locator))
            .click()
            .sendKeys(data)
            .sendKeys(Keys.ENTER)
            .build()
            .perform();
    AppiumUtil.waitForPageToLoad(driver);
  }

  // sendkeys
  public static void hoverAndSendData(WebDriver driver, By locator, String data) {
    Actions action = new Actions(driver);
    action
            .moveToElement(AppiumUtil.wait_until_ElementIs_Visible(driver, locator))
            .sendKeys(data)
            .build()
            .perform();
    AppiumUtil.waitForPageToLoad(driver);
  }

  // Double click
  public static void doubleClick(WebDriver driver, WebElement el) {
    Actions doubleClick = new Actions(driver);
    doubleClick.doubleClick(el).build().perform();
    AppiumUtil.waitForPageToLoad(driver);
  }

  // Double click
  public static void doubleClick(WebDriver driver, By locator) {
    Actions doubleClick = new Actions(driver);
    doubleClick
            .doubleClick(AppiumUtil.wait_until_ElementIs_Visible(driver, locator))
            .build()
            .perform();
    AppiumUtil.waitForPageToLoad(driver);
  }

  // Drag and Drop by offset
  public static void dragAndDropOffset(WebDriver driver, WebElement el, int offsetX, int offsetY) {
    Actions builder = new Actions(driver);
    builder.dragAndDropBy(el, offsetX, offsetY).build().perform();
    AppiumUtil.waitForPageToLoad(driver);
  }

  // Drag and drop Elements
  public static void dragAndDropToElementContainner(
          WebDriver driver, WebElement source, WebElement target) {
    Actions builder = new Actions(driver);
    builder.dragAndDrop(source, target).build().perform();
    AppiumUtil.waitForPageToLoad(driver);
  }

  // ************************************************************* JAVA SCRIPT CLICK
  // ****************************************************************//

  public static void jsClick(WebDriver driver, By locator) {
    String code =
            "var fireOnThis = arguments[0];"
                    + "var evObj = document.createEvent('MouseEvents');"
                    + "evObj.initEvent( 'click', true, true );"
                    + "fireOnThis.dispatchEvent(evObj);";

    WebElement el = wait_until_ElementIs_Visible(driver, locator);
    ((JavascriptExecutor) driver).executeScript(code, el);
    AppiumUtil.waitForPageToLoad(driver);
  }

  public static void jsClick_Old(WebDriver driver, By locator) {
    WebElement element = AppiumUtil.wait_until_ElementIs_Present(driver, locator);
    ((JavascriptExecutor) driver).executeScript("arguments[0].focus();", element);
    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    AppiumUtil.waitForPageToLoad(driver);
  }

  public static void jsFocusAndClick(WebDriver driver, WebElement el) {
    ((JavascriptExecutor) driver).executeScript("arguments[0].focus();", el);
    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
    AppiumUtil.waitForPageToLoad(driver);
  }

  // ************************************************************* PAGE LOAD STATE
  // ****************************************************************//

  // Get Page State
  public static String getPageState(WebDriver driver) {
    WebElement el = driver.findElement(By.cssSelector("body"));
    String code = "return document.readyState";
    String result = (String) ((JavascriptExecutor) driver).executeScript(code, el);
    LOG.info("PageState-" + result);
    return result;
  }

  // Wait For Page to Load Completely
  public static void waitForPageToLoad(WebDriver driver) {
    while (!getPageState(driver).equals("complete")) {
      sleep(1);
    }
  }

  // Wait for Page title to change
  public static void waitForPageTitleToChange(WebDriver driver, String title) {
    while (driver.getTitle().equalsIgnoreCase(title)) {
      sleep(1);
    }
    while (driver.getTitle().matches(".*?" + title + ".*?")) {
      sleep(1);
    }
  }

  // ************************************************************* WINDOW HANDLES
  // ****************************************************************//

  public static int getWindowHandlesSize(WebDriver driver) {
    return driver.getWindowHandles().size();
  }

  public static void switchToNewWindowHandle_After_ClickingOnGivenElement(
          WebDriver driver, By locator) {

    parentHandle = driver.getWindowHandle();
    AppiumUtil.wait_until_ElementIs_Clickable(driver, locator).click();
    AppiumUtil.waitForPageToLoad(driver);

    if (driver.getWindowHandles().size()
            >= 2) { // switch to a new window handle if there more than 1 window handles.
      // Switch to new window opened
      for (String winHandle : driver.getWindowHandles()) {
        if (!winHandle.equals(parentHandle)) {
          driver.switchTo().window(winHandle);
          LOG.info("Switching to Child Window handle");
        }
        driver.manage().window().maximize();
      }
    }
  }

  public static void switchToParentWindowHandle(WebDriver driver) {
    driver.switchTo().window(parentHandle);
    driver.manage().window().maximize();
  }

  // ************************************************************* FRAMES
  // ****************************************************************//

  public static int getNumberOfFrames(WebDriver driver) {
    return driver.findElements(By.tagName("iframe")).size();
  }

  public static void SwitchToFrame_ByNumber(int n) {
    driver.switchTo().frame(n);
  }

  public static void SwitchToFrame_ByNAME_OR_ByID(String nameorID) {
    driver.switchTo().frame(nameorID);
  }

  // Switch To default Content - Works to get back from a frame
  public static void switchToDefaultContent(WebDriver driver) {
    driver.switchTo().defaultContent();
  }

  // ************************************************************* ALERTS
  // ****************************************************************//

  public static boolean isAlertPresent_SwitchToAlert(WebDriver driver) {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException ex) {
      return false;
    }
  }

  public static String getAlertText(WebDriver driver) {
    alert = driver.switchTo().alert();
    return alert.getText();
  }

  public static void sendTextToAlert(WebDriver driver, String text) {
    alert = driver.switchTo().alert();
    alert.sendKeys(text);
  }

  public static void closeAlert(WebDriver driver, boolean acceptAlert) {
    alert = driver.switchTo().alert();
    if (acceptAlert) {
      alert.accept();
    } else {
      alert.dismiss();
    }
  }

  // ************************************************************* EXPLICIT WAITS ON SINGLE ELEMENT
  // ****************************************************************//

  // WAIT FOR MAX TIME 45 SECS TILL THE ELEMENT IS CLICKABLE - DISPLAYED AND ENABLED
  public static WebElement wait_until_ElementIs_Clickable(WebDriver driver, By locator) {
    wait = new WebDriverWait(driver, 15);
    return wait.until(ExpectedConditions.elementToBeClickable(locator));
  }

  // WAIT FOR MAX TIME 45 SECS TILL THE ELEMENT IS VISIBLE
  public static WebElement wait_until_ElementIs_Visible(WebDriver driver, By locator) {
    wait = new WebDriverWait(driver, 15);
    return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
  }

  // WAIT FOR MAX TIME 45 SECS TILL THE ELEMENT IS PRESENT
  public static WebElement wait_until_ElementIs_Present(WebDriver driver, By locator) {
    wait = new WebDriverWait(driver, 15);
    return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
  }

  // *************************************************************** EXPLICIT WAITS ON MULTIPLE
  // ELEMENTS **********************************************//

  // WAIT FOR MAX TIME 45 SECS TILL THE ELEMENT IS PRESENT
  public static List<WebElement> wait_until_ElementsAre_Present(WebDriver driver, By locator) {
    wait = new WebDriverWait(driver, 15);
    return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
  }

  // WAIT FOR MAX TIME 45 SECS TILL THE ELEMENT IS VISIBLE
  public static List<WebElement> wait_until_ElementsAre_Visible(WebDriver driver, By locator) {
    wait = new WebDriverWait(driver, 15);
    return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
  }

  // ************************************************ EXPLICIT WAITS ON PAGE TITLE,URL AND
  // ELEMENT_NOT_PRESENT *****************************************//

  public static boolean wait_until_TitleContains(WebDriver driver, String keyword) {
    wait = new WebDriverWait(driver, 10);
    return wait.until(ExpectedConditions.titleContains(keyword));
  }

  public static boolean wait_until_URL_Matches(WebDriver driver, String regex) {
    wait = new WebDriverWait(driver, 10);
    return wait.until(ExpectedConditions.urlMatches(regex));
  }

  public static boolean IS_Element_NotPresent(WebDriver driver, By locator) {
    wait = new WebDriverWait(driver, 10);
    return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
  }

  // *************************************************************** EXTRAS
  // ***********************************************************************//

  // REFRESH PAGE
  public static void refreshPage() {
    driver.navigate().refresh();
  }

  // Sleep
  public static void sleep(int s) {
    try {
      Thread.sleep(s * 1000);
    } catch (InterruptedException ex) {
    } catch (IllegalArgumentException ex) {
    }
  }

  public static boolean verify_Element_NotPresent(WebDriver driver, By locator) {
    return driver.findElements(locator).size() == 0;
  }

  public static void wait_until_Element_is_Not_Present(WebDriver driver, By locator) {
    if (driver.findElements(locator).size() > 0) {
      AppiumUtil.sleep(2);
    }
  }

  public static String randomString() {
    Date date = new Date();
    Format dateformat = new SimpleDateFormat("yyyyMMddHHmmss");
    return dateformat.format(date);
  }

  public static void clickText(String text) { // used to get focus out of a text box
    AppiumUtil.hoverAndClick(driver, By.xpath("//*[contains(text(),'" + text + "')]"));
    AppiumUtil.sleep(2);
  }

  public static void setClipboardData(String string) {
    // StringSelection is a class that can be used for copy and paste operations.
    StringSelection stringSelection = new StringSelection(string);
    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
  }
}
