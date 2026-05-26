package databasetestautomation;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Ace
 * @created 25-05-2026
 */
public class TestDataBseConnection {

    String fileName = "config.properties";
    String url=ReadFromConfig.readFromConfig(fileName,"url" );
    String port=ReadFromConfig.readFromConfig(fileName,"port" );
    String user=ReadFromConfig.readFromConfig(fileName,"user" );
    String password=ReadFromConfig.readFromConfig(fileName,"password" );
    String defaultDb=ReadFromConfig.readFromConfig(fileName,"defaultDb" );
    String dbType=ReadFromConfig.readFromConfig(fileName,"dbType" );
    DBConnection dbConnection;
    Connection connection;

    @BeforeClass
    public void testConnection(){
        dbConnection = new DBConnection();
        Connection connection = dbConnection.connect(url, port, user, password, defaultDb, ConnectionRypes.MySQL);
//        Assert.assertTrue(connection != null,"Connection failed");
        try {
            Assert.assertTrue(!connection.isClosed(),"Connection failed");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

