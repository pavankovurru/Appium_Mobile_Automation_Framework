package com.company.project.utilities;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class TestListener extends TestListenerAdapter {

  WebDriver driver;
  public static Logger log = LogManager.getLogger();

  @Override
  public void onTestFailure(ITestResult result) {
    log.error(result.getName() + " ***** TEST FAILED *****");
    driver = AppiumUtil.driver;
    String testClassName = getTestClassName(result.getInstanceName()).trim();
    String testMethodName = result.getName().toString().trim();
    String screenShotName = testMethodName + ".png";

    if (driver != null) {
      String imagePath =
          System.getProperty("user.dir")
              + "/Screenshots"
              + "/"
              + testClassName
              + "/"
              + takeScreenShot(driver, screenShotName, testClassName);
      log.info("Screenshot is placed at : " + imagePath);
    }
  }

  @Override
  public void onTestSkipped(ITestResult result) {
    result.setStatus(2);
  }

  public static String takeScreenShot(WebDriver driver, String screenShotName, String testName) {
    try {
      File file = new File("Screenshots");
      if (!file.exists()) {
        log.info("Directory created " + file);
        file.mkdir();
      }

      driver =
          (RemoteWebDriver)
              new Augmenter().augment(driver); // remote web driver screenshot configuration.
      File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
      File targetFile = new File("Screenshots/" + testName, screenShotName);
      FileUtils.copyFile(screenshotFile, targetFile);
      return screenShotName;

    } catch (Exception e) {
      log.error("An exception occured while taking screenshot " + e.getCause());
      return null;
    }
  }

  public String getTestClassName(String testName) {
    Matcher m = Pattern.compile(".*\\.(.+?)$").matcher(testName);
    if (m.find()) {
      return m.group(1);
    }
    return testName;
  }
}
