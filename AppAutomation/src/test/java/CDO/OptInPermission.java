package CDO;

import com.relevantcodes.extentreports.LogStatus;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/*
 * Srinivas created this class on the 03/05/2021
 */
class OptInPermission {

    WebDriverWait wait = BaseSetup.wait;
    String android_version= BaseSetup.androidVersion;
    CDO.URL URL = new URL();
    Boolean callLog = BaseSetup.callLogPermission;
    AndroidDriver<AndroidElement> androidDriver = BaseSetup.androidDriver;
    String deviceName = BaseSetup.deviceName;
    String appPackage = BaseSetup.appPackage;
    String green = "\u001B[32m",defaultColour = "\u001B[0m";

    public void acceptAllThePermission() throws InterruptedException {
        Thread.sleep(1000);
        clickOn(URL.getOptInContinueButtonURL());
        if(android_version.equals("10")||android_version.equals("9")){
            if(callLog) {
                allowPermission(android_version,"Phone");
                allowPermission(android_version,"Call Log");
                allowPermission(android_version,"Contact");
            }else{
                allowPermission(android_version,"Phone");
                allowPermission(android_version,"Contact"); }
        }else {
            allowPermission(android_version,"Phone");
            allowPermission(android_version,"Contact");
        }

        if(android_version.equals("10")){
            allowOverlayPermission();
            Thread.sleep(1000);
            allowLocationPermission();
        }else {
            allowLocationPermission();
            Thread.sleep(1000);
            allowOverlayPermission(); }

        Thread.sleep(1000);
        if(deviceName.equals("Huawei")){
            clickOn(URL.getContinueButtonURL());
            System.out.println("Battery optimization");
            Thread.sleep(1000);
            androidDriver.pressKey(new KeyEvent(AndroidKey.BACK));
        }
    }

    public void denyAllThePermissions() throws InterruptedException{
        clickOn(appPackage + ":id/content_accept_btn");

        if(android_version.equals("10") || android_version.equals("9")){
            if(callLog) {
                denyPermission(android_version,"Phone");
                denyPermission(android_version,"Call Log");
                denyPermission(android_version,"Contact");
            }else{
                denyPermission(android_version,"Phone");
                denyPermission(android_version,"Contact");
            }
        }else {
            denyPermission(android_version,"Phone");
            denyPermission(android_version,"Contact");
        }
        if(android_version.equals("10")){
            denyOverlayPermission();
            Thread.sleep(1000);
            denyLocationPermission();
        } else{
            denyLocationPermission();
            Thread.sleep(1000);
            denyOverlayPermission();
            Thread.sleep(1000);
        }
    }
    public void acceptPhoneAndContactPermission() throws InterruptedException {
        clickOn(appPackage + ":id/content_accept_btn");
        if(android_version.equals("10")){
            if(callLog) {
                allowPermission(android_version,"Phone");
                allowPermission(android_version,"Call Log");
                allowPermission(android_version,"Contact");
            }else{
                allowPermission(android_version,"Phone");
                allowPermission(android_version,"Contact");
            }
        }else {
            allowPermission(android_version,"Phone");
            allowPermission(android_version,"Contact");
        }
    }

    public void allowPermission(String android_version, String permissionName) throws InterruptedException {
        clickOn(URL.getPermissionAcceptURL());
        Thread.sleep(2000);
        System.out.println(green+ permissionName + " permission accepted on android version "+android_version+ defaultColour);
        BaseSetup.logger.log(LogStatus.PASS, permissionName + " permission accepted on android version "+android_version +" successfully");
    }

    public void denyPermission(String android_version, String permissionName) throws InterruptedException {
        clickOn(URL.getPermissionDenyURL());
        Thread.sleep(1000);
        System.out.println(green + permissionName + " permission denied on android version "+ android_version +defaultColour);
        BaseSetup.logger.log(LogStatus.PASS, permissionName + " permission denied");
    }

    public void allowOverlayPermission() throws InterruptedException {
        //permission_overlay
        Thread.sleep(1000);
        try {
            if(androidDriver.findElement(By.id(URL.getContinueButtonURL())).isDisplayed()){
                clickOn(URL.getContinueButtonURL());
                System.out.println("Tap on overlay continue button");
                Thread.sleep(2000);
                clickOn(URL.getOverLaySwitchButtonURL());
                System.out.println("Toggle button enabled");
                System.out.println(green+"Over lay permission accepted on android version "+android_version+defaultColour); }
                BaseSetup.logger.log(LogStatus.PASS, "Over lay permission accepted on android version"+android_version +" successfully");
        }catch (Exception e) {
            System.err.println("Over lay permission not requested");
            BaseSetup.logger.log(LogStatus.WARNING, "Over lay permission not requested");
        }
    }

    public void denyOverlayPermission() throws InterruptedException {
        //permission_overlay
        Thread.sleep(1000);
        clickOn(URL.getContinueButtonURL());
        Thread.sleep(1000);
        clickOn(appPackage+":id/action_bar_root");
        androidDriver.pressKey(new KeyEvent(AndroidKey.BACK));
        System.out.println(green+"Overlay permission is denied"+defaultColour);
        BaseSetup.logger.log(LogStatus.PASS, "Overlay permission is denied");
    }

    public void allowLocationPermission() throws InterruptedException {
        Thread.sleep(1000);
        clickOn(URL.getContinueButtonURL());
        System.out.println("Tap on continue button on Location screen");
        if (android_version.equals("10")){
            Thread.sleep(1000);
            try {
                if(androidDriver.findElement(By.id(URL.getLocationAllowAlwaysButtonURL())).isDisplayed()) {
                    clickOn(URL.getLocationAllowAlwaysButtonURL());
                    System.out.println("Location permission \"Allow always\" is accepted successfully");
                    BaseSetup.logger.log(LogStatus.INFO, "Background location permission \"Allow always\" is accepted successfully");
                }
            }catch (NoSuchElementException exception){
                clickOn(URL.getLocationAllowForegroundButtonURL());
                System.out.println("Location permission \"Allow When app open\" is accepted successfully");
                BaseSetup.logger.log(LogStatus.INFO, "Location permission \"Allow When app open\" is accepted successfully");}
        }else{
            clickOn(URL.getLocationAllowButtonURL()); }
            System.out.println(green+"Location permission accepted on android version "+android_version+defaultColour);
            BaseSetup.logger.log(LogStatus.PASS, "Location permission accepted on android version "+android_version);
    }

    public void denyLocationPermission(){
        clickOn(URL.getContinueButtonURL());
        clickOn(URL.getPermissionDenyURL());
        System.out.println(green+"Location permission is denied"+defaultColour);
        BaseSetup.logger.log(LogStatus.PASS, "Location permission is denied");
    }

    public void clickOn(String url) {
        WebElement Button = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(url)));
        Button.click(); }
}
