package CDO;

import com.relevantcodes.extentreports.LogStatus;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.IOException;

/*
 * Srinivas created this class on the 03/05/2021
 */
class AfterCallScreen {
    AndroidDriver<AndroidElement> androidDriver = BaseSetup.androidDriver;
    String androidVersion = BaseSetup.androidVersion;
    String green = "\u001B[32m",defaultColour = "\u001B[0m";
    CDO.URL URL = new URL();
    ScreenShot screenShot = new ScreenShot();

    public void verifyAfterCall(String testCase) throws IOException, InterruptedException {
        Thread.sleep(2000);
        String activity = androidDriver.currentActivity();
        System.out.println(activity);
        try {
            if(activity.equals(URL.getAfterCallActivity())){
                BaseSetup.wait.until(ExpectedConditions.presenceOfElementLocated(By.id(URL.getAfterCallImageButtonURL())));
                if (androidDriver.findElement(By.id(URL.getAfterCallImageButtonURL())).isDisplayed()){
                    screenShot.getScreenshot(testCase+"_AC.png");
                    System.out.println(green+"After call screen shown"+defaultColour);
                    BaseSetup.logger.log(LogStatus.PASS, "After call screen shown");
                }
            }else {
                Thread.sleep(2000);
                if (androidDriver.findElement(By.id(URL.getAfterCallImageButtonURL())).isDisplayed()){
                    screenShot.getScreenshot(testCase+"_AC.png");
                    System.out.println(green+"After call screen shown on 2nd try"+defaultColour);
                    BaseSetup.logger.log(LogStatus.PASS, "After call screen shown on 2nd try");
                }else{
                    System.err.println("No after call screen shown");
                    BaseSetup.logger.log(LogStatus.FAIL, "No after call screen shown");
                }
            }
        }catch (NoSuchElementException e){
            System.err.println("No after call screen shown " + e);
            BaseSetup.logger.log(LogStatus.FAIL, "No after call screen shown");
        }
    }
}
