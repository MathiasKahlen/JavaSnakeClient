package Controller;
/**
 * Created by Kahlen on 06-11-2015.
 */

import GUI.MainPane;
import SDK.ServerConnection;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SnakeAppJavaFXEdition extends Application {

    static ServerConnection serverConnection;

    public static void main(String[] args) {
        serverConnection = new ServerConnection();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        try {
            MainPane mainPane = new MainPane();
            mainPane.addScreens();
            mainPane.setId("mainPane");
            mainPane.getStylesheets().add("GUI/CSS/style.css");
            mainPane.fadeScreen(MainPane.LOGIN_PANEL);


            Group root = new Group();
            root.getChildren().addAll(mainPane);

            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);

            primaryStage.setScene(scene);
            primaryStage.setTitle("Multiplayer Snake");
            primaryStage.initStyle(StageStyle.TRANSPARENT);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
