package pagedesign;

import com.github.javafaker.Faker;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @created : 21/04/2026,00:46,mar.
 **/
public class FunctionLibrary {

    // Instance du driver Chrome partagée avec les pages
    ChromeDriver driver;

    // Gère les attentes explicites (max 10s)
    WebDriverWait wait;

    Faker faker ;

    public FunctionLibrary(Faker faker) {
        this.faker = faker;

    }

    /**
     * Constructeur — reçoit le driver et initialise le wait
     */
    public FunctionLibrary(ChromeDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.faker  = new Faker();
    }

    public String[] generateCustomer() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String firstName = faker.name().firstName();
        String lastName  = faker.name().lastName();
        String email     = firstName.toLowerCase() + "." + lastName.toLowerCase()
                           + timestamp + "@" + faker.internet().domainName();
        return new String[]{firstName, lastName, email};
    }

    public String generateFakeName() {
        return faker.name().firstName();
    }

    public String generateEmail() {
        return faker.internet().emailAddress();
    }

    /**
     * Met le thread en pause (utilisé pour attendre des animations)
     * Note : la valeur passée en paramètre est ignorée, pause fixe de 1s
     */
    public void sleep(int seconds) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Attend que l'élément soit visible dans le DOM avant d'agir dessus
     * Evite les NoSuchElementException et StaleElementException
     */
    public void waitForElementPresent(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }
}
