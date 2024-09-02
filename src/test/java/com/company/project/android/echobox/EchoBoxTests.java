package com.company.project.android.echobox;

import com.company.project.utilities.appium.AppiumDriverFactory;
import com.company.project.utilities.appium.AppiumUserSimulations;
import io.appium.java_client.AppiumDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class EchoBoxTests {

    private Logger log = LogManager.getLogger();
    AppiumUserSimulations appium = null;
    AppiumDriver driver = null;
    AppiumDriverFactory driverFactory = null;
    EchoBoxScreen echoBoxScreen = null;
    private String uniqueString = "";


    @BeforeMethod(alwaysRun = true)
    @Parameters({"runOn", "appName"})
    public void invokeApp(String runOn, String appName) {
        log.info("Trying to create appium driver for -" + runOn);
        driverFactory = new AppiumDriverFactory();

        if (runOn.equalsIgnoreCase("AndroidEmulator")){
            driver = driverFactory.createLocalAndroidDriverForEmulator(appName, true);
        } else if(runOn.equalsIgnoreCase("AndroidDevice")){
            driver = driverFactory.createLocalAndroidDriverForEmulator(appName, true);
        } else {
            log.info("Incorrect runOn parameter provided" + runOn);
        }

        log.info("--------------------------------------------------------------------------");
        log.info("Appium driver created for - " + runOn);
        log.info("Targeting app - " + appName);
        log.info("--------------------------------------------------------------------------");

        appium = new AppiumUserSimulations(driver);
        uniqueString = appium.generateUniqueString();
        echoBoxScreen = new EchoBoxScreen(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        log.info("Tearing down driver");
        if (driver != null) {
            driver.quit();
        }
    }

    @Test(priority = 1)
    public void validateNavigationToEchoBoxScreen(){
        echoBoxScreen.navigateToEchoBoxScreen();
        echoBoxScreen.assertEchoBoxScreenTitle();
    }

    @Test(priority = 2)
    public void saveAndValidateMessageOnEchoBoxScreen(){
        echoBoxScreen.navigateToEchoBoxScreen();
        echoBoxScreen.sendDataToMessageInputTextBox(uniqueString);
        echoBoxScreen.saveDateSentToMessageTextBox();
        echoBoxScreen.assertSavedData(uniqueString);
    }

    @Test(priority = 3)
    public void ValidateNavigationFromEchoBoxToLandingScreen(){
        echoBoxScreen.navigateToEchoBoxScreen();
        echoBoxScreen.navigateFromEchoBoxScreenToLandingScreen();
        echoBoxScreen.assertLandingScreenTitle();
    }
}
