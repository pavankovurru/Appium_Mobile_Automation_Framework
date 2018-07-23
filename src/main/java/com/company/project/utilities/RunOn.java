package com.company.project.utilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by pavankovurru on 5/19/17.
 */
public class RunOn {

    private static Logger log = LogManager.getLogger();
    private AppiumDriver driver;
    private DesiredCapabilities cap;
    private String deviceName;
    private String udid;
    private String bundleId;

    public AppiumDriver run(String runOn, String appName) {

        // Creating Android driver object based on parameters provided
        String run = runOn;
        String appname = System.getProperty("user.dir") + "/src/main/resources/" + appName;

        switch (run) {

            // ANDROID NATIVE APP
            case "AndroidEmulatorNativeApp":
                driver = createLocalAndroidDriver_For_Emulator(appname);
                log.info(driver.getContext());
                break;

            case "AndroidDeviceNativeApp":
                driver = createLocalAndroidDriver_For_RealDevice(appname);
                break;

            // ANDROID WEB APP
            case "AndroidEmulatorWebApp":
                driver = createLocalAndroidDriver_For_WebApp_In_Emulator("Chrome");
                break;

            case "AndroidDeviceWebApp":
                driver = createLocalAndroidDriver_For_WebApp_In_RealDevice("Chrome");
                break;

            // TODO RUN ON BROWSER STACK, SAUCE LABS ..
            default:
                throw new IllegalArgumentException(
                        "Invalid parameter used in Environment.properties file : " + run);
        }
        return driver;
    }


    public AppiumDriver run(String runOn, String appName, String deviceName, String udid, String bundleId) {

        // Creating IOS driver object based on parameters provided
        String run = runOn;
        String appname = System.getProperty("user.dir") + "/src/main/resources/" + appName;

        switch (run) {

            // IOS NATIVE APP
            case "IosSimulatorNativeApp":
                driver = createLocalIOSDriver_For_NativeApp_In_Simulator(appname);
                break;

            case "IosDeviceNativeApp":
                driver =
                        createLocalIOSDriver_For_NativeApp_In_IOSDEVICE(
                                appname, deviceName, udid, bundleId);
                break;

            // IOS WEB APP
            case "IosSimulatorWebApp":
                driver = createLocalIOSDriver_For_WebApp_In_Simulator("Safari");
                break;

            case "IosDeviceWebApp":
                driver =
                        createLocalIOSDriver_For_WebApp_In_IOSDEVICE(
                                "Safari", deviceName, "TEST ORG ID");
                break;

            // TODO RUN ON BROWSER STACK, SAUCE LABS ..
            default:
                throw new IllegalArgumentException(
                        "Invalid parameter used in Environment.properties file : " + run);
        }
        return driver;
    }

    // *********************    LOCAL RUN METHODS     ************************//

    // Android

    public AppiumDriver createLocalAndroidDriver_For_Emulator(String appName) {

        cap = new DesiredCapabilities();
        cap.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
        cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
        cap.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 60);
        cap.setCapability(MobileCapabilityType.APP, appName);
        cap.setCapability("noReset", true);

        try {
            driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), cap);
            log.info("Android Driver object created for emulator");
            return driver;
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        return driver;
    }

    public AppiumDriver createLocalAndroidDriver_For_RealDevice(String appName) {

        cap = new DesiredCapabilities();
        cap.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
        cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
        cap.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Device");
        cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 120);
        cap.setCapability(MobileCapabilityType.APP, appName);
        cap.setCapability("noReset", true);


        try {
            driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), cap);
            log.info("Android Driver object created for Real android Device");
            return driver;
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        return driver;
    }

    public AppiumDriver createLocalAndroidDriver_For_RealDeviceUsing_AppPackageActivity(
            String appName, String appPackage, String appActivity, String udid) {

        cap = new DesiredCapabilities();
        cap.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
        cap.setCapability("noReset", true);
        cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
        cap.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Device");
        cap.setCapability(MobileCapabilityType.UDID, udid);
        cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 200);
        cap.setCapability("appPackage", appPackage);
        cap.setCapability("appActivity", appActivity);
        try {
            driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), cap);
            log.info("Android Driver object created for Real android Device");
            return driver;
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }

        return driver;
    }

    public AppiumDriver createLocalAndroidDriver_For_WebApp_In_Emulator(String browserName) {

        cap = new DesiredCapabilities();
        cap.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
        cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
        cap.setCapability(MobileCapabilityType.BROWSER_NAME, browserName);
        cap.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 60);

        try {
            driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), cap);
            log.info("Android Driver object created for Web App in  android Emulator");
            return driver;
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }

        return driver;
    }

    public AppiumDriver createLocalAndroidDriver_For_WebApp_In_RealDevice(String browserName) {

        cap = new DesiredCapabilities();
        cap.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
        cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
        cap.setCapability(MobileCapabilityType.BROWSER_NAME, browserName);
        cap.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Device");
        cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "60");

        try {
            driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), cap);
            log.info("Android Driver object created for Web App in  android Device");
            return driver;
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }

        return driver;
    }

    // ******** IOS **********//

    public AppiumDriver createLocalIOSDriver_For_NativeApp_In_Simulator(String appName) {

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
            log.info("IOS Driver object created for Simulator");
            return driver;
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        return driver;
    }

    public AppiumDriver createLocalIOSDriver_For_NativeApp_In_IOSDEVICE(
            String appName, String deviceName, String udid, String bundleID) {

        cap = new DesiredCapabilities();
        cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
        cap.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
        cap.setCapability(MobileCapabilityType.APP, appName);
        cap.setCapability("noReset", true);
        cap.setCapability("xcodeOrgId", "84CK77G588");
        cap.setCapability("xcodeSigningId", "iPhone Developer");
        cap.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
        cap.setCapability(MobileCapabilityType.UDID, udid);
        cap.setCapability("updatedWDABundleId", bundleID);

        try {
            driver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), cap);
            log.info("IOS Driver object created for IOS DEVICE");
            return driver;
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        return driver;
    }

    public AppiumDriver createLocalIOSDriver_For_WebApp_In_Simulator(String browserName) {

        cap = new DesiredCapabilities();
        cap.setCapability(MobileCapabilityType.BROWSER_NAME, browserName);
        cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
        cap.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
        cap.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone Simulator");
        cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "60");
        cap.setCapability(IOSMobileCapabilityType.LAUNCH_TIMEOUT, 500000);

        try {
            driver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), cap);
            log.info("IOS Driver object created for Simulator");
            return driver;
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        return driver;
    }

    public AppiumDriver createLocalIOSDriver_For_WebApp_In_IOSDEVICE(
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
            driver = new IOSDriver(new URL("http://127.0.0.1:4727/wd/hub"), cap);
            log.info("IOS Driver object created for IOS DEVICE");
            return driver;
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        return driver;
    }

    // **** REMOTE RUN (BROWSER-STACK ,SAUCE-LABS , AWS ...ETC ) ****//
    // *** AWS DEVICE FARM RUN ***//

    public AppiumDriver createAndroidDriver_AWS_Device_Farm() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        URL url = null;
        try {
            url = new URL("http://127.0.0.1:4723/wd/hub");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        log.info("Android Driver created for AWS Device farm");

        driver = new AndroidDriver<MobileElement>(url, capabilities);

        return driver;
    }

}
