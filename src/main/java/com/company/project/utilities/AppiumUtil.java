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
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
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

    private static Logger log = LogManager.getLogger();
    private AppiumDriver driver;
    private String parentHandle;
    private Alert alert;
    private WebDriverWait wait;


    public AppiumUtil(AppiumDriver driver) {
        this.driver = driver;
    }

    // ******* EXPLICIT WAITS ON SINGLE ELEMENT ******************//

    // WAIT FOR MAX TIME 15 SECS TILL THE ELEMENT IS CLICKABLE - DISPLAYED AND ENABLED
    public WebElement wait_until_MobileElementIs_Clickable(WebDriver driver, By locator) {
        log.info("15 secs - Waiting for element using -"+locator);
        wait = new WebDriverWait(driver, 15);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    // WAIT FOR MAX TIME 15 SECS TILL THE ELEMENT IS VISIBLE
    public WebElement wait_until_MobileElementIs_Visible(WebDriver driver, By locator) {
        log.info("15 secs - Waiting for element using -"+locator);
        wait = new WebDriverWait(driver, 15);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // WAIT FOR MAX TIME 15 SECS TILL THE ELEMENT IS PRESENT
    public WebElement wait_until_MobileElementIs_Present(WebDriver driver, By locator) {
        log.info("15 secs - Waiting for element using -"+locator);
        wait = new WebDriverWait(driver, 15);
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    // ************* EXPLICIT WAITS ON MULTIPLE ELEMENTS ***************//

    // WAIT FOR MAX TIME 5 SECS TILL THE ELEMENT IS PRESENT
    public List<WebElement> wait_until_MobileElementsAre_Present(WebDriver driver, By locator) {
        log.info("5 secs - Waiting for elements using -"+locator);
        wait = new WebDriverWait(driver, 5);
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    // WAIT FOR MAX TIME 5 SECS TILL THE ELEMENT IS VISIBLE
    public List<WebElement> wait_until_MobileElementsAre_Visible(WebDriver driver, By locator) {
        log.info("5 secs - Waiting for elements using -"+locator);
        wait = new WebDriverWait(driver, 5);
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    // ******** EXPLICIT WAITS ON PAGE TITLE,URL AND ELEMENT_NOT_PRESENT ************//

    public boolean IS_MobileElement_NotPresent(WebDriver driver, By locator) {
        log.info("5 secs - checking for element presence using -"+locator);
        wait = new WebDriverWait(driver, 5);
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    // **** RETURNING MOBILE ELEMENT ANDROID *****//

    public WebElement returnMobileElementPresentUsingTextAndroid(String text) {
        log.info("Trying to find element with text - " + text);
        return wait_until_MobileElementIs_Visible(driver,
                MobileBy.AndroidUIAutomator("new UiSelector().text(\"" + text + "\")"));
    }

    public WebElement returnMobileElementPresentUsingIDAndroid(String id) {
        log.info("Trying to find element with ID - " + id);
        return wait_until_MobileElementIs_Visible(driver,
                MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"" + id + "\")"));
    }

    public WebElement returnMobileElementPresentUsingContentDescAndroid(String contentDesc) {
        log.info("Trying to locate element using content-desc - " + contentDesc);
        return wait_until_MobileElementIs_Visible(driver,
                MobileBy.AndroidUIAutomator("new UiSelector().description(\"" + contentDesc + "\")"));
    }


    // open notifications Android
    public void openNotificationsAndroid() {
        log.info("Opening notifications page");
        ((AndroidDriver) driver).openNotifications();
    }


    //rotate screen
    public void rotateScreenPotrait() {
        log.info("rotating screen -  PORTRAIT mode");
        driver.rotate(ScreenOrientation.PORTRAIT);
    }

    public void rotateScreenlandscape() {
        log.info("rotating screen -  LANDSCAPE mode");
        driver.rotate(ScreenOrientation.LANDSCAPE);
    }


    // clear notifications
    public void clearNotificationsAndroid() {

        // google pixel related
        if (isMobileElementPresentUsingTextAndroid("CLEAR ALL")) {
            wait_until_MobileElementIs_Clickable(driver, MobileBy.AndroidUIAutomator("new UiSelector().text(\"CLEAR ALL\")"))
                    .click();
            log.info("Existing notifications Cleared");

        } else {
            log.info("Could not find clear existing notifications option in the notifications page");
        }

        // TODO update clear notifications present
    }


    // Set location Android
    public void setLocationAndroid(double latitude, double longitude) {
        log.info("Trying to mock location --> Lat:" + latitude + " Long: " + longitude);
        Location loc = new Location(latitude, longitude, 10); // latitude, longitude, altitude
        driver.setLocation(loc);
        log.info("Location set to --> Latitude: " + latitude + " Longitude: " + longitude);
    }


    // **** CONTEXT SWITCHING *****//

    public Set<String> switchToWebViewAndReturnAllContextHandles() {
        log.info("Trying to switch to webview");
        Set<String> availableContexts = driver.getContextHandles();
        log.info("No of Contexts Found  = " + availableContexts.size());
        for (String context : availableContexts) {
            log.info("Context - " + context);
            if (context.matches(".*?WEBVIEW.*?")) {
                log.info("Switching to context " + context);
                driver.context(context);
                log.info("Context switched to -" + driver.getContext());
                break;
            }
        }
        return availableContexts;
    }

    public void switchToNativeContext(Set<String> availableContexts) {
        for (String context : availableContexts) {
            if (context.contains("NATIVE")) {
                log.info("Trying to switch to native context");
                driver.context(context);
                log.info("Switched to Context" + context);
            }
        }
    }


    // **** SCROLL FUNCTIONS *****//

    public MobileElement scrollToTextAndroid(String text) {
        log.info("Trying to scroll to element with text  - " + text);

        MobileElement el =
                (MobileElement)
                        wait_until_MobileElementIs_Visible(driver,
                                MobileBy.AndroidUIAutomator(
                                        "new UiScrollable(new UiSelector()).scrollIntoView(text(\"" + text + "\"));"));
        return el;
    }

    public MobileElement scrollToContentDescAndroid(String contentDesc) {
        log.info("Trying to scroll to element with content desc - " + contentDesc);
        MobileElement el =
                (MobileElement)
                        wait_until_MobileElementIs_Visible(driver,
                                MobileBy.AndroidUIAutomator(
                                        "new UiScrollable(new UiSelector()).scrollIntoView(description(\""
                                                + contentDesc
                                                + "\"));"));
        return el;
    }

    public MobileElement scrollToIDAndroid(String id) {
        log.info("Trying to scroll to android element with ID - " + id);

        MobileElement el =
                (MobileElement)
                        wait_until_MobileElementIs_Visible(driver,
                                MobileBy.AndroidUIAutomator(
                                        "new UiScrollable(new UiSelector()).scrollIntoView(resourceId(\""
                                                + id
                                                + "\"));"));
        return el;
    }

    public MobileElement scrollToTextUsingResourceIDAndroid(String resourceID, String text) {
        log.info("Trying to scroll to android element with text - " + text);
        // make sure u give the resouce ID of the complete list of elements here as parameter

        MobileElement el =
                (MobileElement)
                        wait_until_MobileElementIs_Visible(driver,
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

    public MobileElement scrollToIndexUsingResourceIDAndroid(String resourceID, int index) {
        log.info("Trying to scroll to android element with index - " + index);

        // make sure u give the resouce ID of the complete list of elements here as parameter

        MobileElement el =
                (MobileElement)
                        wait_until_MobileElementIs_Visible(driver,
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


    // **** ANDOID KEY EVENT FUNCTIONS *****//

    public void androidHomeKeyEvent() {
        log.info("android Home event");
        ((AndroidDriver) driver).pressKeyCode(AndroidKeyCode.HOME);
        sleep(2);
    }

    public void androidBackKeyEvent() {
        log.info("android back event");
        ((AndroidDriver) driver).pressKeyCode(AndroidKeyCode.BACK);
        sleep(2);
    }

    public void androidEnter() {
        log.info("android Enter event");
        ((AndroidDriver) driver).pressKeyCode(AndroidKeyCode.ENTER);
        sleep(2);
    }

    public void androidTab() {
        log.info("android Tab event");
        ((AndroidDriver) driver).pressKeyCode(AndroidKeyCode.KEYCODE_TAB);
        sleep(2);
    }

    public void androidMultiTaskingKeyEvent() {
        ((AndroidDriver) driver).pressKeyCode(AndroidKeyCode.KEYCODE_APP_SWITCH);
        sleep(2);
    }

    // ********** TOUCH ACTIONS **************//

    public void basicSwipe(WebElement element1, WebElement element2) {
        TouchAction touch = new TouchAction(driver);
        touch.press(element1).moveTo(element2).release().perform();
        log.info("Swipe Action performed");
    }

    public void dragDrop(WebElement element1, WebElement element2) {
        TouchAction touch = new TouchAction(driver);
        touch.longPress(element1).moveTo(element2).release().perform();
        log.info("Drag and drop Action performed");
    }

    public void pressAndHold(WebElement element1, int timeInSeconds) {
        TouchAction touch = new TouchAction(driver);
        touch.longPress(element1, Duration.ofSeconds(timeInSeconds)).release().perform();
        log.info("Press and Hold Action performed");
    }

    public void pressAndHold_Moveto_NewElement(WebElement element1, WebElement element2) {
        TouchAction touch = new TouchAction(driver);
        touch.press(element1).waitAction(Duration.ofSeconds(2)).moveTo(element2).release().perform();
        log.info("Press/hold , move to element Action performed");
    }

    public void tap(WebElement element) {
        TouchAction touch = new TouchAction(driver);
        touch.moveTo(element).tap(element).perform();
        log.info("Tap Action performed");
    }


    // ********** CHECK FOR PRESENCE OF MOBILE ELEMENT ANDROID **************//

    public boolean isMobileElementPresentUsingTextAndroid(String text) {

        log.info("Trying to find element with text - " + text);
        if (wait_until_MobileElementsAre_Present(driver, (MobileBy.AndroidUIAutomator("new UiSelector().text(\"" + text + "\")")))
                .size()
                >= 1) {
            log.info("element found");
            return true;
        } else {
            log.info("element not found");
            return false;
        }
    }

    public boolean isMobileElementPresentUsingIDAndroid(String id) {
        log.info("Trying to find element with ID - " + id);

        if (wait_until_MobileElementsAre_Present(driver, (
                MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"" + id + "\")")))
                .size()
                >= 1) {
            log.info("Found element having id -" + id);
            return true;
        } else return false;
    }

    public boolean isMobileElementPresentUsingContentDescAndroid(String contentDesc) {
        log.info("Trying to locate element using content-desc - " + contentDesc);
        if (wait_until_MobileElementsAre_Present(driver, (
                MobileBy.AndroidUIAutomator(
                        "new UiSelector().description(\"" + contentDesc + "\")")))
                .size()
                >= 1) {
            log.info("Found element having content desc - " + contentDesc);
            return true;
        } else return false;
    }


    // ********** SCROLL AND CHECK FOR PRESENCE OF MOBILE ELEMENT ANDROID **************//

    public boolean isMobileElementPresentUsingText_ScrollAndroid(String text) {
        log.info("Trying to find element with text - " + text);
        if (wait_until_MobileElementsAre_Present(driver, (
                MobileBy.AndroidUIAutomator(
                        "new UiScrollable(new UiSelector()).scrollIntoView(text(\"" + text + "\"));")))
                .size()
                >= 1) {
            log.info("element found");
            return true;
        } else {
            log.info("element not found");
            return false;
        }
    }

    public boolean isMobileElementPresentUsingID_ScrollAndroid(String id) {
        log.info("Trying to find element with ID - " + id);
        if (wait_until_MobileElementsAre_Present(driver, (
                MobileBy.AndroidUIAutomator(
                        "new UiScrollable(new UiSelector()).scrollIntoView(resourceId(\""
                                + id
                                + "\"));")))
                .size()
                >= 1) return true;
        else return false;
    }

    public boolean isMobileElementPresentUsingContentDesc_ScrollAndroid(String contentDesc) {
        log.info("Trying to locate element using content-desc - " + contentDesc);
        if (wait_until_MobileElementsAre_Present(driver, (
                MobileBy.AndroidUIAutomator(
                        "new UiScrollable(new UiSelector()).scrollIntoView(description(\""
                                + contentDesc
                                + "\"));")))
                .size()
                >= 1) {
            log.info("Found element having content desc -" + contentDesc);
            return true;
        } else return false;
    }


    // ******************************     IOS FUNCTIONS      ******************************* //

    // ***** RETURN MOBILE ELEMENT ****** //

    public WebElement returnMobileElementUsingAccessibilityId(String accessibilityId) {
        log.info("Trying to find element with id - " + accessibilityId);
        return wait_until_MobileElementIs_Visible(driver,
                MobileBy.AccessibilityId(accessibilityId));
    }

    public WebElement returnMobileElementUsingName(String name) {
        log.info("Trying to find element with name - " + name);
        return wait_until_MobileElementIs_Visible(driver,
                MobileBy.name(name));
    }

    public WebElement returnMobileElementUsingXpath(String xPath) {
        log.info("Trying to find element with xPath - " + xPath);
        return wait_until_MobileElementIs_Visible(driver,
                MobileBy.xpath(xPath));
    }

    public WebElement returnMobileElementUsingPredicateString(String predicateString) {
        log.info("Trying to find element with predicateString - " + predicateString);
        return wait_until_MobileElementIs_Visible(driver,
                MobileBy.iOSNsPredicateString(predicateString));
    }

    public WebElement returnMobileElementUsingClassChain(String classChain) {
        log.info("Trying to find element with classChain - " + classChain);
        return wait_until_MobileElementIs_Visible(driver,
                MobileBy.iOSClassChain(classChain));
    }


    // ***** PRESENCE OF MOBILE ELEMENT ****** //

    public boolean isElementPresentUsingAccessibilityId(String acessibilityId) {

        log.info("Trying to find element with AcessibilityId - " + acessibilityId);
        if (wait_until_MobileElementsAre_Present(driver, (MobileBy.AccessibilityId(acessibilityId)))
                .size()
                >= 1) {
            log.info("element found");
            return true;
        } else {
            log.info("element not found");
            return false;
        }
    }

    public boolean isElementPresentUsingName(String name) {

        log.info("Trying to find element with name - " + name);
        if (wait_until_MobileElementsAre_Present(driver, (MobileBy.name(name)))
                .size()
                >= 1) {
            log.info("element found");
            return true;
        } else {
            log.info("element not found");
            return false;
        }
    }


    public boolean isElementPresentUsingXpath(String xPath) {

        log.info("Trying to find element with xPath - " + xPath);
        if (wait_until_MobileElementsAre_Present(driver, (MobileBy.xpath(xPath)))
                .size()
                >= 1) {
            log.info("element found");
            return true;
        } else {
            log.info("element not found");
            return false;
        }
    }

    public boolean isElementPresentUsingPredicateString(String predicateString) {

        log.info("Trying to find element with predicateString - " + predicateString);
        if (wait_until_MobileElementsAre_Present(driver, (MobileBy.iOSNsPredicateString(predicateString)))
                .size()
                >= 1) {
            log.info("element found");
            return true;
        } else {
            log.info("element not found");
            return false;
        }
    }

    public boolean isElementPresentUsingClassChain(String classChain) {

        log.info("Trying to find element with classChain - " + classChain);
        if (wait_until_MobileElementsAre_Present(driver, (MobileBy.iOSClassChain(classChain)))
                .size()
                >= 1) {
            log.info("element found");
            return true;
        } else {
            log.info("element not found");
            return false;
        }
    }


    // ***** IOS SCROLL FUNCTIONS ****** //

    public void scrollDown_InIosUsingLabel(String label) {
        log.info("Trying to scroll down to element with label -"+label);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        HashMap scrollObject = new HashMap();
        scrollObject.put("direction", "down");
        scrollObject.put("predicateString", "label == '" + label + "'");
        js.executeScript("mobile: scroll", scrollObject);
        log.info("scrolled to element with label -"+label);

    }

    public void scrollUp_InIosUsingLabel(String label) {
        log.info("Trying to scroll down to element with label -"+label);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        HashMap scrollObject = new HashMap();
        scrollObject.put("direction", "up");
        scrollObject.put("predicateString", "label == '" + label + "'");
        js.executeScript("mobile: scroll", scrollObject);
        log.info("scrolled to element with label -"+label);
    }

    public void scrollDown_InIosUsingElement(RemoteWebElement element) {
        log.info("Trying to scroll down to element");
        String elementID = element.getId();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        HashMap scrollObject = new HashMap();
        scrollObject.put("element", elementID); // Only for ‘scroll in element’
        scrollObject.put("direction", "down");
        js.executeScript("mobile: scroll", scrollObject);
        log.info("scrolled to element");
    }

    public void scrollUp_InIosUsingElement(RemoteWebElement element) {
        log.info("Trying to scroll up to element");
        String elementID = element.getId();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        HashMap scrollObject = new HashMap();
        scrollObject.put("element", elementID); // Only for ‘scroll in element’
        scrollObject.put("direction", "up");
        js.executeScript("mobile: scroll", scrollObject);
        log.info("scrolled to element");
    }

    // Manual check and scroll functions
    public WebElement scroll_Ios_Up_UntilElementWithAccessibilityIDIsFound(String accessibilityID) {
        log.info("Trying to scroll up to element With Accessibility ID-"+accessibilityID);
        Boolean cont = true;
        WebElement el = null;

        while (cont) {
            try {
                el = driver.findElementByAccessibilityId(accessibilityID);
                if (el.isDisplayed()) {
                    log.info("element with accessibilityID " + accessibilityID + " is Visible");
                    log.info("scrolled to element");
                    return el;
                } else scrollIOS_up();
            } catch (NoSuchElementException e) {
                scrollIOS_up();
            }
        }
        log.info("scrolled to element");
        return el;
    }

    public WebElement scroll_Ios_Down_UntilElementWithAccessibilityIDIsFound(String accessibilityID) {
        log.info("Trying to scroll down to element With Accessibility ID-"+accessibilityID);

        Boolean cont = true;
        WebElement el = null;

        while (cont) {
            try {
                el = driver.findElementByAccessibilityId(accessibilityID);
                if (el.isDisplayed()) {
                    log.info("element with accessibilityID " + accessibilityID + " is Visible");
                    log.info("scrolled to element");
                    return el;
                } else scrollIOS_down();
            } catch (NoSuchElementException e) {
                scrollIOS_up();
            }
        }
        log.info("scrolled to element");
        return el;
    }

    public void scrollIOS_up() {
        log.info("Trying to scroll up using screen height");
        Dimension size = driver.manage().window().getSize();
        int x = size.getWidth() / 2;
        int start_y = (int) (size.getHeight() * 0.60);
        int end_y = (int) (size.getHeight() * 0.30);
        new TouchAction(driver).press(x, start_y).moveTo(x, end_y).release().perform();
        log.info("scroll done");
    }

    public void scrollIOS_down() {
        log.info("Trying to scroll up using screen height");
        Dimension size = driver.manage().window().getSize();
        int x = size.getWidth() / 2;
        int start_y = (int) (size.getHeight() * 0.30);
        int end_y = (int) (size.getHeight() * 0.60);
        new TouchAction(driver).press(x, start_y).moveTo(x, end_y).release().perform();
        log.info("scroll done");
    }

    // Swiping element

    public void swipeElementToTheLeft(WebElement el) {

        log.info("Trying to swipe element to the left");
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
        log.info("Element swipe to the left performed");
    }

    public void swipeElementToTheRight(WebElement el) {

        log.info("Trying to swipe element to the left");
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
        log.info("Element flicked to the left");
    }

    public void horizontalSwipeLeft(WebElement el) {
        int x = (int) (driver.manage().window().getSize().width * 0.01);
        // replace 2 with 3 or 4  if this does not work due to presence of element in the top position
        int y = (int) (driver.manage().window().getSize().height / 2);
        TouchAction action = new TouchAction((MobileDriver) driver);
        action.longPress(el).moveTo(x, y).release().perform();
    }

    public void horizontalSwipeRight(WebElement el) {
        int x = (int) (driver.manage().window().getSize().width * 0.80);
        // replace 2 with 3 or 4  if this does not work due to presence of element in the top position
        int y = (int) (driver.manage().window().getSize().height / 2);
        TouchAction action = new TouchAction((MobileDriver) driver);
        action.longPress(el).moveTo(x, y).release().perform();
    }

    public void verticalSwipeUp(WebElement el) {
        int y = (int) (driver.manage().window().getSize().height * 0.01);
        // replace 2 with 3 or 4  if this does not work due to presence of element in the top position
        int x = (int) (driver.manage().window().getSize().width / 2);
        TouchAction action = new TouchAction((MobileDriver) driver);
        action.longPress(el).moveTo(x, y).release().perform();
    }

    public void verticalSwipeDown(WebElement el) {
        int y = (int) (driver.manage().window().getSize().height * 0.80);
        // replace 2 with 3 or 4  if this does not work due to presence of element in the top position
        int x = (int) (driver.manage().window().getSize().width / 2);
        TouchAction action = new TouchAction((MobileDriver) driver);
        action.longPress(el).moveTo(x, y).release().perform();
    }

    // ********* MULTI TOUCH ACTIONS ***************************//

    public void zoom_Using_MultiTouchActions() {

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

    // ******* EXPLICIT WAITS ON SINGLE ELEMENT ******************//

    // WAIT FOR MAX TIME 15 SECS TILL THE ELEMENT IS CLICKABLE - DISPLAYED AND ENABLED
    public WebElement wait_until_ElementIs_Clickable(WebDriver driver, By locator) {
        wait = new WebDriverWait(driver, 15);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    // WAIT FOR MAX TIME 15 SECS TILL THE ELEMENT IS VISIBLE
    public WebElement wait_until_ElementIs_Visible(WebDriver driver, By locator) {
        wait = new WebDriverWait(driver, 15);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // WAIT FOR MAX TIME 15 SECS TILL THE ELEMENT IS PRESENT
    public WebElement wait_until_ElementIs_Present(WebDriver driver, By locator) {
        wait = new WebDriverWait(driver, 15);
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    // WAIT FOR MAX TIME 15 SECS TILL SELENIUM FINDS 2 WINDOWS
    public void wait_until_Two_Windows_Are_Available(WebDriver driver) {
        wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
    }

    // ************* EXPLICIT WAITS ON MULTIPLE ELEMENTS ***************//

    // WAIT FOR MAX TIME 15 SECS TILL THE ELEMENT IS PRESENT
    public List<WebElement> wait_until_ElementsAre_Present(WebDriver driver, By locator) {
        wait = new WebDriverWait(driver, 15);
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    // WAIT FOR MAX TIME 15 SECS TILL THE ELEMENT IS VISIBLE
    public List<WebElement> wait_until_ElementsAre_Visible(WebDriver driver, By locator) {
        wait = new WebDriverWait(driver, 15);
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    // ******** EXPLICIT WAITS ON PAGE TITLE,URL AND ELEMENT_NOT_PRESENT ************//

    public boolean wait_until_TitleContains(WebDriver driver, String keyword) {
        wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.titleContains(keyword));
    }

    public boolean wait_until_URL_Matches(WebDriver driver, String regex) {
        wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.urlMatches(regex));
    }

    public boolean IS_Element_NotPresent(WebDriver driver, By locator) {
        wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }


    // ************** NORMAL CLICK *******************//

    public void click(WebDriver driver, By locator) {
        wait_until_ElementIs_Present(driver, locator).click();
        waitForPageToLoad(driver);
    }

    // ******************** ACTIONS ***********************//

    // Hover over an element
    public void hover(WebDriver driver, By locator) {
        Actions action = new Actions(driver);
        action.moveToElement(wait_until_ElementIs_Visible(driver, locator)).build().perform();
        log.info("Hover action done on element -" + locator);
        waitForPageToLoad(driver);
    }

    // Hover over an element and click
    public void hoverAndClick(WebDriver driver, By locator) {
        Actions action = new Actions(driver);
        action.moveToElement(wait_until_ElementIs_Visible(driver, locator)).click().build().perform();
        log.info("click action on -" + locator + " performed");
        waitForPageToLoad(driver);
    }

    public void hoverAndClear(WebDriver driver, By locator) {
        Actions action = new Actions(driver);
        WebElement el = wait_until_ElementIs_Visible(driver, locator);
        action.moveToElement(el).click().build().perform();
        el.clear();
        log.info("click and clear actions on -" + el + " performed");
        waitForPageToLoad(driver);
    }

    // Hover over an element, click and press enter
    public void hoverClickAndPressEnter(WebDriver driver, By locator) {
        Actions action = new Actions(driver);
        action
                .moveToElement(wait_until_ElementIs_Visible(driver, locator))
                .click()
                .sendKeys(Keys.ENTER)
                .build()
                .perform();
        waitForPageToLoad(driver);
    }

    // Hover over an element click and send data
    public void hoverClickAndSendData(WebDriver driver, By locator, String data) {
        Actions action = new Actions(driver);
        action
                .moveToElement(wait_until_ElementIs_Visible(driver, locator))
                .click()
                .sendKeys(data)
                .build()
                .perform();
        waitForPageToLoad(driver);
    }

    // Hover over an element click, send data and press enter
    public void hoverClickSendDataAndPressEnter(WebDriver driver, By locator, String data) {
        Actions action = new Actions(driver);
        action
                .moveToElement(wait_until_ElementIs_Visible(driver, locator))
                .click()
                .sendKeys(data)
                .sendKeys(Keys.ENTER)
                .build()
                .perform();
        waitForPageToLoad(driver);
    }

    // sendkeys
    public void hoverAndSendData(WebDriver driver, By locator, String data) {
        Actions action = new Actions(driver);
        action
                .moveToElement(wait_until_ElementIs_Visible(driver, locator))
                .sendKeys(data)
                .build()
                .perform();
        waitForPageToLoad(driver);
    }

    // Double click
    public void doubleClick(WebDriver driver, By locator) {
        Actions doubleClick = new Actions(driver);
        doubleClick.doubleClick(wait_until_ElementIs_Visible(driver, locator)).build().perform();
        waitForPageToLoad(driver);
    }

    // Drag and Drop by offset
    public void dragAndDropOffset(WebDriver driver, By locator, int offsetX, int offsetY) {
        WebElement el = wait_until_ElementIs_Visible(driver, locator);
        Actions builder = new Actions(driver);
        builder.dragAndDropBy(el, offsetX, offsetY).build().perform();
        waitForPageToLoad(driver);
    }

    // Drag and drop Elements
    public void dragAndDropToElementContainner(
            WebDriver driver, WebElement source, WebElement target) {
        Actions builder = new Actions(driver);
        builder.dragAndDrop(source, target).build().perform();
        waitForPageToLoad(driver);
    }

    // *********** JAVA SCRIPT CLICK **********************************//

    public void jsClick(WebDriver driver, By locator) {
        String code =
                "var fireOnThis = arguments[0];"
                        + "var evObj = document.createEvent('MouseEvents');"
                        + "evObj.initEvent( 'click', true, true );"
                        + "fireOnThis.dispatchEvent(evObj);";

        WebElement el = wait_until_ElementIs_Visible(driver, locator);
        ((JavascriptExecutor) driver).executeScript(code, el);
        waitForPageToLoad(driver);
    }

    public void jsFocusAndClick(WebDriver driver, By locator) {
        WebElement element = wait_until_ElementIs_Present(driver, locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].focus();", element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        waitForPageToLoad(driver);
    }

    public void jsFocusAndClick(WebDriver driver, WebElement el) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].focus();", el);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        waitForPageToLoad(driver);
    }

    // ************ PAGE LOAD STATE ****************************//

    // Get Page State
    public String getPageState(WebDriver driver) {
        WebElement el = driver.findElement(By.cssSelector("body"));
        String code = "return document.readyState";
        String result = (String) ((JavascriptExecutor) driver).executeScript(code, el);
        log.info("PageState-" + result);
        return result;
    }

    // Wait For Page to Load Completely
    public void waitForPageToLoad(WebDriver driver) {
        while (!getPageState(driver).equals("complete")) {
            sleep(1);
        }
    }

    // Wait for Page title to change
    public void waitForPageTitleToChange(WebDriver driver, String title) {
        while (driver.getTitle().equalsIgnoreCase(title)) {
            sleep(1);
        }
        while (driver.getTitle().matches(".*?" + title + ".*?")) {
            sleep(1);
        }
    }

    // ******************** WINDOW HANDLES ******************************//

    public int getWindowHandlesSize(WebDriver driver) {
        log.info("Window Handles -" + driver.getWindowHandles().size());
        return driver.getWindowHandles().size();
    }

    public void switchToNewWindowHandle_After_ClickingOnGivenElement(WebDriver driver, By locator) {

        parentHandle = driver.getWindowHandle();
        log.info("Parent handle -" + parentHandle);
        wait_until_ElementIs_Clickable(driver, locator).click();
        waitForPageToLoad(driver);

        if (driver.getWindowHandles().size()
                >= 2) { // switch to a new window handle if there more than 1 window handles.
            // Switch to new window opened
            for (String winHandle : driver.getWindowHandles()) {
                log.info("Window Handle -" + winHandle);
                if (!winHandle.equals(parentHandle)) {
                    driver.switchTo().window(winHandle);
                    log.info("Switching to Child Window handle");
                    break;
                }
                driver.manage().window().maximize();
            }
        }
    }

    public void switchToNewWindowHandle(WebDriver driver, String parentHandle) {

        int windowHandles = driver.getWindowHandles().size();

        if (windowHandles >= 2) { // switch to a new window handle if there more than 1 window handles.
            for (String winHandle : driver.getWindowHandles()) {
                log.info("Window Handle -" + winHandle);
                if (!winHandle.equals(parentHandle)) {
                    driver.switchTo().window(winHandle);
                    log.info("Switching to Child Window handle -" + winHandle);
                    break;
                }
                driver.manage().window().maximize();
            }
        } else {
            log.info("Window handles found - " + windowHandles);
        }
    }

    public void switchToParentWindowHandle(WebDriver driver) {
        driver.switchTo().window(parentHandle);
        driver.manage().window().maximize();
    }

    // ******************** FRAMES *************//

    public int getNumberOfFrames(WebDriver driver) {
        return driver.findElements(By.tagName("iframe")).size();
    }

    public void SwitchToFrame_ByNumber(int n) {
        driver.switchTo().frame(n);
    }

    public void SwitchToFrame_ByNAME_OR_ByID(String nameorID) {
        driver.switchTo().frame(nameorID);
    }

    // Switch To default Content - Works to get back from a frame
    public void switchToDefaultContent(WebDriver driver) {
        driver.switchTo().defaultContent();
    }

    // ************* ALERTS *********************//

    public boolean isAlertPresent_SwitchToAlert(WebDriver driver) {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException ex) {
            return false;
        }
    }

    public String getAlertText(WebDriver driver) {
        alert = driver.switchTo().alert();
        return alert.getText();
    }

    public void sendTextToAlert(WebDriver driver, String text) {
        alert = driver.switchTo().alert();
        alert.sendKeys(text);
    }

    public void closeAlert(WebDriver driver, boolean acceptAlert) {
        alert = driver.switchTo().alert();
        if (acceptAlert) {
            alert.accept();
        } else {
            alert.dismiss();
        }
    }


    // *************** EXTRAS ********************//

    // REFRESH PAGE
    public void refreshPage() {
        driver.navigate().refresh();
    }

    // Sleep
    public void sleep(int s) {
        try {
            Thread.sleep(s * 1000);
        } catch (InterruptedException ex) {
        } catch (IllegalArgumentException ex) {
        }
    }

    public boolean verify_Element_NotPresent(WebDriver driver, By locator) {
        return driver.findElements(locator).size() == 0;
    }

    public void wait_until_Element_is_Not_Present(WebDriver driver, By locator) {
        if (driver.findElements(locator).size() > 0) {
            sleep(2);
        }
    }

    public String randomString() {
        Date date = new Date();
        Format dateformat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateformat.format(date);
    }

    public void clickText(String text) { // used to get focus out of a text box
        hoverAndClick(driver, By.xpath("//*[contains(text(),'" + text + "')]"));
        sleep(2);
    }

    // ************ PERFORMANCE/NETWORK **************************//

    public String getNetworkData() {
        String scriptToExecute =
                "var performance = window.performance || window.mozPerformance || window.msPerformance || window.webkitPerformance || {}; var network = performance.getEntries() || {}; return network;";
        String netData = ((JavascriptExecutor) driver).executeScript(scriptToExecute).toString();
        return netData;
    }

    // ************ ROBOT **************************//

    public void setClipboardData(String string) {
        // StringSelection is a class that can be used for copy and paste operations.
        StringSelection stringSelection = new StringSelection(string);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
    }

    public void uploadFile(String fileLocation) {

        if (Platform.getCurrent().toString().matches(".*?(win|WIN).*?")) {
            try {
                setClipboardData(fileLocation);
                Robot robot = new Robot();

                robot.keyPress(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_V);
                robot.keyRelease(KeyEvent.VK_V);
                robot.keyRelease(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);
            } catch (AWTException ex) {
                ex.printStackTrace();
            }
        }

        if (Platform.getCurrent().toString().matches(".*?(mac|MAC).*?")) {
            try {
                setClipboardData(fileLocation);
                Robot robot = new Robot();

                // Cmd + Tab is needed since it launches a Java app and the browser looses focus

                robot.keyPress(KeyEvent.VK_META);
                robot.keyPress(KeyEvent.VK_TAB);
                robot.keyRelease(KeyEvent.VK_META);
                robot.keyRelease(KeyEvent.VK_TAB);
                robot.delay(500);

                // Open Goto window

                robot.keyPress(KeyEvent.VK_META);
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress(KeyEvent.VK_G);
                robot.keyRelease(KeyEvent.VK_META);
                robot.keyRelease(KeyEvent.VK_SHIFT);
                robot.keyRelease(KeyEvent.VK_G);

                // Paste the clipboard value

                robot.keyPress(KeyEvent.VK_META);
                robot.keyPress(KeyEvent.VK_V);
                robot.keyRelease(KeyEvent.VK_META);
                robot.keyRelease(KeyEvent.VK_V);

                // Press Enter key to close the Goto window and Upload window

                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);
                robot.delay(500);
                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);

            } catch (AWTException ex) {
                ex.printStackTrace();
            }
        }
    }
}
