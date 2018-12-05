

# APPIUM IOS

## IOS CONFIGURATIONS AND DEPENDANCIES

1. Create [apple developer account](https://developer.apple.com/)
2. Install `XCODE`. 
2. Install  appium desktop or `APPIUM 1.6 or higher` using `npm install -g appium`.
3. Install [home brew] (https://brew.sh/)
4. `brew install libimobiledevice` - open source package which is able to communicate with iOS devices.
5. `brew install ios-deploy` - for transferring iOS apps onto your device.
6. `brew install carthage` - WDA itself requires an iOS dependency manager called Carthage.
Since Appium will be automatically building the WDA app, we need to install Carthage so it is available to the WDA bootstrap process.
7. Turn on IOS device, Plug it into mac, Unlock Device, `Trust` the mac, select Display & Brightness, select Auto-Lock,
and set it to never, to ensure that the screen never locks on us mid-test.
8. Open Xcode and create a new xcode project, make sure that you give a unique bundle ID and sign the project with apple account created in step1.  
make note of the `Bundle ID`, This will be the `updatedWDABundleId` value that will be used in desired capabilities.

![alt tag](https://github.com/pavankovurru/Appium_Mobile_Automation_Framework/blob/master/src/main/resources/WebDriverAgentRunner.png)

9.open `Keychain Access` mac app, click on my certificates, Double click on certificate and make note of the `Organization unit`  
This will be the `xcodeOrgId` value that will be used in desired capabilities.


## IOS DEVICE SET UP

Basic configuration is to have below capabilities passed to IOS Driver

 ```
 capabilities.setCapability("xcodeOrgId", "COMPANY ORG ID");
 capabilities.setCapability("xcodeSigningId", "iPhone Developer");
 ```

Sign in to developer.apple.com/account, and click Membership in the sidebar. Your Team ID appears in the Membership Information section under the team name.

Use the link below if basic configuration does not work, This will happen if a free apple developer account `xcodeOrgId` is used in the desired capabilities above.

![IOS REAL DEVICE SETUP -- READ ME](https://github.com/appium/appium-xcuitest-driver/blob/master/docs/real-device-config.md)


NOTE : APPS BUILD FOR SIMULATOR WILL RUN ONLY ON SIMULATORS AND APPS BUILT FOR REAL DEVICES WILL RUN ONLY ON REAL DEVICES.


## ISSUES

1)Original error: Could not determine Xcode version: Could not get Xcode version. /Library/Developer/Info.plist does not exist on disk.

```
Solution:
sudo xcode-select --reset
sudo xcode-select --switch /Applications/Xcode.app
Restart Appium Server

```


## IOS ELEMENT LOCATOR TOOLS

## 1. APPIUM INSPECTOR 

```
USE APPIUM DESKTOP --> START SERVER --> NEW SESSION --> GIVE DESIRED CAPABILITIES -->THIS WILL START THE APP AND APPIUM INSPECTOR
```

![alt tag](https://github.com/pavankovurru/Appium_Mobile_Automation_Framework/blob/master/src/main/resources/AppiumInspector.png)



`driver.findElement(MobileBy.AccessibilityId(accessibilityId));`  -- `AccessibilityId` in inspector can be used here
`driver.findElement(MobileBy.name(name));`  -- `name` value in inspector can be used here
 `XPATH` value in appium Inspector can be used in  `driver.findElement(MobileBy.xpath(xPath));`. 
  
### iOS Predicate String Strategy

Predicate Format Strings are a typical Apple dev thing, and they also work in iOS. Predicate format strings enable basic comparisons and matching. In our case, they allow basic matching of elements according to simple criteria. What's really useful about predicate strings is that you can combine simple criteria to form more complex matches. In the XCUITest driver, predicate strings can be used to match various element attributes, including name, value, label, type, visible, etc...  

![Appium Documentation](https://github.com/appium/appium/blob/master/docs/en/writing-running-appium/ios/ios-predicate.md)

```
Examples:
label CONTAINS 'your label string'
label BEGINSWITH 'your label string'
name BEGINSWITH 'your name string'
label ENDSWITH 'your label string'
label == 'your label string'


driver.findElementByIosNsPredicate('type == "XCUIElementTypeTable" AND name == "table"');
driver.findElementByIosNsPredicate('type == "XCUIElementTypeTable" AND (name == "table" OR label == "tableLabel")');
driver.findElementByIosNsPredicate('type == "XCUIElementTypeTable" AND name IN {"table2","table1"}');

```

One example from the WebDriverAgent predicate string guide shows a fun compound predicate:

`type == 'XCUIElementTypeButton' AND value BEGINSWITH[c] 'bla' AND visible == 1`

This predicate string would match any visible button whose value begins with 'bla'. How would we write this up in our Java client code? Simply by using the appropriate MobileBy method, as follows:

```
String selector = "type == 'XCUIElementTypeButton' AND value BEGINSWITH[c] 'bla' AND visible == 1";

driver.findElement(MobileBy.iOSNsPredicateString(selector));

```
Because predicate matching is built into XCUITest, it has the potential to be much faster than Appium's XPath strategy.

### iOS Class Chain Strategy

The final option is a sort of hybrid between XPath and predicate strings: the -ios class chain locator strategy. This was developed by the Appium team to meet the need of hierarchical queries in a more performant way. The types of queries possible via the class chain strategy are not as powerful as those enabled by XPath, but this restriction means a better performance guarantee (this is because it is possible to map class chain queries into a series of direct XCUITest calls, rather than having to recursively build an entire UI tree). Class chain queries look very much like XPath queries, however the only allowed filters are basic child/descendant indexing or predicate string matching. It's worth checking out the class chain docs to find a number of examples. Let's take a look at just a couple:

`XCUIElementTypeWindow[2]` selects the second window in the hierarchy.

`XCUIElementTypeWindow[`label BEGINSWITH "foo"`][-1]` selects the last window whose label begins with foo.

`**/XCUIElementTypeCell[`name BEGINSWITH "C"`]/XCUIElementTypeButton[10]`` selects the 10th child button of the first cell in the tree whose name starts with C and which has at least ten direct children of type XCUIElementTypeButton.
Just as before, there is a special MobileBy method to hook up your class chain queries in the Java client:

`driver.findElement(MobileBy.iOSClassChain(selector));`


## 2. XCODE ACCESSIBILITY INSPECTOR 

`ACCESSIBILITY INSPECTOR` is present at `XCODE -- OPEN DEVELOPER TOOLS -- ACCESSIBILITY INSPECTOR.`   
This is useful in locating elements when an app installed in real device.  
Coonect device , open ACCESSIBILITY INSPECTOR and select the device that you want to use.  
Usage is similar to appium inspector.  


## IOS WEB APPS 
One way to identify elements is to modify `UserAgent` as done in andoird.Safari has built in `UserAgent` in `develop` tool bar options.


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

## Testing Push notifications IOS

```
driver.terminateApp(BUNDLE_ID);
showNotifications();
driver.findElement(By.xpath("//XCUIElementTypeCell[contains(@label, 'notification text')]"));
hideNotifications();
driver.activateApp(BUNDLE_ID);

The terminateApp and activateApp commands are self-explanatory, and simply require the bundle ID of your app.  
The only mystery here is in the showNotifications and hideNotifications methods.   
These are not driver methods, but rather helper methods I've implemented to take care of swiping the notifications shade down (to open) and up (to close).  
The implementation currently uses the TouchAction interface to set up the swipe.   
Assuming we have a field called screenSize in our test class which has previously queried the screen dimensions from Appium,  
then our notifications helpers look like:


private void showNotifications() {
    manageNotifications(true);
}

private void hideNotifications() {
    manageNotifications(false);
}

private void manageNotifications(Boolean show) {

    private Dimension screenSize = driver.manage().window().getSize();

    int yMargin = 5;
    int xMid = screenSize.width / 2;
    PointOption top = PointOption.point(xMid, yMargin);
    PointOption bottom = PointOption.point(xMid, screenSize.height - yMargin);

    TouchAction action = new TouchAction(driver);
    if (show) {
        action.press(top);
    } else {
        action.press(bottom);
    }
    action.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)));
    if (show) {
        action.moveTo(bottom);
    } else {
        action.moveTo(top);
    }
    action.perform();
}

```

## IOS EXTRAS

## SWITCHING BETWEEN IOS APPS DURING TEST

This is all possible to encode into your Appium scripts using two handy commands: `mobile: launchApp` and `mobile: activateApp.` activateApp merely brings app back to the foreground. Here are examples of how we'd use these commands, by incorporating the Bundle IDs of the apps we're dealing with (having these Bundle IDs available is a pre-requisite):

```
// launch the photos app (with the special bundle id seen below)
HashMap<String, Object> args = new HashMap<>();
args.put("bundleId", "com.apple.mobileslideshow");
driver.executeScript("mobile: launchApp", args);

// re-activate that AUT (in this case The App)
args.put("bundleId", "io.cloudgrey.the-app");
driver.executeScript("mobile: activateApp", args);
```

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

