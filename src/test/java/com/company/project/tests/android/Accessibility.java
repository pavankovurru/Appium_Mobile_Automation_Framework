package com.company.project.tests.android;

import com.company.project.page.android.AccessibilityPage;
import com.company.project.utilities.AppiumUtil;
import com.company.project.utilities.RunOn;
import io.appium.java_client.AppiumDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;

/**
 * Created by pavankovurru on 7/14/17.
 */
public class Accessibility {

    private Logger log = LogManager.getLogger();
    AppiumUtil appium = null;
    RunOn run_on = new RunOn();
    AppiumDriver driver = null;
    AccessibilityPage accessibilitypage = null;

    @BeforeMethod(alwaysRun = true)
    @Parameters({"runOn", "appName"})
    public void invokeApp(String runOn, String appName) {
        driver = run_on.run(runOn, appName);
        log.info("--------------------------------------------------------------------------");
        log.info("Appium driver created for - " + runOn);
        log.info("Targeting app - " + appName);
        log.info("--------------------------------------------------------------------------");
        appium = new AppiumUtil(driver);
        accessibilitypage = new AccessibilityPage(driver);
        accessibilitypage.clickAccessibility();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        log.info("Tearing down driver");
        if (driver != null) {
            driver.quit();
        }
    }


    @Test (groups = { "funtional", "positive" })
    public void validateAccessibilitySubLinks() {
        accessibilitypage.clickAccessibilityNodeProvider();
        appium.androidBackKeyEvent();
        accessibilitypage.clickAccessibilityNodeQuerying();
        appium.androidBackKeyEvent();
        accessibilitypage.clickAccessibilityService();
        appium.androidBackKeyEvent();
        accessibilitypage.clickCustomView();
    }

    @Test (groups = { "funtional", "positive" })
    public void validatePresenceOfAccessibilityNodeProvider() {
        accessibilitypage.clickAccessibilityNodeProvider();
        Assert.assertEquals(
                accessibilitypage.getAccessibilityNodeProviderText(),
                "Enable TalkBack and Explore-by-touch from accessibility settings. Then touch the colored squares.");
    }

    @Test (groups = { "funtional", "positive" })
    public void validateAccessibilityNodeQueryingCheckBoxes() {
        accessibilitypage.clickAccessibilityNodeQuerying();
        accessibilitypage.checkAllUncheckedTextBoxesInAccessibilityNodeQuerying();
        Assert.assertTrue(accessibilitypage.areAllCheckBoxesSelected(),"All Checkboxes in Accesibility node querying page are not selected");
        accessibilitypage.unCheckAllcheckedTextBoxesInAccessibilityNodeQuerying();
        Assert.assertFalse(accessibilitypage.areAllCheckBoxesSelected(),"All Checkboxes in Accesibility node querying page are selected");
        appium.androidBackKeyEvent();
    }

    @Test (groups = { "funtional", "positive" })
    public void validateAccessibilityService() {
        accessibilitypage.clickAccessibilityService();
        driver.findElementById("io.appium.android.apis:id/button").click();
        Assert.assertTrue(
                appium.androidScrollToText("Accessibility").getText().equals("Accessibility"));
    }


}
