package com.company.project.android.echobox;

import com.company.project.android.landing.LandingScreenAndroid;
import com.company.project.utilities.appium.AppiumUserSimulations;
import io.appium.java_client.AppiumDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

public class EchoBoxScreen extends LandingScreenAndroid {

    private final Logger log = LogManager.getLogger();
    private AppiumUserSimulations appium;
    private AppiumDriver driver;

    public EchoBoxScreen(AppiumDriver driver) {
        super(driver);
        this.driver = driver;
        appium = new AppiumUserSimulations(driver);
    }

    // USER ACTIONS
    public void sendDataToMessageInputTextBox(String uniqueString) {
        appium.click(EchoBoxLocators.echoBoxMessageInputTextBox);
        appium.clear(EchoBoxLocators.echoBoxMessageInputTextBox);
        appium.sendKeys(EchoBoxLocators.echoBoxMessageInputTextBox, uniqueString);
    }

    public void saveDateSentToMessageTextBox() {
        appium.click(EchoBoxLocators.echoBoxMessageInputSaveButton);
    }

    public void navigateFromEchoBoxScreenToLandingScreen() {
        appium.click(EchoBoxLocators.navigateBackFromEchoBoxToLandingScreenButton);
    }

    // ASSERTIONS
    public void assertEchoBoxScreenTitle() {
        Assert.assertTrue(appium.isDisplayed(EchoBoxLocators.echoBoxScreenTitle));
    }

    public void assertSavedData(String uniqueString) {
        String inputFromApp = appium.getText(EchoBoxLocators.echoBoxSavedMessage);
        Assert.assertEquals(inputFromApp, uniqueString);
    }
}
