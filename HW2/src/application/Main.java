package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Main application class that allows users to select a role (Student/Admin)
 * and navigates to the respective page.
 */
public class Main extends Application {

    private static User currentUser;

    @Override
    public void start(Stage primaryStage) {
        showRoleSelection(primaryStage);
    }

    /**
     * Displays the role selection screen.
     */
    public void showRoleSelection(Stage primaryStage) {
        VBox layout = new VBox(10);
        layout.setStyle("-fx-alignment: center; -fx-padding: 20;");

        Label roleLabel = new Label("Select your role:");
        roleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        ComboBox<String> roleComboBox = new ComboBox<>();
        roleComboBox.getItems().addAll("Student", "Admin");

        Button continueButton = new Button("Continue");

        continueButton.setOnAction(e -> {
            String selectedRole = roleComboBox.getValue();
            if (selectedRole == null) {
                showAlert("Error", "Please select a role.");
                return;
            }

            currentUser = new User(selectedRole.equals("Admin"));

            if (selectedRole.equals("Admin")) {
                new AdminView().show(primaryStage);
            } else {
                new StudentView().show(primaryStage);
            }
        });

        layout.getChildren().addAll(roleLabel, roleComboBox, continueButton);
        Scene scene = new Scene(layout, 400, 200);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Role Selection");
        primaryStage.show();
       
    }

    /**
     * Displays an error alert.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
