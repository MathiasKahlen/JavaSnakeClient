package Controller;

import GUI.*;
import SDK.Api;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Kahlen on 14-11-2015.
 */
public class MainMenuController implements Initializable, ControlledScreen {

    Api api = new Api();
    MainPane mainPane = new MainPane();

    @FXML
    private Button gamesBtn;

    @FXML
    private Button logOutBtn;

    @FXML
    private Button playBtn;

    @FXML
    private Button highscoresBtn;

    @FXML
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        logOutBtn.setOnAction(event -> {
                SnakeAppJavaFXEdition.api.logout();
                mainPane.fadeScreen(MainPane.LOGIN_PANEL);
                });

        highscoresBtn.setOnAction(event -> mainPane.fadeScreen(MainPane.HIGHSCORES_PANEL));
        playBtn.setOnAction(event -> mainPane.fadeScreen(MainPane.PLAY_PANEL));
        gamesBtn.setOnAction(event -> {
//            SnakeAppJavaFXEdition.api.getAllUsers(null);
//            System.out.println(SnakeAppJavaFXEdition.api.getCachedData().getAllUsers());
            SnakeAppJavaFXEdition.api.getHighScores(null);
            System.out.println(SnakeAppJavaFXEdition.api.getCachedData().getHighScores());
        });
    }

    @Override
    public void setScreenParent(MainPane parentPane) {
        mainPane = parentPane;
    }
}
