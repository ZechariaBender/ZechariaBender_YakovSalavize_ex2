package bendersalavize.ex2.Crawl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Crawl {
    public static void main(String[] args) {
        URLCheckerFactory urlCheckerFactory = new URLCheckerFactory();
        if (args.length == 4) {
            int poolSize = Integer.valueOf(args[0]);
            int delay = Math.abs(Integer.valueOf(args[1]));
            int attempts = Integer.valueOf(args[2]);
            String filename = args[3];
            // get instance of ImageURLChecker from urlCheckerFactory
            URLChecker urlChecker = urlCheckerFactory.getChecker("image");
            DatabaseManager dbm = new DatabaseManager("jdbc:mysql://localhost:3306/ex2?user=root&password=&serverTimezone=UTC");
            try {
                BufferedReader urlReader = new BufferedReader(new FileReader(filename));
                String url;
                ExecutorService pool = Executors.newFixedThreadPool(poolSize);
                // run each url task on separate thread
                while ((url = urlReader.readLine()) != null)
                    pool.execute(new URLAnalyzeInsertTask(url, urlChecker, delay, attempts, dbm));
                pool.shutdown();
                // wait for all threads to finish, and then output performance log
                pool.awaitTermination(1, TimeUnit.HOURS);
                for (int i = 0; i < URLAnalyzeInsertTask.getPerformanceLog().size(); i++) {
                    System.out.println(URLAnalyzeInsertTask.getPerformanceLog().get(i));
                }
                urlReader.close();
            } catch (FileNotFoundException e) {
                System.err.println("file \"" + filename + "\" not found");
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("\n" +
                    "==========================================\n" +
                    "\n" +
                    "    Crawl\n" +
                    "\n" +
                    "    By:\n" +
                    "    Zecharia bender ID: 320826118\n" +
                    "    Yakov Salavize ID: 203810239\n" +
                    "\n" +
                    "    Date: April 15 2019\n" +
                    "\n" +
                    "==========================================\n" +
                    "\n" +
                    "    This program receives 4 mandatory arguments:\n" +
                    "        1. first a pool size (positive non zero number, see below),\n" +
                    "        2. second a delay for retries (positive non zero milliseconds),\n" +
                    "        3. third a number of retries,\n" +
                    "        4. fourth a file name.\n" +
                    "\n" +
                    "    The program is a simple web crawler that scans URLs of images,\n" +
                    "    records thread performance, and stores them in a database\n" +
                    "    (only the URL, not the image itself).\n" +
                    "      * The first argument defines the pool size for concurrency purposes.\n" +
                    "        In case the program fails to connect to a properly formed URL,\n" +
                    "        the program tries to connect again.\n" +
                    "      * The second argument specifies the delay until next attempt at\n" +
                    "        connecting.\n" +
                    "      * The third argument specifies the maximum number of such attempts.\n" +
                    "      * The fourth argument should be the path to a text file containing\n" +
                    "        a list of URLs to check and insert.\n" +
                    "    The program uses a class called ImageURLChecker which implements\n" +
                    "    the URLChecker interface to verify that the content type of\n" +
                    "    a given URL is an image.\n" +
                    "    A programmer can arbitrarily add alternative URLCheckers with\n" +
                    "    other content types.\n" +
                    "    The current available implementations of URLChecker are:");
            // print documentation for all available URLChecker implementations
            urlCheckerFactory.printAll();
        }
    }
}
