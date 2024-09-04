package com.company.project.ios.alertviews;

import com.company.project.ios.landing.LandingScreenIOS;
import com.company.project.utilities.appium.AppiumUserSimulations;
import io.appium.java_client.AppiumDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

public class AlertViewsScreen extends LandingScreenIOS {

    private final Logger log = LogManager.getLogger();
    private AppiumUserSimulations appium;
    private AppiumDriver driver;

    public AlertViewsScreen(AppiumDriver driver) {
        super(driver);
        this.driver = driver;
        appium = new AppiumUserSimulations(driver);
    }

    // USER ACTIONS
    // Simple Alert
    public void tapSimpleAlertStyleOnAlertViewsScreen() {
        appium.click(AlertViewsLocators.alertViewsSimpleAlert);
    }

    public void tapSimpleAlertOKButton() {
        appium.click(AlertViewsLocators.alertViewsSimpleAlertOKButton);
    }

    // ASSERTIONS
    public void assertAlertViewsScreenTitle() {
        Assert.assertTrue(appium.isDisplayed(AlertViewsLocators.alertViewsScreenTitle));
    }

    public void assertSimpleAlertView() {
        Assert.assertTrue(appium.isDisplayed(AlertViewsLocators.alertViewsSimpleAlertContainer));
        Assert.assertTrue(appium.isDisplayed(AlertViewsLocators.alertViewsSimpleAlertTitle));
        Assert.assertTrue(appium.isDisplayed(AlertViewsLocators.alertViewsSimpleAlertMessage));
        Assert.assertTrue(appium.isDisplayed(AlertViewsLocators.alertViewsSimpleAlertOKButton));
    }

    public void assertSimpleAlertViewNotPresent() {
        Assert.assertFalse(appium.isDisplayed(AlertViewsLocators.alertViewsSimpleAlertContainer));
    }


    
}
