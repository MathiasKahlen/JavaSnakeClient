package GUI.Dialogs;

import Controller.PlayMenuController;
import GUI.MainPane;
import SDK.Model.Game;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.stage.StageStyle;

/**
 * Created by Kahlen on 04-12-2015.
 */
public class InformationDialogs {

    /**
     * Message when logging out
     * @param mainPane shown inside mainPane
     */
    public static void loggedOutMessage(MainPane mainPane){
        Alert alert = createAlert(mainPane, "Logged out");
        alert.setContentText("You have been logged out");
        alert.showAndWait();
    }

    /**
     * Message when a game is finished
     * @param mainPane shown inside mainPane
     * @param selectedGame used for creating ContentText
     * @param finishedGame used for creating ContentText
     */
    public static void gameResult(MainPane mainPane, Game selectedGame, Game finishedGame){
        Alert alert = createAlert(mainPane, "Game Result");
        alert.setContentText(selectedGame.getHost().getUsername() + " scored: " + finishedGame.getHost().getScore() + "\n" +
                selectedGame.getOpponent().getUsername() + " scored: " + finishedGame.getOpponent().getScore() + "\n" +
                "The winner is: " + finishedGame.getWinner().getUsername());
        alert.showAndWait();
    }

    /**
     * Message when creating a user
     * @param mainPane shown inside mainPane
     * @param message message received from the server when attempting to create a user.
     */
    public static void createUserMessage(MainPane mainPane, String message){
        Alert alert = createAlert(mainPane, "Create User");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Message when creating a game
     * @param mainPane shown inside mainPane
     * @param message message received from the server when attempting to create a game
     */
    public static void createGameMessage(MainPane mainPane, String message){
        Alert alert = createAlert(mainPane, "Create Game");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Message when joining a game
     * @param mainPane shown inside mainPane
     * @param message message received from the server when attempting to create a game
     */
    public static void joinGameMessage(MainPane mainPane, String message){
        Alert alert = createAlert(mainPane, "Join Game");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * used for creating alert dialogs with the same styling
     * @param mainPane shown inside mainPane
     * @param title the title of the dialog
     * @return returns a standardized alert dialog
     */
    public static Alert createAlert(MainPane mainPane, String title){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        //This line sets the owner of the Alert to the stage which mainPane is connected to
        alert.initOwner(mainPane.getScene().getWindow());
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.initStyle(StageStyle.UTILITY);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add("GUI/CSS/style.css");
        dialogPane.getStyleClass().add("myDialog");
        return alert;
    }
}
