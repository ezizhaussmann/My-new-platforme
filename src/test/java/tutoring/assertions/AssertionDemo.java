package tutoring.assertions;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Ace
 * @created 27-04-2026
 */
public class AssertionDemo {
    @Test
    public void testAssertions(){
//        Assert.assertTrue(7<6,"7 is not less than 6");
        Assert.assertTrue(9>6);
        Assert.assertEquals("Ace","Ace");
        Assert.assertFalse(9<6);
        Assert.assertNotEquals("get","Get" );
        Assert.assertNull(null);
    }
}
