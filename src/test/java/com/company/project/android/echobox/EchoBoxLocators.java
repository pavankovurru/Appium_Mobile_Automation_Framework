package com.company.project.android.echobox;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

public class EchoBoxLocators {

    //echo box locators
    public static final By echoBoxScreenTitle = AppiumBy.androidUIAutomator("new UiSelector().text(\"Echo Screen\")");
    public static final By echoBoxMessageInputTextBox = AppiumBy.accessibilityId("messageInput");
    public static final By echoBoxMessageInputSaveButton = AppiumBy.accessibilityId("messageSaveBtn");
    public static final By echoBoxSavedMessage = AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"savedMessage\")");

    //Navigate back to landing screen
    public static final By navigateBackFromEchoBoxToLandingScreenButton = AppiumBy.accessibilityId("Navigate Up");
}
