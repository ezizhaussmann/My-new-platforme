package revision;


import org.ScreenShotListener;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.*;


/**
 * @created : 26/04/2026,01:05,dim.
 **/
@Listeners({TestResultListener.class, ScreenShotListener.class, ExtentReportListener.class})
public class TestNgDemo extends DemoDriver {
    @BeforeSuite
    public void beforeSuite(){
        System.out.println("run before suite");}
    @BeforeTest
    public void beforeTest(ITestContext context){
        System.out.println("run before test");
    }
    @BeforeClass
    public void beforeClass(){System.out.println("run before class");}
    @BeforeMethod
    public void beforeMethod(){System.out.println("run before method");}
    @BeforeGroups("smoke")
    public void beforeGroup(){System.out.println("run before group");}
    @AfterGroups("smoke")
    public void afterGroup(){System.out.println("run after group");}
    @Test
    public void test(){
        System.out.println("run test");
        Assert.assertEquals("run test","run test");
    }
    @Test
    public void test2(){
        System.out.println("run test2");
        Assert.assertEquals("run test2", "run test2");
    }
    @Test(groups = {"smoke"})
    public void test3(){
        System.out.println("run test3");
        Assert.assertEquals("run test3", "run test3");
    }
    @AfterMethod
    public void afterMethod(){System.out.println("run after method");}
    @AfterClass
    public void afterClass(){System.out.println("run after class");}
    @AfterTest
    public void afterTest(){System.out.println("run after test");}
    @AfterSuite
    public void afterSuite(){System.out.println("run after suite");}

    @Test
    public void test4(){
        System.out.println("run test4");
        Assert.assertEquals("run test4", "run test4");
    }
}
