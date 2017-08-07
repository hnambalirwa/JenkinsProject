package unitTests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import reporting.ExtentManager;

/**
 * Created by Harriet Nambalirwa on 04/08/17.
 */
public class ExtentReportUnitTest {

        static ExtentReports extent;
        ExtentTest test;
        static WebDriver driver;


        @BeforeClass
        public static void M1()  throws Exception{

            extent = ExtentManager.GetExtent();
            DesiredCapabilities capabilies = DesiredCapabilities.chrome();
            capabilies.setBrowserName("chrome");
            capabilies.setPlatform(Platform.ANY);

            driver = new ChromeDriver();
        }

        @Test
        public void checkHome() throws Exception
        {
            try{
                driver.get("http://www.qavalidation.com/");

                //test = extent.startTest("OpenUT", "Verify HomePage");//earlier version
                test = extent.createTest("QAVsite", "Verify HomePage");

                if(driver.getTitle().contains("QA manual")){
                    //test.log(LogStatus.PASS, driver.getTitle() +" contain "+"QA & Validation" );//earlier version
                    //test.log(Status.PASS, driver.getTitle() +" contain "+"QA & Validation");
                    //or
                    test.pass(driver.getTitle() +" contain "+"QA manual");
                    //test.log(Status.INFO, "Snapshot" +  test.addScreenCaptureFromPath("./1.jpg"));
                }
                else
                    //test.log(LogStatus.FAIL, driver.getTitle() +" doesn't contain "+"QA & Validation" );//earlier version
                    test.log(Status.FAIL, driver.getTitle() +" doesn't contain "+"QA manual" );
            }catch(Exception e)
            {
                test.log(Status.ERROR, e.getMessage());
            }
        }

        @Test
        public void checkFail()
        {
            test = extent.createTest("Testing how fail works");
            //test.log(Status.INFO, "fail check started");
            test.fail("Test fail");
        }

        @AfterClass
        public static void tear()  throws Exception
        {
            //extent.endTest(test);//earlier version
            extent.flush();
            extent.close();
            driver.quit();
        }
    }
