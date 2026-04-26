package advanceaction;

import org.ExtentReportManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import pagedesign.FunctionLibrary;

import java.time.Duration;


public class Demo1 {
    ChromeDriver driver;
    WebDriverWait wait;
    FunctionLibrary functionLibrary;
    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        functionLibrary = new FunctionLibrary(driver);
        driver.manage().window().maximize();

    }
    @Test
    public void test() {
        driver.get("https://jqueryui.com/");
        ExtentReportManager.createTest("Test jQuery UI - Devenir membre");
        ExtentReportManager.getTest().info("Navigation vers https://jqueryui.com/");
        WebElement conttributelink = driver.findElement(By.xpath("//section[@id=\"global-nav\"]//div//ul[@class=\"links\"]//*[contains(text(),\"Contribute\")]"));
        Actions actions = new Actions(driver);
        actions.moveToElement(conttributelink).build().perform();
        ExtentReportManager.getTest().info("Survol du menu 'Contribute' effectué.");
        functionLibrary.sleep(2000);
        WebElement cla=driver.findElement(By.xpath("//a[contains(text(),\"CLA\")]"));
        functionLibrary.waitForElementPresent(cla);
        actions.click(cla).build().perform();
        WebElement become=driver.findElement(By.linkText("Become a member"));
        functionLibrary.waitForElementPresent(become);
        boolean isbecome = become.isDisplayed();
        Assert.assertTrue(isbecome, "Le lien 'Become a member' n'est pas visible.");
        ExtentReportManager.createTest("Test jQuery UI Contribution");


    }
    @AfterSuite
    public void tearDown() {
        driver.quit();
        ExtentReportManager.flushReports();
    }
}
