package unitTests.tests;

import Core.BaseClass;
import Entities.TestEntity;
import com.aventstack.extentreports.Status;

import reporting.ExtentManager;

/**
 * Created by Harriet Nambalirwa on 07/08/17.
 */
public class Verify_Home_Page_Test_Class extends BaseClass {

    TestEntity testData;
    int counter;

    //Driver Definition .
    //static WebDriver driver ;


    public Verify_Home_Page_Test_Class(TestEntity testData) {
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

    public boolean checkHome() {
        try {
            Driver.get("http://www.qavalidation.com/");

            //test = extent.startTest("OpenUT", "Verify HomePage");//earlier version
            testcasename = "Test Screenshot Capture";

            test = extent.createTest("Test Screenshot Capture", "Testing Screenshot Capturing");
            //Calling Screenshot method.
            ExtentManager.ScreenshotCapture(false, "Load Page", testcasename, Driver, test);


            if (Driver.getTitle().contains("QA manual")) {
                //test.log(LogStatus.PASS, driver.getTitle() +" contain "+"QA & Validation" );//earlier version
                //test.log(Status.PASS, driver.getTitle() +" contain "+"QA & Validation");
                //or
                test.pass(Driver.getTitle() + " contain " + "QA manual");
                //test.log(Status.INFO, "Snapshot" +  test.addScreenCaptureFromPath("./1.jpg"));
            } else {
                //test.log(LogStatus.FAIL, driver.getTitle() +" doesn't contain "+"QA & Validation" );//earlier version
                test.log(Status.FAIL, Driver.getTitle() + " doesn't contain " + "QA manual");
                test.fail("ailed to Get the expected Title");
                ExtentManager.ScreenshotCapture(true, "Failed to Get the expected Title", testcasename, Driver, test);
                return false;
            }
        } catch (Exception e) {
            test.log(Status.ERROR, e.getMessage());
        }

        return true;
    }
}