import java.util.stream.Stream;
import java.util.*;
import java.nio.file.*;
import java.io.*;

public class SimpleSearchEngine {

    private Indexer indexer;

    public SimpleSearchEngine() {
        this.indexer = new Indexer();
    }

    public void index(String path) {
        System.out.println("Indexing files");
        try (Stream<Path> paths = Files.walk(Paths.get(path))) {
            paths
                .filter(Files::isRegularFile)
                .forEach(this.indexer::indexFile);
        } catch (IOException err) {
            System.out.println(err);
        }
        System.out.println("Indexing done");
    }

    public void search() {
        Scanner reader = new Scanner(System.in);

        while (true) {
            System.out.println("\nPress 1 to make a search \nPress any other key to quit");

            if (reader.hasNextInt() && reader.nextInt() == 1) {
                System.out.println("Enter your search term:");
                String searchTerm = reader.next();

                ArrayList<String> result = this.indexer.search(searchTerm);
                if (result == null) {
                    System.out.println("Result: No result for search term");
                } else {
                    System.out.println("Result: " + result);
                }
            } else {
                break;
            }
        }

        reader.close();
    }

    public static void main(String[] args) {
        if (args.length > 0) {
            SimpleSearchEngine simpleSearchEngine = new SimpleSearchEngine();
            simpleSearchEngine.index(args[0]);
            simpleSearchEngine.search();
        } else {
            System.out.println("Give a directory for file indexing");
        }
    }
}
