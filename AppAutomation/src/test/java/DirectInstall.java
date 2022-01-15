import CDO.BaseSetup;
import CDO.ListenerTest;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;

/*
 * Srinivas created this class on the 21/04/2021
 */
@Listeners(ListenerTest.class)
public class DirectInstall extends BaseSetup {

    @Test(priority = 0)
    public void backButtonAndHomeButton() throws InterruptedException, IOException {
        Thread.sleep(2000);
        logger = extent.startTest("EULA & PP screen", "Device BACK button dialog, Close & Reopen the app on PP & EULA screen,Debug screen to get version numbers ");
        logger.log(LogStatus.INFO, "Validate the PP & EULA screen by tapping the device BACK button");
        System.err.println("Test case  - Validate the PP & EULA screen by tapping the device BACK button ");
        calldoradoSDKTest.deviceBACKButton();
        Thread.sleep(1000);
        logger.log(LogStatus.INFO, "Validate the PP & EULA screen by tapping the device HOME button & launch the app using the app icon");
        System.err.println("Test case  - Validate the PP & EULA screen by tapping the device HOME button & launch the app using the app icon");
        calldoradoSDKTest.deviceHOMEButton();
        Thread.sleep(1000);
    }

    @Test(priority = 1)
    public void acceptAllOptInPermissions() throws InterruptedException, IOException {
        Thread.sleep(1000);
        System.err.println("Test case  - Accept all CDO SDK permissions and verify WIC & AC is working ");
        logger = extent.startTest("Accept all optIn permissions", "Accept all CDO SDK permissions and verify WIC & AC is working ");
        calldoradoSDKTest.acceptAllPermissions();
    }
    @Test(priority = 2)
    public void debugScreenshot() throws IOException, InterruptedException {
        Thread.sleep(1000);
        System.err.println("Debug - CDO version number and opt-in SDK version number ,FAN SDK version , Google AD SDK screenshot saved" );
        logger.log(LogStatus.INFO, "Debug - CDO version number and opt-in SDK version number ,FAN SDK version , Google AD SDK screenshot saved");
        calldoradoSDKTest.debugScreenshot();
    }

    @Test(priority = 3)
    public void validateThirdPartySDKLogs() throws InterruptedException, IOException {
        Thread.sleep(1000);
        System.err.println("Test case  - Validate third-party data SDK P3, Tutela, Open signal, and Cuebiq initialization ");
        logger = extent.startTest("Third parties SDK", "Validate third-party data SDK P3, Tutela, Open signal, and Cuebiq initialization ");
        calldoradoSDKTest.directInstallThirdParty();
    }

    @Test(priority = 4, enabled = false)
    public void denyOverlay() throws InterruptedException, IOException {
        Thread.sleep(1000);
        System.err.println("Test case :  Validate tapping the device back button on the Overlay screen with Got it button screen and verify CDO is working on Android 9 & below devices and make sure missing overlay permission notification on Android 10 and above");
        logger = extent.startTest("Deny overlay", "Validate tapping the device back button on the Overlay screen with Got it button screen and verify CDO is working on Android 9 & below devices and make sure missing overlay permission notification on Android 10 and above");
        calldoradoSDKTest.denyOverLay();
    }
}
