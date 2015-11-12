package Controller;/**
 * Created by Kahlen on 06-11-2015.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SnakeAppJavaFXEdition extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        try {
            Pane logInPane = FXMLLoader.load(SnakeAppJavaFXEdition.class.getResource("/GUI/JavaFX/LogInPane.fxml"));
            Scene scene = new Scene(logInPane);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Multiplayer Snake");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(SnakeAppJavaFXEdition.class.getName()).log(Level.SEVERE, null, e);
        }

    }
}
