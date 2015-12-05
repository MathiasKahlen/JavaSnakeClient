package Controller;

import GUI.Animation.GUIAnimations;
import GUI.ControlledScreen;
import GUI.Dialogs.InformationDialogs;
import GUI.MainPane;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Created by MathiasKahlen on 04/12/2015.
 */
public class CreateUserController implements ControlledScreen
{

    MainPane mainPane;

    @FXML
    private TextField lastNameTf;

    @FXML
    private TextField firstNameTf;

    @FXML
    private TextField eMailTf;

    @FXML
    private TextField usernameTf;

    @FXML
    private PasswordField passwordTf;


    public void createUser(){
        if (firstNameTf.getLength()<=0 || lastNameTf.getLength()<=0 || eMailTf.getLength()<=0 || usernameTf.getLength()<=0 || passwordTf.getLength()<=0) {
            if (firstNameTf.getLength() <= 0)
                GUIAnimations.scaleTransition(400, firstNameTf);
            if (lastNameTf.getLength() <= 0)
                GUIAnimations.scaleTransition(400, lastNameTf);
            if (eMailTf.getLength() <= 0)
                GUIAnimations.scaleTransition(400, eMailTf);
            if (usernameTf.getLength() <= 0)
                GUIAnimations.scaleTransition(400, usernameTf);
            if (passwordTf.getLength() <= 0)
                GUIAnimations.scaleTransition(400, passwordTf);
        } else {
            String message = SnakeApp.serverConnection.createUser(firstNameTf.getText(),
                    lastNameTf.getText(), eMailTf.getText(), usernameTf.getText(), passwordTf.getText());
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
