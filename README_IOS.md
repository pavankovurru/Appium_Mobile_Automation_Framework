

# APPIUM IOS

## IOS CONFIGURATIONS AND DEPENDANCIES

1. Install `XCODE`.  
2. Install  appium desktop or `APPIUM 1.6 or higher` using `npm install -g appium`.   
3. `brew install ideviceinstaller`
4. `brew install carthage`
5. `npm install -g ios-deploy`
6. `npm install -g deviceconsole`
7. `gem install xcpretty`
8. `brew install --HEAD libimobiledevice`
9. `npm install -g appium-doctor`

run `appium-doctor` command in terminal to make sure that all the dependancies are installed properly.  

## IOS DEVICE SET UP

Basic configuration is to have below capabilities passed to IOS Driver

 ```
 capabilities.setCapability("xcodeOrgId", "COMPANY ORG ID");
 capabilities.setCapability("xcodeSigningId", "iPhone Developer");
 ```

Sign in to developer.apple.com/account, and click Membership in the sidebar. Your Team ID appears in the Membership Information section under the team name.

Use the link below if basic configuration does not work, This will happen if a free apple developer account `xcodeOrgId` is used in the desired capabilities above.

* [IOS REAL DEVICE SETUP -- READ ME](https://github.com/appium/appium-xcuitest-driver/blob/master/docs/real-device-config.md)


NOTE : APPS BUILD FOR SIMULATOR WILL RUN ONLY ON SIMULATORS AND APPS BUILT FOR REAL DEVICES WILL RUN ONLY ON REAL DEVICES.


## IOS ELEMENT LOCATOR TOOLS

## 1. APPIUM INSPECTOR 

```
USE APPIUM DESKTOP --> START SERVER --> NEW SESSION --> GIVE DESIRED CAPABILITIES -->THIS WILL START THE APP AND APPIUM INSPECTOR
```

![alt tag](https://github.com/pavankovurru/Appium_Mobile_Automation_Framework/blob/master/src/main/resources/AppiumInspector.png)



`driver.findElementByAccessibilityId("");`  -- AccessibilityId in inspector can be used here
`driver.findElementByClassName("");`  -- type value in inspector can be used here
 `XPATH` value in appium Inspector can be used in  `driver.findElementsByXPath("");`. 
  
  

## 2. XCODE ACCESSIBILITY INSPECTOR 

`ACCESSIBILITY INSPECTOR` is present at `XCODE -- OPEN DEVELOPER TOOLS -- ACCESSIBILITY INSPECTOR.`   
This is useful in locating elements when an app installed in real device.  
Coonect device , open ACCESSIBILITY INSPECTOR and select the device that you want to use.  
Usage is similar to appium inspector.  


## IOS WEB APPS 
One way to identify elements is to modify `UserAgent` as done in andoird.


## SAFARI WEB INSPECTOR 

1. `SETTINGS --> SAFARI --> ADVANCED --> WEBINSPECTOR` (make sure that this is enabled)  
2. Open Safari Browser on Decice/Simulator and navigate to desired URL  
3. Open Safari Browser on mac, Click on `DEVELOP` option from menu and select device/simulator    
4. Safari Web Inspector opens as a new app, Identifying elements is similar to Selenium Web Driver  


## HYBRID APPS - A NATIVE APP WHICH HAS WEB VIEW

Appium Driver capabilities are similar to Native app except that user has to switch to web context when trying to simulate Web view user actions

`driver.getConext()` —-> Will return the context of the app (WEB VIEW or NATIVE APP).  
`driver.getContextHandles()` —-> Will return Set of Strings similar to Window Handles in selenium.  
`driver.context(“give one of the String that was returned by previous getContextHandles() method”)` --> Used to Switch to Web view or Native view depending on the task.  



## IOS EXTRAS

## RUN IOS APP ON IOS SIMULATOR

```
1. Open the .xcodeproj with Xcode
2. Select the iOS simulator by clicking the project name which is located on the top left corner (next to Stop button)
3. Select Product -> Clean
4. Select Product -> Build
5. Click on “RUN” button
6. If everything is fine we will be notified with a Build Success message and the selected simulator will open the app.
7. Use the app located at  (/Users/XXX/Library/Developer/Xcode/DerivedData/XXXXXX-xxxxxxx/Build/Products/Debug-iphonesimulator/xxx.app)
```

