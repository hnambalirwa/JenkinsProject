package unitTests.testSuits;

import Utils.ApplicationConfig;
import org.testng.annotations.Test;
import tests.TestMarshallTest;
import unitTests.tests.BaseTest;

import java.io.FileNotFoundException;

/**
 * Created by Harriet Nambalirwa on 04/08/17.
 */
public class Verify_Home_Page_Test_Pack_Test extends BaseTest{

    static TestMarshallTest instance;


    public Verify_Home_Page_Test_Pack_Test()
    {
        ApplicationConfig appConfig = new ApplicationConfig();
    }

    @Test
    public void RunCustomGroupTestPackChrome() throws FileNotFoundException
    {
        instance = new TestMarshallTest("TestPacks/Verify_Home_Page.xlsx");
        //instance = new TestMarshallTest();
        instance.runKeywordDrivenTests();
    }

}
