package org;


import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ScreenShotListener implements ITestListener {

    private void captureWithDriver(String fileName, WebDriver driver) {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destFile = buildDestFile(fileName);
        try {
            FileUtils.copyFile(srcFile, destFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void captureWithRobot(String fileName) {
        try {
            Robot robot = new Robot();
            Rectangle screen = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage image = robot.createScreenCapture(screen);
            ImageIO.write(image, "png", buildDestFile(fileName));
        } catch (AWTException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private File buildDestFile(String fileName) {
        String timestamp = new DateTime().toString(DateTimeFormat.forPattern("dd-MM-yy_hh-mm"));
        File dir = new File("screenshots");
        if (!dir.exists()) dir.mkdirs();
        return new File(dir, timestamp + "_" + fileName + ".png");
    }

    private void takeScreenshot(ITestResult result, String suffix) {
        String name = result.getMethod().getMethodName().trim() + suffix;
        WebDriver driver = (WebDriver) result.getTestContext().getAttribute("driver");
        if (driver != null)
            captureWithDriver(name, driver);
        else
            captureWithRobot(name);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        takeScreenshot(result, "_Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        takeScreenshot(result, "_Failed");
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        onTestFailure(result);
    }
}