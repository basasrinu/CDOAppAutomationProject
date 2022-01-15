import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

import java.io.File;

public class AppiumServer {

    static void Start(){
        getInstance().start();
    }

    static AppiumDriverLocalService getInstance(){
        AppiumServiceBuilder builder = new AppiumServiceBuilder();
        builder
                .withAppiumJS(new File("C:\\Program Files\\Appium\\resources\\app\\node_modules\\appium\\lib\\main.js"))
                .usingDriverExecutable(new File("C:\\Program Files\\nodejs\\node.exe"))
                .usingPort(4723)
                .withLogFile(new File("C:\\Users\\calldorado\\Desktop\\Screenshot\\AppiumLog.txt"));

        return AppiumDriverLocalService.buildService(builder);
    }

    public static void main(String[] args) throws InterruptedException{
        AppiumServer.Start();
    }

}
