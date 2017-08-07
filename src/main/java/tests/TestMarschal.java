package tests;

import Entities.Enums;
import Entities.TestEntity;
import Core.BaseClass;
import Utils.ExcelReaderUtility;
import Utils.SeleniumDriverUtility;
import org.openqa.selenium.io.MultiOutputStream;
import testClasses.JenkinsTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Harriet Nambalirwa on 04/08/17.
 */
public class TestMarschal extends BaseClass {

    // Handles calling test methods based on test parameters , instantiates Selenium Driver object
    ExcelReaderUtility excelInputReader;
    PrintStream errorOutputStream;
    PrintStream infoOutputStream;
    private Integer screenShotCounter = 1;
    static Enums.BrowserType browserType;

    String inputFilePath;
    List<TestEntity> testDataList ;

    public TestMarschal(String testDataFilePath) {

        testDataList = new ArrayList<>();
        excelInputReader = new ExcelReaderUtility();
        seleniumDriverInstance = new SeleniumDriverUtility();

    }

    public  void runKeywordDrivenTests() throws FileNotFoundException {

        int numberOfTest = 0;

        testDataList = loadTestData(inputFilePath);
        this.generateReportDirectory();
        this.redirectOutputStreams();

        if (testDataList.size() < 1) {
            System.err.println("Test data object is empty - spreadsheet not found or is empty");

    } else {
        // Each case represents a test keyword found in the excel spreadsheet
        for (TestEntity testData : testDataList) {

            // Make sure browser is not null - could have thrown an exception and terminated
            CheckBrowserExists();

            // Skip test methods and test case id's starting with ';'
            if (!testData.TestMethod.startsWith(";") && !testData.TestCaseId.startsWith(";")) {
                System.out.println("Executing test - " + testData.TestMethod);

                try {
                    switch (testData.TestMethod) {
                        // A login test starts with a fresh Driver instance

                        case "Login": {
                            ensureNewBrowserInstance();
                            JenkinsTest jenkinsTest = new JenkinsTest(testData);
                            //reportGenerator.addResult(jenkinsTest.executeTest());
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
                SeleniumDriverUtility.incrementScreenShotFolderCounter();}
            }

            System.out.println("-- END OF TEST PACK EXECUTION --");
            SeleniumDriverUtility.resetScreenShotFolderCounter(screenShotCounter);
        }

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

    public String generateDateTimeString() {
        Date dateNow = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");
        String dateTime = dateFormat.format(dateNow).toString();
        return dateTime;
    }

    public void generateReportDirectory() {
        reportDirectory += "\\" + resolveScenarioName() + "_" + this.generateDateTimeString();
        String[] reportsFolderPathSplit = this.reportDirectory.split("\\\\");
        this.currentTestDirectory = reportDirectory + "\\" + reportsFolderPathSplit[reportsFolderPathSplit.length - 1];
    }

    public void redirectOutputStreams()
    {
        try
        {
            File reportDirectoryFile = new File(reportDirectory);
            reportDirectoryFile.mkdirs();

            FileOutputStream infoFile = new FileOutputStream(reportDirectory + "\\" + "InfoTestLog.txt");
            FileOutputStream errorFile = new FileOutputStream(reportDirectory + "\\" + "ErrorTestLog.txt");

            MultiOutputStream multiOut = new MultiOutputStream(System.out,infoFile);
            MultiOutputStream multiErr = new MultiOutputStream(System.err,errorFile);

            infoOutputStream = new PrintStream(multiOut);
            errorOutputStream = new PrintStream(multiErr);

            System.setOut(infoOutputStream);
            System.setErr(errorOutputStream);
        }
        catch (FileNotFoundException ex)
        {
            System.err.println("[Error] could not create log files - " + ex.getMessage());
        }
    }

    public void flushOutputStreams() {

        errorOutputStream.flush();
        infoOutputStream.flush();

        errorOutputStream.close();
        infoOutputStream.close();

    }

}
