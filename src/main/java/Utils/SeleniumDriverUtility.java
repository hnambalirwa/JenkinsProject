package Utils;

import Core.BaseClass;
import Entities.Enums;
import Entities.RetrievedTestValues;
import Entities.TestEntity;
import org.apache.commons.io.FileUtils;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static java.lang.System.err;

/**
 * Created by Harriet Nambalirwa on 04/08/17.
 */
public class SeleniumDriverUtility extends BaseClass {

    public WebDriver Driver;
    private Enums.BrowserType browserType;
    private Boolean _isDriverRunning;
    public RetrievedTestValues retrievedTestValues;
    public String DriverExceptionDetail = "";
    static int screenShotCounter;

    public SeleniumDriverUtility() {

        _isDriverRunning = false;

        startDriver();
        Driver.get(environment.toString());

    }

    public boolean isDriverRunning() {
        return _isDriverRunning;
    }

    public Set getCookiesAsSet()
    {
        try
        {
            Driver.manage().deleteAllCookies();
            return Driver.manage().getCookies();
        }
        catch(Exception ex)
        {
            err.println("[ERROR] Failed to retrieve cookies from browser session - " + ex.getMessage());
        }
        return null;
    }


    public boolean setCookiesAsPropertySet(Set cookieSet)
    {
        try
        {
            Set<Cookie> cookSet = cookieSet;
            for(Cookie cook : cookSet)
            {
                Driver.manage().addCookie(cook);
            }
            return true;
        }
        catch(Exception ex)
        {
            err.println("[ERROR] Failed to add cookies to browser session - " + ex.getMessage());
            return false;
        }

    }

    public void startDriver() {
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.Jdk14Logger");

        switch (browserType) {
            case IE:

                DesiredCapabilities cap = new DesiredCapabilities();
                cap.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false);

                Driver = new InternetExplorerDriver(cap);
                _isDriverRunning = true;
                break;

            case FireFox:
                Driver = new FirefoxDriver();
                _isDriverRunning = true;
                break;

            case Chrome:
                Driver = new ChromeDriver();
                _isDriverRunning = true;
                break;

            case Safari:
                break;
        }

        retrievedTestValues = new RetrievedTestValues();
        Driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        Driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
        Driver.manage().timeouts().setScriptTimeout(1, TimeUnit.SECONDS);
        Driver.manage().window().maximize();

    }

    public void shutDown() {
        retrievedTestValues = null;
        try {

            Driver.quit();
            //CloseChromeInstances();
        } catch (Exception e) {
            System.err.println("Error shutting down driver - " + e.getMessage());
            this.DriverExceptionDetail = e.getMessage();
        }
        _isDriverRunning = false;
    }

    public void ScreenshotCapture(boolean isError, String screenShotDescription, TestEntity testData, int screenShotCounter)
    {
        StringBuilder imageFilePath = new StringBuilder("Screenshots/" + testData.getData("testCaseId") + "/");

        if (isError) {
            imageFilePath.append("FAILED");
            imageFilePath.append("/");
        }  else {
            imageFilePath.append("PASSED");
            imageFilePath.append("/");
        }

        imageFilePath.append(screenShotDescription + "_Screenshot_" + screenShotCounter  +  ".jpg");
        // Now taking screenshot using the following code.
        File screenshotfile = ((TakesScreenshot) Driver).getScreenshotAs(OutputType.FILE);

        try {
            // Give path where you want to store your screenshot.
            FileUtils.copyFile(screenshotfile, new File(imageFilePath.toString()));
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public static void resetScreenShotFolderCounter(int inScreenShotFolderCounter) {
        screenShotFolderCounter = inScreenShotFolderCounter;
    }

    public static void incrementScreenShotFolderCounter() {
        screenShotFolderCounter++;
    }

    public static void resetScreenShotCounter(int inScreenShotCounter) {
       screenShotCounter = inScreenShotCounter;
    }

}
