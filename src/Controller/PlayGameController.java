package Controller;

import GUI.ControlledScreen;
import GUI.MainPane;
import SDK.Model.Game;
import SDK.Model.Gamer;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.stage.StageStyle;

/**
 * Created by Kahlen on 02-12-2015.
 */
public class PlayGameController implements ControlledScreen{

    MainPane mainPane;

    @FXML
    private Button mainMenuBtn;

    @FXML
    private TextField controls;

    @FXML
    private Button backBtn;

    @FXML
    private Button playBtn;

    public void playGame(){
        Game finishedGame = SnakeAppJavaFXEdition.serverConnection.startGame(PlayMenuController.selectedGame.getGameId(), controls.getText());

        //The server doesn't return a username on the winner so for now the winners username is found here.
        if (finishedGame.getWinner().getId()==PlayMenuController.selectedGame.getHost().getId()) {
            finishedGame.getWinner().setUsername(PlayMenuController.selectedGame.getHost().getUsername());
        } else if (finishedGame.getWinner().getId()==PlayMenuController.selectedGame.getOpponent().getId()){
            finishedGame.getWinner().setUsername(PlayMenuController.selectedGame.getOpponent().getUsername());
        }


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(mainPane.getScene().getWindow());
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(PlayMenuController.selectedGame.getHost().getUsername() + " scored: " + finishedGame.getHost().getScore() + "\n" +
                PlayMenuController.selectedGame.getOpponent().getUsername() + " scored: " + finishedGame.getOpponent().getScore() + "\n" +
                "The winner is: " + finishedGame.getWinner().getUsername());
        alert.initStyle(StageStyle.UTILITY);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add("GUI/CSS/style.css");
        dialogPane.getStyleClass().add("myDialog");
        alert.showAndWait();
        mainPane.setScreen(MainPane.PLAY_MENU_PANEL);
    }

    public void goBack(){
        mainPane.setScreen(MainPane.PLAY_MENU_PANEL);
    }

    public void goToMainMenu(){
        mainPane.setScreen(MainPane.MAIN_MENU_PANEL);
    }

    @Override
    public void setScreenParent(MainPane parentPane) {
        mainPane = parentPane;
    }
}
