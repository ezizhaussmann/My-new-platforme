package advanceaction;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenUtility {

    public static String getScreenshot(WebDriver driver, String screenshotName) throws IOException {
        try {
            String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);

            String userDir = System.getProperty("user.dir");
            String destination = userDir + File.separator + "screenshots" + File.separator + screenshotName + dateName + ".png";
            File finalDestination = new File(destination);
            

            File screenshotsDir = finalDestination.getParentFile();
            if (!screenshotsDir.exists()) {
                boolean created = screenshotsDir.mkdirs();
                if (!created) {
                    String tempPath = System.getProperty("java.io.tmpdir") + File.separator + "screenshots";
                    File tempDir = new File(tempPath);
                    if (!tempDir.exists() && !tempDir.mkdirs()) {
                        throw new IOException("Failed to create screenshots directory in both project and temp folders");
                    }
                    destination = tempPath + File.separator + screenshotName + dateName + ".png";
                    finalDestination = new File(destination);
                    System.out.println("Using temp directory for screenshots: " + tempPath);
                }
            }

            if (finalDestination.exists() && !finalDestination.canWrite()) {
                if (!finalDestination.delete()) {
                    throw new IOException("Cannot overwrite existing screenshot file: " + destination);
                }
            }

            int retryCount = 0;
            while (retryCount < 3) {
                try {
                    FileUtils.copyFile(source, finalDestination);
                    break;
                } catch (Exception e) {
                    retryCount++;
                    if (retryCount >= 3) {
                        throw e;
                    }
                    Thread.sleep(100); // Attendre 100ms avant de réessayer
                }
            }

            if (!finalDestination.exists()) {
                throw new IOException("Screenshot file was not created: " + destination);
            }
            
            return destination;
            
        } catch (Exception e) {
            throw new IOException("Failed to capture screenshot: " + e.getMessage(), e);
        }
    }
}
