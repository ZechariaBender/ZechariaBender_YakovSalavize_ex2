package bendersalavize.ex2.Crawl;

import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class ImageURLChecker implements URLChecker {
    @Override
    public boolean accept(String url) throws MalformedURLException, SSLHandshakeException {
        try {
            URL u = new URL(url);
            URLConnection connection = u.openConnection();
            connection.connect();
            String contentType = connection.getContentType();
            if (contentType.contains("image"))
                return true;
        } catch (MalformedURLException e) {
            throw e;
        } catch (SSLHandshakeException e) {
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void print() {

    }
}
