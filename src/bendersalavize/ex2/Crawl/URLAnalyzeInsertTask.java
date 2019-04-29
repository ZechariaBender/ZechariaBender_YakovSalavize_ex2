package bendersalavize.ex2.Crawl;

import javax.net.ssl.SSLHandshakeException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class URLAnalyzeInsertTask implements Runnable {

    private int index;
    private String url;
    private URLChecker urlChecker;
    private int delay;
    private int attempts;
    private DatabaseManager dbm;
    static AtomicReference<ArrayList<String>> performanceLog;

    URLAnalyzeInsertTask(String url, URLChecker urlChecker, int delay, int attempts, DatabaseManager dbm) {
        this.url = url;
        this.urlChecker = urlChecker;
        this.delay = delay;
        this.attempts = attempts;
        this.dbm = dbm;
        if (performanceLog == null) {
            performanceLog = new AtomicReference<>();
            performanceLog.set(new ArrayList<>());
        }
    }

    @Override
    public void run() {
        index = performanceLog.get().size();
        performanceLog.get().add(null);
        double startTime = System.nanoTime();
        try {
            int i;
            for (i = 0; i < attempts; i++) {
                try{
                    if(urlChecker.accept(url)) {
//                        dbm.insert(url);
                        performanceLog.get().set(index, displayUrl() + (System.nanoTime() - startTime) / 1000000 + " ms");
                        break;
                    }
                } catch (SSLHandshakeException ignored) {}
                Thread.sleep(delay);
            }
            if (i == attempts){
                performanceLog.get().set(index, displayUrl() + "timeout");
            }
        } catch (MalformedURLException e) {
            performanceLog.get().set(index, displayUrl() + "failed");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    String displayUrl() {
        int maxLength = 50;
        if (url.length() > maxLength)
            return url.substring(0, maxLength) + "... ";
        return url + ":" + " ".repeat(maxLength - url.length() + 3);
    }
}
