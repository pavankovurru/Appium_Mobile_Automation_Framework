package com.company.project.android.landing;

import com.company.project.utilities.appium.AppiumUserSimulations;
import io.appium.java_client.AppiumDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LandingScreen {

    private final Logger log = LogManager.getLogger();
    private AppiumUserSimulations appium;
    private AppiumDriver driver;

    public LandingScreen(AppiumDriver driver) {
        this.driver = driver;
        appium = new AppiumUserSimulations(driver);
    }

    // USER ACTIONS
    public void navigateToEchoBoxScreen() {
        appium.click(LandingScreenLocators.echoBoxNavigationLinkFromLanding);
    }

    // ASSERTIONS
    public void assertLandingScreenTitle() {
        appium.isDisplayed(LandingScreenLocators.LandingScreenTitle);
    }
}
