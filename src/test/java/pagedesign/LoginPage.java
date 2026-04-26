package pagedesign;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.awt.Robot;
import java.awt.event.KeyEvent;

/**
 * @created : 20/04/2026,20:01,lun.
 * Page Object de la page de connexion CubeCart
 **/
public class LoginPage {

    // Driver Chrome partagé
    ChromeDriver driver;

    // Bibliothèque d'utilitaires (wait, sleep...)
    FunctionLibrary functionLibrary;

    // Champ "Username" — localisé par son attribut id="username"
    @FindBy(id = "username")
    WebElement usernameField;

    // Champ "Password" — localisé par son attribut id="password"
    @FindBy(id = "password")
    WebElement passwordField;

    // Bouton "Log In" — localisé par son attribut id="login"
    @FindBy(id = "login")
    WebElement loginButton;

    /**
     * Constructeur — initialise les locators via PageFactory
     * PageFactory.initElements lie les @FindBy aux WebElements
     */
    public LoginPage(ChromeDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        functionLibrary = new FunctionLibrary(driver);
    }

    /**
     * Effectue la connexion avec les identifiants fournis
     * Gère aussi l'alerte Chrome "Sauvegarder le mot de passe"
     */
    public void login(String username, String password) {
        // Attend que le champ username soit visible puis saisit la valeur
        functionLibrary.waitForElementPresent(usernameField);
        usernameField.sendKeys(username);

        // Attend que le champ password soit visible puis saisit la valeur
        functionLibrary.waitForElementPresent(passwordField);
        passwordField.sendKeys(password);

        // Attend que le bouton soit cliquable puis clique
        functionLibrary.waitForElementPresent(loginButton);
        loginButton.click();

        // Ferme l'alerte Chrome "Sauvegarder le mot de passe" avec ENTER
        try {
            Robot robot = new Robot();
            Thread.sleep(2000); // attend 2s que l'alerte apparaisse
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            System.out.println("✅ Alerte Chrome fermée avec ENTER.");
        } catch (Exception e) {
            System.out.println("ℹ️ Pas d'alerte détectée : " + e.getMessage());
        }
    }
}
