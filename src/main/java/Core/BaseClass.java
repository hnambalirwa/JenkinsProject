package Core;


import Entities.Enums;
import Entities.TestEntity;
import Utils.ApplicationConfig;
import Utils.SeleniumDriverUtility;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.openqa.selenium.WebDriver;
import reporting.ExtentManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by Harriet Nambalirwa on 04/08/17.
 */
public class BaseClass {

    public static List<TestEntity> testDataList;
    public static Enums.BrowserType browserType;
    public static SeleniumDriverUtility SeleniumDriverInstance;
    public static ApplicationConfig appConfig = new ApplicationConfig();
    private DateTime startTime, endTime;
    private Duration testDuration;
    public static String testCaseId;
    public static String reportDirectory;
    public static Enums.Environment environment;
    public static SeleniumDriverUtility seleniumDriverInstance;
    public static String inputFilePath;
    public static int screenShotFolderCounter;
    public static int screenShotCounter;
    public static WebDriver Driver;
    public static String testcasename;

    public static ExtentTest test;
    public static ExtentManager extentManager;
    public static ExtentReports extent;
    public static ExtentHtmlReporter htmlReporter;


    public BaseClass(){
        System.setProperty("java.awt.headless", "false");

        reportDirectory = "TestReports/";
        environment = Enums.Environment.UAT;
    }

    public void setStartTime()
    {
        this.startTime = new DateTime();
    }

    public long getTotalExecutionTime()
    {
        this.endTime = new DateTime();
        testDuration = new Duration(this.startTime, this.endTime);
        return testDuration.getStandardSeconds();
    }

    public String generateDateTimeString() {
        Date dateNow = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");
        String dateTime = dateFormat.format(dateNow).toString();
        return dateTime;
    }

}
