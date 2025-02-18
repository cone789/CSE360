package application;

import java.util.ArrayList;
import java.util.List;

public class QuestionManager {
    private static List<Question> questions = new ArrayList<>();

    public static List<Question> getQuestions() {
        return questions;
    }

    public static void addQuestion(Question question) {
        questions.add(question);
    }

    public static void deleteQuestion(Question question, User user) {
        if (user.isAdmin() || question.getPostedBy().equals("Student")) {
            questions.remove(question);
        }
    }

    public static void addAnswer(Question question, Answer answer) {
        question.addAnswer(answer);
    }

    public static void deleteAnswer(Question question, Answer answer, User user) {
        if (user.isAdmin() || answer.getPostedBy().equals("Student")) {
            question.getAnswers().remove(answer);
        }
    }
}
