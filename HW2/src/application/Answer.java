package application;

public class Answer {
    private String text;
    private String postedBy;

    public Answer(String text, String postedBy) {
        this.text = text;
        this.postedBy = postedBy;
    }

    public String getText() {
        return text;
    }

    public String getPostedBy() {
        return postedBy;
    }
}
