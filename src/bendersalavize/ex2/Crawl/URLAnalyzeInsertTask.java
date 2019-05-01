package bendersalavize.ex2.Crawl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class URLAnalyzeInsertTask implements Runnable {
    private int index;
    private String url;
    private URLChecker urlChecker;
    private int delay;
    private int attempts;
    private AtomicReference<DatabaseManager> dbm;
    private static ArrayList<String> performanceLog;

    URLAnalyzeInsertTask(String url, URLChecker urlChecker, int delay, int attempts, AtomicReference<DatabaseManager> dbm) {
        if (performanceLog == null)
            performanceLog = new ArrayList<>();
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
        double startTime = System.nanoTime();
        int i;
        for (i = 0; i < attempts; i++) {
            try {
                if (urlChecker.accept(url)) {
                        dbm.insert(url);
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
            performanceLog.set(index, displayUrl() + (System.nanoTime() - startTime) / 1000000 + " ms");
            break;
        }
        if (i == attempts) {
            performanceLog.set(index, displayUrl() + "timeout");
        }
    }

    static ArrayList<String> getPerformanceLog() {
        return new ArrayList<>(performanceLog);
    }

    private String displayUrl() {
        int maxLength = 50;
        if (url.length() > maxLength)
            return url.substring(0, maxLength) + "... ";
        return url + ":" + " ".repeat(maxLength - url.length() + 3);
    }
}
