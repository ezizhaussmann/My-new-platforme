package pageobjectdesignpattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pagedesign.FunctionLibrary;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @created : 21/04/2026,01:20,mar.
 * Page Object du formulaire Add Customer et de la liste des clients
 **/
public class CustomerListePage {

    // Driver Chrome partagé
    ChromeDriver driver;

    // Bibliothèque d'utilitaires (wait, sleep...)
    FunctionLibrary functionLibrary;

    // Attente explicite pour les vérifications
    WebDriverWait wait;

    // Champ "First Name" — localisé par name="customer[first_name]"
    @FindBy(css = "img.checkbox.cbs[rel='#customer_status']")
    private WebElement statusCheckbox;

    @FindBy(name = "customer[first_name]")
    private WebElement firstNameField;

    // Champ "Last Name" — localisé par name="customer[last_name]"
    @FindBy(name = "customer[last_name]")
    private WebElement lastNameField;

    // Champ "Email" — localisé par name="customer[email]"
    @FindBy(name = "customer[email]")
    private WebElement emailField;

    // Champ "Phone" — localisé par name="customer[phone]"
    @FindBy(name = "customer[phone]")
    private WebElement phoneField;

    // Champ "Mobile" — localisé par name="customer[mobile]"
    @FindBy(name = "customer[mobile]")
    private WebElement mobileField;

    // Champ "Password" — localisé par name="customer[password]"
    @FindBy(name = "customer[password]")
    private WebElement passwordField;

    // Bouton "Save" — localisé par CSS input[name='save']
    @FindBy(css = "input[name='save']")
    private WebElement saveButton;

    // Message de succès après ajout — localisé par CSS div.success
    @FindBy(css = "div.success")
    private WebElement successMessage;

    /**
     * Constructeur — initialise les locators et le wait
     * PageFactory.initElements lie les @FindBy aux WebElements
     */
    public CustomerListePage(ChromeDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        functionLibrary = new FunctionLibrary(driver);
        wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
    }

    /**
     * Remplit et soumet le formulaire d'ajout d'un client
     * Rafraîchit les locators via PageFactory après navigation
     */
    public void addCustomer(String firstName, String lastName, String email) {
        PageFactory.initElements(driver, this);
        functionLibrary.waitForElementPresent(firstNameField);
        firstNameField.sendKeys(firstName);
        functionLibrary.waitForElementPresent(lastNameField);
        lastNameField.sendKeys(lastName);
        functionLibrary.waitForElementPresent(emailField);
        emailField.sendKeys(email);
        functionLibrary.waitForElementPresent(saveButton);
        saveButton.click();
        System.out.println("✅ Formulaire soumis, en attente de confirmation...");
        printCustomerInfo(firstName, lastName, email);
    }


    /**
     * Affiche les informations du client ajouté dans la console
     */
    public void printCustomerInfo(String firstName, String lastName, String email) {
        System.out.println("\n========================================");
        System.out.println("📋 Informations du client ajouté :");
        System.out.println("   Prénom  : " + firstName);
        System.out.println("   Nom     : " + lastName);
        System.out.println("   Email   : " + email);
        System.out.println("========================================\n");
    }

    /**
     * Vérifie que le message "Customer successfully added." est visible
     * Utilise assertTrue — le test échoue si le message est absent
     */
    public void verifyCustomerAdded() {
        WebElement msg = wait.until(ExpectedConditions.visibilityOfElementLocated(
                org.openqa.selenium.By.cssSelector("div.success")));
        String actualMessage = msg.getText();
        assertTrue(actualMessage.contains("Customer successfully added."),
                "❌ Message attendu 'Customer successfully added.' mais reçu : " + actualMessage);
        System.out.println("✅ Client ajouté avec succès : " + actualMessage);
    }

    /**
     * Vérifie que le message de succès contient le texte attendu (version paramétrable)
     */
    public void verifyCustomerAdded(String expectedMessage) {
        WebElement msg = wait.until(ExpectedConditions.visibilityOfElementLocated(
                org.openqa.selenium.By.cssSelector("div.success")));
        String actualMessage = msg.getText();
        assertTrue(actualMessage.contains(expectedMessage),
                "❌ Message attendu : '" + expectedMessage + "' mais reçu : '" + actualMessage + "'");
        System.out.println("✅ Client ajouté avec succès : " + actualMessage);
    }

    /**
     * Retourne true si le message de succès est visible
     * Utilise assertTrue — le test échoue si non visible
     */
    public boolean isCustomerAdded() {
        boolean displayed = wait.until(ExpectedConditions.visibilityOf(successMessage)).isDisplayed();
        assertTrue(displayed, "❌ Le message de succès n'est pas visible.");
        return displayed;
    }
}
