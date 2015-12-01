package GUI;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.HashMap;

/**
 * Created by Kahlen on 14-11-2015.
 */


public class MainPane extends StackPane {

    private HashMap<String, Node> screens = new HashMap<>();

    public static final String LOGIN_PANEL = "LoginPanel";
    public static final String LOGIN_PANEL_FILE = "/GUI/fxml/LogInPane.fxml";
    public static final String USER_WELCOME = "UserWelcome";
    public static final String USER_WELCOME_FILE = "/GUI/fxml/MainMenuPane.fxml";
    public static final String HIGHSCORES_PANEL = "HighScores";
    public static final String HIGHSCORES_PANEL_FILE = "/GUI/fxml/HighScoresPane.fxml";
    public static final String PLAY_PANEL = "/GUI/fxml/HighScoresPane.fxml";
    public static final String PLAY_PANEL_FILE = "/GUI/fxml/PlayMenuPane.fxml";


    public MainPane() {
        super();
    }

    public void setScreen(String name) {

        if (!getChildren().isEmpty()) {
            getChildren().remove(0);
            getChildren().add(0, screens.get(name));
        } else
            getChildren().add(0, screens.get(name));
    }

    public void fadeScreen(String name) {
        if (screens.get(name) != null) {
            DoubleProperty opacity = opacityProperty();

            //if there is more than one screen
            if (!getChildren().isEmpty()) {
                Timeline fade = new Timeline(
                        new KeyFrame(Duration.ZERO,
                                new KeyValue(opacity, 1.0)),
                        new KeyFrame(new Duration(1000),

                                //Anonymous lambda event listener
                                event -> {
                                    //remove displayed screen
                                    getChildren().remove(0);
                                    //add new screen
                                    getChildren().add(0, screens.get(name));
                                    Timeline fadeIn = new Timeline(
                                            new KeyFrame(Duration.ZERO,
                                                    new KeyValue(opacity, 0.0)),
                                            new KeyFrame(new Duration(800),
                                                    new KeyValue(opacity, 1.0)));
                                    fadeIn.play();
                                }, new KeyValue(opacity, 0.0)));
                fade.play();
            } else {
                //no other screen displayed
                setOpacity(0.0);
                getChildren().add(screens.get(name));
                Timeline fadeIn = new Timeline(
                        new KeyFrame(Duration.ZERO,
                                new KeyValue(opacity, 0.0)),
                        new KeyFrame(new Duration(2500),
                                new KeyValue(opacity, 1.0)));
                fadeIn.play();
            }
        }
    }

    //Add all screens in the GUI to the HashMap
    public void addScreens() {
        loadScreen(LOGIN_PANEL, LOGIN_PANEL_FILE);
        loadScreen(USER_WELCOME, USER_WELCOME_FILE);
        loadScreen(HIGHSCORES_PANEL, HIGHSCORES_PANEL_FILE);
        loadScreen(PLAY_PANEL, PLAY_PANEL_FILE);
    }

    public void addScreen(String name, Node screen) {
        screens.put(name, screen);
    }

    public void loadScreen(String name, String resource) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(resource));
            Parent loadScreen = fxmlLoader.load();
            ControlledScreen myScreenController = fxmlLoader.getController();
            myScreenController.setScreenParent(this);
            addScreen(name, loadScreen);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
