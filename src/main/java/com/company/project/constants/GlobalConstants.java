package com.company.project.constants;

public class GlobalConstants {

  // ****** DRIVER EXECUTABLE FILE PATHS *******************************

  // ****** CHROME ******

  // Windows Chrome driver location
  public static final String WINDOWS_CHROME_DRIVER_PATH =
      System.getProperty("user.dir") + "/src/main/resources/remoteWebDrivers/chromedriver_old.exe";

  // Mac Chrome driver location
  public static final String MAC_CHROME_DRIVER_PATH =
      System.getProperty("user.dir") + "/src/main/resources/remoteWebDrivers/chromedriver_old";

  // ****** FIREFOX ******

  // Mac Firefox Gecko driver location
  public static final String MAC_FIREFOX_GECKO_DRIVER_PATH =
      System.getProperty("user.dir") + "/src/main/resources/remoteWebDrivers/geckodriver";

  // Windows Firefox Gecho driver location
  public static final String WINDOWS_FIREFOX_GECKO_DRIVER_PATH =
      System.getProperty("user.dir") + "/src/main/resources/remoteWebDrivers/geckodriver.exe";

  // ****** MICROSOFT WEB DRIVER ******

  public static final String MICROSOFT_WEB_DRIVER_DRIVER_PATH =
      System.getProperty("user.dir")
          + "/src/main/resources/remoteWebDrivers/MicrosoftWebDriver.exe";
}
