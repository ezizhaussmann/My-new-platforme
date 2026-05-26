package databasetestautomation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Ace
 * @created 25-05-2026
 */
public class DBConnection {
    public Connection connect(String dbUrl, String dbPort, String dbUser, String dbPassword,
                              String defaultDb, ConnectionRypes connectionRypes) {

        String JTDS_DRIVER = "net.sourceforge.jtds.jdbc.Driver";
        String MYSQL_DRIVER = "com.mysql.cj.jdbc.Driver";
        String ORACLE_DRIVER = "oracle.jdbc.OracleDriver";
        Connection connection = null;
        switch (connectionRypes){
            case MySQL :
                try {
                    Class.forName(MYSQL_DRIVER);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                String mysqlPath = "jdbc:mysql://" + dbUrl + ":" + dbPort + "/" + defaultDb+"?useSSl=False";
                try {
                    connection = DriverManager.getConnection(mysqlPath, dbUser, dbPassword);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                try {
                    if (!connection.isClosed()){
                        System.out.println("Successfully Connected to : " + mysqlPath);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case MssQl:
                try {
                    Class.forName(JTDS_DRIVER);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                String mssqlPath = "jdbc:jtds:sqlserver://" + dbUrl + ":" + dbPort + ";databaseName=" + defaultDb;
                try {
                    connection=DriverManager.getConnection(mssqlPath,dbUser,dbPassword);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;

        }
        return connection;

    }
    public void closeConnection(Connection connection){
        try {
            if (connection.isClosed()){
                System.out.println("Connection is already closed");
            }else{
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
