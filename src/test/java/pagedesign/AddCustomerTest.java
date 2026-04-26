

package pagedesign;

import com.github.javafaker.Faker;
import org.BaseClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pagedesign.DashboardPage;
import pagedesign.LoginPage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @created : 21/04/2026,02:05,mar.
 * Classe de test pour l'ajout d'un client dans CubeCart
 * Hérite de BaseClass pour la gestion du browser
 **/
public class AddCustomerTest extends BaseClass {

    // Page Object de la page de connexion
    LoginPage loginPage;

    // Page Object du dashboard (menu principal)
    DashboardPage dashboardPage;

    // Page Object du formulaire Add Customer
    pageobjectdesignpattern.CustomerListePage customerListePage;

    /**
     * Exécuté avant chaque test — initialise le browser et les Page Objects
     * L'ordre est important : initBrowser() doit être appelé en premier
     * car driver est null avant cette méthode
     */
    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        initBrowser();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        quitBrowser();
    }

    @Test(groups = "regression")
    public void addCustomerTest() {
        loginPage         = new LoginPage(driver);
        dashboardPage     = new DashboardPage(driver);
        customerListePage = new pageobjectdesignpattern.CustomerListePage(driver);
        // Génère des données de test aléatoires
        Faker faker     = new Faker();
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String firstName = faker.name().firstName();
        String lastName  = faker.name().lastName();

        // Email unique : prenom.nom + timestamp + domaine aléatoire
        String email = firstName.toLowerCase() + "." + lastName.toLowerCase()
                     + timestamp + "@" + faker.internet().domainName();

        // Étape 1 : Se connecter
        loginPage.login("cubecart", "cubecart");

        // Étape 2 : Naviguer vers la liste des clients
        dashboardPage.clickCustomerList();

        // Étape 3 : Ouvrir le formulaire Add Customer
        dashboardPage.clickAddCustomer();

        // Étape 4 : Remplir et soumettre le formulaire
        customerListePage.addCustomer(firstName, lastName, email);

        // Étape 5 : Vérifier que le client a bien été ajouté
        customerListePage.verifyCustomerAdded();

        // Étape 6 : Afficher les infos du client ajouté
        customerListePage.printCustomerInfo(firstName, lastName, email);
    }
}
