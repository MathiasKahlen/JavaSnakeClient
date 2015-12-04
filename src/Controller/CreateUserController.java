package Controller;

import GUI.Animation.GUIAnimations;
import GUI.ControlledScreen;
import GUI.Dialogs.InformationDialogs;
import GUI.MainPane;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Created by MathiasKahlen on 04/12/2015.
 */
public class CreateUserController implements ControlledScreen
{

    MainPane mainPane;

    @FXML
    private TextField lastName;

    @FXML
    private TextField firstName;

    @FXML
    private PasswordField password;

    @FXML
    private Button backBtn;

    @FXML
    private Button createBtn;

    @FXML
    private TextField username;

    @FXML
    private TextField eMail;


    public void createUser(){
        if (firstName.getLength()<=0 || lastName.getLength()<=0 || eMail.getLength()<=0 || username.getLength()<=0 || password.getLength()<=0) {
            if (firstName.getLength() <= 0)
                GUIAnimations.scaleTransition(400, firstName);
            if (lastName.getLength() <= 0)
                GUIAnimations.scaleTransition(400, lastName);
            if (eMail.getLength() <= 0)
                GUIAnimations.scaleTransition(400, eMail);
            if (username.getLength() <= 0)
                GUIAnimations.scaleTransition(400, username);
            if (password.getLength() <= 0)
                GUIAnimations.scaleTransition(400, password);
        } else {
            String message = SnakeApp.serverConnection.createUser(firstName.getText(),
                    lastName.getText(), eMail.getText(), username.getText(), password.getText());
            InformationDialogs.createUserMessage(mainPane, message);
        }

    }

    public void goBack(){
        mainPane.setScreen(MainPane.LOGIN_PANEL);
    }



    @Override
    public void setScreenParent(MainPane parentPane) {
        mainPane = parentPane;
    }
}
