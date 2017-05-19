package com.company.project.utilities;

import com.company.project.constants.Global.GlobalConstants;
import io.appium.java_client.AppiumDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by pavankovurru on 5/19/17.
 */
public class RunOn {

    public static Logger log = LogManager.getLogger();
    static PropertiesLoader pro = new PropertiesLoader(GlobalConstants.ENVIRONMENT_PROPERTY_PATH);
    static AppiumDriver driver = null;


    public  static AppiumDriver run(){
        //Creating driver object based on Environment.properties configuration

        String run = pro.getProperty("RunOn");
        String appname = System.getProperty("user.dir")+"/src/main/resources/"+pro.getProperty("AppName");

        switch (run) {

            //ANDROID NATIVE APP

            case "AndroidEmulatorNativeApp":
                driver=AppiumUtil.createLocalAndroidDriver_For_Emulator(appname);
                break;

            case "AndroidDeviceNativeApp":
                driver=AppiumUtil.createLocalAndroidDriver_For_RealDevice(appname);
                break;

            //ANDROID WEB APP

            case "AndroidEmulatorWebApp":
                driver=AppiumUtil.createLocalAndroidDriver_For_WebApp_In_Emulator("Chrome");
                break;

            case "AndroidDeviceWebApp":
                driver=AppiumUtil.createLocalAndroidDriver_For_WebApp_In_RealDevice("Chrome");
                break;

            // IOS NATIVE APP

            case "IosSimulatorNativeApp":
                driver=AppiumUtil.createLocalIOSDriver_For_NativeApp_In_Simulator(appname);
                break;

            case "IosDeviceNativeApp":
                driver=AppiumUtil.createLocalIOSDriver_For_NativeApp_In_IOSDEVICE(appname,"iPhone 7","TEST ORG ID");
                break;

            //IOS WEB APP

            case "IosSimulatorWebApp":
                driver=AppiumUtil.createLocalIOSDriver_For_WebApp_In_Simulator("Safari");
                break;

            case "IosDeviceWebApp":
                driver=AppiumUtil.createLocalIOSDriver_For_WebApp_In_IOSDEVICE("Safari","iPhone 7","TEST ORG ID");
                break;


            default:
                throw new IllegalArgumentException("Invalid parameter used in Environment.properties file : " + run);
        }
        return driver;
    }

}
