package com.company.project.page.android;

import com.company.project.utilities.AppiumUtil;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Created by pavankovurru on 7/17/17.
 */
public class AccessibilityPage {

    private Logger log = LogManager.getLogger();
    AppiumUtil appium = null;
    AppiumDriver driver;

    public AccessibilityPage(AppiumDriver driver) {
        this.driver = driver;
        appium = new AppiumUtil(driver);
    }

    public void clickAccessibility() {
        appium.scrollToTextAndroid("Accessibility").click();
    }

    // Accessibility Node Provider
    public void clickAccessibilityNodeProvider() {
        appium.scrollToTextAndroid("Accessibility Node Provider").click();
    }

    public String getAccessibilityNodeProviderText() {
        return driver
                .findElementByXPath("//android.widget.LinearLayout/android.widget.TextView")
                .getText();
    }

    // Accessibility Node Querying
    public void clickAccessibilityNodeQuerying() {
        appium.scrollToTextAndroid("Accessibility Node Querying").click();
    }

    public void checkAllUncheckedTextBoxesInAccessibilityNodeQuerying() {
        List<MobileElement> checkboxes = driver.findElementsByClassName("android.widget.CheckBox");
        log.info("found - " + checkboxes.size() + "checkboxes");
        for (MobileElement mo : checkboxes) {
            if (mo.getAttribute("checked").matches("false")) {
                mo.click();
            }
        }
    }

    public void unCheckAllcheckedTextBoxesInAccessibilityNodeQuerying() {
        List<MobileElement> checkboxes = driver.findElementsByClassName("android.widget.CheckBox");
        for (MobileElement mo : checkboxes) {
            if (mo.getAttribute("checked").matches("true")) {
                mo.click();
            }
        }
    }

    public boolean areAllCheckBoxesSelected() {
        List<MobileElement> checkboxes = driver.findElementsByClassName("android.widget.CheckBox");
        for (MobileElement mo : checkboxes) {
            if (mo.getAttribute("checked").matches("false")) {
                return false;
            }
        }
        return true;
    }

    // Accessibility Service
    public void clickAccessibilityService() {
        appium.scrollToTextAndroid("Accessibility Service").click();
    }

    // Custom View
    public void clickCustomView() {
        appium.scrollToTextAndroid("Custom View").click();
    }
}
