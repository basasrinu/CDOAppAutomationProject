package CDO;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;

/*
 * Srinivas created this class on the 03/05/2021
 */
class ScreenShot {
    AndroidDriver<AndroidElement> androidDriver = BaseSetup.androidDriver ;
    String appName = BaseSetup.appName;
    String screenShotPath = BaseSetup.screenShotPath;
    public void getScreenshot(String outputLocation ) throws IOException, InterruptedException {
        Thread.sleep(1500);
        System.out.println("Capturing the snapshot");
        File srcFiler=((TakesScreenshot)androidDriver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFiler, new File(screenShotPath+outputLocation));
    }
}
