package org;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DashboardPage {

    private static final Logger log = LogManager.getLogger(DashboardPage.class);

    ChromeDriver driver;
    FunctionLibrary functionLibrary;

    @FindBy(linkText = "Customer List")
    WebElement customerListLink;

    @FindBy(css = "a[href*='logout']")
    WebElement logoutLink;
    @FindBy(id = "nav_products")
    WebElement productsLink;

    public DashboardPage(ChromeDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        functionLibrary = new FunctionLibrary(driver);
    }

    public void clickCustomerList() {
        log.info("Navigation vers Customer List.");
        functionLibrary.waitForElementPresent(customerListLink);
        customerListLink.click();
    }

    public void clickAddCustomer() {
        log.info("Navigation vers le formulaire Add Customer.");
        driver.get("https://demo.cubecart.com/admin_5xArPd.php?_g=customers&action=add");
    }
    public void clickProducts() {
        log.info("Navigation vers Products.");
        functionLibrary.waitForElementPresent(productsLink);
        productsLink.click();
    }

    public void clickLogout() {
        log.info("Déconnexion en cours.");
        functionLibrary.waitForElementPresent(logoutLink);
        logoutLink.click();
    }
}
