package com.company.project.tests;

import com.company.project.constants.Global.GlobalConstants;
import com.company.project.utilities.AppiumUtil;
import com.company.project.utilities.PropertiesLoader;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class LoginTest {

  public static Logger log = LogManager.getLogger();
  static PropertiesLoader pro = new PropertiesLoader(GlobalConstants.ENVIRONMENT_PROPERTY_PATH);
  static AppiumDriver driver = null;

  @BeforeMethod
  public void login() {

    //Creating driver object based on Environment.properties configuration

    String run = pro.getProperty("RunOn");
    String appname = System.getProperty("user.dir")+"/src/main/resources/"+pro.getProperty("AppName");

    switch (run) {

        //ANDROID NATIVE APP

        case "AndroidEmulatorNativeApp":
            driver=AppiumUtil.createLocalAndroidDriver_For_Emulator(appname);
            break;

        case "AndroidDeviceNativeApp":
            driver=AppiumUtil.createLocalAndroidDriver_For_RealDevice(appname);
            break;

        //ANDROID WEB APP

        case "AndroidEmulatorWebApp":
            driver=AppiumUtil.createLocalAndroidDriver_For_WebApp_In_Emulator("Chrome");
            break;

        case "AndroidDeviceWebApp":
            driver=AppiumUtil.createLocalAndroidDriver_For_WebApp_In_RealDevice("Chrome");
            break;

       // IOS NATIVE APP

        case "IosSimulatorNativeApp":
            driver=AppiumUtil.createLocalIOSDriver_For_NativeApp_In_Simulator(appname);
            break;

        case "IosDeviceNativeApp":
            driver=AppiumUtil.createLocalIOSDriver_For_NativeApp_In_IOSDEVICE(appname,"iPhone 7","TEST ORG ID");
            break;

        //IOS WEB APP

        case "IosSimulatorWebApp":
            driver=AppiumUtil.createLocalIOSDriver_For_WebApp_In_Simulator("Safari");
            break;

        case "IosDeviceWebApp":
            driver=AppiumUtil.createLocalIOSDriver_For_WebApp_In_IOSDEVICE("Safari","iPhone 7","TEST ORG ID");
            break;


        default:
        throw new IllegalArgumentException("Invalid parameter used in Environment.properties file : " + run);
    }


  }

  @Test(priority = 1)
  public void androidSimulator() {

      log.info("Testing a - "+driver.getContext());
      driver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"Views\")")).click();

      MobileElement el=(MobileElement)driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()"
              + ".resourceId(\"android:id/list\")).scrollIntoView("
              + "new UiSelector().text(\"Gallery\"));"));

      System.out.println("****"+el.getLocation());
      el.click();
      AppiumUtil.sleep(2);

  }


    @Test(priority = 2)
    public void androidDevice() {

        driver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"Views\")")).click();

        MobileElement el=(MobileElement)driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()"
                + ".resourceId(\"android:id/list\")).scrollIntoView("
                + "new UiSelector().text(\"Gallery\"));"));

        System.out.println("****"+el.getLocation());
        el.click();

        //simulating key events
        ((AndroidDriver)driver).pressKeyCode(AndroidKeyCode.BACK);
        ((AndroidDriver)driver).pressKeyCode(AndroidKeyCode.KEYCODE_APP_SWITCH);
        ((AndroidDriver)driver).pressKeyCode(AndroidKeyCode.HOME);

        driver.launchApp();
        AppiumUtil.sleep(2);

    }


    @Test(priority = 3)
    public void androidBrowserEmulator() {

        log.info("*****"+driver.getContext());

        //TODO -  ANDROID EMULATOR CHOME APP ALWAYS TAKES USER TO WELCOME SCREEN NEED TO BYPASS THAT
        driver.get("http://www.google.com");
        driver.findElement(By.id("lst-ib")).click();
        driver.findElement(By.id("lst-ib")).clear();
        driver.findElement(By.id("lst-ib")).sendKeys("pavan kovurru");
        AppiumUtil.waitForPageToLoad(driver);
        AppiumUtil.sleep(5);
    }

    @Test(priority = 4)
    public void androidBrowserRealDevice() {
        driver.get("http://www.google.com");
        driver.findElement(By.id("lst-ib")).click();
        driver.findElement(By.id("lst-ib")).clear();
        driver.findElement(By.id("lst-ib")).sendKeys("pavan kovurru");
        AppiumUtil.waitForPageToLoad(driver);
        AppiumUtil.sleep(5);
    }


    @Test(priority = 5)
    public void iosSimulatorNativeApp(){

      log.info("IOS driver for simulator successful");
      driver.findElementById("TextView").click();
       AppiumUtil.sleep(3);
       driver.findElementByName("Back").click();
        AppiumUtil.sleep(3);
        driver.findElementByAccessibilityId("TextView").click();
        driver.findElementsByXPath("");

  }






    @AfterMethod
      public void logout() {
      log.info("Appium Driver session terminated");
      //driver.quit();
  }
}
