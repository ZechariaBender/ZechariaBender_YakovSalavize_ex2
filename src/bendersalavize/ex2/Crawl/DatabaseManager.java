package bendersalavize.ex2.Crawl;

import java.sql.*;


public class DatabaseManager {



    private Statement statement;
    public DatabaseManager() {
        this.statement = null;
    }
    public DatabaseManager(Statement statement) {
        this.statement = statement;
    }


    public boolean insert(String url) {
        try {
            return statement.execute("insert url into images");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void initDB()
    {
        try {
            // Load the Oracle driver
            // Java can dymamically load classes - here we load the external library jdbc-sql-connector
            // String odbcDriver = "sun.jdbc.odbc.JdbcOdbcDriver";
            String odbcDriver = "com.mysql.jdbc.Driver";
            Class.forName(odbcDriver);

        }catch (Exception e) {
            System.out.println("Failed to load the driver");
            return;
        }

        String url = "jdbc:mysql://localhost:3306/ex2?user=root&password=&serverTimezone=UTC";

        // The connection object to the database
        // will be used to perform queries
        Connection connect = null;
        try {
            // Create the connection to the database
            connect = DriverManager.getConnection(url);

            System.out.println("Made a connection to the database");

            // Get a statement object from the connection
            // this object will allow us to run queries
            Statement statement = connect.createStatement();


            // Droping a the table books if it is exists so that later we
            // can create a new table books. If the table does not exists
            // then the dropping will cause an exception to be thrown.
            // The exception is caught so that nothing happens.
            // SEE THE REMARK BELOW
        }
        catch (Exception e) {
            // debuggin information
            System.out.println("Exception was thrown:\n");
            e.printStackTrace();
        }
    }
}
