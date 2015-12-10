package Controller;

import GUI.ControlledScreen;
import GUI.Dialogs.ConfirmationDialogs;
import GUI.Dialogs.InformationDialogs;
import GUI.MainPane;
import SDK.Model.Game;

import com.sun.jersey.api.client.ClientHandlerException;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by Kahlen on 25-11-2015.
 */
public class PlayMenuController implements Initializable, ControlledScreen {

    MainPane mainPane;
    public static Game selectedGame;

    @FXML
    private Button backBtn;

    @FXML
    private Button playBtn;

    @FXML
    private Button joinBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button newGameBtn;

    @FXML
    private ComboBox<String> gamesToShow;

    @FXML
    private TableView<Game> gamesTable;

    @FXML
    private TableColumn mapSizeColumn;

    @FXML
    private TableColumn gameNameColumn;

    @FXML
    private TableColumn<Game, String> hostNameColumn;

    @FXML
    private TableColumn gameCreatedColumn;

    @FXML
    private TableColumn gameStatusColumn;

    @FXML
    private TableColumn<Game, String> opponentNameColumn;

    @FXML
    private TableColumn<Game, String> winnerColumn;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        backBtn.setOnAction(event -> mainPane.setScreen(MainPane.MAIN_MENU_PANEL));

        //Sets all column's values
        gameNameColumn.setCellValueFactory(new PropertyValueFactory<Game, String>("name"));
        hostNameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getHost().getUsername()));
        opponentNameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getOpponent().getUsername()));
        gameStatusColumn.setCellValueFactory(new PropertyValueFactory<Game, String>("status"));
        gameCreatedColumn.setCellValueFactory(new PropertyValueFactory<Game, Date>("created"));
        mapSizeColumn.setCellValueFactory(new PropertyValueFactory<Game, Double>("mapSize"));
        winnerColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getWinner().getUsername()));

        //Adds ChangeListener to the combobox in order to be able to execute different methods depending on
        //the user's selection
        gamesToShow.getSelectionModel().selectedItemProperty().addListener((selected, oldValue, newValue) -> {
            if (newValue != null) {
                switch (newValue) {
                    case "Pending games":
                        playBtn.setDisable(false);
                        joinBtn.setDisable(true);
                        deleteBtn.setDisable(false);
                        showPendingGames();
                        break;
                    case "Hosted games":
                        joinBtn.setDisable(true);
                        playBtn.setDisable(true);
                        deleteBtn.setDisable(false);
                        showHostedGames();
                        break;
                    case "Join game":
                        joinBtn.setDisable(false);
                        playBtn.setDisable(true);
                        deleteBtn.setDisable(true);
                        showOpenGames();
                        break;
                    case "Finished games":
                        joinBtn.setDisable(true);
                        playBtn.setDisable(true);
                        deleteBtn.setDisable(true);
                        showFinishedGames();
                        break;
                }
            }
        });

        //Sets the id of the rows depending on content of the Host column. If host is the currentUser the row will be red
        //Otherwise the row will be green. This allows the user to easily see which games are playable.
        //TODO: this should be changed for finished games, I havn't figured how to do this yet.
        hostNameColumn.setCellFactory(column -> new TableCell<Game, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                setText(empty ? "" : getItem().toString());
                setGraphic(null);

                TableRow<Game> currentRow = getTableRow();

                    if (!isEmpty()) {
                            if (item.equals(SnakeApp.serverConnection.getSession().getCurrentUser().getUsername())) {
                                currentRow.setId("redRow");
                            } else {
                                currentRow.setId("greenRow");
                            }
                    }
            }
        });
    }

    /**
     * If pendingGames ArrayList in Session isn't null the content of the table will be set to this
     * otherwise the table will be cleared.
     */
    public void showPendingGames() {
        if (SnakeApp.serverConnection.getSession().getPendingGames() != null){
        ObservableList<Game> data = FXCollections.observableArrayList(SnakeApp.serverConnection.getSession().getPendingGames());
        gamesTable.setItems(data);
        } else {
            gamesTable.getItems().clear();
        }
    }

    /**
     * If hostedGames ArrayList in Session isn't null the content of the table will be set to this
     * otherwise the table will be cleared.
     */
    public void showHostedGames() {
        if (SnakeApp.serverConnection.getSession().getHostedGames()!=null) {
            ObservableList<Game> data = FXCollections.observableArrayList(SnakeApp.serverConnection.getSession().getHostedGames());
            gamesTable.setItems(data);
        } else
        {
            gamesTable.getItems().clear();
        }
    }

    /**
     * If openJoinableGames ArrayList in Session isn't null the content of the table will be set to this
     * otherwise the table will be cleared.
     */
    public void showOpenGames() {
        if (SnakeApp.serverConnection.getSession().getOpenJoinableGames()!=null) {
            ObservableList<Game> data = FXCollections.observableArrayList(SnakeApp.serverConnection.getSession().getOpenJoinableGames());
            gamesTable.setItems(data);
        } else {
            gamesTable.getItems().clear();
        }
    }

    /**
     * If finishedGames ArrayList in Session isn't null the content of the table will be set to this
     * otherwise the table will be cleared.
     */
    public void showFinishedGames() {
        if (SnakeApp.serverConnection.getSession().getFinishedGames()!=null) {
            ObservableList<Game> data = FXCollections.observableArrayList(SnakeApp.serverConnection.getSession().getFinishedGames());
            gamesTable.setItems(data);
        } else {
            gamesTable.getItems().clear();
        }
    }

    /**
     * Updates Session's ArrayLists depending on the selection of the ComboBox and sets the content of the table
     * accordingly
     */
    public void refreshSelectedGamesList() {
        //Threading this method to avoid blocking the UI if the connection to the server is weak or offline
        ThreadUtil.executorService.execute(new Task() {
            @Override
            protected Object call() throws Exception {

                try {
                    if (gamesToShow.getSelectionModel().getSelectedItem().equals("Pending games")) {
                        SnakeApp.serverConnection.getCurrentUsersGames("pending");
                        showPendingGames();
                    }
                    if (gamesToShow.getSelectionModel().getSelectedItem().equals("Hosted games")) {
                        SnakeApp.serverConnection.getCurrentUsersGames("hosted");
                        showHostedGames();
                    }
                    if (gamesToShow.getSelectionModel().getSelectedItem().equals("Join game")) {
                        SnakeApp.serverConnection.getOpenGames();
                        showOpenGames();
                    }
                    if (gamesToShow.getSelectionModel().getSelectedItem().equals("Finished games")) {
                        SnakeApp.serverConnection.getCurrentUsersGames("finished");
                        showFinishedGames();
                    }
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

    /**
     * Executes the ServerConnecton's joinGame method with the selected game from the table.
     * If game is successfully joined the game will be removed from the table, otherwise a dialog box will be
     * called in a runLater method telling the user, that the game is no longer available
     */
    public void joinGame(){
        //Threading this method to avoid blocking the UI if the connection to the server is weak or offline
        ThreadUtil.executorService.execute(new Task() {
            @Override
            protected Object call() throws Exception {
                try {
                    if (SnakeApp.serverConnection.joinGame(gamesTable.getSelectionModel().getSelectedItem().getGameId())){
                        //Next two rows updates the table locally without calling the Server after the update
                        gamesTable.getItems().remove(gamesTable.getSelectionModel().getSelectedItem());
                        gamesTable.refresh();
                    } else {
                        Platform.runLater(() -> InformationDialogs.joinGameErrorMessage(mainPane));
                    }
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

    /**
     * If currentUser's id and Host id in the selected game is not the same the user will be redirected to the
     * PlayGamePane
     * Otherwise nothing currently happens as it is not possible to join games in which you are the host since
     * you already played the game
     */
    public void playGame(){
        if (gamesTable.getSelectionModel().getSelectedItem().getHost().getId()!= SnakeApp.serverConnection.getSession().getCurrentUser().getId()){
            selectedGame = gamesTable.getSelectionModel().getSelectedItem();
            mainPane.setScreen(MainPane.PLAY_GAME_PANEL);
        } else {
            //TODO: Maybe a dialog box here? Or find a way to disable play button when a hosted game is selected
            System.out.println("YOU'RE THE HOST OF THIS GAME");
        }
    }

    /**
     * If the game isn't finished and if the currentUser is either the host or the opponent in the game then
     * deletGameAndUpdateTable method in the bottom of this class will be called.
     */
    public void deleteGame(){
        //This seems redundant as the delete button is disabled on the list of finished games, but keeping it for now.
        if (!gamesTable.getSelectionModel().getSelectedItem().getStatus().equals("finished")) {
            //If current user is the host in the game
            if (gamesTable.getSelectionModel().getSelectedItem().getHost().getUsername().equals(SnakeApp.serverConnection.getSession().getCurrentUser().getUsername()))
            {
                if (ConfirmationDialogs.deleteGameConfirmationDialog(mainPane)) {
                    //Threading this part of the method to avoid blocking the UI if the connection to the server is weak or offline
                    ThreadUtil.executorService.execute(deleteGameAndUpdateTable());
                }
                //If current user is the opponent in the game
            } else if (gamesTable.getSelectionModel().getSelectedItem().getOpponent().getUsername() != null &&
                    gamesTable.getSelectionModel().getSelectedItem().getOpponent().getUsername().equals(SnakeApp.serverConnection.getSession().getCurrentUser().getUsername()))
            {
                if (ConfirmationDialogs.deleteGameConfirmationDialog(mainPane)){
                    //Threading this part of the method to avoid blocking the UI if the connection to the server is weak or offline
                    ThreadUtil.executorService.execute(deleteGameAndUpdateTable());
                }
            } else {
                //This will never happen since the button for deleting games is disabled on lists where you are
                //not allowed to delete games
                System.out.println("You are not a player in this game.");
            }
        } else {
            //This will never happen either, since the button is also disabled here
            System.out.println("You can't delete finished games");
        }
    }

    /**
     * Purpose of this method is to avoid redundant code.
     * Method used twice in deleteGame().
     * @return returns a Task which calls ServerConnection's deleteGame() method and updates the gamesTable locally
     * without having to call the server for an updated list.
     */
    public Task deleteGameAndUpdateTable(){
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                try {
                    SnakeApp.serverConnection.deleteGame(gamesTable.getSelectionModel().getSelectedItem().getGameId());
                    //Next two rows updates the table locally without calling the Server after the update
                    gamesTable.getItems().remove(gamesTable.getSelectionModel().getSelectedItem());
                    gamesTable.refresh();
                    //ClientHandlerException will cancel the task if the connection is timed out
                } catch (ClientHandlerException e) {
                    //Cancels the task
                    this.cancel(true);
                    super.cancel(true);
                    Platform.runLater(() -> InformationDialogs.noConnectionError(mainPane));
                }
                return null;
            }
        };
        return task;
    }

    public void createGame(){
        mainPane.setScreen(MainPane.CREATE_GAME_PANEL);
    }

    @Override
    public void setScreenParent(MainPane parentPane) {
        mainPane = parentPane;
    }
}
