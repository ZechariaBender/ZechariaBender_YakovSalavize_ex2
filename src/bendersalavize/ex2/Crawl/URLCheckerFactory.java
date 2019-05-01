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

    // printAll() prints documentation for all available URLChecker implementations
    void printAll() {
        // incidentally there is only one available implementation
        ImageURLChecker.print();
    }
}
