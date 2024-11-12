package com.company.project.utilities.appium;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

public class AppiumDriverFactory {

  private static Logger log = LogManager.getLogger();
  private AndroidDriver androidDriver;
  private IOSDriver iosDriver;
  private AppiumDriver driver;
  private DesiredCapabilities cap;
  private URL appiumServerURL;

  // ********************** Android Drivers *************************

  public AppiumDriver createLocalAndroidDriverForEmulator(
      String appName, Boolean autoAcceptPermissions) {
    cap = new DesiredCapabilities();
    cap.setCapability("appium:deviceName", "Android Emulator");
    cap.setCapability("appium:platformName", "Android");
    cap.setCapability("appium:automationName", "UiAutomator2");
    cap.setCapability(
        "appium:app", System.getProperty("user.dir") + "/src/main/resources/testApps/" + appName);

    //      These two capabilities are required for an android app that has splash screen as main activity
    //      cap.setCapability( "appium:appPackage", "com.company.project");
    //      cap.setCapability( "appium:appActivity", "activity.SplashScreenActivity");

    if (autoAcceptPermissions) {
      cap.setCapability("appium:autoGrantPermissions", true);
    }

    try {
      appiumServerURL = new URL("http://127.0.0.1:4723");
      log.info("Trying to create Android Driver on emulator");
      driver = new AndroidDriver(appiumServerURL, cap);
    } catch (MalformedURLException e) {
      log.info("Exception occurred while trying to create appium url session");
      e.printStackTrace();
    }
    return driver;
  }

  public AppiumDriver createLocalAndroidDriverForPhysicalDevice(
      String appName, String deviceName, Boolean autoAcceptPermissions) {
    cap = new DesiredCapabilities();
    cap.setCapability("appium:deviceName", deviceName);
    cap.setCapability("appium:platformName", "Android");
    cap.setCapability("appium:automationName", "UiAutomator2");
    cap.setCapability(
        "appium:app", System.getProperty("user.dir") + "/src/main/resources/testApps/" + appName);

    //      These two capabilities are required for an android app that has splash screen as main activity
    //      cap.setCapability( "appium:appPackage", "com.company.project");
    //      cap.setCapability( "appium:appActivity", "activity.SplashScreenActivity");

    if (autoAcceptPermissions) {
      cap.setCapability("appium:autoGrantPermissions", true);
    }

    try {
      appiumServerURL = new URL("http://127.0.0.1:4723");
      log.info("Trying to create Android Driver on physical device");
      driver = new AndroidDriver(appiumServerURL, cap);
    } catch (MalformedURLException e) {
      log.info("Exception occurred while trying to create appium url session");
      e.printStackTrace();
    }
    return driver;
  }

  // ********************** iOS Drivers *************************

  public AppiumDriver createLocalIOSDriverForSimulator(
      String dotAppFileBuiltForSimulator, String udid, Boolean autoAcceptAlerts) {
    cap = new DesiredCapabilities();
    cap.setCapability("appium:udid", udid);
    cap.setCapability("appium:platformName", "iOS");
    cap.setCapability("appium:automationName", "XCUITest");
    cap.setCapability(
        "appium:app",
        System.getProperty("user.dir") + "/src/main/resources/testApps/" + dotAppFileBuiltForSimulator);

    if (autoAcceptAlerts) {
      cap.setCapability("appium:autoAcceptAlerts", true);
    }

    try {
      appiumServerURL = new URL("http://127.0.0.1:4723");
      log.info("Trying to create iOS/iPadOS Driver on a simulator");
      driver = new IOSDriver(appiumServerURL, cap);
    } catch (MalformedURLException e) {
      log.info("Exception occurred while trying to create appium url session");
      e.printStackTrace();
    }
    return driver;
  }

  public AppiumDriver createLocalIOSDriverForPhysicalDevice(
      String dotIPAFile, String udid, String orgID, Boolean autoAcceptAlerts) {
    cap = new DesiredCapabilities();
    cap.setCapability("appium:udid", udid);
    cap.setCapability("appium:platformName", "iOS");
    cap.setCapability("appium:automationName", "XCUITest");
    cap.setCapability("xcodeOrgId", orgID);
    cap.setCapability("xcodeSigningId", "iPhone Developer");
    cap.setCapability(
        "appium:app", System.getProperty("user.dir") + "/src/main/resources/testApps/" + dotIPAFile);

    if (autoAcceptAlerts) {
      cap.setCapability("appium:autoAcceptAlerts", true);
    }

    try {
      appiumServerURL = new URL("http://127.0.0.1:4723");
      log.info("Trying to create iOS/iPadOS Driver on physical device");
      driver = new IOSDriver(appiumServerURL, cap);
    } catch (MalformedURLException e) {
      log.info("Exception occurred while trying to create appium url session");
      e.printStackTrace();
    }
    return driver;
  }
}
