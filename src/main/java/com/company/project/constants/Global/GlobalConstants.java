package com.company.project.constants.Global;

public class GlobalConstants {
	

//****************************************************************** ENVIRONMENT FILE PATH *******************************************************************

// Configuration property file location
public static final String ENVIRONMENT_PROPERTY_PATH = System.getProperty("user.dir") + "/src/main/java/com/company/project/properties/Environment.properties";

//****************************************************************** RESOURCE FILE PATHS *******************************************************************

// Windows Chrome driver location
public static final String WINDOWS_CHROME_DRIVER_PATH = System.getProperty("user.dir") + "/src/main/resources/chromedriver.exe";
				
// Mac Chrome driver location
public static final String MAC_CHROME_DRIVER_PATH = System.getProperty("user.dir") + "/src/main/resources/chromedriver";

// Mac Firefox Gecho driver location
public static final String MAC_FIREFOX_GECKO_DRIVER_PATH = System.getProperty("user.dir") + "/src/main/resources/geckodriver";

}
