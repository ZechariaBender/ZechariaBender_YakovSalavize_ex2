package bendersalavize.ex2.Crawl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class URLAnalyzeInsertTask implements Runnable {
    private int index;
    private String url;
    private URLChecker urlChecker;
    private int delay;
    private int attempts;
    private DatabaseManager dbm;
    // performanceLog logs all tasks in order of dispatch
    // and is therefore necessarily shared by all tasks
    private static ArrayList<String> performanceLog;

    URLAnalyzeInsertTask(String url, URLChecker urlChecker, int delay, int attempts, DatabaseManager dbm) {
        if (performanceLog == null)
            performanceLog = new ArrayList<>();
        // index necessary to output performance in same order as url text file
        index = performanceLog.size();
        performanceLog.add(null);
        this.url = url;
        this.urlChecker = urlChecker;
        this.delay = delay;
        this.attempts = attempts;
        this.dbm = dbm;
    }

    @Override
    public void run() {
        double startTime = System.nanoTime(); // record start time for performance
        int i;
        // loop will run only once unless there is a connection issue
        // (i.e. any IOException besides MalformedURLException)
        for (i = 0; i < attempts; i++) {
            try {
                if (urlChecker.accept(url)) {
//                        dbm.insert(url);
                }
            } catch (MalformedURLException e) {
                performanceLog.set(index, displayUrl() + "failed");
                break;
            } catch (IOException e) {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                continue;
            }
            // record performance in appropriate index of performanceLog array
            performanceLog.set(index, displayUrl() + (System.nanoTime() - startTime) / 1000000 + " ms");
            break;
        }
        // if there was a persistent connection issue
        if (i == attempts) {
            performanceLog.set(index, displayUrl() + "timeout");
        }
    }

    static ArrayList<String> getPerformanceLog() {
        return new ArrayList<>(performanceLog);
    }

    // displayUrl() shortens long urls for readability
    private String displayUrl() {
        int maxLength = 50;
        if (url.length() > maxLength)
            return url.substring(0, maxLength) + "... ";
        return url + ":" + " ".repeat(maxLength - url.length() + 3);
    }
}
