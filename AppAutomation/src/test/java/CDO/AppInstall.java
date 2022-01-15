package CDO;

import com.relevantcodes.extentreports.LogStatus;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

/*
 * Srinivas created this class on the 03/05/2021
 */
class AppInstall {

    AndroidDriver<AndroidElement> androidDriver = BaseSetup.androidDriver;
    String appPackage = BaseSetup.appPackage;
    String appPath_latest_version = BaseSetup.appLatestPath,appPath_production_version = BaseSetup.appProductionPath;
    String green = "\u001B[32m",defaultColour = "\u001B[0m";

    public void reInstallApp(String testcase) throws InterruptedException {
        androidDriver.removeApp(appPackage);
        System.out.println("Test case : "+ testcase +" app removed");
        androidDriver.installApp(appPath_latest_version);
        System.out.println("Test case : "+ testcase +"  Installed " + appPath_latest_version);
        BaseSetup.logger.log(LogStatus.INFO, appPath_latest_version+" reinstalled");
        Thread.sleep(2000);
        androidDriver.launchApp();
        System.out.println("Test case : "+ testcase +" app launch");
    }
    public void installProductionApp(String testcase) throws InterruptedException {
        if(androidDriver.isAppInstalled(appPackage)){
            androidDriver.removeApp(appPackage);
            System.out.println("Test case : "+ testcase +" app removed");
        }
        androidDriver.installApp(appPath_production_version);
        System.out.println("Test case : "+ testcase +"  Installed " + appPath_production_version);
        BaseSetup.logger.log(LogStatus.INFO, "Installed production version " + appPath_production_version);
        Thread.sleep(2000);
    }
    public void upgradeApp(String testcase) throws InterruptedException {
        Thread.sleep(2000);
        androidDriver.installApp(appPath_latest_version);
        System.out.println( green+testcase +" Updated " + appPath_latest_version+defaultColour);
        BaseSetup.logger.log(LogStatus.INFO, "Upgrade to latest version " + appPath_latest_version);
    }
}
