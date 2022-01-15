package CDO;

import Apps.VariableInterface;
import com.relevantcodes.extentreports.LogStatus;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.List;

/*
 * Srinivas created this class on the 21/04/2021
 */
public class CalldoradoSDKTest {
    WebDriverWait wait = BaseSetup.wait;
    AndroidDriver<AndroidElement> androidDriver = BaseSetup.androidDriver;

    String appPath_latest_version = BaseSetup.appLatestPath,
            android_version,
            udid,
            screenshotPath = BaseSetup.screenShotPath,
            deviceName,
            appPackage;
    Boolean callLog;
    String green = "\u001B[32m",defaultColour = "\u001B[0m";

    //URL's
    CDO.URL URL = new URL();
    //Print & save logs on file
    Logs logs = new Logs();
    //Make call & hangup
    MakeCall makeCallAndHangUp = new MakeCall();
    //OptIn permission
    OptInPermission optInPermission = new OptInPermission();
    //Take screenshot & save the screenshot on local system device
    ScreenShot screenShot = new ScreenShot();
    //Check after call & save screenshot
    AfterCallScreen afterCallScreen = new AfterCallScreen();
    //Install the app, reinstall & upgrade
    AppInstall appInstall = new AppInstall();

    public CalldoradoSDKTest(VariableInterface variableInterface, String udid, String androidVersion, String deviceName)  {
        appPackage = variableInterface.getTestAppPackage();
        this.udid = udid;
        this.android_version = androidVersion;
        callLog = variableInterface.getCallLogPermission();
        this.deviceName = deviceName;
    }

    public void deviceBACKButton() throws InterruptedException, IOException {
        //Validate the "PP & EULA" screen by tapping the device BACK button
        Thread.sleep(1000);
        if (androidDriver.findElement(By.id(URL.getOptInContinueButtonURL())).isDisplayed()) {
            androidDriver.pressKey(new KeyEvent(AndroidKey.BACK));
            //System.out.println("Validate the "PP & EULA" screen by tapping the device BACK button");
            Thread.sleep(1000);
            if (androidDriver.findElement(By.id(URL.getConsentDialogOkButtonURL())).isDisplayed()) {
                //Ok button on dialog
                screenShot.getScreenshot("DirectInstall"+"\\"+"backButtonDialog.png");
                System.out.println("backButtonDialog screenshot saved " + android_version);
                clickOn(URL.getConsentDialogOkButtonURL());
                System.out.println(green+"Test case :  is success - Back button dialog is show on PP & EULA screen."+defaultColour);
                BaseSetup.logger.log(LogStatus.PASS, "Validate the PP & EULA screen by tapping the device BACK button - back button dialog shown successfully");
            } else {
                System.err.println("Test case :  is failed - No dialog shown");
                BaseSetup.logger.log(LogStatus.FAIL, "Validate the PP & EULA screen by tapping the device BACK button - No dialog shown");
            }
        }
    }

