package com.company.project.utilities;

import java.util.Set;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

/** Created by pavankovurru on 12/27/16. */
public class TestCountResetterAfterReRun implements ITestListener {
  @Override
  public void onFinish(ITestContext context) {
    Set<ITestResult> failedTests = context.getFailedTests().getAllResults();
    Set<ITestResult> skippedTests = context.getSkippedTests().getAllResults();
    for (ITestResult temp : failedTests) {
      ITestNGMethod method = temp.getMethod();
      if (context.getFailedTests().getResults(method).size() > 1) {
        failedTests.remove(temp);
      } else {
        if (context.getPassedTests().getResults(method).size() > 0) {
          failedTests.remove(temp);
        }
      }
    }
    for (ITestResult temp : skippedTests) {
      ITestNGMethod method = temp.getMethod();
      if (context.getSkippedTests().getResults(method).size() > 1) {
        skippedTests.remove(temp);
      } else {
        if (context.getPassedTests().getResults(method).size() > 0) {
          skippedTests.remove(temp);
        }
      }
    }
  }

  public void onTestStart(ITestResult result) {}

  public void onTestSuccess(ITestResult result) {}

  public void onTestFailure(ITestResult result) {}

  public void onTestSkipped(ITestResult result) {}

  public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}

  public void onStart(ITestContext context) {}
}
