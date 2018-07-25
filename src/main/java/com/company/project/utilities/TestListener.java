package com.company.project.utilities;

import io.appium.java_client.AppiumDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

public class TestListener implements ITestListener {

  @Override
  public void onTestStart(ITestResult iTestResult) {}

  @Override
  public void onTestSuccess(ITestResult iTestResult) {}

  @Override
  public void onTestFailure(ITestResult iTestResult) {
    Class c = iTestResult.getTestClass().getRealClass();
    try {
      Field field = c.getDeclaredField("driver");
      field.setAccessible(true);

      AppiumDriver<?> driver = (AppiumDriver<?>) field.get(iTestResult.getInstance());

      File file = driver.getScreenshotAs(OutputType.FILE);

      // the filename is the folder name on test.screenshot.path property plus the completeTestName
      FileUtils.copyFile(
          file,
          new File(
              System.getProperty("user.dir")
                  + "/screenshots"
                  + "/"
                  + fileName(iTestResult)
                  + ".png"));

    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onTestSkipped(ITestResult iTestResult) {}

  @Override
  public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {}

  @Override
  public void onStart(ITestContext iTestContext) {}

  @Override
  public void onFinish(ITestContext iTestContext) {}

  private String fileName(ITestResult iTestResult) {
    StringBuffer completeFileName = new StringBuffer();

    completeFileName.append(
        iTestResult.getTestClass().getRealClass().getSimpleName()); // simplified class name
    completeFileName.append("_TestCase_");
    completeFileName.append(iTestResult.getName()); // method name

    // all the parameters information
    Object[] parameters = iTestResult.getParameters();
    for (Object parameter : parameters) {
      completeFileName.append("_");
      completeFileName.append(parameter);
    }

    // return the complete name and replace : by - (in the case the emulator have port as device
    // name)
    return completeFileName.toString().replace(":", "-");
  }
}
