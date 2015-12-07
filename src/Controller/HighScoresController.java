package Controller;

import GUI.ControlledScreen;
import GUI.Dialogs.InformationDialogs;
import GUI.Effects.TextBlend;
import GUI.MainPane;
import SDK.Model.Score;
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
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Kahlen on 19-11-2015.
 */
public class HighScoresController implements Initializable, ControlledScreen{

    MainPane mainPane = new MainPane();

    @FXML
    private Button backBtn;

    @FXML
    private Text highScoreText;

    @FXML
    private TableView<Score> highScoresTable;

    @FXML
    private TableColumn<Score, String> totalScoreColumn;

    @FXML
    private TableColumn<Score, String> usernameColumn;

    @FXML
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert backBtn != null : "fx:id=\"backBtn\" was not injected: check your FXML file 'HighScoresPane.fxml'.";

        usernameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getUser().getUsername()));
        totalScoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        //Colors the rows so the games the user already played are red
        usernameColumn.setCellFactory(column -> new TableCell<Score, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                setText(empty ? "" : getItem().toString());
                setGraphic(null);

                TableRow<Score> currentRow = getTableRow();

                if (!isEmpty()) {
                    currentRow.setId("greenRow");
                }
            }
        });
        showHighScores();

        backBtn.setOnAction(event -> mainPane.setScreen(MainPane.MAIN_MENU_PANEL));

        TextBlend blend = new TextBlend();
        highScoreText.setEffect(blend);

    }

    public void showHighScores(){
        if (SnakeApp.serverConnection.getCachedData().getHighScores()!=null) {
            ObservableList<Score> data = FXCollections.observableArrayList(SnakeApp.serverConnection.getCachedData().getHighScores());
            highScoresTable.setItems(data);
        } else {
            highScoresTable.getItems().clear();
        }
    }

    public void updateHighScores(){
        //Threading this method to avoid blocking the UI if the connection to the server is weak or offline
        ThreadUtil.executorService.execute(new Task() {
            @Override
            protected Object call() throws Exception {
                try {
                    SnakeApp.serverConnection.getHighScores();
                    showHighScores();
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

    @Override
    public void setScreenParent(MainPane parentPane) {
        mainPane = parentPane;
    }
}
