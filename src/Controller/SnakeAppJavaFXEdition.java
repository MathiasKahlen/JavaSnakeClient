package Controller;
/**
 * Created by Kahlen on 06-11-2015.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
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


            StackPane container = new StackPane();
            container.getChildren().add(0, FXMLLoader.load(SnakeAppJavaFXEdition.class.getResource("/GUI/JavaFX/LogInPane.fxml")));

            Group root = new Group();

            root.getChildren().addAll(container);
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Multiplayer Snake");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(SnakeAppJavaFXEdition.class.getName()).log(Level.SEVERE, null, e);
        }

    }
}
