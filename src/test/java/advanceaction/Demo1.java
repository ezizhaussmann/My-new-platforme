package advanceaction;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
//import org.ExtentReportManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pagedesign.FunctionLibrary;

import java.io.IOException;
import java.time.Duration;


public class Demo1 {
    ChromeDriver driver;
    WebDriverWait wait;
    FunctionLibrary functionLibrary;
    ExtentReports extent;
    ScreenUtility screenUtility;
    ExtentTest test;
    @BeforeSuite
    public void setExtent() {
        extent=ExtentReport.setup("Test Actions - Drag and Drop - Menu");
        screenUtility = new ScreenUtility();
    }


    @BeforeClass
    public void setup() {
        if (extent == null) {
            extent = ExtentReport.setup("Test Actions - Drag and Drop - Menu");
        }
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        functionLibrary = new FunctionLibrary(driver);
        screenUtility = new ScreenUtility();
        driver.manage().window().maximize();
        test=extent.createTest("Initialisation du navigateur Chrome");


    }
    @Test()
    public void test() {
        test = extent.createTest("Test jQuery UI - Navigation Contribution");
        test.info("Navigation vers https://jqueryui.com/");

        driver.get("https://jqueryui.com/");
        WebElement conttributelink = driver.findElement(By.xpath("//section[@id=\"global-nav\"]//div//ul[@class=\"links\"]//*[contains(text(),\"Contribute\")]"));
        Actions actions = new Actions(driver);
        actions.moveToElement(conttributelink).build().perform();
        test.info("Survol du lien 'Contribute' effectué");

        functionLibrary.sleep(2000);
        WebElement cla = driver.findElement(By.xpath("//a[contains(text(),\"CLA\")]"));
        functionLibrary.waitForElementPresent(cla);
        actions.click(cla).build().perform();
        test.info("Clic sur le lien 'CLA' effectué");

        WebElement become = driver.findElement(By.linkText("Become a member"));
        functionLibrary.waitForElementPresent(become);
        boolean isbecome = become.isDisplayed();
        Assert.assertTrue(isbecome, "Le lien 'Become a member' n'est pas visible.");

        if (isbecome) {
            test.pass("Le lien 'Become a member' est bien visible");
        } else {
            test.fail("Le lien 'Become a member' n'est pas visible");
        }

        try {
            String screenshot = ScreenUtility.getScreenshot(driver, "jQuery_UI_Contribution");
            test.pass("Capture d'écran du test réussie",
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
        } catch (IOException e) {
            test.fail("Échec de la capture d'écran: " + e.getMessage());

            System.err.println("Screenshot capture failed: " + e.getMessage());
        }
    }
    @Test
    public void dragAndDrop() {
        test = extent.createTest("Test Drag and Drop");
        test.info("Navigation vers https://jqueryui.com/droppable/");

        driver.get("https://jqueryui.com/droppable/");
        WebElement iframe = driver.findElement(By.tagName("iframe"));
        driver.switchTo().frame(iframe);

        WebElement drag = driver.findElement(By.id("draggable"));
        WebElement drop = driver.findElement(By.id("droppable"));
        test.info("Éléments drag et drop localisés");

        Actions actions = new Actions(driver);
        actions.dragAndDrop(drag, drop).build().perform();
        test.info("Opération drag and drop effectuée");

        functionLibrary.sleep(2000);
        Assert.assertEquals(drop.getText(), "Dropped!");
        test.pass("Test réussi: L'élément a été déposé avec succès. Texte obtenu: " + drop.getText());

        try {
            String screenshotPath = ScreenUtility.getScreenshot(driver, "DragAndDrop_Success");
            test.pass("Capture d'écran du test réussi",
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        } catch (IOException e) {
            test.fail("Échec de la capture d'écran: " + e.getMessage());
            System.err.println("Screenshot capture failed: " + e.getMessage());
        }

        driver.switchTo().defaultContent();
        driver.findElement(By.linkText("Selectable")).click();
        String sc= null;
        try {
            sc = ScreenUtility.getScreenshot(driver, "Selectable");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        test.pass("Capture d'écran du test réussi",
                MediaEntityBuilder.createScreenCaptureFromPath(sc).build());

    }
    @Test
    public void menuTest() {
        driver.get("https://jqueryui.com/menu/");
        WebElement iframe = driver.findElement(By.tagName("iframe"));
        driver.switchTo().frame(iframe);
        WebElement musicElement = driver.findElement(By.id("ui-id-9"));
        Actions actions = new Actions(driver);
        actions.moveToElement(musicElement).pause(Duration.ofMillis(500)).perform();
        WebElement jazzElement = driver.findElement(By.id("ui-id-10"));
        functionLibrary.waitForElementPresent(jazzElement);
        actions.moveToElement(jazzElement).pause(Duration.ofMillis(500)).perform();
        WebElement bigBandElement = driver.findElement(By.id("ui-id-11"));
        functionLibrary.waitForElementPresent(bigBandElement);
        actions.moveToElement(bigBandElement).pause(Duration.ofMillis(500)).perform();
        Assert.assertTrue(bigBandElement.isDisplayed());
        test.pass("Test réussi: L'élément 'Big Band' est bien affiché");
        try {
            MediaEntityBuilder.createScreenCaptureFromPath(ScreenUtility.getScreenshot(driver, "Menu_Test")).build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


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
    public void tearDown() {
        driver.quit();
        extent.flush();
    }
}
