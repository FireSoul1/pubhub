package barring.alcohol;

/**
 * Created by Rishabh on 4/11/2016.
 */
public class Chat {

    private String title;
    private String author;

    @SuppressWarnings("unused")
    private Chat() {
    }

    Chat(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
}
