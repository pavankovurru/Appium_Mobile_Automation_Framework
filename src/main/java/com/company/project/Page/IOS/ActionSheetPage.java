package com.company.project.Page.IOS;

import io.appium.java_client.AppiumDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ActionSheetPage {

    private static final Logger log = LogManager.getLogger();
    AppiumDriver driver;

    public ActionSheetPage(AppiumDriver driver) {
        this.driver = driver;
    }


    public void clickBackButton(){
        driver.findElementByAccessibilityId("Back").click();
    }

    public void clickOnActionSheets(){
        driver.findElementByAccessibilityId("Action Sheets").click();
    }

    public void clickOKCancelButton(){
        driver.findElementByAccessibilityId("Okay / Cancel").click();
    }

    public void clickOK(){
        driver.findElementByName("OK").click();
    }

    public void clickCancel(){
        driver.findElementByName("Cancel").click();
    }


    public boolean validatePageLanding( String label){
        if (driver.findElementByAccessibilityId(label).getText().matches(label)){
            return true;
        }
        return false;
    }


}
