public class Posting {

    private int docId;
    private int size;

    public Posting(int docId) {
        this.docId = docId;
        this.size = 1;
    }

    public void add() {
        this.size++;
    }

    public int getDocId() {
        return this.docId;
    }

    public int size() {
        return this.size;
    }
}
