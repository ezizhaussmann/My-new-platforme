package tutoring.assertions;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

/**
 * @author Ace
 * @created 27-04-2026
 */
public class SoftAssertDemo {
    SoftAssert softAssert;
    @Test
    public void test1()
    {
        softAssert = new SoftAssert();
        softAssert
.assertEquals(1, 1);
        softAssert.assertEquals(1,2);
        softAssert.assertAll();



    }
    @Test
    public void test2()
    {
       softAssert
 = new SoftAssert();
       softAssert.assertTrue(5<6);
    }
}
