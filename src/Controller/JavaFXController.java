package Controller;

import SDK.Api;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Kahlen on 06-11-2015.
 */
public class JavaFXController implements Initializable {

    Api api = new Api();

    @FXML
    private TextField usernameTf;

    @FXML
    private Button loginBtn;

    @FXML
    private PasswordField passwordTf;

    @FXML
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert usernameTf != null : "fx:id=\"usernameTf\" was not injected: check your FXML file 'LogInPane.fxml'.";
        assert loginBtn != null : "fx:id=\"loginBtn\" was not injected: check your FXML file 'LogInPane.fxml'.";
        assert passwordTf != null : "fx:id=\"passwordTf\" was not injected: check your FXML file 'LogInPane.fxml'.";

        loginBtn.setOnAction(event -> {
            api.login(usernameTf.getText(), passwordTf.getText());
            System.out.println(api.getCurrentUser());
        });


    }










}
