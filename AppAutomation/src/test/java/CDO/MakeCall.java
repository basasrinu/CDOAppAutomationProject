package CDO;

import com.relevantcodes.extentreports.LogStatus;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.NoSuchElementException;

import java.io.IOException;

/*
 * Srinivas created this class on the 21/04/2021
 */
public class MakeCall {
    public static AndroidDriver<AndroidElement> androidDriver;
    String udid;
    ScreenShot screenShot = new ScreenShot();

    public MakeCall() {
        androidDriver = BaseSetup.androidDriver;
        udid = BaseSetup.udid;
    }

    public void makePhoneCall() throws IOException, InterruptedException {
        Thread.sleep(1000);
        Process exec = Runtime.getRuntime().exec("adb -s "+udid+" shell am start -a android.intent.action.CALL -d tel:53505915");
       // System.out.println(exec.info());
        System.out.println("Call number : 53505915");
        BaseSetup.logger.log(LogStatus.PASS, "Dial 53505915");
        Thread.sleep(1000);
    }

    public void hangUpCall(String testCase,String deviceName) throws InterruptedException, IOException {
        Thread.sleep(2000);
        if(deviceName.equals("Huawei")){
            try {
                screenShot.getScreenshot(testCase+"_WIC.png");
                androidDriver.findElementByAccessibilityId("End").click();
            } catch (NoSuchElementException e){
                androidDriver.pressKey(new KeyEvent(AndroidKey.BACK));
                androidDriver.findElementByAccessibilityId("End").click(); }
        }else{
            try {
                screenShot.getScreenshot(testCase+"_WIC.png");
                androidDriver.findElementByAccessibilityId("End call").click();
            } catch (NoSuchElementException e){
                androidDriver.pressKey(new KeyEvent(AndroidKey.BACK));
                androidDriver.findElementByAccessibilityId("End call").click(); }
            }
        System.out.println("Tap on the End call button");
        BaseSetup.logger.log(LogStatus.PASS, "Hang up");
    }
}
