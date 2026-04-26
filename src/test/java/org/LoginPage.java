package org;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public class LoginPage {

    private static final Logger log = LogManager.getLogger(LoginPage.class);

   public ChromeDriver driver;
    FunctionLibrary functionLibrary;

    @FindBy(id = "username")
    WebElement usernameField;

    @FindBy(id = "password")
    WebElement passwordField;

    @FindBy(id = "login")
    WebElement loginButton;

    public LoginPage(ChromeDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        functionLibrary = new FunctionLibrary(driver);
    }

    public void login(String username, String password) {
        log.info("Tentative de connexion avec l'utilisateur : {}", username);
        functionLibrary.waitForElementPresent(usernameField);
        usernameField.sendKeys(username);
        functionLibrary.waitForElementPresent(passwordField);
        passwordField.sendKeys(password);
        functionLibrary.waitForElementPresent(loginButton);
        loginButton.click();

        try {
            Robot robot = new Robot();
            Thread.sleep(2000);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            log.info("Alerte Chrome fermée avec ENTER.");
        } catch (Exception e) {
            log.warn("Pas d'alerte détectée : {}", e.getMessage());
        }
        log.info("Connexion réussie.");
    }
}
