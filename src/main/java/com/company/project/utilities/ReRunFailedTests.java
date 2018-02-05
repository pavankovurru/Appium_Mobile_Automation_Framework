package com.company.project.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/** Created by pavankovurru on 12/27/16. */
public class ReRunFailedTests implements IRetryAnalyzer {

  private int retryCount = 0;
  private int maxRetryCount = 1;

  public boolean retry(ITestResult result) {
    if (retryCount < maxRetryCount) {
      System.out.println(
          "Retrying test "
              + result.getName()
              + " with status "
              + getResultStatusName(result.getStatus())
              + " for the "
              + (retryCount + 1)
              + " time(s).");
      retryCount++;
      return true;
    }
    return false;
  }

  public String getResultStatusName(int status) {
    String resultName = null;
    if (status == 1) resultName = "SUCCESS";
    if (status == 2) resultName = "FAILURE";
    if (status == 3) resultName = "SKIP";
    return resultName;
  }
}
