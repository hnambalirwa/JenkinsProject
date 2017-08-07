package testClasses;

import Entities.Enums;
import Entities.TestEntity;
import Entities.TestResult;
import Core.BaseClass;
import org.junit.Assert;

/**
 * Created by Harriet Nambalirwa on 04/08/17.
 */
public class JenkinsTest extends BaseClass{

    BaseClass constants;
    TestEntity testData;
    int counter;

    public JenkinsTest(TestEntity testData){
        this.testData = testData;
        counter = BaseClass.screenShotCounter;
    }


    public TestResult executeTest() {

        this.setStartTime();


        if(!login())
        {
            seleniumDriverInstance.ScreenshotCapture(true,"click Manage", testData, this.screenShotCounter++);
            counter++;
            return new TestResult(testData, Enums.ResultStatus.FAIL, "Failed to click on Manage", this.getTotalExecutionTime());

        }

        return new TestResult(testData, Enums.ResultStatus.PASS, "Successfully Added a Role.", this.getTotalExecutionTime());
    }


    public boolean login(){
        //we expect the title “Google “ should be present
        String Expectedtitle = "Google";

        //it will fetch the actual title
        String Actualtitle = testData.getData("Title");
        System.out.println("Before Assetion " + Expectedtitle + Actualtitle);

        //it will compare actual title and expected title
        Assert.assertEquals(Actualtitle, Expectedtitle);

        //print out the result
        System.out.println("After Assertion " + Expectedtitle + Actualtitle + " Title matched ");

        return true;
    }

}
