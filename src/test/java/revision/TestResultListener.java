package revision;

import advanceaction.ScreenUtility;
import org.ScreenShotUtility;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * @created : 25/04/2026,19:13,sam.
 **/
public class TestResultListener implements ITestListener {
    ScreenShotUtility screenUtility;
    public void onTestStart(ITestResult result) {

    }
    public void onTestSuccess(ITestResult result) {

    }
    public void onTestFailure(ITestResult result)
    {
        screenUtility.captureScreen(result.getMethod().getMethodName().trim()+"passed",(ChromeDriver)result.getTestContext().getAttribute("driver"));

    }
    public void onTestSkipped(ITestResult result) {
        screenUtility.captureScreen(result.getMethod().getMethodName().trim()+
                "skipped",(ChromeDriver)result.getTestContext().getAttribute("driver"));

    }
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }
    public void onTestFailedWithTimeout(ITestResult result) {
        onTestFailure(result);
    }
    public void onStart(ITestContext context) {}
    public void onFinish(ITestContext context) {}
}