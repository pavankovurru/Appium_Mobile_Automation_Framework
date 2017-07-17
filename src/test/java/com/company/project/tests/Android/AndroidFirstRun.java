package com.company.project.tests.Android;

import com.company.project.utilities.RunOn;
import io.appium.java_client.AppiumDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * Created by pavankovurru on 7/14/17.
 */
public class AndroidFirstRun {

    public static Logger log = LogManager.getLogger();
    static AppiumDriver driver = null;

    @BeforeMethod(alwaysRun = true)
    @Parameters({"runOn", "appName"})
    public void login(String runOn , String appName) {
        driver = RunOn.run(runOn,appName);
        log.info("--------------------------------------------------------------------------");
        log.info("Appium driver created to run on - "+runOn);
        log.info("Targeting app - "+appName);
        log.info("--------------------------------------------------------------------------");
    }

    @Test(priority = 1)
    public void androidEmulator() {

        log.info("Testing a - "+driver.getContext());

//        driver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"Views\")")).click();
//
//        MobileElement el=(MobileElement)driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()"
//                + ".resourceId(\"android:id/list\")).scrollIntoView("
//                + "new UiSelector().text(\"Gallery\"));"));
//
//        System.out.println("****"+el.getLocation());
//        el.click();
//        AppiumUtil.sleep(2);

    }

}
