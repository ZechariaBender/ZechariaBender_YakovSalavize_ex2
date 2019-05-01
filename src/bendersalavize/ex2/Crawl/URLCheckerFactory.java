package bendersalavize.ex2.Crawl;

class URLCheckerFactory {

    // getChecker returns new instance of specified URLChecker implementation
    URLChecker getChecker(String type) {
        // incidentally there is only one available implementation
        if (type.equalsIgnoreCase(ImageURLChecker.getKeyword())) {
            return new ImageURLChecker();
        }
        return null;
    }
}
