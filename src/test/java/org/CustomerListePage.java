package org;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerListePage {

    private static final Logger log = LogManager.getLogger(CustomerListePage.class);

    ChromeDriver driver;
    FunctionLibrary functionLibrary;
    WebDriverWait wait;

    @FindBy(name = "customer[first_name]")
    private WebElement firstNameField;

    @FindBy(name = "customer[last_name]")
    private WebElement lastNameField;

    @FindBy(name = "customer[email]")
    private WebElement emailField;

    @FindBy(name = "customer[phone]")
    private WebElement phoneField;

    @FindBy(name = "customer[mobile]")
    private WebElement mobileField;

    @FindBy(name = "customer[password]")
    private WebElement passwordField;

    @FindBy(css = "input[name='save']")
    private WebElement saveButton;

    @FindBy(css = "div.success")
    private WebElement successMessage;

    public CustomerListePage(ChromeDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        functionLibrary = new FunctionLibrary(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void addCustomer(String firstName, String lastName, String email) {
        log.info("Ajout du client : {} {} - {}", firstName, lastName, email);
        PageFactory.initElements(driver, this);
        functionLibrary.waitForElementPresent(firstNameField);
        firstNameField.sendKeys(firstName);
        functionLibrary.waitForElementPresent(lastNameField);
        lastNameField.sendKeys(lastName);
        functionLibrary.waitForElementPresent(emailField);
        emailField.sendKeys(email);
        functionLibrary.waitForElementPresent(saveButton);
        saveButton.click();
        log.info("Formulaire soumis, en attente de confirmation...");
        printCustomerInfo(firstName, lastName, email);
    }

    public void printCustomerInfo(String firstName, String lastName, String email) {
        log.info("========================================");
        log.info("Informations du client ajouté :");
        log.info("   Prénom  : {}", firstName);
        log.info("   Nom     : {}", lastName);
        log.info("   Email   : {}", email);
        log.info("========================================");
    }

    public void verifyCustomerAdded() {
        wait.until(ExpectedConditions.visibilityOf(successMessage));
        String actualMessage = successMessage.getText();
        assertTrue(actualMessage.contains("Customer successfully added."),
                "❌ Message attendu 'Customer successfully added.' mais reçu : " + actualMessage);
        log.info("Client ajouté avec succès : {}", actualMessage);
    }

    public void verifyCustomerAdded(String expectedMessage) {
        wait.until(ExpectedConditions.visibilityOf(successMessage));
        String actualMessage = successMessage.getText();
        assertTrue(actualMessage.contains(expectedMessage),
                "❌ Message attendu : '" + expectedMessage + "' mais reçu : '" + actualMessage + "'");
        log.info("Vérification réussie : {}", actualMessage);
    }

    public boolean isCustomerAdded() {
        boolean displayed = wait.until(ExpectedConditions.visibilityOf(successMessage)).isDisplayed();
        assertTrue(displayed, "❌ Le message de succès n'est pas visible.");
        log.info("Message de succès visible : {}", displayed);
        return displayed;
    }
}
