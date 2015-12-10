package Controller;

import GUI.Animation.GUIAnimations;
import GUI.ControlledScreen;
import GUI.Dialogs.InformationDialogs;
import GUI.MainPane;
import com.sun.jersey.api.client.ClientHandlerException;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Created by MathiasKahlen on 04/12/2015.
 */
public class CreateUserController implements ControlledScreen {

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


    /**
     * Method for called when pressing the Create User button
     * First checks if textfields are empty, if not a new Task will be run performing the actual serverrequest
     * for creating the user with the entered information
     */
    public void createUser() {
        if (firstNameTf.getLength() <= 0 || lastNameTf.getLength() <= 0 || eMailTf.getLength() <= 0 || usernameTf.getLength() <= 0 || passwordTf.getLength() <= 0) {
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
            //Threading this part of the method to avoid blocking the UI if the connection to the server is weak or offline
            ThreadUtil.executorService.execute(new Task() {
                @Override
                protected Object call() throws Exception {
                    try {
                        String message = SnakeApp.serverConnection.createUser(firstNameTf.getText(),
                                lastNameTf.getText(), eMailTf.getText(), usernameTf.getText(), passwordTf.getText());
                        Platform.runLater(() -> InformationDialogs.createUserMessage(mainPane, message));
                        clearTextFields();
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
    }

    public void clearTextFields(){
        firstNameTf.clear();
        lastNameTf.clear();
        eMailTf.clear();
        usernameTf.clear();
        passwordTf.clear();
    }

    public void goBack() {
        clearTextFields();
        mainPane.setScreen(MainPane.LOGIN_PANEL);
    }

    @Override
    public void setScreenParent(MainPane parentPane) {
        mainPane = parentPane;
    }
}
