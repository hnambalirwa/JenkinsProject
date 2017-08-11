/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import Entities.Enums.BrowserType;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 *
 * @author fnell
 */
public final class ApplicationConfig 
{

    public static Properties appConfig;
    private String appConfigFilePath = "config.properties";
    
    private static String ExcelInputFile, ReportFileDirectory, browserTypeConfig, mailingList;
    private static String emailSender, emailHost;
    public static BrowserType browserType;
    public static boolean verboseReporting;
    
    public static boolean verboseReporting()
    {
        return verboseReporting;
    }

    
    public static String InputFileName()
    {
        return ExcelInputFile;
    }
  
    public static String ReportFileDirectory()
    {
        return ReportFileDirectory;
    }
    
    public static BrowserType SelectedBrowser()
    {
        return browserType;
    }
    public static String EmailHost()
    {
        return emailHost;
    }
    
    public static String[] MailingList() 
    {
        try
        {
            return mailingList.split(";");
        }
        catch(Exception ex)
        {
            return null;
        }
    }
    
    public static String EmailSender()
    {
        return emailSender;
    }
    
    public ApplicationConfig()
    {
        
        try
        {
            loadConfigurationSettings();
        }
        catch(Exception e)
        {
          // One or more of the appConfig values could not be found in the config file - 
          // Reload default values and read from file. 
          generateDefaultConfigurationFile();
          loadExistingConfigurationFile();
          loadConfigurationSettings();
        }
        
    }
    
    private void loadConfigurationSettings()
    {
        if(!loadExistingConfigurationFile())
        {
            generateDefaultConfigurationFile();
        }
        try
        {
            ExcelInputFile = appConfig.getProperty("ExcelInputFile");
            ReportFileDirectory = appConfig.getProperty("ReportFileDirectory");
            
            mailingList = appConfig.getProperty("MailingList");
            emailSender = appConfig.getProperty("EmailSender");
            emailHost = appConfig.getProperty("EmailHost");
            verboseReporting = Boolean.parseBoolean(appConfig.getProperty("VerboseReporting"));

            browserType = resolveBrowserType();
        }
        catch(Exception e)
        {
            System.out.println("Error Loading application configuration...see stack trace:");
            e.printStackTrace();
        }       
                
    }
    public static BrowserType resolveBrowserType()
    {
        browserTypeConfig = appConfig.getProperty("BrowserType");
        
        switch(browserTypeConfig)
        {
            case "IE":
                return BrowserType.IE;
            case "FireFox":
                return BrowserType.FireFox;
            case "Chrome":
                return BrowserType.Chrome;
            case "Safari":
                return BrowserType.Safari;
            default: 
                return BrowserType.IE;
        }
    }
    
    
    public BrowserType resolveBrowserType(String browserType) 
    {
        browserTypeConfig = browserType;

        switch (browserTypeConfig) {
            case "IE":
                return BrowserType.IE;
            case "FireFox":
                return BrowserType.FireFox;
            case "Chrome":
                return BrowserType.Chrome;
            case "Safari":
                return BrowserType.Safari;
            default:
                return BrowserType.IE;
        }
    }
    private void generateDefaultConfigurationFile()
    {
        try
        {
           appConfig = new Properties();
           appConfig.setProperty("ExcelInputFile", "Keyword Input.xls");
           appConfig.setProperty("ReportFileDirectory", "TestReports/");
           appConfig.setProperty("BrowserType","IE");
           appConfig.setProperty("WaitTimeout", "15");
           appConfig.setProperty("MyPageUrl","");
           
           appConfig.setProperty("MailingList", "fnell@dvt.co.za");
           appConfig.setProperty("EmailSender" , "Automation@etana.co.za");
           appConfig.setProperty("EmailHost", "localhost");      
           
           appConfig.store(new FileOutputStream(appConfigFilePath),null);
           
        }
        catch(Exception e)
        {
            System.out.println("Error Loading default configuration...see stack trace:");
            e.printStackTrace();
        }
    }
    private boolean loadExistingConfigurationFile()
    {
        try
        {  
            if(appConfig == null)
            {
                appConfig = new Properties();
            }
           appConfig.load(new FileInputStream(appConfigFilePath));
           return true;
           
        }
        catch(Exception e)
        {
            System.out.println("Configuration file not found, reverting to default configuration...see stack trace:");
            e.printStackTrace();
            System.out.println("Loading default configuration");
            return false;
        }
    }
    
}
