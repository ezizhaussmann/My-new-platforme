package org;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.*;

/**
 * @created : 26/04/2026
 * Tests de suppression de produit dans CubeCart
 **/
public class DeleteProductTest extends BaseClass {

    LoginPage loginPage;
    DashboardPage dashboardPage;
    ProductsPage productsPage;

    @BeforeMethod(alwaysRun = true)
    public void setUp(ITestContext context) {
        initBrowser();
        loginPage     = new LoginPage(driver);
        dashboardPage = new DashboardPage(driver);
        productsPage  = new ProductsPage(driver);
        loginPage.login("cubecart", "cubecart");
        dashboardPage.clickProducts();
        context.setAttribute("driver", driver);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        quitBrowser();
    }

    @Test
    public void deleteLastProductTest() {
        productsPage.deleteLastProduct();
        Assert.assertTrue(productsPage.verifyProductDelete(),
                "Le dernier produit n'a pas été supprimé avec succès.");
    }

    @Test
    public void deleteExistingProductTest() {
        productsPage.deleteProduct();
        Assert.assertTrue(productsPage.verifyProductDelete(),
                "Le produit existant n'a pas été supprimé avec succès.");
    }

    @Test
    public void deleteNewlyCreatedProductTest() {
        productsPage.addProduct();
        Assert.assertTrue(productsPage.isProductAddedSuccessfully(),
                "Le produit n'a pas été créé avec succès.");
        productsPage.deleteOwnProduct();
        Assert.assertTrue(productsPage.verifyProductDelete(),
                "Le produit nouvellement créé n'a pas été supprimé avec succès.");
    }
}
