package Controller;

import GUI.ControlledScreen;
import GUI.MainPane;
import SDK.Model.Game;

import SDK.Model.Gamer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by Kahlen on 25-11-2015.
 */
public class PlayMenuController implements Initializable, ControlledScreen {

    MainPane mainPane = new MainPane();

    @FXML
    private Button backBtn;

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

        backBtn.setOnAction(event -> mainPane.fadeScreen(MainPane.USER_WELCOME));

        gameNameColumn.setCellValueFactory(new PropertyValueFactory<Game, String>("name"));
        hostNameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getHost().getUsername()));
        opponentNameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getOpponent().getUsername()));
        gameStatusColumn.setCellValueFactory(new PropertyValueFactory<Game, String>("status"));
        gameCreatedColumn.setCellValueFactory(new PropertyValueFactory<Game, Date>("created"));
        mapSizeColumn.setCellValueFactory(new PropertyValueFactory<Game, Double>("mapSize"));
        winnerColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getWinner().getUsername()));
    }

    public void showPendingGames() {
        ObservableList<Game> data = FXCollections.observableArrayList(SnakeAppJavaFXEdition.serverConnection.getSession().getPendingGames());
        gamesTable.setItems(data);
    }

    public void showHostedGames() {
        ObservableList<Game> data = FXCollections.observableArrayList(SnakeAppJavaFXEdition.serverConnection.getSession().getHostedGames());
        gamesTable.setItems(data);
    }

    public void showOpenGames() {
    }

    public void showFinishedGames() {
        ObservableList<Game> data = FXCollections.observableArrayList(SnakeAppJavaFXEdition.serverConnection.getSession().getFinishedGames());
        gamesTable.setItems(data);
    }

    public void refreshSelectedGamesList() {
        SnakeAppJavaFXEdition.serverConnection.getCurrentUsersGames("pending");
        SnakeAppJavaFXEdition.serverConnection.getCurrentUsersGames("hosted");
        SnakeAppJavaFXEdition.serverConnection.getCurrentUsersGames("finished");
    }

    @Override
    public void setScreenParent(MainPane parentPane) {
        mainPane = parentPane;
    }


}
