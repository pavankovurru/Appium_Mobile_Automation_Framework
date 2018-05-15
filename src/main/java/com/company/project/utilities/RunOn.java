package com.company.project.utilities;

import io.appium.java_client.AppiumDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/** Created by pavankovurru on 5/19/17. */
public class RunOn {

  public static Logger log = LogManager.getLogger();
  AppiumUtil appium = new AppiumUtil();
  static AppiumDriver driver = null;

  public AppiumDriver run(String runOn, String appName) {

    // Creating driver object based on testNGXML configuration

    String run = runOn;
    String appname = System.getProperty("user.dir") + "/src/main/resources/" + appName;

    switch (run) {

        // ANDROID NATIVE APP

      case "AndroidEmulatorNativeApp":
        driver = appium.createLocalAndroidDriver_For_Emulator(appname);
        log.info(driver.getContext());
        break;

      case "AndroidDeviceNativeApp":
        driver = appium.createLocalAndroidDriver_For_RealDevice(appname);
        break;

        // ANDROID WEB APP

      case "AndroidEmulatorWebApp":
        driver = appium.createLocalAndroidDriver_For_WebApp_In_Emulator("Chrome");
        break;

      case "AndroidDeviceWebApp":
        driver = appium.createLocalAndroidDriver_For_WebApp_In_RealDevice("Chrome");
        break;

        // IOS NATIVE APP

      case "IosSimulatorNativeApp":
        driver = appium.createLocalIOSDriver_For_NativeApp_In_Simulator(appname);
        break;

      case "IosDeviceNativeApp":
        driver =
                appium.createLocalIOSDriver_For_NativeApp_In_IOSDEVICE(
                appname, "iPhone 7", "udid", "bundleID");
        break;

        // IOS WEB APP

      case "IosSimulatorWebApp":
        driver = appium.createLocalIOSDriver_For_WebApp_In_Simulator("Safari");
        break;

      case "IosDeviceWebApp":
        driver =
                appium.createLocalIOSDriver_For_WebApp_In_IOSDEVICE(
                "Safari", "iPhone 7", "TEST ORG ID");
        break;

        // TODO RUN ON BROWSER STACK, SAUCE LABS ..

      default:
        throw new IllegalArgumentException(
            "Invalid parameter used in Environment.properties file : " + run);
    }
    return driver;
  }
}
