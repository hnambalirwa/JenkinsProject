package unitTests;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;

/**
 * Created by Harriet Nambalirwa on 07/08/17.
 */
public class TakeScreenShotUnitTest {

    //Driver Definition .
    static WebDriver driver = new ChromeDriver();

    // Test Case Name which is used to create folder in Screenshot folder. You can manage this name and used in the function.
    static String testcasename = "MyTest";

    //Main Method.
    public static void main(String[] args) {

        // Launching Application through Selenium Webdriver.
        driver.get("http://www.addictinggames.com/lego-friends-community-clubhouse");

        //Calling Screenshot method.
        TakeScreenShotUnitTest screenshot= new TakeScreenShotUnitTest();
        screenshot.ScreenshotCapture(false,"Load Page");

        driver.close();

    }
    // Method to Capture Active screen .
    public void ScreenshotCapture(boolean isError, String screenShotDescription)
    {
        StringBuilder imageFilePath = new StringBuilder("Screenshots/" + testcasename + "/");

        if (isError) {
            imageFilePath.append("FAILED");
            imageFilePath.append("/");
        }  else {
            imageFilePath.append("PASSED");
            imageFilePath.append("/");
        }

        imageFilePath.append(screenShotDescription + "_Screenshot_" + 2  +  ".png");
        // Now taking screenshot using the following code.
        File screenshotfile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        try {
            // Give path where you want to store your screenshot.
            FileUtils.moveFile(screenshotfile, new File(imageFilePath.toString()));
        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
