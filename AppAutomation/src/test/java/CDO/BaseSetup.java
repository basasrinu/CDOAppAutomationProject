package CDO;

import Apps.AllEmailAccess;
import Apps.RingtoneMaker;
import Apps.VariableInterface;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

/*
 * Srinivas created this class on the 03/05/2021
 */
public class BaseSetup {
    public static Boolean callLogPermission;
    public static AndroidDriver<AndroidElement> androidDriver;
    public static WebDriverWait wait;
    public static URL url;
    public static String udid, androidVersion,appPackage,appActivity,appProductionPath,appLatestPath,appName,deviceName,screenShotPath;
    VariableInterface variableInterface;
    Boolean upgradeInstall;
    public static CalldoradoSDKTest calldoradoSDKTest;
    String green = "\u001B[32m",defaultColour = "\u001B[0m";
    //report
    public static ExtentReports extent;
    public static ExtentTest logger;

    @BeforeClass(alwaysRun=true)
    @Parameters({"deviceName","port","udid","version","upgrade"})
    public void setUp(String deviceMName, String port, String deviceUdid, String version,Boolean upgrade) throws IOException, InterruptedException {
        //Parameters of devices from testng.xml file
        System.out.println("Parameters value  1. deviceName  "+deviceMName+" 2. Port  "+port + " 3. Udid  "+deviceUdid + " 5. version "+version);
        udid=deviceUdid;
        androidVersion = version;
        this.upgradeInstall = upgrade;
        deviceName = deviceMName;

        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = ClassLoader.getSystemClassLoader().getResourceAsStream("config.properties");
            prop.load(input);
            appName = prop.getProperty("appName");
            System.out.println(green+"App Name : "+appName+defaultColour);
            System.out.println(green+"Production APK Path : " + prop.getProperty("productionAPKPath")+defaultColour);
            appProductionPath = prop.getProperty("productionAPKPath");
            System.out.println(green+"Latest APK Path : " +prop.getProperty("latestAPKPath")+defaultColour);
            appLatestPath = prop.getProperty("latestAPKPath");
            screenShotPath = prop.getProperty("screenshotPath")+"\\"+appName+"\\"+deviceName+"_"+androidVersion+"\\";
        } catch (IOException io) {
            io.printStackTrace();
        }

        //Choose app class here
       /* switch (appName) {

            case "RingtoneMaker" -> variableInterface = new RingtoneMaker();
            case "AllEmailAccess" -> variableInterface = new AllEmailAccess();

            default -> System.err.println(appName + " did not match");
        }
        */
        variableInterface = new AllEmailAccess();

        //Values getting from properties file
        appPackage = variableInterface.getTestAppPackage();
        appActivity = variableInterface.getTestAppActivity();
        callLogPermission = variableInterface.getCallLogPermission();
        appName = variableInterface.getTestAppName();

        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("deviceName",deviceName);
        cap.setCapability("udid",udid);
        cap.setCapability("platformName","Android");
        cap.setCapability("appPackage",appPackage );
        cap.setCapability("appActivity",appActivity);
        cap.setCapability("noReset", "true");
        //cap.setCapability("fullReset", "false");
        System.out.println(upgradeInstall);
        if(upgradeInstall){
            cap.setCapability("app",appProductionPath);
            extent = new ExtentReports(screenShotPath+"CDO_Upgrade.html"); }
        else{
            cap.setCapability("app",appLatestPath);
            extent = new ExtentReports(screenShotPath+"CDO_Direct.html"); }

        try {
            url = new URL("http://0.0.0.0:" + port +"/wd/hub");
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        androidDriver = new AndroidDriver<AndroidElement>(url, cap);
        System.out.println(appName+" App started....");
        wait = new WebDriverWait(androidDriver, 30);
        calldoradoSDKTest = new CalldoradoSDKTest(variableInterface,udid,androidVersion,deviceName);
        Dimension windowSize = androidDriver.manage().window().getSize();
        System.out.println(windowSize.height + " _ " + windowSize.width);
    }

    @AfterMethod
    public void getResult(ITestResult result) throws Exception {
        if (result.getStatus() == ITestResult.SUCCESS) {
            logger.log(LogStatus.PASS, result.getName()+" passed.");
        }else {
            BaseSetup.logger.log(LogStatus.FAIL, result.getName()+" failed.");
        }
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        extent.flush();
        androidDriver.quit();
    }
}
