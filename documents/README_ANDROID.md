
# ANDROID

## ANDROID CONFIGURATIONS 

1. Install JAVA.  
2. Install Android SDK.    
3. Create ANDROID_HOME environment variable which contains path of android sdk.     
4. Update path variable to include $ANDROID_HOME/Platform_tools folder.  
5. Update path variable to include $ANDROID_HOME/tools folder.  

```
nano ~/.bash_profile 

Add lines:
export ANDROID_HOME=/Users/`USER`/Library/Android/sdk
export PATH=$ANDROID_HOME/platform-tools:$PATH
export PATH=$ANDROID_HOME/tools/bin:$PATH

Reopen terminal and check if it worked:
source ~/.bash_profile
echo $ANDROID_HOME

```

6. Create an android emulator and have it up and running  along with appium server if tests are run locally on android emulator.  


## ANDROID ELEMENT LOCATOR TOOLS  

1) `$ANDROID_HOME/tools/bin/uiautomatorviewer`

Have the app running, open `UIAutomatorViewer` and click on the device screen shot icon to locate elements of the running page in an emulator/Android Device. Hover over the mouse on the element and observe (node detail) section present at lower bottom corner of the uiautomatorviewer Which lists out all available attribute names and values.

```
Similar to selenium "resource-ID" value can be used in driver.findElementById() method , "class" value can be used in driver.findElementByClassName() method.
```

2) `By XPATH`

Examples:  `android.widget.TextView` is the class name displayed in ui automator viewer. text, index and content-desc are attributes shown in ui automator viewer.

`driver.findElementByXPath("//android.widget.LinearLayout/android.widget.TextView")`

```
driver.findElementByXPath("//android.widget.TextView[@text='Views']").click();
driver.findElementByXPath("//android.widget.TextView[@index='1']").click();
driver.findElementByXPath("//android.widget.TextView[@content-desc='App']").click();
```
 

3) `AndroidUIAutomator.`

Examples :   
`driver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"Text goes here\")")).click();`
`driver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"id goes here")")).click();`
`driver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().description(\"content-desc\")")).click();`



## ANDROID KEY EVENTS HOME,BACK,MULTITASKING..etc..

```
((AndroidDriver)driver).pressKey(new KeyEvent(AndroidKey.BACK));  

((AndroidDriver)driver).pressKey(new KeyEvent(KEYCODE_APP_SWITCH));  

((AndroidDriver)driver).pressKey(new KeyEvent(AndroidKey.HOME));  

driver.longPressKey(new KeyEvent(AndroidKey.POWER));   -- > To power Off Device (notice `longPressKey` method)  
  ```
  

## ANDROID WEB APPS - Identifying Elements

1) `User Agent` - A Web page will be rendered depending on the user agent, Web agent of a mobile browser will be different from a computer browser.

Changing user agent on a web browser will trick it to be considered as a mobile device browser and will help in finding elements on mobile.
`User Agent Switcher` is an add on in firefox which will do this trick.

2)`Android Remote Debugging` - Can be used to identify elements in emulators and on real devices.
make sure that `adb devices` will return you connected emulator or a mobile device.

`chrome browser -> more tools -> development tools -> 3 dot menu -> more tools -> remote devices`

Navigate to an url in mobile browser, remote devices tab will display the web link, Click on the `Inspect` button which will open 'Remote Debugging In Android`, After this identifying elements and simulaying users actions is similar to selenium Web Driver.  

```NOTE :WebView debugging should be enabled; developer must enable ‘setWebContentsDebuggingEnabled’ flag in the WebView class. This flag enables debugging of web contents (HTML / CSS / JavaScript) loaded into any WebViews of app.```


## HYBRID APPS - A NATIVE APP WHICH HAS WEB VIEW

Appium Driver capabilities are similar to Native app except that user has to switch to web context when trying to simulate Web view user actions

One feature of mobile platforms is the ability to embed a chromeless web browser inside of a `native` application these are called ‘webviews’.  
Android developers use “android.webkit.WebView” class to implement webview inside an app.  

WebView debugging should be enabled; developer must enable `setWebContentsDebuggingEnabled` flag in the WebView class.  
This flag enables debugging of web contents (HTML / CSS / JavaScript) loaded into any WebViews of app.


`driver.getConext()` —-> Will return the context of the app (WEB VIEW or NATIVE APP).  
`driver.getContextHandles()` —-> Will return Set of Strings similar to Window Handles in selenium.  
`driver.context(“give one of the String that was returned by previous getContextHandles() method”)` --> Used to Switch to Web view or Native view depending on the task.  



