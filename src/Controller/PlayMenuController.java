package Controller;

import GUI.ControlledScreen;
import GUI.MainPane;
import SDK.Model.Game;

import SDK.Model.Gamer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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

        backBtn.setOnAction(event -> mainPane.fadeScreen(MainPane.USER_WELCOME));

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
                if (newValue!=null){
                    switch (newValue){
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
    }

    public void showPendingGames() {
        ObservableList<Game> data = FXCollections.observableArrayList(SnakeAppJavaFXEdition.serverConnection.getSession().getPendingGames());
        gamesTable.setItems(data);

        //Colors the rows so the games the user already played are red
        hostNameColumn.setCellFactory(column -> new TableCell<Game, String>(){
            @Override
            protected void updateItem(String item, boolean empty){
                super.updateItem(item, empty);

                setText(empty ? "" : getItem().toString());
                setGraphic(null);

                TableRow<Game> currentRow = getTableRow();

                if (!isEmpty()){
                    if (item.equals(SnakeAppJavaFXEdition.serverConnection.getSession().getCurrentUser().getUsername())) {
                        currentRow.setStyle("-fx-background-color: crimson");
                        currentRow.setStyle("-fx-background: crimson");
                    }
                    else {
                        currentRow.setStyle("-fx-background-color: forestgreen");
                        currentRow.setStyle("-fx-background: forestgreen");
                    }

                }
            }
        });
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
        if (gamesToShow.getSelectionModel().getSelectedItem().equals("Pending games")){
            SnakeAppJavaFXEdition.serverConnection.getCurrentUsersGames("pending");
            showPendingGames();
        }
        if (gamesToShow.getSelectionModel().getSelectedItem().equals("Hosted games")){
            SnakeAppJavaFXEdition.serverConnection.getCurrentUsersGames("hosted");
            showHostedGames();
        }
        if (gamesToShow.getSelectionModel().getSelectedItem().equals("Finished games")){
            SnakeAppJavaFXEdition.serverConnection.getCurrentUsersGames("finished");
            showFinishedGames();
        }
    }

    @Override
    public void setScreenParent(MainPane parentPane) {
        mainPane = parentPane;
    }


}
