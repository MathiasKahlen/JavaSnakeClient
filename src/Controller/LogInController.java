package Controller;

import GUI.Animation.GUIAnimations;
import GUI.Effects.TextBlend;
import GUI.MainPane;

import GUI.ControlledScreen;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.CacheHint;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;


import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Kahlen on 06-11-2015.
 */
public class LogInController implements Initializable, ControlledScreen{

    private MainPane mainPane;

    @FXML
    private TextField usernameTf;

    @FXML
    private Button loginBtn;

    @FXML
    private Button createUserBtn;

    @FXML
    private PasswordField passwordTf;

    @FXML
    private Text christmasText;

    @FXML
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert usernameTf != null : "fx:id=\"usernameTf\" was not injected: check your FXML file 'LogInPane.fxml'.";
        assert loginBtn != null : "fx:id=\"loginBtn\" was not injected: check your FXML file 'LogInPane.fxml'.";
        assert passwordTf != null : "fx:id=\"passwordTf\" was not injected: check your FXML file 'LogInPane.fxml'.";

        createUserBtn.setOnAction(event -> mainPane.setScreen(MainPane.CREATE_USER_PANEL));

        //Setting effect on the christmas text
        TextBlend blend = new TextBlend();
        christmasText.setEffect(blend);
        //Setting cache to true on the Text greatly improves performance on the application
        //When the Text isn't cached the setScreen effect lags when changing to logInPane.
        christmasText.setCache(true);
        christmasText.setCacheHint(CacheHint.SPEED);

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
            String message = SnakeApp.serverConnection.login(usernameTf.getText(), passwordTf.getText());

            if (SnakeApp.serverConnection.getSession().getCurrentUser()!=null){

                //If user is successfully logged in the application spawns a new Thread which repeatedly checks if the currentUser is still authenticated
                //Get Post Put and Delete methods in ServerConnection automatically logs out the user from the application if a status code 401 Unauthorized is received
                Thread test = new Thread(new Runnable() {
                    boolean authenticated = true;
                    @Override
                    public void run() {
                        while (authenticated){
                            if (SnakeApp.serverConnection.getSession().getCurrentUser()==null) {
                                authenticated=false;
                                mainPane.setScreen(MainPane.LOGIN_PANEL);
                                mainPane.reloadUi();
                                break;
                            }
                            try{
                                Thread.sleep(1000);
                            } catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                });
                //Creating thread as a daemon which makes sure the thread will be stopped when the application is closed.
                test.setDaemon(true);
                test.start();
                mainPane.setScreen(MainPane.MAIN_MENU_PANEL);
            }
            else{
                System.out.println(message);
            }
        }
    }

    public void createUser(){
        //TODO: Create screen for user creation
        //mainPane.setScreen();
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
