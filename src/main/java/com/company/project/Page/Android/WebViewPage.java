package com.company.project.Page.Android;

import com.company.project.utilities.AppiumUtil;
import io.appium.java_client.AppiumDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

import java.util.Set;

/** Created by pavankovurru on 7/17/17. */
public class WebViewPage {

    private static final Logger log = LogManager.getLogger();
    AppiumDriver driver;

    public WebViewPage(AppiumDriver driver) {
        this.driver = driver;
    }


    public void clickViews(){
        AppiumUtil.androidScrollToText("Views").click();
    }

    public void clickWebView(){
        AppiumUtil.androidScrollToText("WebView").click();
        AppiumUtil.sleep(2);
        //clicking on web view part of the app
        //driver.findElementByClassName("android.webkit.WebView").click();

    }


    public void switchtoWebView(){

        Set<String > handles = driver.getContextHandles();

        for (String handle : handles){
            if (handle.matches(".*?WEBVIEW.*?")){
                log.info("Trying to switch Context to - "+handle);
                driver.context(handle);
                log.info("Current context - "+driver.getContext());
                break;
            }
        }
    }

    public void switchToNative(){
        Set<String > handles = driver.getContextHandles();
        for (String handle :handles){
            if (handle.matches("NATIVE_APP")){
                log.info("Trying to switch Context to - "+handle);
                driver.context(handle);
                log.info("Current context - "+driver.getContext());
                break;
            }
        }
    }

   public String gettext(){
   return driver.findElement(By.xpath("//body/a")).getText();
   }

}
