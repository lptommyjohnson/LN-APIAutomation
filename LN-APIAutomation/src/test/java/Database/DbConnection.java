package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private final static String url = "jdbc:postgresql://lqdnettest.cifpk9rhtssl.ap-southeast-1.rds.amazonaws.com:5432/LQDNETTEST?Schemas=lqdnetdev";
            // /LQDNETTEST?currentSchema=lqdnetdev";
    private final static String user = "lqdnetdev";
    private final static String password = "dev444lqd444net";
    private static Connection conn = null;
    /**
     * Connect to the PostgreSQL database
     *
     * @return a Connection object
     * @throws ClassNotFoundException
     */
    public static Connection connect() throws Exception {

        //Class.forName("org.postgresql.Driver");
        Connection conn = DriverManager.getConnection(url, user, password);

        System.out.println("Connected to the PostgreSQL server successfully.");

        return conn;
    }

    public static void closeConnection() throws SQLException {
        if(conn!=null && !conn.isClosed()){
            conn.close();
        }
    }
}
