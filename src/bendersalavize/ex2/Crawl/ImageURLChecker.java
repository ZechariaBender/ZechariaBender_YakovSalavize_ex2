package bendersalavize.ex2.Crawl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class ImageURLChecker implements URLChecker {
    @Override
    public boolean accept(String url) {
        try {
            URL u = new URL(url);
            URLConnection connection = u.openConnection();
            connection.connect();
            String contentType = connection.getContentType();
            if (contentType.startsWith("image/"))
                return true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void print() {

    }
}
