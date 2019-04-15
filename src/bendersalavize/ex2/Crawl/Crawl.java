package bendersalavize.ex2.Crawl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Crawl {
    public static void main(String[] args) {
        if (args.length == 4) {
            int poolSize = Integer.valueOf(args[0]);
            int delay = Math.abs(Integer.valueOf(args[1]));
            int attempt = Integer.valueOf(args[2]);
            String filename = args[3];

            URLChecker urlChecker = new ImageURLChecker();
            DatabaseManager dbm = new DatabaseManager();
            try {
                BufferedReader urlReader = new BufferedReader(new FileReader(filename));
                String url;
                ExecutorService pool = Executors.newFixedThreadPool(poolSize);
                while((url = urlReader.readLine()) != null) {
                    pool.execute(new URLAnalyzeInsertTask(url, urlChecker, dbm));
                }
                pool.shutdown();
                urlReader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else {
            try {
                // display printout from readme
                BufferedReader readme = new BufferedReader
                        (new FileReader("src/Crawl/README"));
                String line;
                while((line = readme.readLine()) != null)
                    System.out.println(line);
                readme.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
