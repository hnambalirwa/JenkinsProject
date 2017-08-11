package unitTests.testSuits;

import Utils.ApplicationConfig;
import org.testng.annotations.Test;
import tests.TestMarshallTest;
import unitTests.tests.BaseTest;

import java.io.FileNotFoundException;

/**
 * Created by Harriet Nambalirwa on 04/08/17.
 */
public class QA_manual_Test_Pack_Test extends BaseTest {

    static TestMarshallTest instance;


    public QA_manual_Test_Pack_Test()
    {
        ApplicationConfig appConfig = new ApplicationConfig();
    }

   @Test
    public void RunCustomGroupTestPackChrome() throws FileNotFoundException
    {
        instance = new TestMarshallTest("TestPacks/QA manual.xlsx");
        instance.runKeywordDrivenTests();
    }

}
