package bendersalavize.ex2.Crawl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class URLAnalyzeInsertTask implements Runnable {

    private String url;
    private URLChecker urlChecker;
    private DatabaseManager dbm;

    public URLAnalyzeInsertTask(String url, URLChecker urlChecker, DatabaseManager dbm) {
        this.url = url;
        this.urlChecker = urlChecker;
        this.dbm = dbm;
    }

    @Override
    public void run() {
        if(urlChecker.accept(url))
                dbm.insert(url);
    }
}
