package bendersalavize.ex2.Crawl;

public class URLCheckerFactory {

    public URLChecker getChecker(String type) {
        if (type.equalsIgnoreCase(ImageURLChecker.getKeyword())) {
            return new ImageURLChecker();
        }
        return null;
    }

    public void printAll() {
        ImageURLChecker.print();
    }
}
