import java.util.*;

public class InvertedIndex {

    private HashMap<String, PostingsList> index;
    private HashMap<Integer, String> documentIds;
    private HashMap<Integer, Integer> documentLengths;
    private HashMap<Integer, HashSet<String> > documentTerms;

    public InvertedIndex() {
        this.documentIds = new HashMap<Integer, String>();
        this.index = new HashMap<String, PostingsList>();
        this.documentTerms = new HashMap<Integer, HashSet<String> >();
        this.documentLengths = new HashMap<Integer, Integer>();
    }

    public void insert(int docId, String token) {
        // Create postings list for token or add term to postings list
        if(index.containsKey(token)) {
            index.get(token).insert(docId);
        } else {
            PostingsList postingsList = new PostingsList();
            postingsList.insert(docId);
            index.put(token, postingsList);
        }

        // Keeps track of document lenghts
        if(documentLengths.containsKey(docId)) {
            documentLengths.put(docId, documentLengths.get(docId) + 1);
        } else {
            documentLengths.put(docId, 1);
        }

        // Keeps track of terms in document
        HashSet<String> terms = documentTerms.get(docId);
        if(terms == null) {
            terms = new HashSet<String>();
            documentTerms.put(docId, terms);
        }

        terms.add(token);
    }

    public void insertDocument(String doc, int docId) {
        this.documentIds.put(docId, doc);
    }

    public ArrayList<String> search(String searchTerm) {
        PostingsList termDocuments = this.index.get(searchTerm);
        if ( termDocuments == null ) {
            return null;
        }

        ArrayList<Tuple> scores = new ArrayList<Tuple>();
        ListIterator<Posting> it = termDocuments.listIterator();
        double IDF = Math.log( (double) this.documentIds.size() / termDocuments.size() );

        while (it.hasNext()) {
            Posting document = it.next();
            int docId = document.getDocId();
            String docName = this.documentIds.get(docId);

            double TF = (double) document.size() / this.documentLengths.get(docId);
            double score = TF * IDF;
            scores.add(new Tuple(docName, score));
        }

        ArrayList<String> rankedDocuments = new ArrayList<String>();
        scores.sort((a, b) -> a.value < b.value ? 1 : a.value == b.value ? 0 : -1);
        scores.forEach((docScore) -> rankedDocuments.add(docScore.key));

        return rankedDocuments;
    }

    private class Tuple {
        public String key;
        public double value;

        public Tuple(String key, double value) {
            this.key = key;
            this.value = value;
        }
    }
}
