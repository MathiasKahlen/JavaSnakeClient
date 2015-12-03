package Controller;

import GUI.ControlledScreen;
import GUI.MainPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

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
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert backBtn != null : "fx:id=\"backBtn\" was not injected: check your FXML file 'HighScoresPane.fxml'.";

        backBtn.setOnAction(event -> mainPane.setScreen(MainPane.MAIN_MENU_PANEL));

    }

    @Override
    public void setScreenParent(MainPane parentPane) {
        mainPane = parentPane;
    }
}
