package revision;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.model.Media;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class ExtentReportListener implements ITestListener {

    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onStart(ITestContext context) {
        String timestamp = new DateTime().toString(DateTimeFormat.forPattern("dd-MM-yy_HH-mm"));
        ExtentSparkReporter spark = new ExtentSparkReporter("test-output/ExtentReport_" + timestamp + ".html");
        spark.config().setDocumentTitle("Test Report");
        spark.config().setReportName(context.getSuite().getName());
        extent = new ExtentReports();
        extent.attachReporter(spark);
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java", System.getProperty("java.version"));
    }

    @Override
    public void onTestStart(ITestResult result) {
        test.set(extent.createTest(result.getMethod().getMethodName()));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().log(Status.PASS, "Test Passed");
        attachScreenshot(result, Status.PASS);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.get().log(Status.FAIL, "Test Failed: " + result.getThrowable());
        attachScreenshot(result, Status.FAIL);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().log(Status.SKIP, "Test Skipped");
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        onTestFailure(result);
    }

    @Override
    public void onFinish(ITestContext context) {
        if (extent != null) extent.flush();
    }

    private void attachScreenshot(ITestResult result, Status status) {
        String label = status == Status.PASS ? "Screenshot - Passed" : "Screenshot - Failed";
        try {
            String base64;
            WebDriver driver = (WebDriver) result.getTestContext().getAttribute("driver");
            if (driver != null) {
                base64 = Base64.getEncoder().encodeToString(
                        ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
            } else {
                Robot robot = new Robot();
                Rectangle screen = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
                BufferedImage image = robot.createScreenCapture(screen);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(image, "png", baos);
                base64 = Base64.getEncoder().encodeToString(baos.toByteArray());
            }
            Media media = com.aventstack.extentreports.MediaEntityBuilder
                    .createScreenCaptureFromBase64String(base64, label)
                    .build();
            test.get().log(status, label, media);
        } catch (AWTException | IOException e) {
            test.get().log(Status.WARNING, "Screenshot failed: " + e.getMessage());
        }
    }
}
