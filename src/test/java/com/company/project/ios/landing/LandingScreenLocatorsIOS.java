package com.company.project.ios.landing;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

public class LandingScreenLocatorsIOS {
    public static final By LandingScreenTitle =
            AppiumBy.iOSNsPredicateString(
                    "name == \"UIKitCatalog\" AND type == \"XCUIElementTypeNavigationBar\"");

    //Navigate to
    public static final By alertViewsNavigationFromLandingScreen =
            AppiumBy.xpath("//XCUIElementTypeStaticText[@name=\"Alert Views\"]");
}
