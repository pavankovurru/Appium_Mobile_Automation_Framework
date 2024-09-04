package com.company.project.android.landing;

import com.company.project.utilities.appium.AppiumUserSimulations;
import io.appium.java_client.AppiumDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LandingScreenAndroid {

    private final Logger log = LogManager.getLogger();
    private AppiumUserSimulations appium;
    private AppiumDriver driver;

    public LandingScreenAndroid(AppiumDriver driver) {
        this.driver = driver;
        appium = new AppiumUserSimulations(driver);
    }

    // USER ACTIONS
    public void navigateToEchoBoxScreen() {
        appium.click(LandingScreenLocatorsAndroid.echoBoxNavigationLinkFromLanding);
    }

    // ASSERTIONS
    public void assertLandingScreenTitle() {
        appium.isDisplayed(LandingScreenLocatorsAndroid.LandingScreenTitle);
    }
}
