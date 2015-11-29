package Controller;

import GUI.Animation.GUIAnimations;
import GUI.MainPane;

import GUI.ControlledScreen;

import SDK.SDKConfig.Config;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Kahlen on 06-11-2015.
 */
public class LogInController implements Initializable, ControlledScreen{

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
    }

    public void login(){
        //If textfield or passwordfield are empty
        if (usernameTf.getLength()<=0 || passwordTf.getLength()<=0){
            if (usernameTf.getLength()<=0){
                GUIAnimations.scaleTransition(400, usernameTf);
            }
            if (passwordTf.getLength()<=0){
                GUIAnimations.scaleTransition(400, passwordTf);
            }
            System.out.println("fields cannot be empty");
        } else {
            String message = SnakeAppJavaFXEdition.serverConnection.login(usernameTf.getText(), passwordTf.getText());
            clearAll();
            if (SnakeAppJavaFXEdition.serverConnection.getSession().getCurrentUser()!=null)
                mainPane.fadeScreen(MainPane.USER_WELCOME);
            else{
                System.out.println(message);
            }
        }
    }

    public void clearAll(){
        usernameTf.clear();
        passwordTf.clear();
    }

    @FXML
    public void onEnter(KeyEvent event){
        if (event.getCode()== KeyCode.ENTER){
            login();
        }
    }

    @Override
    public void setScreenParent(MainPane parentPane) {
        mainPane = parentPane;
    }
}
