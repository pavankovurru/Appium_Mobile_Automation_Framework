
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
export ANDROID_HOME=/YOUR_PATH_TO/android-sdk
export PATH=$ANDROID_HOME/platform-tools:$PATH
export PATH=$ANDROID_HOME/tools:$PATH

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

Example : `driver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"Text-To-Speech\")")).click();`
text() above can be replaced by any of the attributes present in uiautomatorviewer


## ANDROID KEY EVENTS HOME,BACK,MULTITASKING..etc..

```
((AndroidDriver)driver).pressKeyCode(AndroidKeyCode.BACK);
   
((AndroidDriver)driver).pressKeyCode(AndroidKeyCode.KEYCODE_APP_SWITCH);
     
((AndroidDriver)driver).pressKeyCode(AndroidKeyCode.HOME);
  ```
  

## ANDROID WEB APPS - Identifying Elements

1) `User Agent` - A Web page will be rendered depending on the user agent, Web agent of a mobile browser will be different from a computer browser.

Changing user agent on a web browser will trick it to be considered as a mobile device browser and will help in finding elements on mobile.
`User Agent Switcher` is an add on in firefox which will do this trick.

2)`Android Remote Debugging` - Can be used to identify elements in emulators and on real devices.
make sure that `adb devices` will return you connected emulator or a mobile device.

`chrome browser -> more tools -> development tools -> 3 dot menu -> more tools -> remote devices`

Navigate to an url in mobile browser, remote devices tab will display the web link, Click on the `Inspect` button which will open 'Remote Debugging In Android`, After this identifying elements and simulaying users actions is similar to selenium Web Driver.  


## HYBRID APPS - A NATIVE APP WHICH HAS WEB VIEW

Appium Driver capabilities are similar to Native app except that user has to switch to web context when trying to simulate Web view user actions

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


