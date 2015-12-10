package GUI.Dialogs;

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
     * This dialog is used when the Client objects in ServerConnection time out
     * This happens in case of slow or no connection to the server
     * @param mainPane shown inside mainPane
     */
    public static void noConnectionError(MainPane mainPane){
        Alert alert = createAlert(mainPane, "Connection Error");
        alert.setContentText("Connection failed. Please check your internet connection.");
        alert.showAndWait();
    }

    /**
     * Message when log in fails
     * @param mainPane shown inside mainPane
     * @param message the message returned from the server
     */
    public static void logInErrorMessage(MainPane mainPane, String message){
        Alert alert = createAlert(mainPane, "Log in Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

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
     * Message when unable to join game
     * @param mainPane shown inside mainPane
     */
    public static void joinGameErrorMessage(MainPane mainPane){
        Alert alert = createAlert(mainPane, "Join Game");
        alert.setContentText("Unable to join the game. Game may no longer be available.");
        alert.showAndWait();
    }

    /**
     * used for creating alert dialogs with the same styling
     * @param mainPane shown inside mainPane
     * @param title the title of the dialog
     * @return returns a standardized alert dialog
     */
    private static Alert createAlert(MainPane mainPane, String title){
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
