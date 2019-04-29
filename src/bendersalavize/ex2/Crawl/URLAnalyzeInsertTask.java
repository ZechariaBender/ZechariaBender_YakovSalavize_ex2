package bendersalavize.ex2.Crawl;

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
        double startTime=System.nanoTime();
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        if(urlChecker.accept(url))
            dbm.insert(url);
        double elapsedTimeSoFar = System.nanoTime()-startTime;
        System.out.println(elapsedTimeSoFar / 1000000);
    }
}
