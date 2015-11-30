package Controller;
/**
 * Created by Kahlen on 06-11-2015.
 */

import GUI.CustomStageDecorator;
import GUI.MainPane;
import SDK.ServerConnection;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class SnakeAppJavaFXEdition extends Application {

    static ServerConnection serverConnection;
    private double xOffset;
    private double yOffset;

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

            Button exitButton = new Button("X");
            exitButton.setOnAction(event -> Platform.exit());
            exitButton.setId("btnExit");
            exitButton.setLayoutX(560);
            exitButton.setLayoutY(30);

            Group root = new Group();
            root.getStylesheets().add("GUI/CSS/style.css");
            root.getChildren().addAll(mainPane, exitButton);

            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);

            /*
            The two following lambda expressions makes it possible to move the application without the standard StageStyle
             */
            //Lambda mouse event handler
            scene.setOnMousePressed(event -> {
                xOffset = primaryStage.getX() - event.getScreenX();
                yOffset = primaryStage.getY() - event.getScreenY();
            });
            //Lambda mouse event handler
            scene.setOnMouseDragged(event -> {
                primaryStage.setX(event.getScreenX() + xOffset);
                primaryStage.setY(event.getScreenY() + yOffset);
            });


            primaryStage.setScene(scene);
            primaryStage.setTitle("Multiplayer Snake");
            primaryStage.initStyle(StageStyle.TRANSPARENT);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
