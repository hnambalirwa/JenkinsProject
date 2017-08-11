package unitTests.tests;

import Core.BaseClass;
import Entities.TestEntity;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import reporting.ExtentManager;

/**
 * Created by Harriet Nambalirwa on 04/08/17.
 */
public class QA_Manual_Test_Class extends BaseClass {

    TestEntity testData;
    int counter;

    public QA_Manual_Test_Class(TestEntity testData) {
        this.testData = testData;
    }

    public void executeTest() {

        this.setStartTime();

        if (!checkHome()) {
            seleniumDriverInstance.ScreenshotCapture(true, "click Manage", testData, this.screenShotCounter++);
            counter++;
            //Calling Screenshot method.
            ExtentManager.ScreenshotCapture(false, "Load Page", testcasename, Driver, test);
            test.fail("Failed to click on Manage");
            //return new TestResult(testData, Enums.ResultStatus.FAIL, "Failed to click on Manage", this.getTotalExecutionTime());

        }
        test.pass("Successfully clicked on Manage");
        //return new TestResult(testData, Enums.ResultStatus.PASS, "Successfully Added a Role.", this.getTotalExecutionTime());
    }

    public boolean checkHome()
    {
        try{
            Driver.get("http://www.qavalidation.com/");

            //test = extent.startTest("OpenUT", "Verify HomePage");//earlier version
            test = extent.createTest("QA manual Validation", "Verify Home Page");

            if(Driver.getTitle().contains("QA manual")){
                //test.log(LogStatus.PASS, driver.getTitle() +" contain "+"QA & Validation" );//earlier version
                //test.log(Status.PASS, driver.getTitle() +" contain "+"QA & Validation");
                //or
                test.pass(Driver.getTitle() +" contain "+"QA manual");
                //test.log(Status.INFO, "Snapshot" +  test.addScreenCaptureFromPath("./1.jpg"));
                return true;
            }
            else
                //test.log(LogStatus.FAIL, driver.getTitle() +" doesn't contain "+"QA & Validation" );//earlier version
                test.log(Status.FAIL, Driver.getTitle() +" doesn't contain "+"QA manual" );
        }catch(Exception e)
        {
            test.log(Status.ERROR, e.getMessage());
        }

        return false;
    }


}
