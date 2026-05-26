package revision;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * @author Ace
 * @created 20-05-2026
 */
public class ActionsDemo1 {
    public static void main(String[] args) throws AWTException, InterruptedException {
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--guest");
        options.addArguments("--disable-features=AutofillPasswordLeakDetection");

        options.addArguments("--disable-extensions");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-features=VizDisplayCompositor");
//        options.addArguments("--headless");
        WebDriver driver =new ChromeDriver(options);
//        Robot robot = new Robot();
        driver.get("https://demo.cubecart.com/admin_5xArPd.php");
        driver.manage().window().maximize();
        WebElement userNameField = driver.findElement(By.id("username"));
        WebElement passeField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login"));
        Actions actions = new Actions(driver);
//        actions.moveToElement(userNameField).click().sendKeys("cubecart").build().perform();
//        actions.moveToElement(passeField).click().sendKeys("cubecart").build().perform();
//        actions.moveToElement(loginButton).click().build().perform();
//        actions.moveToElement(userNameField).click().sendKeys("cubecart").
//                moveToElement(passeField).click().sendKeys("cubecart").
//                moveToElement(loginButton).click().build().perform();
//        actions.sendKeys(userNameField,"cubecart").sendKeys(passeField,"cubecart").click(loginButton).build().perform();

        options.addArguments("--disable-popup-blocking");
        actions.keyDown(Keys.SHIFT).sendKeys(userNameField, "cube").keyUp(Keys.SHIFT).sendKeys("cart").sendKeys(passeField, "cubecart").click(loginButton).build().perform();
        Thread.sleep(2000);
        new Actions(driver).sendKeys(Keys.ENTER).build().perform();
        new Actions(driver).doubleClick().keyDown(Keys.ENTER).build().perform();
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        Thread.sleep(3000);
        driver.quit();



    }
}
