package advanceaction;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pagedesign.FunctionLibrary;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Ace
 * @created 04-05-2026
 */
public class NewDemo {
    ChromeDriver driver;
    WebDriverWait wait;
    FunctionLibrary functionLibrary;
    ExtentReports extent;
    ScreenUtility screenUtility;
    ExtentTest test;
    @BeforeSuite
    public void setExtent() {
        extent=ExtentReport.setup("Test Windows - Hyperlinks");
        screenUtility = new ScreenUtility();
    }
    @BeforeClass
    public void setUp(){
        driver=new ChromeDriver();
        wait=new WebDriverWait(driver, Duration.ofSeconds(10));
        functionLibrary=new FunctionLibrary(driver);
        screenUtility=new ScreenUtility();
        driver.manage().window().maximize();
        test=extent.createTest("Initialisation du navigateur Chrome");
    }
    @Test
    public void test() {
        test.info("Navigation vers teh_internet herokuapp");
        driver.get("https://the-internet.herokuapp.com/");
        WebElement multiWindow=driver.findElement(By.linkText("Multiple Windows"));
        functionLibrary.waitForElementPresent(multiWindow);
        String mainWindowHandle=driver.getWindowHandle();
        System.out.println("Main window handle: " + mainWindowHandle);
        test.info("Clic sur le lien Multiple Windows");
        multiWindow.click();
        Set<String> allWindowHandles = driver.getWindowHandles();
        for (String eachWindow : allWindowHandles) {
            System.out.println("Window handle: " + eachWindow);
            if (!eachWindow.equals(mainWindowHandle)) {
                driver.switchTo().window(eachWindow);
                WebElement clickButton = driver.findElement(By.linkText("Click Here"));
                functionLibrary.waitForElementPresent(clickButton);
                test.info("Clic sur le lien 'Click Here'");
                Assert.assertTrue(clickButton.isDisplayed());
                clickButton.click();
                break;
            }
        }


    }
    @Test
    public void testHyperlink() {
        driver.get("https://jqueryui.com/");
        test.info("Navigation vers jqueryui.com");
        test.info("Recherche des liens");
      List<WebElement> links = driver.findElements(By.xpath("//*[@id=\"sidebar\"]//a"));
      test.info("Liste des liens trouvés: " + links.size());
        System.out.println(links.size());
        test.info("Recherche des liens valides");
        List<String> urls=new ArrayList<>();
        for(WebElement link:links) {
            urls.add(link.getAttribute("href"));

//
        }
        int count=0;
        for (String url : urls) {
            driver.navigate().to(url);
            count++;
        }
        test.info("Nombre de liens valides: " + count);
        Assert.assertTrue(count == links.size());
    }
    @AfterMethod
    public void testResult(ITestResult result ) {
        if (result.getStatus() == ITestResult.SUCCESS) {
            try {
                String screenshotPath = ScreenUtility.getScreenshot(driver, result.getName());
                test.pass("Test passé avec succès",
                        MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            } catch (IOException e) {
                test.fail("Failed to capture screenshot: " + e.getMessage());
                System.err.println("Screenshot capture failed: " + e.getMessage());
            }
        }

    }

    @AfterSuite
    public void flushExtent() {
        driver.quit();
        extent.flush();
    }
}
