package Controller;

import GUI.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Kahlen on 14-11-2015.
 */
public class MainMenuController implements Initializable, ControlledScreen {

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

        highscoresBtn.setOnAction(event -> mainPane.setScreen(MainPane.HIGHSCORES_PANEL));
        playBtn.setOnAction(event -> mainPane.setScreen(MainPane.PLAY_MENU_PANEL));
        gamesBtn.setOnAction(event -> {
            System.out.println(SnakeApp.serverConnection.getSession().getJwtToken());
//            SnakeApp.serverConnection.getAllUsers(null);
//            System.out.println(SnakeApp.serverConnection.getCachedData().getAllUsers());
            //SnakeApp.serverConnection.getHighScores();
            //System.out.println(SnakeApp.serverConnection.getCachedData().getHighScores());
        });
    }

    public void logout(){
        SnakeApp.serverConnection.logout();
        mainPane.reloadUi();
        //The thread that was spawned in LogInController will handle the screen swapping
//        mainPane.setScreen(MainPane.LOGIN_PANEL);
    }

    @Override
    public void setScreenParent(MainPane parentPane) {
        mainPane = parentPane;
    }
}
