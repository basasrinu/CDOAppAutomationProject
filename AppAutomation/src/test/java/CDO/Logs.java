package CDO;

import com.relevantcodes.extentreports.LogStatus;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.logging.LogEntry;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/*
 * Srinivas created this class on the 03/05/2021
 */
class Logs {
    String green = "\u001B[32m",defaultColour = "\u001B[0m";
    public void logs(String fileName, String screenshotPath, String android_version, AndroidDriver<AndroidElement> androidDriver) throws IOException {

        File logFile = new File(screenshotPath + fileName +".txt");
        logFile.getParentFile().mkdirs();

        List<LogEntry> logs = androidDriver.manage().logs().get("logcat").getAll();
        System.out.println(fileName);

        PrintWriter log_file_writer = new PrintWriter(logFile);

        for (LogEntry logEntry : logs) {
            if (logEntry.getMessage().contains("initialiseSdk")) {
                System.out.println(green+logEntry.getMessage()+defaultColour);
                BaseSetup.logger.log(LogStatus.PASS,logEntry.getMessage());
                log_file_writer.println(logEntry);
            }
            if (logEntry.getMessage().contains("CuebiqSDK v6.2.1 initialized successfully!")) {
                System.out.println(green+logEntry.getMessage()+defaultColour);
                BaseSetup.logger.log(LogStatus.PASS,logEntry.getMessage());
                log_file_writer.println(logEntry);
            }
            if (logEntry.getMessage().contains("Umlaut is initialized!")) {
                System.out.println(green+logEntry.getMessage()+defaultColour);
                BaseSetup.logger.log(LogStatus.PASS,logEntry.getMessage());
                log_file_writer.println(logEntry);
            }
            if (logEntry.getMessage().contains("Tutela is initialized!")) {
                System.out.println(green+logEntry.getMessage()+defaultColour);
                BaseSetup.logger.log(LogStatus.PASS,logEntry.getMessage());
                log_file_writer.println(logEntry);
            }else if(logEntry.getMessage().contains("Tutela is running...")){
                System.out.println(green+logEntry.getMessage()+defaultColour);
                BaseSetup.logger.log(LogStatus.PASS,logEntry.getMessage());
                log_file_writer.println(logEntry);
            }
            if (logEntry.getMessage().contains("CellRebelSDK")) {
                System.out.println(green+logEntry.getMessage()+defaultColour);
                BaseSetup.logger.log(LogStatus.PASS,logEntry.getMessage());
                log_file_writer.println(logEntry);
            }
            if (logEntry.getMessage().contains("Umlaut")||logEntry.getMessage().contains("Cuebiq")||logEntry.getMessage().contains("Tutela")||logEntry.getMessage().contains("OpenSignal")||logEntry.getMessage().contains("CellRebel")) {
                System.out.println(logEntry.getMessage());
                BaseSetup.logger.log(LogStatus.INFO,logEntry.getMessage());
                log_file_writer.println(logEntry);
            }
        }
        log_file_writer.flush();
    }
}

