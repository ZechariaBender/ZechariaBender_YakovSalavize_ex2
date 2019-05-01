package bendersalavize.ex2.Crawl;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class ImageURLChecker implements URLChecker {
    @Override
    public boolean accept(String url) throws IOException {
        URL u = new URL(url);
        URLConnection connection = u.openConnection();
        connection.connect();
        String contentType = connection.getContentType();
        return contentType.contains("image");
    }

    public static void print() {
        System.out.println("\tImageURLChecker: checks if content type of URL is image");
    }

    public static String getKeyword() {
        return "image";
    }

}
