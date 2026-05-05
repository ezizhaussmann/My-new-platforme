package tutoring.assertions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * @author Ace
 * @created 27-04-2026
 */
public class AssertDemo2 {
    WebDriver driver;
    @BeforeClass
    public void setUp(){
        driver=new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
        driver.manage().window().maximize();
    }
    @Test
    public void LoginTest(){
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        WebElement swag=driver.findElement(By.xpath("//div[@class='app_logo' and text()='Swag Labs']"));
        try {
            Robot robot = new Robot();
            Thread.sleep(2000);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            System.out.println("✅ Alerte Chrome fermée avec ENTER.");
        } catch (Exception e) {
            System.out.println("ℹ️ Pas d'alerte détectée : " + e.getMessage());
        }
        String s=swag.getText();
//        driver.findElement(By.id("login-button")).click();
//        Assert.assertTrue(s.contains("Swag Labs"),"Swag Labs logo is not displayed");
        Assert.assertTrue(s.contains("Swag Labs"),"Title does not contain Products");
    }
 @AfterClass
    public void tearDown(){
        driver.quit();
    }
}
