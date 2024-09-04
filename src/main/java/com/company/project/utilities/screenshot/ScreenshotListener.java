package com.company.project.utilities.screenshot;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

public class ScreenshotListener implements ITestListener {

    private WebDriver driver;

    @Override
    public void onTestStart(ITestResult result) {

        // Get the WebDriver instance from the test class
        Object currentClass = result.getInstance();
        try {
            driver = (WebDriver) currentClass.getClass().getDeclaredField("driver").get(currentClass);
        } catch (Exception e) {
            // Handle exceptions if the driver field is not accessible
            e.printStackTrace();
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
            if (driver != null) {
                // Take a screenshot
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

                // Save the screenshot with a meaningful name
                try {
                    // Adjust the path if needed, ensure it's relative to the TestNG report output directory
                    String dateName = new SimpleDateFormat("yyyy/MM/dd/hh:mm:ss").format(new Date());
                    String screenshotPath = System.getProperty("user.dir") + File.separator + "test-screenshots" + File.separator +dateName + "-" +result.getName() + ".png";
                    FileUtils.copyFile(screenshot, new File(screenshotPath));

                    // Attach the screenshot to the TestNG report
                    Reporter.log("<a href='" + screenshotPath + "'> <img src='" + screenshotPath + "' height='200' width='200'/> </a>");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onTestSuccess (ITestResult result){
            // Not needed for taking screenshots on failure
        }

        @Override
        public void onTestSkipped (ITestResult result){
            // Not needed for taking screenshots on failure
        }

        @Override
        public void onTestFailedButWithinSuccessPercentage (ITestResult result){
            // Not needed for taking screenshots on failure
        }

        @Override
        public void onStart (ITestContext context){
            // Not needed for taking screenshots on failure
        }

        @Override
        public void onFinish (ITestContext context){
            // Not needed for taking screenshots on failure
        }
    }
