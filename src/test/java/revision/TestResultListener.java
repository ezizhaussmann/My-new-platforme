package revision;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * @created : 25/04/2026,19:13,sam.
 **/
public class TestResultListener implements ITestListener {
    public void onTestStart(ITestResult result) {}
    public void onTestSuccess(ITestResult result) {}
    public void onTestFailure(ITestResult result) {}
    public void onTestSkipped(ITestResult result) {}
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}
    public void onTestFailedWithTimeout(ITestResult result) { onTestFailure(result); }
    public void onStart(ITestContext context) {}
    public void onFinish(ITestContext context) {}
}