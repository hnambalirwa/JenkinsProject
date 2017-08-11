package tests;

import Entities.Enums;
import Entities.TestEntity;
import Core.BaseClass;
import Utils.ApplicationConfig;
import Utils.ExcelReaderUtility;
import Utils.SeleniumDriverUtility;
import unitTests.tests.QA_Manual_Test_Class;
import unitTests.tests.Verify_Home_Page_Test_Class;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Harriet Nambalirwa on 04/08/17.
 */
public class TestMarshallTest extends BaseClass {

    // Handles calling test methods based on test parameters , instantiates Selenium Driver object
    ExcelReaderUtility excelInputReader;

//

    public TestMarshallTest() {
        inputFilePath = ApplicationConfig.InputFileName();
        testDataList = new ArrayList<>();
        excelInputReader = new ExcelReaderUtility();
        browserType = ApplicationConfig.SelectedBrowser();
        SeleniumDriverInstance = new SeleniumDriverUtility(browserType);

    }

    public TestMarshallTest(String inputFilePathIn) {
        inputFilePath = inputFilePathIn;
        testDataList = new ArrayList<>();
        excelInputReader = new ExcelReaderUtility();
        browserType = ApplicationConfig.resolveBrowserType();
        SeleniumDriverInstance = new SeleniumDriverUtility(browserType);

    }


    public TestMarshallTest(String inputFilePathIn, Enums.BrowserType browserTypeOverride) {
        inputFilePath = inputFilePathIn;
        testDataList = new ArrayList<>();
        excelInputReader = new ExcelReaderUtility();
        browserType = browserTypeOverride;
        SeleniumDriverInstance = new SeleniumDriverUtility(browserType);

    }



    public  void runKeywordDrivenTests() throws FileNotFoundException {

        int numberOfTest = 0;

        testDataList = loadTestData(inputFilePath);

        // Make sure browser is not null - could have thrown an exception and terminated
        CheckBrowserExists();

        if (testDataList.size() < 1)
        {
            System.err.println("Test data object is empty - spreadsheet not found or is empty");

        } else {
            // Each case represents a test keyword found in the excel spreadsheet
            for (TestEntity testData : testDataList) {

                // Skip test methods and test case id's starting with ';'
                if (!testData.TestMethod.startsWith(";") && !testData.TestCaseId.startsWith(";")) {
                    System.out.println("Executing test - " + testData.TestMethod);

                    try {
                        switch (testData.TestMethod) {
                            // A login test starts with a fresh Driver instance

                            case "Login to Google": {
                                ensureNewBrowserInstance();
                                QA_Manual_Test_Class qaManualTest = new QA_Manual_Test_Class(testData);
                                qaManualTest.executeTest();

                                numberOfTest++;
                                break;
                            }case "Verify Home Page": {

                                ensureNewBrowserInstance();
                                Verify_Home_Page_Test_Class verifyHomePageTest = new Verify_Home_Page_Test_Class(testData);
                                verifyHomePageTest.executeTest();

                                numberOfTest++;
                                break;
                            }
                        }

                    }catch(Exception ex){
                        System.err.println("[ERROR] Exception was thrown TestMarhsall" + ex.getStackTrace());

                    }

                    System.out.println("Continuing to next test method");
                    System.out.println("===========================================================================================================");
                    SeleniumDriverUtility.resetScreenShotCounter(screenShotCounter);
                    SeleniumDriverUtility.incrementScreenShotFolderCounter();
                }
            }
                System.out.println("-- END OF TEST PACK EXECUTION --");
                SeleniumDriverUtility.resetScreenShotFolderCounter(screenShotCounter);
            }

        if(SeleniumDriverInstance.Driver != null){
            SeleniumDriverInstance.shutDown();//
        }

        PrintWriter pw = new PrintWriter(new File("NumberOfTestsRun.txt"));
        pw.write((numberOfTest - 1) + "");
        pw.close();

        //**************************************************************************

        //reportEmailer = new TestReportEmailerUtility(reportGenerator.testResults);
        //reportEmailer.SendResultsEmail();


        //**************************************************************************
    }


    private List<TestEntity> loadTestData(String inputFilePath) {
        return excelInputReader.getTestDataFromExcelFile(inputFilePath);
    }

    public static void CheckBrowserExists() {
        if (seleniumDriverInstance == null) {
            seleniumDriverInstance = new SeleniumDriverUtility();
            seleniumDriverInstance.startDriver();
        }
    }

    public static void ensureNewBrowserInstance() {
        if (seleniumDriverInstance.isDriverRunning()) {
            seleniumDriverInstance.shutDown();
        }
        seleniumDriverInstance.startDriver();
    }

}
