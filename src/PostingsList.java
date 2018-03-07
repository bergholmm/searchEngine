import java.util.LinkedList;
import java.util.ListIterator;

public class PostingsList {

    private LinkedList<Posting> postingsList;

    public PostingsList() {
        this.postingsList = new LinkedList<Posting>();
    }

    public void insert(int docId) {
        if(this.postingsList.size() == 0) {
            this.postingsList.addLast(new Posting(docId));
        } else{
            Posting last = this.postingsList.getLast();
            if(last.getDocId() == docId) {
                last.add();
            } else {
                this.postingsList.addLast(new Posting(docId));
            }
        }
    }

    public ListIterator<Posting> listIterator() {
        return postingsList.listIterator();
    }

    public int size() {
        return postingsList.size();
    }
}

