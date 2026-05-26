package revision;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

/**
 * @author Ace
 * @created 20-05-2026
 */
public class Acrions_copyPaste {
    ChromeOptions options = new ChromeOptions();

    WebDriver driver;
    @Test
    public void copyPaste(){

        options.addArguments("--disable-extensions");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-features=VizDisplayCompositor");
//        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("https://www.selenium.dev/selenium/web/single_text_input.html");
        driver.manage().window().maximize();
        WebElement element=driver.findElement(By.id("textInput"));

//        Keys cmdCtrl;
//        if(Platform.getCurrent().is(Platform.MAC))
//            cmdCtrl = Keys.COMMAND;
//        else
//            cmdCtrl = Keys.CONTROL;
        Keys cmdCtrl = Platform.getCurrent().is(Platform.MAC) ? Keys.COMMAND : Keys.CONTROL;
        new Actions(driver).sendKeys(element,"Katachan").doubleClick().keyDown(cmdCtrl).sendKeys("cvvv").keyUp(cmdCtrl).build().perform();

        Assert.assertEquals(element.getAttribute("value"),"KatachanKatachanKatachan");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        driver.quit();
    }

}
