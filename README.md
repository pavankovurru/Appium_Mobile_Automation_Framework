# Appium Mobile Automation Framework
This framework streamlines your mobile app testing with Appium, offering compatibility with Appium Java Client 9.3.0 and support for parallel test execution. Test across real devices, emulators, and simulators on both Android and iOS platforms.

## Features
* **Parallel Testing:** Efficiently run tests in parallel using TestNG and multiple Appium server instances.
* **Cross-Platform:**  Test seamlessly on both Android and iOS devices.
* **Utility Functions:**  Simplify test creation with pre-built functions for common mobile interactions.
* **Dynamic Configuration:** Easily target different apps, environments, and devices using parameterized TestNG XML files.
* **Automatic Logs/Screenshots:** Capture logs/screenshots on test failures for faster debugging.

## Tech Stack
* Appium Java Client 9.3.0
* Appium Inspector 2024.8.2
* LOG4J 2.23.1
* TestNG 7.10.2
* Gradle 8.5

## Getting Started (Mac)
1. **Install Appium Server** - `npm install -g appium`
2. **Install Appium Drivers** - `appium setup`
3. **Install Appium Inspector** - `https://github.com/appium/appium-inspector`
1. **Launch Appium Server:** Open multiple instances for parallel testing:
   ```bash
   open -n /Applications/Appium.app
   ```
2. **Add Your Apps:** Place your `.apk` (Android) and `.ipa/.app` (iOS) files in `src/main/resources/testApps`.
3. **Configure Tests:** Adjust parameters in the TestNG XML files within `src/test/resources` to target specific apps and devices.
4. **Run Tests:** Execute using your TestNG runner or Gradle.

## Framework Structure
* `src/main/resources/testApps`: Store your app files here.
* `src/main/java/com/company/project/utilities/`:
    * `AppiumUserSimulations.java`: Contains utility functions for common mobile actions.
    * `AppiumDriverFactory.java`:  Provides methods to create Appium drivers for different platforms.
* `src/test/resources`:  Contains parameterized TestNG XML configuration files.
* `screenshots`:  Automatically stores screenshots on test failures.

## Tips
* Utilize the utility functions in `AppiumUserSimulations.java` for interacting with elements.
* Manage Appium driver instances using `AppiumDriverFactory.java`.
* Leverage parameterized TestNG XML files for flexible test configuration.

## TIPS AND TRICKS

* [ ANDROID READ ME ](documents/README_ANDROID.md)

* [ IOS  READ ME ](documents/README_IOS.md)

* [ WEB APPS  READ ME ](documents/WebApps_ReadMe.md)

* [ MOCKING LOCATION ](documents/MockLocation.md)

**Happy testing!**

