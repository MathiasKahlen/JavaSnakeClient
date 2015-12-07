package Controller;

import GUI.Animation.GUIAnimations;
import GUI.Dialogs.InformationDialogs;
import GUI.Effects.TextBlend;
import GUI.MainPane;

import GUI.ControlledScreen;

import com.sun.org.apache.xpath.internal.SourceTree;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.CacheHint;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;


import java.net.ConnectException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.*;

/**
 * Created by Kahlen on 06-11-2015.
 */
public class LogInController implements Initializable, ControlledScreen {

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

        //Setting effect on the christmas text
        TextBlend blend = new TextBlend();
        christmasText.setEffect(blend);
        //Setting cache to true on the Text greatly improves performance on the application
        //When the Text isn't cached the setScreen effect lags when changing to logInPane.
        christmasText.setCache(true);
        christmasText.setCacheHint(CacheHint.SPEED);

    }

    /**
     * Login method.
     * If login is successful currentUser is set in serverConnection.getSession
     * Furthermore a Thread is spawned which continuously checks if the user is still logged in.
     * The application will automaticly log out the user whenever a status code 401 Unauthorized is received from the server.
     */
    public void login(){

        ThreadUtil.executorService.submit(new Task() {
            @Override
            protected Object call() throws Exception {
                //If textfield or passwordfield are empty
                if (usernameTf.getLength() <= 0 || passwordTf.getLength() <= 0) {
                    if (usernameTf.getLength() <= 0) {
                        GUIAnimations.scaleTransition(400, usernameTf);
                    }
                    if (passwordTf.getLength() <= 0) {
                        GUIAnimations.scaleTransition(400, passwordTf);
                    }
                    System.out.println("fields cannot be empty");
                } else {
                    String message = SnakeApp.serverConnection.login(usernameTf.getText(), passwordTf.getText());

                    if (SnakeApp.serverConnection.getSession().getCurrentUser() != null) {

                        //If user is successfully logged in the application spawns a new Thread which repeatedly checks if the currentUser is still authenticated
                        //Get Post Put and Delete methods in ServerConnection automatically logs out the user from the application if a status code 401 Unauthorized is received
                        ThreadUtil.executorService.execute(new Task() {
                            @Override
                            protected Object call() throws Exception {
                                boolean authenticated = true;
                                while (authenticated) {
                                    if (SnakeApp.serverConnection.getSession().getCurrentUser() == null) {
                                        //Since JavaFX is single threaded the Platform.runLater is necessary to make it possible to show the dialog box from another thread than JavaFX's main thread.
                                        Platform.runLater(() -> InformationDialogs.loggedOutMessage(mainPane));
//                                        authenticated = false;
                                        mainPane.setScreen(MainPane.LOGIN_PANEL);
                                        mainPane.reloadUi();
                                        break;
                                    }
                                    try {
                                        Thread.sleep(1000);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                return null;
                            }
                        });
                        mainPane.setScreen(MainPane.MAIN_MENU_PANEL);
                    } else {
                        //Since JavaFX is single threaded the Platform.runLater is necessary to make it possible to show the dialog box from another thread than JavaFX's main thread.
                        Platform.runLater(() -> InformationDialogs.logInErrorMessage(mainPane, message));

                    }
                }
                return null;
            }
        });
    }

    /**
     * Redirecting to create user screen
     */
    public void createUser() {
        mainPane.setScreen(MainPane.CREATE_USER_PANEL);
    }

    /**
     * Sets KeyEvent on enter key which calls login() method.
     *
     * @param event
     */
    @FXML
    public void onEnter(KeyEvent event) throws InterruptedException {
        if (event.getCode() == KeyCode.ENTER) {
            login();
        }
    }

    @Override
    public void setScreenParent(MainPane parentPane) {
        mainPane = parentPane;
    }
}
