package pagedesign;

import java.time.Duration;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pagedesign.FunctionLibrary;

/**
 * @created : 21/04/2026,01:59,mar.
 **/
public class BaseClass {

    // Contrôle le navigateur Chrome
    ChromeDriver driver;

    // Gère les attentes explicites (max 10s par défaut)
    WebDriverWait wait;

    // Bibliothèque de méthodes utilitaires (click, wait, etc.)
    FunctionLibrary functionLibrary;

    /**
     * Appelée dans @BeforeEach — initialise le browser avant chaque test
     */
    public void initBrowser(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-save-password-bubble", "--no-first-run");
        driver = new ChromeDriver(options);

        // Maximise la fenêtre du navigateur
        driver.manage().window().maximize();

        // Supprime les cookies pour avoir une session propre
        driver.manage().deleteAllCookies();

        // Navigue vers la page de connexion CubeCart
        driver.get("https://demo.cubecart.com/admin_5xArPd.php");

        // Configure l'attente explicite à 10 secondes maximum
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Initialise la bibliothèque de méthodes utilitaires
        functionLibrary = new FunctionLibrary(driver);
    }

    /**
     * Appelée dans @AfterEach — ferme le browser après chaque test
     */
    public void quitBrowser(){
        // Ferme l'onglet actif
        driver.close();

        // Ferme complètement le browser et libère la mémoire
        driver.quit();
    }
}
