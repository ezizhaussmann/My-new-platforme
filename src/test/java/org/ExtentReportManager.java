package org;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.joda.time.DateTime;

public class ExtentReportManager {
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public static void initReports() {
        if (extent == null) {
            String date = new DateTime().toString("dd-MM-yy_HH-mm");
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter("test-output/ExtentReport_" + date + ".html");
            sparkReporter.config().setDocumentTitle("Test Report");
            sparkReporter.config().setReportName("Automation Test Results");
            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        }
    }

    public static void initReports(String reportName) {
        if (extent == null) {
            String date = new DateTime().toString("dd-MM-yy_HH-mm");
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter("test-output/" + reportName + "_" + date + ".html");
            sparkReporter.config().setDocumentTitle(reportName);
            sparkReporter.config().setReportName(reportName);
            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        }
    }

    public static void setSystemInfo(String key, String value) {
        if (extent != null) extent.setSystemInfo(key, value);
    }

    public static void logInfo(String message) {
        if (getTest() != null) getTest().info(message);
    }

    public static void logPass(String message) {
        if (getTest() != null) getTest().pass(message);
    }

    public static void logFail(String message) {
        if (getTest() != null) getTest().fail(message);
    }

    public static void logFail(Throwable t) {
        if (getTest() != null) getTest().fail(t);
    }

    public static void createTest(String testName) {
        if (extent == null) initReports();
        test.set(extent.createTest(testName));
    }

    public static ExtentTest getTest() {
        return test.get();
    }

    public static void flushReports() {
        if (extent != null) extent.flush();
    }
}