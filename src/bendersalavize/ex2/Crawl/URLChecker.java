package bendersalavize.ex2.Crawl;

import javax.net.ssl.SSLHandshakeException;
import java.net.MalformedURLException;

public interface URLChecker {
    boolean accept(String url) throws MalformedURLException, SSLHandshakeException;
    void print();
}
