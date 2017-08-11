package reporting;

import Core.BaseClass;
import Entities.TestEntity;
import Utils.SeleniumDriverUtility;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.mongodb.MapReduceCommand;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

/**
 * Created by Harriet Nambalirwa on 04/08/17.
 */
public class ExtentManager{

    private static ExtentReports extent;
    private static ExtentTest test;
    private static ExtentHtmlReporter htmlReporter;
    private static String filePath = "extentreport.html";


    public static ExtentReports GetExtent(){
        if (extent != null)
            return extent; //avoid creating new instance of html file
        extent = new ExtentReports();
        extent.attachReporter(getHtmlReporter());
        return extent;
    }

    public static ExtentHtmlReporter getHtmlReporter() {

        htmlReporter = new ExtentHtmlReporter(filePath);

        // make the charts visible on report open
        htmlReporter.config().setChartVisibilityOnOpen(true);

        htmlReporter.config().setDocumentTitle("QAV automation report");
        htmlReporter.config().setReportName("Regression cycle");
        return htmlReporter;
    }

    public static ExtentTest createTest(String name, String description){
        test = extent.createTest(name, description);
        return test;
    }


    // Method to Capture Active screen .
    public static void ScreenshotCapture(boolean isError, String screenShotDescription, String testCaseName, WebDriver driver, ExtentTest test)
    {
        StringBuilder imageFilePath = new StringBuilder("target/Screenshots/" + testCaseName + "/");

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
            FileUtils.copyFile(screenshotfile, new File(imageFilePath.toString()));
            test.log(Status.INFO,"Screenshot from : " + imageFilePath).addScreenCaptureFromPath(imageFilePath.toString());

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public static void endTest()
    {
        extent.flush();
        extent.close();

    }

}
