package bendersalavize.ex2.Crawl;

import javax.net.ssl.SSLHandshakeException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class URLAnalyzeInsertTask implements Runnable {
    private int index;
    private String url;
    private URLChecker urlChecker;
    private int delay;
    private int attempts;
    private DatabaseManager dbm;
    private static ArrayList<String> performanceLog;

    URLAnalyzeInsertTask(String url, URLChecker urlChecker, int delay, int attempts, DatabaseManager dbm) {
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
        try {
            int i;
            for (i = 0; i < attempts; i++) {
                try{
                    if(urlChecker.accept(url)) {
//                        dbm.insert(url);
                        performanceLog.set(index, displayUrl() + (System.nanoTime() - startTime) / 1000000 + " ms");
                        break;
                    }
                } catch (SSLHandshakeException ignored) {}
                Thread.sleep(delay);
            }
            if (i == attempts){
                performanceLog.set(index, displayUrl() + "timeout");
            }
        } catch (MalformedURLException e) {
            performanceLog.set(index, displayUrl() + "failed");
        } catch (InterruptedException e) {
            e.printStackTrace();
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
