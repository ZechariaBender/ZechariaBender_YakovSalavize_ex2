package bendersalavize.ex2.Crawl;

import java.sql.*;
import java.util.concurrent.atomic.AtomicReference;


class DatabaseManager {
    private String url;
    private AtomicReference<Statement> statement = new AtomicReference<>();
    private Connection connect = null;
    DatabaseManager(String url) {
        this.url = url;
    }

    void insert(String url) {
        try {
//            Thread.sleep(1000);
            statement.get().executeQuery(("SELECT url FROM images WHERE url = '"+ url +"'"));
            ResultSet result = statement.get().getResultSet();
            if(!result.next()) {
                statement.get().execute("insert into images VALUES(default, default, '" + url + "')");
                System.out.println("inserted into DB");
            }
        } catch (SQLException ignored) {}
    }

    void initDB()
    {
        try {
            String odbcDriver = "com.mysql.cj.jdbc.Driver";
            Class.forName(odbcDriver);

        }catch (Exception e) {
            System.err.println("Failed to load the driver");
            return;
        }

        // The connection object to the database
        // will be used to perform queries
        try {
            // Create the connection to the database
            connect = DriverManager.getConnection(url);

            System.out.println("Made a connection to the database");

            // Get a statement object from the connection
            // this object will allow us to run queries
            statement.set(connect.createStatement());


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
    void closeDB() {
            try {
                if (connect != null) { connect.close(); }
            } catch (SQLException e) {
                // debugging info
                e.printStackTrace();
            }
    }

    void printDB()
    {
        try {
            ResultSet result = statement.get().executeQuery("SELECT * FROM images ");
            //	     ResultSet result = statement.executeQuery  ("SELECT MAX(price) AS title FROM books");

            while (result.next()) {
                System.out.println(result.getString(1) + ") " + result.getString(2) + " " + result.getString(3));

            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
