package org;


import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;

public class ScreenShotUtility {
    public File captureScreen(String fileName, ChromeDriver driver) {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String timestamp = new DateTime().toString(DateTimeFormat.forPattern("dd-MM-yy_hh-mm"));
        File dir = new File("screenshots");
        if (!dir.exists()) dir.mkdirs();
        File destFile = new File(dir, timestamp + "_" + fileName + ".png");
        try {
            FileUtils.copyFile(srcFile, destFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return destFile;
    }
}
