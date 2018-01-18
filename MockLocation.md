
## ANDROID 

Location faking in android device can be done using Appium 

```
Location loc = new Location(latitude, longitude, 1000); // latitude, longitude, altitude
driver.setLocation(loc);
```

## IOS

FAKING LOCATION IN A REAL IOS DEVICE USING XCODE GPX FILES.

- Create a basic xcode project, make sure to use the `organization identifier` that is used while creating and signing `WebDriverAgentRunner` project.  
- Create a gpx file and add lat long values to it.  
- Goto Product --> Scheme --> Edit Scheme --> Run -->Options --> Set the GPX file created earlier in `Default location`.  
- Close the project.  
- Follow the same procedure to create a new xcode project with a diff location.


Xcode will let user to run the run a project with a default fake location targeting a Ios device,   
This will change the location of the device according to the coordinates set in the GPX file.
Use `AppiumForMac` to automate xcode in mac , Selenium and Appium jars are required.

Ex:

```
      DesiredCapabilities capabilities = new DesiredCapabilities();
      capabilities.setCapability("platform", "Mac");
      driver = new RemoteWebDriver(new URL("http://127.0.0.1:4622/wd/hub"), capabilities);
      driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
  
       driver.get("Xcode");
       Actions action = new Actions(driver);
       
       action.doubleClick(driver.findElement(By.xpath("xpath goes here"))).build().perform();
       action.click(driver.findElement(By.xpath("xpath goes here"))).build().perform();
       action.moveToElement(driver.findElement(By.xpath("xpath goes here"))).build().perform();

```  

Note : fake location stays until the project is stopped in xcode.
