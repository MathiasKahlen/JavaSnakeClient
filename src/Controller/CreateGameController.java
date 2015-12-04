package Controller;

import GUI.ControlledScreen;
import GUI.MainPane;
import SDK.Model.Game;
import SDK.Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private User selectedOpponent = new User();

    @FXML
    private Button mainMenuBtn;

    @FXML
    private TextField controls;

    @FXML
    private TextField mapSize;

    @FXML
    private TextField gameName;

    @FXML
    private TextField opponent;

    @FXML
    private Button backBtn;

    @FXML
    private Button createBtn;

    @FXML
    private TableView<User> usersTable;

    @FXML
    private TableColumn<User, String> usernameColumn;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        assert mainMenuBtn != null : "fx:id=\"mainMenuBtn\" was not injected: check your FXML file 'CreateGamePane.fxml'.";
        assert usersTable != null : "fx:id=\"usersTable\" was not injected: check your FXML file 'CreateGamePane.fxml'.";
        assert controls != null : "fx:id=\"controls\" was not injected: check your FXML file 'CreateGamePane.fxml'.";
        assert mapSize != null : "fx:id=\"mapSize\" was not injected: check your FXML file 'CreateGamePane.fxml'.";
        assert gameName != null : "fx:id=\"gameName\" was not injected: check your FXML file 'CreateGamePane.fxml'.";
        assert opponent != null : "fx:id=\"opponent\" was not injected: check your FXML file 'CreateGamePane.fxml'.";
        assert backBtn != null : "fx:id=\"backBtn\" was not injected: check your FXML file 'CreateGamePane.fxml'.";
        assert createBtn != null : "fx:id=\"playBtn\" was not injected: check your FXML file 'CreateGamePane.fxml'.";
        assert usernameColumn != null : "fx:id=\"username\" was not injected: check your FXML file 'CreateGamePane.fxml'.";

        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        //Colors the rows so the games the user already played are red
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

    public void showUsers(){
        if (SnakeApp.serverConnection.getCachedData().getAllUsers()!=null) {
            ObservableList<User> data = FXCollections.observableArrayList(SnakeApp.serverConnection.getCachedData().getAllUsers());
            usersTable.setItems(data);
        } else {
            usersTable.getItems().clear();
        }
    }

    public void refreshUsers(){
        SnakeApp.serverConnection.getAllUsers();
        showUsers();
    }

    public void invite(){
        if (usersTable.getSelectionModel().getSelectedItem()!=null) {
            if (!usersTable.getSelectionModel().getSelectedItem().getUsername().equals(SnakeApp.serverConnection.getSession().getCurrentUser().getUsername())) {
                selectedOpponent = usersTable.getSelectionModel().getSelectedItem();
                opponent.setText(usersTable.getSelectionModel().getSelectedItem().getUsername());
            } else {
                System.out.println("You cannot invite yourself");
            }
        } else {
            System.out.println("no user selected");
        }
    }

    public void uninvite(){
        selectedOpponent = null;
        opponent.clear();
    }

    public void createGame(){

        //SDK.ServerConnection Requires opponentId to be 0 in order to create an open game
        if (selectedOpponent==null){
            SnakeApp.serverConnection.createGame(gameName.getText(), Integer.parseInt(mapSize.getText()), 0, controls.getText());
        }
        else if (selectedOpponent!=null){
            SnakeApp.serverConnection.createGame(gameName.getText(), Integer.parseInt(mapSize.getText()), selectedOpponent.getId(), controls.getText());
        }
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
