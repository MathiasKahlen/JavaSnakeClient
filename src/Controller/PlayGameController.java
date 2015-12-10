package Controller;

import GUI.ControlledScreen;
import GUI.CustomComponents.SnakeControlsTextField;
import GUI.Dialogs.InformationDialogs;
import GUI.MainPane;
import SDK.Model.Game;
import com.sun.jersey.api.client.ClientHandlerException;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Created by Kahlen on 02-12-2015.
 */
public class PlayGameController implements ControlledScreen{

    MainPane mainPane;

    @FXML
    private SnakeControlsTextField controls;

    /**
     * First creates a Game object with ServerConnection's startGame method with the selected game's id and
     * the user's controls.
     * Then sets the username in the winner object in finishedGame and then starts a runLater for the dialog
     * showing the results with information from finishedGame object
     * Finally returns the user to the Play Menu
     */
    public void playGame(){
        //Threading this method to avoid blocking the UI if the connection to the server is weak or offline
        ThreadUtil.executorService.execute(new Task() {
            @Override
            protected Object call() throws Exception {
                try {
                    Game finishedGame = SnakeApp.serverConnection.startGame(PlayMenuController.selectedGame.getGameId(), controls.getText());

                    //The server doesn't return a username on the winner so for now the winners username is found here.
                    if (finishedGame.getWinner().getId()==PlayMenuController.selectedGame.getHost().getId()) {
                        finishedGame.getWinner().setUsername(PlayMenuController.selectedGame.getHost().getUsername());
                    } else if (finishedGame.getWinner().getId()==PlayMenuController.selectedGame.getOpponent().getId()){
                        finishedGame.getWinner().setUsername(PlayMenuController.selectedGame.getOpponent().getUsername());
                    }

                    Platform.runLater(() -> InformationDialogs.gameResult(mainPane, PlayMenuController.selectedGame, finishedGame));
                    mainPane.setScreen(MainPane.PLAY_MENU_PANEL);
                    //ClientHandlerException will cancel the task if the connection is timed out
                } catch (ClientHandlerException e) {
                    //Cancels the task
                    this.cancel(true);
                    super.cancel(true);
                    Platform.runLater(() -> InformationDialogs.noConnectionError(mainPane));
                }
                return null;
            }
        });
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
