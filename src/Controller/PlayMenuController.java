package Controller;

import GUI.ControlledScreen;
import GUI.MainPane;
import SDK.Model.Game;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    static Game selectedGame;

    @FXML
    private Button backBtn;

    @FXML
    private Button playBtn;

    @FXML
    private Button joinBtn;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        backBtn.setOnAction(event -> mainPane.setScreen(MainPane.MAIN_MENU_PANEL));

        gameNameColumn.setCellValueFactory(new PropertyValueFactory<Game, String>("name"));
        hostNameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getHost().getUsername()));
        opponentNameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getOpponent().getUsername()));
        gameStatusColumn.setCellValueFactory(new PropertyValueFactory<Game, String>("status"));
        gameCreatedColumn.setCellValueFactory(new PropertyValueFactory<Game, Date>("created"));
        mapSizeColumn.setCellValueFactory(new PropertyValueFactory<Game, Double>("mapSize"));
        winnerColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getWinner().getUsername()));

        gamesToShow.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> selected, String oldValue, String newValue) {
                if (newValue != null) {
                    switch (newValue) {
                        case "Pending games":
                            playBtn.setDisable(false);
                            joinBtn.setDisable(true);
                            showPendingGames();
                            break;
                        case "Hosted games":
                            joinBtn.setDisable(true);
                            playBtn.setDisable(true);
                            showHostedGames();
                            break;
                        case "Join game":
                            joinBtn.setDisable(false);
                            playBtn.setDisable(true);
                            showOpenGames();
                            break;
                        case "Finished games":
                            joinBtn.setDisable(true);
                            playBtn.setDisable(true);
                            showFinishedGames();
                            break;
                    }
                }
            }
        });

        //Colors the rows so the games the user already played are red
        hostNameColumn.setCellFactory(column -> new TableCell<Game, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                setText(empty ? "" : getItem().toString());
                setGraphic(null);

                TableRow<Game> currentRow = getTableRow();

                if (!isEmpty()) {
                    if (item.equals(SnakeAppJavaFXEdition.serverConnection.getSession().getCurrentUser().getUsername())) {
                        currentRow.setId("hostedGameRow");
                    } else {
                        currentRow.setId("invitedGameRow");
                    }

                }
            }
        });
    }

    public void showPendingGames() {
        if (SnakeAppJavaFXEdition.serverConnection.getSession().getPendingGames() != null){
        ObservableList<Game> data = FXCollections.observableArrayList(SnakeAppJavaFXEdition.serverConnection.getSession().getPendingGames());
        gamesTable.setItems(data);
        } else {
            gamesTable.getItems().clear();
        }
    }

    public void showHostedGames() {
        if (SnakeAppJavaFXEdition.serverConnection.getSession().getHostedGames()!=null) {
            ObservableList<Game> data = FXCollections.observableArrayList(SnakeAppJavaFXEdition.serverConnection.getSession().getHostedGames());
            gamesTable.setItems(data);
        } else
        {
            gamesTable.getItems().clear();
        }
    }

    public void showOpenGames() {
        if (SnakeAppJavaFXEdition.serverConnection.getSession().getOpenJoinableGames()!=null) {
            ObservableList<Game> data = FXCollections.observableArrayList(SnakeAppJavaFXEdition.serverConnection.getSession().getOpenJoinableGames());
            gamesTable.setItems(data);
        } else {
            gamesTable.getItems().clear();
        }
    }

    public void showFinishedGames() {
        if (SnakeAppJavaFXEdition.serverConnection.getSession().getFinishedGames()!=null) {
            ObservableList<Game> data = FXCollections.observableArrayList(SnakeAppJavaFXEdition.serverConnection.getSession().getFinishedGames());
            gamesTable.setItems(data);
        } else {
            gamesTable.getItems().clear();
        }
    }

    public void refreshSelectedGamesList() {
        if (gamesToShow.getSelectionModel().getSelectedItem().equals("Pending games")){
            SnakeAppJavaFXEdition.serverConnection.getCurrentUsersGames("pending");
            showPendingGames();
        }
        if (gamesToShow.getSelectionModel().getSelectedItem().equals("Hosted games")){
            SnakeAppJavaFXEdition.serverConnection.getCurrentUsersGames("hosted");
            showHostedGames();
        }
        if (gamesToShow.getSelectionModel().getSelectedItem().equals("Join game")){
            SnakeAppJavaFXEdition.serverConnection.getOpenGames();
            showOpenGames();
        }
        if (gamesToShow.getSelectionModel().getSelectedItem().equals("Finished games")){
            SnakeAppJavaFXEdition.serverConnection.getCurrentUsersGames("finished");
            showFinishedGames();
        }
    }

    public void joinGame(){
        SnakeAppJavaFXEdition.serverConnection.joinGame(gamesTable.getSelectionModel().getSelectedItem().getGameId());
    }

    public void playGame(){
        if (gamesTable.getSelectionModel().getSelectedItem().getHost().getId()!=SnakeAppJavaFXEdition.serverConnection.getSession().getCurrentUser().getId()){
            selectedGame = gamesTable.getSelectionModel().getSelectedItem();
            mainPane.setScreen(MainPane.PLAY_GAME_PANEL);
        } else {
            System.out.println("YOU'RE THE HOST OF THIS GAME");
        }
    }

    public void createGame(){

    }

    public TableView<Game> getGamesTable() {
        return gamesTable;
    }

    @Override
    public void setScreenParent(MainPane parentPane) {
        mainPane = parentPane;
    }


}
