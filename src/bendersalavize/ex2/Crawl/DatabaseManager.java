package bendersalavize.ex2.Crawl;

import java.sql.*;


class DatabaseManager {

    private Statement statement;
    private Connection connect = null;

    void initDB() {
        try {
            String odbcDriver = "com.mysql.cj.jdbc.Driver";
            Class.forName(odbcDriver);
            // Create the connection to the database
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/ex2?user=root&password=&serverTimezone=UTC");
            System.out.println("Successfully connected to database");
            statement = connect.createStatement();
        } catch (ClassNotFoundException e) {
            System.err.println("Failed to load the driver");
        } catch (SQLException e) {
            System.err.println("Database connection failed");
        }

    }

    void closeDB() {
        try {
            if (connect != null) {
                connect.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void insert(String url) {
        try {
            // first check if URL already in DB
            statement.executeQuery(("SELECT url FROM images WHERE url = '" + url + "'"));
            ResultSet result = statement.getResultSet();
            if (!result.next()) {
                statement.execute("insert into images VALUES(default, default, '" + url + "')");
                System.out.println("inserted URL into DB");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void printDB() {
        try {
            ResultSet result = statement.executeQuery("SELECT * FROM images ");
            while (result.next())
                System.out.println(result.getString(1) + ") " + result.getString(2) + " " + result.getString(3));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
