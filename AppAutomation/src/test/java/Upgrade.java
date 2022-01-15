import CDO.BaseSetup;
import CDO.ListenerTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;

/*
 * Srinivas created this class on the 21/04/2021
 */
@Listeners(ListenerTest.class)
public class Upgrade extends BaseSetup {

    @Test(priority = 5,enabled = false)
    public void denyPermissionUpgrade() throws InterruptedException, IOException {
        System.err.println("Test case 5  : Upgrade test cases -  Upgrade the app when all permissions are denied");
        logger = extent.startTest("Deny all permissions & upgrade", "Upgrade the app when all permissions are denied");
        Thread.sleep(1000);
        calldoradoSDKTest.denyAllPermissionsAndUpgrade();
    }

    @Test(priority = 6)
    public void acceptAllPermissionUpgrade() throws InterruptedException, IOException {
        Thread.sleep(1000);
        System.err.println("Test case 6  : Upgrade test cases -  Upgrade the app after accepting all permissions");
        logger = extent.startTest("Accept all permissions & upgrade", "Upgrade the app after accepting all permissions");
        calldoradoSDKTest.acceptAllPermissionsAndUpgrade();
    }

    @Test(priority = 7)
    public void thirdPartyUpgrade() throws InterruptedException, IOException {
        Thread.sleep(1000);
        System.err.println("Test case 7  : Upgrade test cases -  Validate third-party data SDK P3, Tutela, Open signal and Cuebiq initialization");
        logger = extent.startTest("Third party sdk logs after upgrade", "Validate third-party data SDK P3, Tutela, Open signal and Cuebiq initialization");
        calldoradoSDKTest.thirdPartyAfterUpgrade();
    }

    @Test(priority = 8, enabled = false)
    public void denyOverlayUpgrade() throws InterruptedException, IOException {
        Thread.sleep(1000);
        System.err.println("Test case 8  : Deny overlay & upgrade , Validate tapping the device back button on the Overlay screen with Got it button screen and verify CDO is working on Android 9 & below devices and make sure missing overlay permission notification on Android 10 and above");
        logger = extent.startTest("Deny overlay & upgrade", "Validate tapping the device back button on the Overlay screen with  Got it button screen and verify CDO is working on Android 9 & below devices and make sure missing overlay permission notification on Android 10 and above");
        calldoradoSDKTest.denyOverlayAndUpgrade();
    }
}
