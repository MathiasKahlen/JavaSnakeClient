package GUI.Dialogs;

import GUI.MainPane;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.stage.StageStyle;

import java.util.Optional;

/**
 * Created by MathiasKahlen on 05/12/2015.
 */
public class ConfirmationDialogs {


    /**
     * Confirmation dialog that asks the user to confirm before game is deleted
     * @param mainPane shown inside mainPane
     * @return returns true if OK button is clicked or false if cancel is clicked or the dialog is closed
     */
    public static boolean deleteGameConfirmationDialog(MainPane mainPane){
        Alert alert = createAlert(mainPane, "Delete Game");
        alert.setContentText("Are you sure you wish to delete this game?");
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }




    private static Alert createAlert(MainPane mainPane, String title){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
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
