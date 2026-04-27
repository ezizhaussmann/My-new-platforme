package advanceaction;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReport {
    public static ExtentReports extent;
    public static ExtentSparkReporter spark;

    public static ExtentReports setup() {
        if (extent == null) {

            String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
            String reportName = "ExtentReport_" + timeStamp + ".html";
            String path = System.getProperty("user.dir") + "/test-output/" + reportName;
            spark = new ExtentSparkReporter(path);
            spark.config().setDocumentTitle("Rapport de Test Automatisé");
            spark.config().setReportName("Test de Drag and Drop");
            spark.config().setTheme(Theme.STANDARD);

            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("Environnement", "QA");
            extent.setSystemInfo("Testeur", "Ace");
        }
        return extent;
    }
}