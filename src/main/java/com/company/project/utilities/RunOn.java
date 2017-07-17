package com.company.project.utilities;

import io.appium.java_client.AppiumDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/** Created by pavankovurru on 5/19/17. */
public class RunOn {

  public static Logger log = LogManager.getLogger();
  static AppiumDriver driver = null;

  public static AppiumDriver run(String runOn, String appName) {
    //Creating driver object based on testNGXML configuration

    String run = runOn;
    String appname = System.getProperty("user.dir") + "/src/main/resources/" + appName;

    switch (run) {

        //ANDROID NATIVE APP

      case "AndroidEmulatorNativeApp":
        driver = AppiumUtil.createLocalAndroidDriver_For_Emulator(appname);
        break;

      case "AndroidDeviceNativeApp":
        driver = AppiumUtil.createLocalAndroidDriver_For_RealDevice(appname);
        break;

        //ANDROID WEB APP

      case "AndroidEmulatorWebApp":
        driver = AppiumUtil.createLocalAndroidDriver_For_WebApp_In_Emulator("Chrome");
        break;

      case "AndroidDeviceWebApp":
        driver = AppiumUtil.createLocalAndroidDriver_For_WebApp_In_RealDevice("Chrome");
        break;

        // IOS NATIVE APP

      case "IosSimulatorNativeApp":
        driver = AppiumUtil.createLocalIOSDriver_For_NativeApp_In_Simulator(appname);
        break;

      case "IosDeviceNativeApp":
        driver =
            AppiumUtil.createLocalIOSDriver_For_NativeApp_In_IOSDEVICE(
                appname,
                "pavan Kovurru’s iPhone",
                "84CK77G588",
                "phunware.WebDriverAgentRunner",
                "19251c9064c31b1a288ee8609a7eba334b8bddcd",
                "10.3");
        break;

        //IOS WEB APP

      case "IosSimulatorWebApp":
        driver = AppiumUtil.createLocalIOSDriver_For_WebApp_In_Simulator("Safari");
        break;

      case "IosDeviceWebApp":
        driver =
            AppiumUtil.createLocalIOSDriver_For_WebApp_In_IOSDEVICE(
                "Safari", "iPhone 7", "TEST ORG ID");
        break;

        //TODO RUN ON BROWSER STACK, SAUCE LABS ..

      default:
        throw new IllegalArgumentException(
            "Invalid parameter used in Environment.properties file : " + run);
    }
    return driver;
  }
}
