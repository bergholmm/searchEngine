import java.nio.file.Path;
import java.util.*;
import java.io.*;

public class Indexer {

    private int lastDocId;
    private InvertedIndex index;

    public Indexer() {
        this.index = new InvertedIndex();
        this.lastDocId = 0;
    }

    public void indexFile(Path path) {
        try (Reader fileReader = new FileReader(path.toString())) {
            StreamTokenizer tokenizer = new StreamTokenizer(fileReader);
            tokenizer.lowerCaseMode(true);

            int docId = getCurrentDocId();
            this.index.insertDocument(path.getFileName().toString(), docId);

            while (tokenizer.nextToken() != StreamTokenizer.TT_EOF) {
                if( tokenizer.sval != null ) {
                    this.index.insert(docId, tokenizer.sval);
                }
            }

            this.updateDocId();
        } catch (FileNotFoundException err) {
            System.out.println(err);
        } catch (IOException err) {
            System.out.println(err);
        }
    }

    public ArrayList<String> search(String searchTerm) {
        return this.index.search(searchTerm);
    }

    private int getCurrentDocId() {
        return this.lastDocId;
    }

    private void updateDocId() {
        this.lastDocId++;
    }
}