    public void deviceHOMEButton() throws InterruptedException, IOException {
        //Validate the "PP & EULA" screen by tapping the device HOME button & launch the app using the app icon
        Thread.sleep(1000);
        if (androidDriver.findElement(By.id(URL.getOptInContinueButtonURL())).isDisplayed()) {

            androidDriver.pressKey(new KeyEvent(AndroidKey.HOME));
            System.out.println("Tap device Home button when Opt-in welcome screen shown");
            Thread.sleep(1000);
            androidDriver.closeApp();
            Thread.sleep(1000);
            androidDriver.launchApp();
            Thread.sleep(2000);
            if (androidDriver.findElement(By.id(URL.getOptInContinueButtonURL())).isDisplayed()) {
                System.out.println(green+"Test case :  is success - OptIn shown after reopening the app"+defaultColour);
                BaseSetup.logger.log(LogStatus.PASS, "OptIn screen shown successfully");
            } else {
                System.err.println("Test case :  is failed OptIn screen not shown after relaunch the app");
                BaseSetup.logger.log(LogStatus.FAIL, "OptIn screen not shown again");
                screenShot.getScreenshot("DirectInstall"+"\\"+"relaunchFailed.png");
                System.out.println("relaunchFailed screenshot saved");
            }
        }
    }
    public void debugScreenshot()  throws InterruptedException, IOException {

        //Debug - Validate the CDO version number and opt-in SDK version number ,FAN SDK version , Google AD SDK, Third  party SDK version numbers P3 & Tutela in the debug dialog
        Thread.sleep(2000);
        debugCMD();
        //Thread.sleep(1000);
       // optInPermission.acceptAllThePermission();
        Thread.sleep(1000);
        //androidDriver.pressKey(new KeyEvent(AndroidKey.HOME));
       /* makeCall("DirectInstall"+"\\"+"debug");
        Thread.sleep(2000);
        afterCallScreen.verifyAfterCall("DirectInstall"+"\\"+"Debug");
        Thread.sleep(2000);*/
        try {
             if (androidDriver.findElement(By.id(URL.getAfterCallImageButtonURL())).isDisplayed()) {
               clickOn(URL.getAfterCallImageButtonURL());
               Thread.sleep(1000);
               androidDriver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(textContains(\"App Version:\"));");
               screenShot.getScreenshot("DirectInstall"+"\\"+"DebugCDOVersion.png");
               System.out.println("Debug screen - CDO Version screenshot saved");
               BaseSetup.logger.log(LogStatus.PASS, "Debug screen - CDO Version screenshot saved");
               By verifyChecklist = MobileBy.AndroidUIAutomator("new UiSelector().text(\"ADS\")");
               wait.until(elementFoundAndClicked(verifyChecklist));

               androidDriver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(textContains(\"LOAD ALWAYS\"));");

               Thread.sleep(1000);
               screenShot.getScreenshot("DirectInstall"+"\\"+"Debug_FB_Dfp_AdVersion.png");
               System.out.println("Debug screen - Ads provider version screenshot saved");
               BaseSetup.logger.log(LogStatus.PASS, "Debug screen - Ads provider version screenshot saved");
             }
        }catch (NoSuchElementException e){
            System.out.println("Debug screen - failed to enter");
            BaseSetup.logger.log(LogStatus.FAIL, "Debug screen - failed to enter");
        }
    }

    public void directInstallThirdParty() throws InterruptedException, IOException {

        //Debug - Validate third-party data SDK P3, Tutela, Open signal, and Cuebiq initialization
        if(androidDriver.isAppInstalled(appPackage)){
            androidDriver.removeApp(appPackage);
            System.out.println("Test case : directInstallThirdParty app removed");
        }
        androidDriver.installApp(appPath_latest_version);
        System.out.println("App is reinstalled " + appPath_latest_version);
        BaseSetup.logger.log(LogStatus.INFO, appPath_latest_version+"App is reinstalled successfully");
        Thread.sleep(2000);
        debugCMD();
        Thread.sleep(1000);
        androidDriver.launchApp();
        Thread.sleep(1000);
        //Some device need to open the app to execute the debug cmd
        if(deviceName.equals("Huawei") && android_version.equals("10")){
            debugCMD();
            Thread.sleep(1000);
        }
        optInPermission.acceptAllThePermission();
        Thread.sleep(1000);
        // logs("DirectInstallLogs");
        logs.logs("DirectInstall"+"\\"+"DirectInstallLogs",screenshotPath,android_version,androidDriver);
        BaseSetup.logger.log(LogStatus.PASS, "Logs saved successfully in DirectInstallLogs.txt file");
        androidDriver.pressKey(new KeyEvent(AndroidKey.HOME));
        makeCall("DirectInstall"+"\\"+"DirectThirdParty");
        Thread.sleep(2000);
        afterCallScreen.verifyAfterCall("DirectInstall"+"\\"+"DirectInstallThirdParty");
        Thread.sleep(1000);
    }

    public void acceptAllPermissions() throws InterruptedException, IOException {
        //Verify the phone & contact More info screen & Accept all CDO SDK permissions and verify WIC & AC is working
        String tc="acceptAllPermissions";
        //appInstall.reInstallApp(tc);
        //Phone more info
        Thread.sleep(1000);
        clickOn(URL.getPhonePermissionMore());
        Thread.sleep(1000);
        clickOn(URL.getContinueButtonURL());//ok button
        System.out.println(green+"Verified Phone permission more details screen successfully"+defaultColour);
        BaseSetup.logger.log(LogStatus.PASS, "Verified Phone permission more details screen successfully");

        Thread.sleep(1000);
        clickOn(URL.getContactPermissionMore());
        Thread.sleep(1000);
        clickOn(URL.getContinueButtonURL());
        System.out.println(green+"Verified Contact permission more details screen successfully"+defaultColour);
        BaseSetup.logger.log(LogStatus.PASS, "Verified Contact permission more details screen successfully");

        optInPermission.acceptAllThePermission();

        androidDriver.pressKey(new KeyEvent(AndroidKey.HOME));
        makeCall("DirectInstall"+"\\"+tc);
        Thread.sleep(2000);
        afterCallScreen.verifyAfterCall("DirectInstall"+"\\"+tc);

    }


    public void denyOverLay() throws IOException, InterruptedException {
        //Validate tapping the device back button on the Overlay screen with "Got it" button screen and verify CDO is working on Android 9 & below devices and make sure missing overlay permission notification on Android 10 and above
        String tc="denyOverLay";
        appInstall.reInstallApp(tc);
        optInPermission.acceptPhoneAndContactPermission();
        androidDriver.pressKey(new KeyEvent(AndroidKey.BACK));
        Thread.sleep(2000);
        androidDriver.pressKey(new KeyEvent(AndroidKey.HOME));
        makeCall("DirectInstall"+"\\"+tc);
        Thread.sleep(1000);
        overLayNotification("DirectInstall"+"\\"+tc);
        afterCallScreen.verifyAfterCall("DirectInstall"+"\\"+tc+"_AfterCall");
    }

    //Upgrade
    public void denyAllPermissionsAndUpgrade() throws InterruptedException, IOException {
        //Upgrade test cases -  Upgrade the app when all permissions are denied
        String tc="denyAllPermissions";
        appInstall.installProductionApp("denyAllPermissionsAndUpgrade - Upgrade test cases");

        androidDriver.launchApp();
        Thread.sleep(1000);
        optInPermission.denyAllThePermissions();
        androidDriver.closeApp();
        Thread.sleep(1000);

        appInstall.upgradeApp(tc);
        Thread.sleep(2000);
        androidDriver.launchApp();

        Thread.sleep(2000);
        if (androidDriver.findElement(By.id(appPackage + ":id/content_accept_btn")).isDisplayed()) {
            optInPermission.acceptAllThePermission();
            System.out.println(green+"Test case : is success - optIn shown again after all permissions are denied in production version"+defaultColour);
            BaseSetup.logger.log(LogStatus.PASS,"OptIn shown again after all permissions are denied in production version");
        } else {
            System.err.println("Test case : is failed OptIn screen not shown on update version");
            BaseSetup.logger.log(LogStatus.FAIL,"OptIn screen not shown on update version");
            screenShot.getScreenshot(deviceName+"PermissionDeniedInProduction.png");
        }
        makeCall("Upgrade"+"\\"+tc);
        Thread.sleep(2000);
        afterCallScreen.verifyAfterCall("Upgrade"+"\\"+tc);
    }

    public void acceptAllPermissionsAndUpgrade() throws InterruptedException, IOException {
        //Upgrade test cases -  Upgrade the app after accepting all permissions
        String tc ="acceptAllPermissions";
        appInstall.installProductionApp(tc);
        androidDriver.launchApp();
        optInPermission.acceptAllThePermission();
        androidDriver.pressKey(new KeyEvent(AndroidKey.HOME));
        Thread.sleep(2000);
        makeCall("Upgrade"+"\\"+tc+"_production");
        Thread.sleep(1000);
        appInstall.upgradeApp(tc);
        Thread.sleep(10000);
        if(deviceName.equals("Huawei")){
            Thread.sleep(10000); }
        makeCall("Upgrade"+"\\"+tc);
        afterCallScreen.verifyAfterCall("Upgrade"+"\\"+tc);
    }
    public void thirdPartyAfterUpgrade() throws InterruptedException, IOException {
        //Upgrade test cases -  Validate third-party data SDK P3, Tutela, Open signal and Cuebiq initialization
        String tc ="thirdPartyAfterUpgrade";
        appInstall.installProductionApp(tc);

        Thread.sleep(2000);
        debugCMD();
        Thread.sleep(1000);
        androidDriver.launchApp();

        //Some device need to open the app to execute the debug cmd
        if(deviceName.equals("Huawei") && android_version.equals("10")){
            Process exec2 = Runtime.getRuntime().exec("adb -s "+udid+" shell am broadcast -a cfgQWCB -n "+appPackage+"/com.calldorado.receivers.cdfQWCBReceiver");
           // System.out.println(exec2.info());
            Thread.sleep(1000);
        }

        optInPermission.acceptAllThePermission();

        androidDriver.pressKey(new KeyEvent(AndroidKey.HOME));
        //logs("Production_1_");
        BaseSetup.logger.log(LogStatus.INFO,"Logs from Production");
        logs.logs("Upgrade"+"\\"+"Production",screenshotPath,android_version,androidDriver);
        Thread.sleep(1000);
        makeCall("Upgrade"+"\\"+tc);
        Thread.sleep(1000);
        appInstall.upgradeApp(tc);
        Thread.sleep(20000);
        logs.logs("Upgrade"+"\\"+"Upgrade",screenshotPath,android_version,androidDriver);
        BaseSetup.logger.log(LogStatus.INFO,"Logs from upgrade before opening the app saved on Upgrade");
        if(deviceName.equals("Huawei")){
            Thread.sleep(10000); }
        makeCall("Upgrade"+"\\"+tc);
        afterCallScreen.verifyAfterCall("Upgrade"+"\\"+tc);
        androidDriver.launchApp();
        Thread.sleep(10000);
        BaseSetup.logger.log(LogStatus.INFO,"Logs from upgrade after opening the app saved on Upgrade_1");
        logs.logs("Upgrade"+"\\"+"Upgrade_1",screenshotPath,android_version,androidDriver);
        Thread.sleep(1000);
    }

    public void denyOverlayAndUpgrade() throws IOException, InterruptedException {
        //Validate Activity WIC (Without overlay permission) after Upgrading the app
        String tc ="denyOverlayAndUpgrade";
        appInstall.installProductionApp(tc);
        androidDriver.launchApp();
        Thread.sleep(2000);
       optInPermission.acceptPhoneAndContactPermission();
        if(android_version.equals("10")){
            optInPermission.denyOverlayPermission();
            optInPermission.denyLocationPermission();
        }else {
            optInPermission.denyLocationPermission();
            optInPermission.denyOverlayPermission();
        }
        androidDriver.pressKey(new KeyEvent(AndroidKey.BACK));
        Thread.sleep(2000);
        androidDriver.pressKey(new KeyEvent(AndroidKey.HOME));

        appInstall.upgradeApp(tc);

        Thread.sleep(10000);
        if(deviceName.equals("Huawei")){
            Thread.sleep(10000); }
        makeCall("Upgrade"+"\\"+tc);
        Thread.sleep(2000);
        overLayNotification("Upgrade"+"\\"+tc);
        Thread.sleep(2000);
        afterCallScreen.verifyAfterCall("Upgrade"+"\\"+tc);
    }

    public void overLayNotification(String testcase) throws IOException, InterruptedException {
         if(android_version.equals("10")){
              androidDriver.openNotifications();
              List<AndroidElement> allNotifications=androidDriver.findElements(By.id("android:id/title"));
              for (WebElement webElement : allNotifications) {
                 System.out.println(webElement.getText());
                 if(webElement.getText().contains("See call information")){
                  screenShot.getScreenshot(testcase+"Notification.png");
                 System.out.println(green+"See Call information Notification found - Overlay Notification After Update"+defaultColour);
                 BaseSetup.logger.log(LogStatus.PASS, "See Call information Notification found" );
                  webElement.click();
                  break; }
              }
            /*webElement("android:id/switch_widget");
            System.out.println("Toggle button enabled");*/
             System.out.println(green+"Android version "+ android_version  +" Overlay notification shown"+defaultColour);
             BaseSetup.logger.log(LogStatus.PASS, "Android version "+ android_version  +" Overlay notification shown" );
         }else {
                try {
                    String activity = androidDriver.currentActivity();
                    if(activity.equals(URL.getAfterCallActivity())) {
                        if (androidDriver.findElement(By.id(URL.getAfterCallImageButtonURL())).isDisplayed()) {
                            System.out.println(green + "Android version " + android_version + " After call screen shown without overlay permission after update " + defaultColour);
                            BaseSetup.logger.log(LogStatus.PASS, "Android version " + android_version + " After call screen shown without overlay permission" );
                        }
                    }
                }catch (NoSuchElementException e){
                     System.err.println("No after call screen shown" + e);
                    BaseSetup.logger.log(LogStatus.FAIL, "No after call screen shown");
                }
         }
    }



    private ExpectedCondition<Boolean> elementFoundAndClicked(By locator) {
        return driver -> {
            WebElement el = driver.findElement(locator);
            el.click();
            return true;
        };
    }
    public void clickOn(String url) {
        WebElement Button = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(url)));
        Button.click(); }

    public void debugCMD() throws IOException {
        Process exec = Runtime.getRuntime().exec("adb -s "+udid+" shell am broadcast -a cfgQWCB -n "+appPackage+"/com.calldorado.receivers.cdfQWCBReceiver");
        //Process exec = Runtime.getRuntime().exec("adb -s "+udid+" shell am broadcast -a cfgQWCB -n "+appPackage+"/com.calldorado.util.cdfQWCBReceiver");
//        System.out.println(exec.info());
        BaseSetup.logger.log(LogStatus.PASS, "Debug mode enabled successfully");
    }

    public void makeCall(String testCase) throws IOException, InterruptedException {
        makeCallAndHangUp.makePhoneCall();
        Thread.sleep(2000);
        makeCallAndHangUp.hangUpCall(testCase,deviceName);
    }
}

