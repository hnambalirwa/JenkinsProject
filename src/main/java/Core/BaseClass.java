package Core;


import Entities.Enums;
import Utils.SeleniumDriverUtility;
import org.joda.time.DateTime;
import org.joda.time.Duration;


/**
 * Created by Harriet Nambalirwa on 04/08/17.
 */
public class BaseClass {

    public String DriverExceptionDetail = "";
    public static Integer screenShotCounter = 1;
    public static String reportDirectory ;
    public String currentTestDirectory;
    public static SeleniumDriverUtility seleniumDriverInstance;
    public static int screenShotFolderCounter;
    private DateTime startTime, endTime;
    private Duration testDuration;
    public Enums.Environment environment;
    String inputFilePath;

    public BaseClass(){
        reportDirectory = "KeywordDrivenTestReports\\";
        environment = Enums.Environment.UAT;
    }

    public void setStartTime()
    {
        this.startTime = new DateTime();
    }

    public long getTotalExecutionTime()
    {
        this.endTime = new DateTime();
        testDuration = new Duration(this.startTime, this.endTime);
        return testDuration.getStandardSeconds();
    }

    public String resolveScenarioName() {
        String isolatedFileNameString;
        String[] splitFileName;
        String[] inputFileNameArray;
        String resolvedScenarioName = "";

        // Get file name from inputFilePath (remove file extension)
        inputFileNameArray = inputFilePath.split("\\.");
        isolatedFileNameString = inputFileNameArray[0];
        if (isolatedFileNameString.contains("/")) {
            inputFileNameArray = isolatedFileNameString.split("/");
        } else if (isolatedFileNameString.contains("\\")) {
            inputFileNameArray = isolatedFileNameString.split("\\\\");
        }

        isolatedFileNameString = inputFileNameArray[inputFileNameArray.length - 1];

        splitFileName = isolatedFileNameString.split("(?=\\p{Upper})");

        for (String word : splitFileName) {
            resolvedScenarioName += word + " ";
        }

        return resolvedScenarioName.trim();
    }

}
