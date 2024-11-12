package com.company.project.ios.alertviews;

import com.company.project.utilities.appium.AppiumDriverFactory;
import com.company.project.utilities.appium.AppiumUserSimulations;
import com.company.project.utilities.imageComaprison.ImageComparisonHelper;
import com.github.romankh3.image.comparison.model.Rectangle;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AlertViewsTests {
    private Logger log = LogManager.getLogger();
    AppiumUserSimulations appium = null;
    AppiumDriver driver = null;
    AppiumDriverFactory driverFactory = null;
    AlertViewsScreen alertViewsScreen = null;
    ImageComparisonHelper imageComparisonHelper = null;

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
        imageComparisonHelper = new ImageComparisonHelper(driver);
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
    public void AlertViewsScreenImageComparisonTest() throws Exception {
        String screenshotName = "AlertViewsScreenImageComparisonTest";
        alertViewsScreen.navigateToAlertViewsScreen();

        //Pre-requisites & Navigation
        alertViewsScreen.navigateToAlertViewsScreen();

        //Offset multiple to match appium rectangle and image comparison rectangle
        int offsetMultiple = 3;

        //Excluded Top 5 % of the screen
        Dimension screenSize = driver.manage().window().getSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        int screenHeightTopPart = (int) (screenHeight * 0.05);
        Rectangle rectangleTop = new Rectangle(  0,  0,  screenWidth * offsetMultiple, screenHeightTopPart * offsetMultiple);

        //Excluded Bottom 5% of the screen
        int screenHeightBottomPart = (int) (screenHeight * 0.95);
        Rectangle rectangleBottom = new Rectangle( 0, screenHeightBottomPart * offsetMultiple, screenWidth * offsetMultiple, screenHeight * offsetMultiple);

        //Excluded Element
        Dimension elementSize = driver.findElement(AppiumBy.accessibilityId("test-Username")).getSize();
        int elementWidth = elementSize.width;
        int elementHeight = elementSize.height;
        Point location = driver.findElement(AppiumBy.accessibilityId("test-Username")).getLocation();
        Rectangle rectangleElement = new Rectangle(location.getX() * offsetMultiple, location.getY() * offsetMultiple
                , (location.getX() +elementWidth) * offsetMultiple, (location.getY() + elementHeight) * offsetMultiple);

        //Update excluded areas
        List<Rectangle> excludedAreas = new ArrayList<>;
        excludedAreas.add (rectangleTop);
        excludedAreas.add (rectangleBottom);
        excludedAreas.add(rectangleElement);

        //Take Actual Screenshot
        log.info("Taking Current Screenshot");
        File actualScreenshot = imageComparisonHelper.captureScreenshot("screenshots/actual", screenshotName);

        //Get Baseline Screenshot, Take a new screenshot if baseline is not present Log.info"Gathering Baseline Screenshot a.");
        File baseline = new File(imageComparisonHelper.getBaselineScreenshot("screenshots/baseline", screenshotName));

        //Comparing Screenshots
        log.info("Comparing -> Baseline And Actual <- Screenshots");
        String resultLocation = System. getProperty("user,din") + File.separator + "screenshots/results" + File.separator + screenshotName + "-png";
        imageComparisonHelper.compareImages(baseline, actualScreenshot, resultLocation, excludedAreas);
    }

    @Test(priority = 3)
    public void validateSimpleAlertViewOnAlertViewsScreen(){
        alertViewsScreen.navigateToAlertViewsScreen();
        alertViewsScreen.tapSimpleAlertStyleOnAlertViewsScreen();
        alertViewsScreen.assertSimpleAlertView();
    }

    @Test(priority = 4)
    public void ValidateDismissSimpleAlertAction(){
        alertViewsScreen.navigateToAlertViewsScreen();
        alertViewsScreen.tapSimpleAlertStyleOnAlertViewsScreen();
        alertViewsScreen.tapSimpleAlertOKButton();
        alertViewsScreen.assertSimpleAlertViewNotPresent();
    }

}