## ANDROID EXTRAS
## RUNNING APPIUM ON REAL DEVICE WHERE APP IS INSTALLED ALREADY 

This can be done by knowing the PACKAGE and MAINACTIVITY of the app installed on a real device.  
apps like `APK info`  will provide app package and activity information when you install it on ur device.  
Passing `PACKAGE` and `ACTIVITY` as capabilities is required while creating an Android driver for device, instead of sending APP capability.


## INSTALL ANDROID APP IN AN EMULATOR FROM A REAL DEVICE  

1)`adb shell pm list packages` --> Use this to list out all apps in device.  
2)`adb shell pm path "Package name"` --> Lists package path.      
3)`adb pull "Package path" "local destination"` --> copy app in local destination.   
4)`adb install “location of app file extracted in earlier step”` --> To install app on emulator.  


## Faking location

`using appium`
1) Make sure no fake GPS apps are present in the device 
2) Set `Settings/developerOptions/Select mock location app` to  `appium settings` (note : android 6 and higher versions support this)
3) call setLocation on AppiumDriver instance:  
   
   Location location = new Location(latitude, longitude, altitude);  
   driver.setLocation(location);  
   for location you need to import org.openqa.selenium.html5.Location;  
   
   By default it will work only on Android & iOS simulators, but if you select Appium Settings as mocked location app in Developer options, it will work for Android real device as well.   
   

## Testing Performance data 

Building on the wealth of information that comes via adb dumpsys, Appium provides a succinct overview of all aspects of your app's performance via the getPerformanceData command.  
The client call is simple:

`driver.getPerformanceData("<package>", "<perf type>", <timeout>);`

Here, `<package>` is the package of your AUT (or any other app you wish to profile). <perf type> is what kind of performance data you want.  
There is another handy driver command (getSupportedPerformanceDataTypes) which tells you which types are valid.  
For the time being, they are: `cpuinfo, memoryinfo, batteryinfo, and networkinfo`. Finally, `<timeout>` is an integer denoting the number of seconds Appium will poll for performance data  
if it is not immediately available.

Assuming we're using the good old ApiDemos app, our call now looks like:

```
List<List<Object>> data = driver.getPerformanceData("io.appium.android.apis", "memoryinfo", 10);

What is returned is a set of two lists; one list is the keys and the other is the values. We can bundle up the call above along with some helper code that makes it easier to query the specific kind of memory info we're looking for (of course, in the real world we might make a class to hold the data):

private HashMap<String, Integer> getMemoryInfo(AndroidDriver driver) throws Exception {
    List<List<Object>> data = driver.getPerformanceData("io.appium.android.apis", "memoryinfo", 10);
    HashMap<String, Integer> readableData = new HashMap<>();
    for (int i = 0; i < data.get(0).size(); i++) {
        int val;
        if (data.get(1).get(i) == null) {
            val = 0;
        } else {
            val = Integer.parseInt((String) data.get(1).get(i));
        }
        readableData.put((String) data.get(0).get(i), val);
    }
    return readableData;
}

Essentially, we now have a HashMap we can use to query the particular kinds of memory info we retrieved. In our case, we're going to look for the totalPss value:

HashMap<String, Integer> memoryInfo = getMemoryInfo(driver);
int setSize = memoryInfo.get("totalPss");

```



## Unlock Android Device

unlocking the devices with UIAutomation by adding new capabilities that would let you press on pins, draw patterns or send a password depending on which lock you defined for your device.  
Using the unlock with UIAutomation capabilities:  

```
{
  "unlockType": "pin",
  "unlockKey": "1111"
}
```

In case the unlockType capability is not defined, Appium will continue working as it is using the Unlock Helper App, this new capabilities are optionals.

Options:

`unlockType: ['pin', 'password', 'pattern', 'fingerprint']`

pattern pins as treated the numbers of a phone dial.  
fingerprint unlock only works for Android 6+ emulators

## Appium 1.6 Issues 

 Appium 1.6 may not open android app and create session if a splash screen or a sign in screen appears before the main activity (Launchable Activity)
 
 solution :  
  1) Pass the package it finds(check logs) in the `appWaitActivity` cpability, it accepts multiple activities seperated by a comma.  
  2) Use `appActivity` and `appPackage` instead of `app` if the app is already installed

