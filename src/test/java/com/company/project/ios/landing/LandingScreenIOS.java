package com.company.project.ios.landing;

import com.company.project.android.landing.LandingScreenLocatorsAndroid;
import com.company.project.utilities.appium.AppiumUserSimulations;
import io.appium.java_client.AppiumDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LandingScreenIOS {
    private final Logger log = LogManager.getLogger();
    private AppiumUserSimulations appium;
    private AppiumDriver driver;

    public LandingScreenIOS(AppiumDriver driver) {
        this.driver = driver;
        appium = new AppiumUserSimulations(driver);
    }

    // USER ACTIONS
    public void navigateToAlertViewsScreen() {
        appium.click(LandingScreenLocatorsIOS.alertViewsNavigationFromLandingScreen);
    }

    // ASSERTIONS
    public void assertLandingScreenTitle() {
        appium.isDisplayed(LandingScreenLocatorsIOS.LandingScreenTitle);
    }
}
