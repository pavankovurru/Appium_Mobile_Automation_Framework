package com.company.project.utilities.retry;

import org.testng.ITestResult;

import org.testng.IRetryAnalyzer;

public class RetryAnalyzer implements IRetryAnalyzer {

  private int retryCount = 0;
  private static final int maxRetryCount = 1; // set desired retry limit here

  @Override
  public boolean retry(ITestResult result) {
    if (retryCount < maxRetryCount) {
      retryCount++;
      return true; // Retry the test
    }
    return false; //  Stop retrying test
  }
}
