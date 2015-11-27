package Controller;
/**
 * Created by Kahlen on 06-11-2015.
 */

import GUI.MainPane;
import SDK.Api;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SnakeAppJavaFXEdition extends Application {

    static Api api;

    public static void main(String[] args) {
        api = new Api();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        try {
            MainPane mainPane = new MainPane();
            mainPane.addScreens();
            mainPane.fadeScreen(MainPane.LOGIN_PANEL);


            Group root = new Group();
            root.getChildren().addAll(mainPane);

            Scene scene = new Scene(root);

            primaryStage.setScene(scene);
            primaryStage.setTitle("Multiplayer Snake");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
