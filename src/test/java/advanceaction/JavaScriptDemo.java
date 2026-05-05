package advanceaction;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pagedesign.FunctionLibrary;

import java.io.IOException;

/**
 * @author Ace
 * @created 05-05-2026
 */
public class JavaScriptDemo {
    ChromeDriver driver;
    FunctionLibrary functionLibrary;
    ScreenUtility screenUtility;
    ExtentReports extent;
    ExtentTest test;
    @BeforeSuite
    public void beforeSuite(){
        extent= ExtentReport.setup("Test JavaScript Executor");
        screenUtility=new ScreenUtility();
    }

    @BeforeClass
    public void beforeClass(){
        if (extent == null) {
            extent = ExtentReport.setup("Test JavaScript Executor");
        }
        driver=new ChromeDriver();
        functionLibrary=new FunctionLibrary(driver);
        driver.manage().window().maximize();
        test=extent.createTest("scrollToElement");
    }

    @Test
    public void scrollToElement(){
        test=extent.createTest("scrollToElement");
        test.info("Navigation vers https://jqueryui.com/");
        driver.get("https://jqueryui.com/");
        test.info("Survol du lien 'Add Class' effectué");
        test.info("lier le driver avec JavaScript");
        JavascriptExecutor js= (JavascriptExecutor) driver;
        WebElement addClassButton = driver.findElement(By.linkText("Add Class"));
        functionLibrary.waitForElementPresent(addClassButton);
        functionLibrary.sleep(1000);
//        js.executeScript("arguments[0].scrollIntoView(true);", addClassButton);
        js.executeScript("scrollBy(0,200)");
//        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", addClassButton);
        functionLibrary.sleep(1000);
        js.executeScript("scrollBy(0,50)");
        functionLibrary.sleep(1000);
        js.executeScript("scrollBy(0,-100)");
        functionLibrary.sleep(1000);
        Assert.assertTrue(addClassButton.isDisplayed(), "Add Class button should be visible after scrolling");

        test.info("Test scroll to element terminé avec succès");
        try {
            test.pass("Test scroll to element terminé avec succès", MediaEntityBuilder.createScreenCaptureFromPath(screenUtility.getScreenshot(driver,"scrollToElement")).build());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void jsClickTest(){
        driver.get("https://jqueryui.com/dialog/#animated");
        driver.switchTo().frame(0);
        WebElement openDialog = driver.findElement(By.id("opener"));
        functionLibrary.waitForElementPresent(openDialog);
        JavascriptExecutor js= (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", openDialog);
        WebElement basicDialog = driver.findElement(By.xpath("//span[text()=\"Basic dialog\"]"));
        functionLibrary.waitForElementPresent(basicDialog);
        Assert.assertTrue(basicDialog.isDisplayed(), "Basic dialog should be visible after clicking");


    }
    @Test
    public void jsSendKeysTest(){
        driver.get("https://demo.cubecart.com/admin_5xArPd.php");
        JavascriptExecutor js= (JavascriptExecutor) driver;
     js.executeScript("document.getElementById('username').value='cubecart'");
     WebElement userNameField=driver.findElement(By.id("username"));
//     String valueOfUserNameField = js.executeScript("return arguments[0].value;", userNameField).toString();
        String fieldValue = userNameField.getAttribute("value");
        System.out.println("Field value is :" + fieldValue);
        Assert.assertEquals(fieldValue, "cubecart");
    }

//    @Test
//    public void handleDynamicWebTable(){
//        test=extent.createTest("handleDynamicWebTable");
//        test.info("Navigation vers https://www.toolsqa.com/");
//        driver.get("https://www.toolsqa.com/");
//        JavascriptExecutor js= (JavascriptExecutor) driver;
//        js.executeScript("scrollBy(0, 300)");
//        functionLibrary.sleep(1000);
//        driver.findElement(By.xpath("//a[text()='Practice']")).click();
//        functionLibrary.sleep(1000);
//        driver.findElement(By.xpath("//a[text()='Web Tables']")).click();
//        functionLibrary.sleep(1000);
//        js.executeScript("scrollBy(0, 300)");
//        functionLibrary.sleep(1000);
//        int rows = driver.findElements(By.xpath("//div[@class='rt-tbody']//div[@class='rt-tr-group']")).size();
//        System.out.println("Nombre des lignes: " + rows);
//        int cols = driver.findElements(By.xpath("//div[@class='rt-tbody']//div[@class='rt-tr-group'][1]//div[@class='rt-td']")).size();
//        System.out.println("Nombre des colonnes: " + cols);
//
//        for (int i = 1; i <= rows; i++) {
//            for (int j = 1; j <= cols; j++) {
//                String data = driver.findElement(By.xpath("//div[@class='rt-tbody']//div[@class='rt-tr-group'][" + i + "]//div[@class='rt-td'][" + j + "]")).getText();
//                System.out.print(data + " ");
//            }
//            System.out.println();
//        }
//        test.info("Test handle dynamic web table terminé avec succès");
//    }
    @AfterMethod
    public void afterMethod(ITestResult result){
        try {
            String screenshotPath = screenUtility.getScreenshot(driver, result.getName());
            if (result.getStatus() == ITestResult.SUCCESS) {
                test.pass(result.getName(), MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            } else if (result.getStatus() == ITestResult.FAILURE) {
                test.fail(result.getName(), MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                test.fail(result.getThrowable());
            }
        } catch (IOException e) {
            test.fail("Échec de la capture d'écran: " + e.getMessage());
        }
    }
    @AfterSuite
    public void afterSuite(){
        extent.flush();
    }

    @AfterClass
    public void afterClass(){
        driver.quit();
    }

}
