
# AUTOMATING WEB APPS WITH SELENIUM

The nice thing about testing mobile apps is that there is no difference between platforms in how your test is written.   
In the same way that you would expect the code for your Selenium tests to remain the same, regardless of whether you're   
testing Firefox or Chrome, your Appium web tests remain the same regardless of whether you're testing Safari on iOS or Chrome on Android.  
 Let's take a look at what the capabilities would look like to start a session on these two mobile browsers:  

## Test Safari on iOS

```
DesiredCapabilities capabilities = new DesiredCapabilities();
capabilities.setCapability("platformName", "iOS");
capabilities.setCapability("platformVersion", "11.2");
capabilities.setCapability("deviceName", "iPhone 7");
capabilities.setCapability("browserName", "Safari");
```

## Test Chrome on Android

```
DesiredCapabilities capabilities = new DesiredCapabilities();
capabilities.setCapability("platformName", "Android");
capabilities.setCapability("deviceName", "Android Emulator");
capabilities.setCapability("browserName", "Chrome");
From here on out, after we've launched the appropriate RemoteWebDriver session (or IOSDriver or AndroidDriver if you have the Appium client), we can do whatever we want. For example, we could navigate to a website and get its title!
```

```
driver.get("https://appiumpro.com");
String title = driver.getTitle();
// here we might verify that the title contains "Appium Pro"
```


## Special notes for iOS real devices

The only caveat to be aware of is with iOS real devices.  
Because of the way Appium talks to Safari (via the remote debugger exposed by the browser) an extra step is required to   
translate the WebKit remote debug protocol to Apple's iOS web inspector protocol exposed by usbmuxd. Sound complicated?  
Thankfully, the good folks at Google have created a tool that enables this translation, called ios-webkit-debug-proxy (IWDP).

`https://appium.io/docs/en/writing-running-appium/web/ios-webkit-debug-proxy/`

   
For running Safari tests on a real device, or hybrid tests on a real device, IWDP must be installed on your system.  

 
 ```
 verify you have Homebew installed:

$ brew -v
When you're certain you have Homebrew, do the following 

 cd  ~
 sudo apt-get install autoconf automake libusb-dev libusb-1.0-0-dev libplist-dev libplist++-dev usbmuxd libtool  libimobiledevice-dev
 git clone https://github.com/google/ios-webkit-debug-proxy.git
 cd ios-webkit-debug-proxy
 ./autogen.sh
 make
 sudo make install

```

Once you've got IWDP installed, you simply need to add one two more capabilities to the set for iOS above, udid and startIWDP:

```
//extra capabilities for Safari on a real iOS device
capabilities.setCapability("udid", "<the id of your device>");
capabilities.setCapability("startIWDP", true);
If you dont include the startIWDP capability, you must run IWDP on your own and Appium will just assume it's there listening for proxy requests.  
```

If you dont include the startIWDP capability,  
you must run IWDP on your own and Appium will just assume it's there listening for proxy requests.  

## Special notes for Android

Thankfully, things are simpler in the Android world because for both emulators and real devices Appium can take advantage of Chromedriver.  
When you want to automate any Chrome-based browser or webview, Appium simply manages a new Chromedriver process under the hood, so you get the full power of a first-class WebDriver server without having to set anything up yourself.  
This does mean, however, that you may need to ensure that the version of Chrome on your Android system is compatible with the version of Chromedriver used by Appium.  
If it's not, you'll get a pretty obvious error message saying you need to upgrade Chrome.

If you don't want to upgrade Chrome, you can actually tell Appium which version of Chromedriver you want installed, when you install Appium, using the --chromedriver_version flag. For example:

npm install -g appium --chromedriver_version="2.35"

How do you know which version of Chromedriver to install?  
The Appium team maintains a helpful list of which versions of Chromedriver support which versions of Chrome at our Chromedriver guide in the docs.  

`https://appium.io/docs/en/writing-running-appium/web/chromedriver/`
