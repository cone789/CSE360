package application;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StudentView {
    public void show(Stage primaryStage) {
        primaryStage.setTitle("Student Panel");

        ListView<Question> questionListView = new ListView<>();
        questionListView.getItems().addAll(QuestionManager.getQuestions());

        TextField questionField = new TextField();
        questionField.setPromptText("Enter your question");

        Button postQuestionButton = new Button("Post Question");
        postQuestionButton.setOnAction(e -> {
            if (!questionField.getText().trim().isEmpty()) {
                Question question = new Question(questionField.getText(), "Student");
                QuestionManager.addQuestion(question);
                questionListView.getItems().add(question);
                questionField.clear();
            }
        });

        Button deleteQuestionButton = new Button("Delete Selected Question");
        deleteQuestionButton.setOnAction(e -> {
            Question selected = questionListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                QuestionManager.deleteQuestion(selected, Main.getCurrentUser());
                questionListView.getItems().remove(selected);
            }
        });

        VBox layout = new VBox(10, questionField, postQuestionButton, questionListView, deleteQuestionButton);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        primaryStage.setScene(new Scene(layout, 500, 400));
        primaryStage.show();
    }
}
