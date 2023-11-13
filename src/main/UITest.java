package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import util.ResourceHandler;

public class UITest extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane an = FXMLLoader.load(getClass().getResource("/view/TestView.fxml"));
        Scene scene = new Scene(an, 500, 500);

        stage.setScene(scene);
        stage.show();
    }
}
