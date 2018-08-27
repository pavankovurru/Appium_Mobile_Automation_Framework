# APPIUM MOBILE AUTOMATION FRAMEWORK

```
Compatible With Android Oreo and IOS 11
Uses explicit waits internally in all of the `src/main/java/com/company/project/utilities/AppiumUtil.java` functions that return mobile elements.  
  
Supports Parallel runs via testNG xml configuration  
Note : an appium server supports onl one device, make sure multiple appium server instances are run to support parallel execution
`open -n /Applications/Appium.app` - should do the trick on a mac to initialize a new instance of appium server

Works With Native, Web & Hybrid Apps.
Works on Emulators, Simulators & Real Devices.
```

##  FRAME WORK STACK 
```
1. Appium Java Client 5.0.4
2. Appium Desktop 1.6.2
3. Selenium 3.6.0
4. LOG4J 2  
5. TestNG 6.11
6. Gradle
```


##  IMPORTANT FILES  

1. `src/main/resources` -- This folder contains Android,IOS apps that will be tested locally.   
 
2. `src/main/java/com/company/project/utilities/AppiumUtil.java` -- Has Utility functions that can be used to simulate mobile actions.  

3. `src/test/resources` --  This folder contains testNG xml's which are parameterized with `AppName` and `runOn` details so that these XML's.

can be modified to target different apps, environments and target devices instead of making changes to test files.

4. A screen shot gets saved in screenshots folder with test class & test case name when there is a failure. 


## TIPS AND TRICKS

* [ ANDROID READ ME ](documents/README_ANDROID.md)

* [ IOS  READ ME ](documents/README_IOS.md)

* [ WEB APPS  READ ME ](documents/WebApps_ReadMe.md)

* [ MOCKING LOCATION ](documents/MockLocation.md)


