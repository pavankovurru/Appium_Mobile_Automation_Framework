package com.company.project.tests.android;

import com.company.project.page.android.WebViewPage;
import com.company.project.utilities.RunOn;
import io.appium.java_client.AppiumDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.*;

/**
 * Created by pavankovurru on 7/14/17.
 */
public class Webview {

    private Logger log = LogManager.getLogger();
    AppiumDriver driver = null;
    RunOn run_on = new RunOn();
    WebViewPage webviewpage = null;

    @BeforeMethod(alwaysRun = true)
    @Parameters({"runOn", "appName"})
    public void invokeApp(String runOn, String appName) {
        driver = run_on.run(runOn, appName);
        log.info("--------------------------------------------------------------------------");
        log.info("Appium driver created for - " + runOn);
        log.info("Targeting app - " + appName);
        log.info("--------------------------------------------------------------------------");
        webviewpage = new WebViewPage(driver);
        webviewpage.clickViews();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        log.info("Tearing down driver");
        if (driver != null) {
            driver.quit();
        }
    }

    @Test(groups = {"funtional", "positive"})
    public void validateAccessibilityLink() {
        log.info("Testing a - " + driver.getContext());
        webviewpage.clickWebView();
        webviewpage.switchToWebView();
        webviewpage.switchToNative();
    }
}
