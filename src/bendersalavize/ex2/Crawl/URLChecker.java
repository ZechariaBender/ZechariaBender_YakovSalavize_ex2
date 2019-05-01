package bendersalavize.ex2.Crawl;

import java.io.IOException;

public interface URLChecker {
    boolean accept(String url) throws IOException;
}
