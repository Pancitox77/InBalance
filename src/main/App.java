package main;

import java.io.IOException;

import controller.view.MainViewController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainView.fxml"));
            Parent root = loader.load();
            MainViewController.setInstance(loader.getController());

            stage.setTitle("InBalance - Gestión contable");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/res/images/logo.png")));
            stage.setScene(new Scene(root));
            stage.show();

            stage.setOnCloseRequest(event -> {
                Platform.exit();
                System.exit(0);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}