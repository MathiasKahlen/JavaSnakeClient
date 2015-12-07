package Controller;

import GUI.Animation.GUIAnimations;
import GUI.ControlledScreen;
import GUI.CustomComponents.NumberTextField;
import GUI.Dialogs.InformationDialogs;
import GUI.MainPane;
import SDK.Model.Game;
import SDK.Model.User;
import com.sun.jersey.api.client.ClientHandlerException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Kahlen on 03-12-2015.
 */
public class CreateGameController implements Initializable, ControlledScreen {

    MainPane mainPane;
    private User selectedOpponent;

    @FXML
    private TextField controlsTf;

    @FXML
    private NumberTextField mapSizeTf;

    @FXML
    private TextField gameNameTf;

    @FXML
    private TextField opponentTf;

    @FXML
    private Button backBtn;

    @FXML
    private TableView<User> usersTable;

    @FXML
    private TableColumn<User, String> usernameColumn;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        assert usersTable != null : "fx:id=\"usersTable\" was not injected: check your FXML file 'CreateGamePane.fxml'.";
        assert controlsTf != null : "fx:id=\"controlsTf\" was not injected: check your FXML file 'CreateGamePane.fxml'.";
        assert mapSizeTf != null : "fx:id=\"mapSizeTf\" was not injected: check your FXML file 'CreateGamePane.fxml'.";
        assert gameNameTf != null : "fx:id=\"gameNameTf\" was not injected: check your FXML file 'CreateGamePane.fxml'.";
        assert opponentTf != null : "fx:id=\"opponentTf\" was not injected: check your FXML file 'CreateGamePane.fxml'.";
        assert backBtn != null : "fx:id=\"backBtn\" was not injected: check your FXML file 'CreateGamePane.fxml'.";
        assert usernameColumn != null : "fx:id=\"username\" was not injected: check your FXML file 'CreateGamePane.fxml'.";

        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        //Gives all rows the id "greenRow" for styling purpose
        usernameColumn.setCellFactory(column -> new TableCell<User, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                setText(empty ? "" : getItem().toString());
                setGraphic(null);

                TableRow<Game> currentRow = getTableRow();

                if (!isEmpty()) {
                    currentRow.setId("greenRow");
                }
            }
        });
        showUsers();
    }

    /**
     * Method for showing all users in the system
     */
    public void showUsers(){
        if (SnakeApp.serverConnection.getCachedData().getAllUsers()!=null) {
            ObservableList<User> data = FXCollections.observableArrayList(SnakeApp.serverConnection.getCachedData().getAllUsers());
            usersTable.setItems(data);
        } else {
            usersTable.getItems().clear();
        }
    }

    /**
     * Reloads and shows all users in the system
     */
    public void refreshUsers(){
        //Threaded to avoid blocking the UI if the connection to the server is weak or offline
        ThreadUtil.executorService.execute(new Task() {
            @Override
            protected Object call() throws Exception {
                try {
                    SnakeApp.serverConnection.getAllUsers();
                    showUsers();
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

    /**
     * Sets the selectedOpponent variable and sets the opponentTf TextField with the opponentTf's username
     */
    public void invite(){
        if (usersTable.getSelectionModel().getSelectedItem()!=null) {
            if (!usersTable.getSelectionModel().getSelectedItem().getUsername().equals(SnakeApp.serverConnection.getSession().getCurrentUser().getUsername())) {
                selectedOpponent = usersTable.getSelectionModel().getSelectedItem();
                opponentTf.setText(usersTable.getSelectionModel().getSelectedItem().getUsername());
            } else {
                System.out.println("You cannot invite yourself");
            }
        } else {
            System.out.println("no user selected");
        }
    }

    /**
     * Sets selectedOpponent to null and clears the TextField
     */
    public void uninvite(){
        selectedOpponent = null;
        opponentTf.clear();
    }

    /**
     * If required TextFields are filled a game will be created as "open" if no opponentTf is selected and "pending" if an opponentTf is selected
     */
    public void createGame(){

        if (controlsTf.getLength()<=0|| mapSizeTf.getLength()<=0 || gameNameTf.getLength()<=0) {
            //Animations on text fields if they are empty
            if (controlsTf.getLength()<=0)
                GUIAnimations.scaleTransition(400, controlsTf);
            if (mapSizeTf.getLength()<=0)
                GUIAnimations.scaleTransition(400, mapSizeTf);
            if (gameNameTf.getLength()<=0)
                GUIAnimations.scaleTransition(400, gameNameTf);

        } else {
            //Threading this part of the method to avoid blocking the UI if the connection to the server is weak or offline
            ThreadUtil.executorService.execute(new Task() {
                @Override
                protected Object call() throws Exception {
                    try {
                        //SDK.ServerConnection Requires opponentId to be 0 in order to create an open game
                        if (selectedOpponent == null) {
                            String message = SnakeApp.serverConnection.createGame(gameNameTf.getText(), Integer.parseInt(mapSizeTf.getText()), 0, controlsTf.getText());
                            clearTextFields();
                            //New Runnable as lambda expression
                            Platform.runLater(() -> InformationDialogs.createGameMessage(mainPane, message));

                        } else if (selectedOpponent != null) {
                            String message = SnakeApp.serverConnection.createGame(gameNameTf.getText(), Integer.parseInt(mapSizeTf.getText()), selectedOpponent.getId(), controlsTf.getText());
                            clearTextFields();
                            //New Runnable as lambda expression
                            Platform.runLater(() -> InformationDialogs.createGameMessage(mainPane, message));
                        }
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
    }

    /**
     * Clears text fields
     * used when a game is created or if the user leave the screen
     */
    public void clearTextFields(){
        gameNameTf.clear();
        mapSizeTf.clear();
        controlsTf.clear();
        opponentTf.clear();
    }

    /**
     * Clears text fields and shows Play Menu
     */
    public void goBack(){
        clearTextFields();
        mainPane.setScreen(MainPane.PLAY_MENU_PANEL);
    }

    /**
     * Clears text fields and shows Main Menu
     */
    public void goToMainMenu(){
        clearTextFields();
        mainPane.setScreen(MainPane.MAIN_MENU_PANEL);
    }


    @Override
    public void setScreenParent(MainPane parentPane) {
        mainPane = parentPane;
    }
}
