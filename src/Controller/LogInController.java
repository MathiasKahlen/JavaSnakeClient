package Controller;

import GUI.MainPane;

import GUI.ControlledScreen;
import SDK.Api;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Kahlen on 06-11-2015.
 */
public class LogInController implements Initializable, ControlledScreen{

    Api api = new Api();
    private MainPane mainPane = new MainPane();

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
            //If textfield and passwordfield are not empty
            if (!usernameTf.getText().equals("") && !passwordTf.getText().equals("")) {
                String message = SnakeAppJavaFXEdition.api.login(usernameTf.getText(), passwordTf.getText());
                clearAll();
                if (SnakeAppJavaFXEdition.api.getSession().getCurrentUser()!=null)
                mainPane.fadeScreen(MainPane.USER_WELCOME);
                else{
                    System.out.println(message);
                }
            } else {
                System.out.println("fields cannot be empty");
            }
        });
    }

    public void clearAll(){
        usernameTf.clear();
        passwordTf.clear();
    }

    @Override
    public void setScreenParent(MainPane parentPane) {
        mainPane = parentPane;
    }
}
