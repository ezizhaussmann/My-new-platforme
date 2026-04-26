package org;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class BaseClass {

    private static final Logger log = LogManager.getLogger(BaseClass.class);

    public ChromeDriver driver;
    WebDriverWait wait;
    FunctionLibrary functionLibrary;

    public void initBrowser() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.get("https://demo.cubecart.com/admin_5xArPd.php");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        functionLibrary = new FunctionLibrary(driver);
        log.info("Browser initialisé et navigation vers CubeCart.");
    }

    public void quitBrowser() {
        driver.close();
        driver.quit();
        log.info("Browser fermé.");
    }
}
