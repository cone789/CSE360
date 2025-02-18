package application;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminView {
    public void show(Stage primaryStage) {
        primaryStage.setTitle("Admin Panel");

        ListView<Question> questionListView = new ListView<>();
        questionListView.getItems().addAll(QuestionManager.getQuestions());

        Button deleteQuestionButton = new Button("Delete Selected Question");
        deleteQuestionButton.setOnAction(e -> {
            Question selected = questionListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                QuestionManager.deleteQuestion(selected, Main.getCurrentUser());
                questionListView.getItems().remove(selected);
            }
        });

        VBox layout = new VBox(10, questionListView, deleteQuestionButton);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        primaryStage.setScene(new Scene(layout, 500, 400));
        primaryStage.show();
    }
}
