package application;

import java.util.ArrayList;
import java.util.List;

public class Question {
    private static int idCounter = 1;
    private int id;
    private String text;
    private String postedBy;
    private List<Answer> answers;

    public Question(String text, String postedBy) {
        this.id = idCounter++;
        this.text = text;
        this.postedBy = postedBy;
        this.answers = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void addAnswer(Answer answer) {
        answers.add(answer);
    }
}
