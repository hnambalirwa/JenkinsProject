package testSuits;

import Entities.Enums;
import org.junit.Test;
import tests.TestMarschal;

import java.io.FileNotFoundException;

/**
 * Created by Harriet Nambalirwa on 04/08/17.
 */
public class JenkinsTest {

    static TestMarschal instance;

//    @Test
    public void RunCustomGroupTestPackChrome() throws FileNotFoundException
    {
        instance = new TestMarschal("TestPacks/AccessControlTestPack.xlsx");
        instance.runKeywordDrivenTests();
    }

}
