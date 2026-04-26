package pagedesign;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @created : 21/04/2026,00:45,mar.
 * Page Object du Dashboard CubeCart Admin
 **/
public class DashboardPage {

    // Driver Chrome partagé
    ChromeDriver driver;

    // Bibliothèque d'utilitaires (wait, sleep...)
    FunctionLibrary functionLibrary;

    // Lien "Customer List" dans le menu — localisé par son texte
    @FindBy(linkText = "Customer List")
    WebElement customerListLink;

    // Lien "Add Customer" — localisé par l'attribut href contenant "action=add"
    @FindBy(css = "a[href*='action=add']")
    WebElement addCustomerLink;

    // Lien "Log Out" — localisé par l'attribut href contenant "logout"
    @FindBy(css = "a[href*='logout']")
    WebElement logoutLink;

    /**
     * Constructeur — initialise les locators via PageFactory
     */
    public DashboardPage(ChromeDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        functionLibrary = new FunctionLibrary(driver);
    }

    /**
     * Clique sur le lien "Customer List" dans le menu
     */
    public void clickCustomerList() {
        functionLibrary.waitForElementPresent(customerListLink);
        customerListLink.click();
    }

    /**
     * Navigue directement vers le formulaire Add Customer par URL
     * Plus fiable que de cliquer sur le lien (évite les problèmes de locator)
     */
    public void clickAddCustomer() {
        driver.get("https://demo.cubecart.com/admin_5xArPd.php?_g=customers&action=add");
    }

    /**
     * Clique sur le lien "Log Out" pour se déconnecter
     */
    public void clickLogout() {
        functionLibrary.waitForElementPresent(logoutLink);
        logoutLink.click();
    }

    /**
     * Classe interne LoginPage — non utilisée, peut être supprimée
     * @created : 20/04/2026,20:01,lun.
     **/
    public static class LoginPage {
        ChromeDriver driver;
        FunctionLibrary functionLibrary;

        public LoginPage(ChromeDriver driver) {
            this.driver = driver;
            PageFactory.initElements(driver, this);
            functionLibrary = new FunctionLibrary(driver);
        }
    }
}
