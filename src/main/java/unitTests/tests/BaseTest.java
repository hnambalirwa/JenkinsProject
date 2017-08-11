package unitTests.tests;

import Core.BaseClass;
import org.junit.*;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import reporting.ExtentManager;

/**
 * Created by Harriet Nambalirwa on 10/08/17.
 */
public class BaseTest extends BaseClass{

    @BeforeSuite
    private void setUp(){

        System.out.println("Continuing to next @Before method");
        System.out.println("===========================================================================================================");

        extentManager = new ExtentManager();

        extent = ExtentManager.GetExtent();
        htmlReporter = ExtentManager.getHtmlReporter();
    }

    @AfterSuite
    public void tear()
    {
        System.out.println("Continuing to next @After method");
        System.out.println("===========================================================================================================");

        //extent.endTest(test);//earlier version
        extent.flush();
        extent.close();
    }
}
