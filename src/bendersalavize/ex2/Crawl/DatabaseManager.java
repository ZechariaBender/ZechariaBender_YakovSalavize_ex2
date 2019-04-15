package bendersalavize.ex2.Crawl;

import java.sql.SQLException;
import java.sql.Statement;

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
}
