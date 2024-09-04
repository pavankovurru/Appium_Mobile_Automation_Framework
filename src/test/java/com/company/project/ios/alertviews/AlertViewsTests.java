package com.company.project.ios.alertviews;

import com.company.project.utilities.appium.AppiumDriverFactory;
import com.company.project.utilities.appium.AppiumUserSimulations;
import io.appium.java_client.AppiumDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class AlertViewsTests {
    private Logger log = LogManager.getLogger();
    AppiumUserSimulations appium = null;
    AppiumDriver driver = null;
    AppiumDriverFactory driverFactory = null;
    AlertViewsScreen alertViewsScreen = null;

    @BeforeMethod(alwaysRun = true)
    @Parameters({"runOn", "appName", "udid"})
    public void invokeApp(String runOn, String appName, String udid) {
        log.info("Trying to create appium driver for -" + runOn);
        driverFactory = new AppiumDriverFactory();

        if (runOn.equalsIgnoreCase("IOSSimulator")){
            driver = driverFactory.createLocalIOSDriverForSimulator(appName, udid, false);
        } else if(runOn.equalsIgnoreCase("AndroidDevice")){
            driver = driverFactory.createLocalIOSDriverForPhysicalDevice(appName,udid,"", false);
        } else {
            log.info("Incorrect runOn parameter provided" + runOn);
        }

        log.info("--------------------------------------------------------------------------");
        log.info("Appium driver created for - " + runOn);
        log.info("Targeting app - " + appName);
        log.info("--------------------------------------------------------------------------");

        appium = new AppiumUserSimulations(driver);
        alertViewsScreen = new AlertViewsScreen(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        log.info("Tearing down driver");
        if (driver != null) {
            driver.quit();
        }
    }

    @Test(priority = 1)
    public void validateNavigationToAlertViewsScreen(){
        alertViewsScreen.navigateToAlertViewsScreen();
        alertViewsScreen.assertAlertViewsScreenTitle();
    }

    @Test(priority = 2)
    public void validateSimpleAlertViewOnAlertViewsScreen(){
        alertViewsScreen.navigateToAlertViewsScreen();
        alertViewsScreen.tapSimpleAlertStyleOnAlertViewsScreen();
        alertViewsScreen.assertSimpleAlertView();
    }

    @Test(priority = 3)
    public void ValidateDismissSimpleAlertAction(){
        alertViewsScreen.navigateToAlertViewsScreen();
        alertViewsScreen.tapSimpleAlertStyleOnAlertViewsScreen();
        alertViewsScreen.tapSimpleAlertOKButton();
        alertViewsScreen.assertSimpleAlertViewNotPresent();
    }

}
