package Controller;

import GUI.Animation.GUIAnimations;
import GUI.MainPane;

import GUI.ControlledScreen;

import SDK.SDKConfig.Config;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


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
    private Text christmasText;

    @FXML
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert usernameTf != null : "fx:id=\"usernameTf\" was not injected: check your FXML file 'LogInPane.fxml'.";
        assert loginBtn != null : "fx:id=\"loginBtn\" was not injected: check your FXML file 'LogInPane.fxml'.";
        assert passwordTf != null : "fx:id=\"passwordTf\" was not injected: check your FXML file 'LogInPane.fxml'.";

        Blend blend = new Blend();
        blend.setMode(BlendMode.MULTIPLY);

        DropShadow ds = new DropShadow();
        ds.setColor(Color.rgb(254, 235, 66, 0.3));
        ds.setOffsetX(5);
        ds.setOffsetY(5);
        ds.setRadius(5);
        ds.setSpread(0.2);

        blend.setBottomInput(ds);

        DropShadow ds1 = new DropShadow();
        ds1.setColor(Color.web("#f13a00"));
        ds1.setRadius(20);
        ds1.setSpread(0.2);

        Blend blend2 = new Blend();
        blend2.setMode(BlendMode.MULTIPLY);

        InnerShadow is = new InnerShadow();
        is.setColor(Color.web("#feeb42"));
        is.setRadius(9);
        is.setChoke(0.8);
        blend2.setBottomInput(is);

        InnerShadow is1 = new InnerShadow();
        is1.setColor(Color.web("#f13a00"));
        is1.setRadius(5);
        is1.setChoke(0.4);
        blend2.setTopInput(is1);

        Blend blend1 = new Blend();
        blend1.setMode(BlendMode.MULTIPLY);
        blend1.setBottomInput(ds1);
        blend1.setTopInput(blend2);

        blend.setTopInput(blend1);

        christmasText.setEffect(blend);
        //Setting cache to true on the Text greatly improves performance on the application
        //When the Text isn't cached the fadeScreen effect lags when changing to logInPane.
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
            String message = SnakeAppJavaFXEdition.serverConnection.login(usernameTf.getText(), passwordTf.getText());
            clearAll();
            if (SnakeAppJavaFXEdition.serverConnection.getSession().getCurrentUser()!=null){
                //If user is successfully logged in the application spawns a new Thread which repeatedly checks if the currentUser is still authenticated
                //Get Post Put and Delete methods in ServerConnection automatically logs out the user from the application if a status code 401 Unauthorized is received
                new Thread(new Runnable() {
                    boolean authenticated = true;
                    @Override
                    public void run() {
                        while (authenticated){
                            if (SnakeAppJavaFXEdition.serverConnection.getSession().getCurrentUser()==null) {
                                authenticated=false;
                                mainPane.fadeScreen(MainPane.LOGIN_PANEL);
                                break;
                            }
                            try{
                                Thread.sleep(1000);
                            } catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
                mainPane.fadeScreen(MainPane.USER_WELCOME);
            }
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
