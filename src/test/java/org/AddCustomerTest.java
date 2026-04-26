package org;

import com.github.javafaker.Faker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddCustomerTest extends BaseClass {

    private static final Logger log = LogManager.getLogger(AddCustomerTest.class);

    LoginPage loginPage;
    DashboardPage dashboardPage;
    CustomerListePage customerListePage;

    @BeforeEach
    public void setUp() {
        log.info("Initialisation du browser...");
        initBrowser();
        loginPage         = new LoginPage(driver);
        dashboardPage     = new DashboardPage(driver);
        customerListePage = new CustomerListePage(driver);
    }

    @Test
    public void addCustomerTest() {
        Faker faker    = new Faker();
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String firstName = faker.name().firstName();
        String lastName  = faker.name().lastName();
        String email     = firstName.toLowerCase() + "." + lastName.toLowerCase() + timestamp + "@" + faker.internet().domainName();

        log.info("Démarrage du test addCustomerTest");
        loginPage.login("cubecart", "cubecart");
        dashboardPage.clickCustomerList();
        dashboardPage.clickAddCustomer();
        customerListePage.addCustomer(firstName, lastName, email);
        customerListePage.verifyCustomerAdded();
        customerListePage.printCustomerInfo(firstName, lastName, email);
        log.info("Test addCustomerTest terminé avec succès.");
    }

    @AfterEach
    public void tearDown() {
        log.info("Fermeture du browser...");
        quitBrowser();
    }
}
