
# ANDROID

## ANDROID CONFIGURATIONS

1. **Install JAVA.**
2. **Install Android SDK.**
3. **Set ANDROID_HOME Environment Variable:**
   - Define `ANDROID_HOME` to point to your Android SDK path.
4. **Update PATH Variable:**
   - Include `$ANDROID_HOME/platform-tools` in your system’s PATH.

   ```bash
   touch ~/.zshenv 

   # Add the following lines:
   export JAVA_HOME="/Library/Java/JavaVirtualMachines/jdk-version.jdk/Contents/Home"
   export ANDROID_HOME=/Users/`USER`/Library/Android/sdk
   export PATH=$ANDROID_HOME/platform-tools:$PATH

   # Apply changes:
   source ~/.zshenv
   echo $ANDROID_HOME
   ```

5. **Create and Run an Android Emulator:**
   - Ensure the emulator is up and running along with the Appium server for local test execution on the Android emulator.



**TIP** - Developer Options -> enable `show taps` and `pointer location` to see where appium is performing actions

## ANDROID ELEMENT LOCATOR TOOLS

1. **Appium Inspector:**  
   [Appium Inspector GitHub Repository](https://github.com/appium/appium-inspector)

   ```bash
   # Have the app running, open `appium-inspector`.
   # Add the necessary capabilities to create an Appium driver.
   # Start a session and record locators to be used in tests.
   ```

   **AppiumBy Class Locator Strategies:**

   ```java
   https://javadoc.io/doc/io.appium/java-client/latest/io/appium/java_client/AppiumBy.html
   AppiumBy.accessibilityId("messageInput");
   AppiumBy.androidUIAutomator("new UiSelector().text(\"Save\")");
   AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"savedMessage\")");
   ```

## ANDROID KEY EVENTS (HOME, BACK, MULTITASKING, etc.)

```java
((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.BACK));  
((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.KEYCODE_APP_SWITCH));  
((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.HOME));  
driver.longPressKey(new KeyEvent(AndroidKey.POWER)); // To power off the device (note the `longPressKey` method)
```

## ANDROID WEB APPS - Identifying Elements

1. **User Agent:**
   - A web page's rendering depends on the user agent. By changing the user agent on a web browser, you can simulate a mobile browser environment to help identify mobile-specific elements.
   - Use the "User Agent Switcher" add-on in Firefox for this purpose.

2. **Android Remote Debugging:**
   - Useful for identifying elements in emulators and real devices.
   - Ensure `adb devices` lists your connected emulator or mobile device.

   ```bash
   chrome browser -> more tools -> developer tools -> 3 dot menu -> more tools -> remote devices
   ```

   - Navigate to a URL in the mobile browser, and use the remote devices tab to inspect elements. This works similarly to Selenium WebDriver once connected.

   **Note:** WebView debugging must be enabled by the developer using the `setWebContentsDebuggingEnabled` flag in the WebView class.

## HYBRID APPS - A NATIVE APP WITH WEBVIEW

- Refer to the [Appium Context Documentation](https://appium.io/docs/en/latest/guides/context/) for handling hybrid apps.

## ANDROID EXTRAS

### Running Appium on a Real Device with Pre-installed Apps

- Identify the app's PACKAGE and MAINACTIVITY on a real device using apps like "APK Info".
- Use these values as capabilities when creating an Android driver instead of providing the APP capability.

### Installing an Android App on an Emulator from a Real Device

1. List all apps on the device:
   ```bash
   adb shell pm list packages
   ```
2. Find the package path:
   ```bash
   adb shell pm path "Package name"
   ```
3. Copy the app to a local destination:
   ```bash
   adb pull "Package path" "local destination"
   ```
4. Install the app on the emulator:
   ```bash
   adb install “location of app file extracted in earlier step”
   ```

### Faking Location

1. Ensure no fake GPS apps are present on the device.
2. Set `Settings > Developer Options > Select mock location app` to `Appium Settings`.
   - This works on Android 6 and higher.

3. Set the location on the AppiumDriver instance:

   ```java
   Location location = new Location(latitude, longitude, altitude);  
   driver.setLocation(location);  
   ```

   - Import `org.openqa.selenium.html5.Location`.

   **Note:** This method works on Android & iOS simulators by default, and can also be enabled for real Android devices by setting Appium Settings as the mock location app.

### Testing Performance Data

- Use the `getPerformanceData` command to retrieve performance metrics:

   ```java
   driver.getPerformanceData("<package>", "<perf type>", <timeout>);
   ```

- Available performance data types include `cpuinfo`, `memoryinfo`, `batteryinfo`, and `networkinfo`.

- Example for fetching memory info:

   ```java
   List<List<Object>> data = driver.getPerformanceData("io.appium.android.apis", "memoryinfo", 10);

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

   HashMap<String, Integer> memoryInfo = getMemoryInfo(driver);
   int setSize = memoryInfo.get("totalPss");
   ```

## Unlock Android Device

- Unlock your Android device using UIAutomation by adding capabilities that correspond to the device’s unlock type:

   ```json
   {
     "unlockType": "pin",
     "unlockKey": "1111"
   }
   ```

- If `unlockType` is not defined, Appium will continue to use the Unlock Helper App.
- Supported `unlockType` values: `['pin', 'password', 'pattern', 'fingerprint']`.

  **Note:** Pattern pins are treated as numbers on a phone dial, and fingerprint unlock works only for Android 6+ emulators.

