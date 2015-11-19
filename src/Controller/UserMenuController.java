package Controller;

import GUI.ControlledScreen;
import GUI.MainPane;
import SDK.Api;
import SDK.Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Kahlen on 14-11-2015.
 */
public class UserMenuController implements Initializable, ControlledScreen {


    Api api = new Api();
    MainPane mainPane = new MainPane();

    @FXML
    private Button button1;

    @FXML
    private Button printSelected;

    @FXML
    private TableView<User> allUsers;

    @FXML
    private TableColumn id;

    @FXML
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        button1.setOnAction(event -> mainPane.fadeScreen(MainPane.LOGIN_PANEL));

        id.setCellValueFactory(new PropertyValueFactory<User, String>("id"));

        allUsers.setItems(getUsers());

        printSelected.setOnAction(event -> System.out.println(allUsers.getSelectionModel().getSelectedItem().getId()));

    }

    public ObservableList<User> getUsers(){
        User user1 = new User();
        user1.setId(1);
        User user2 = new User();
        user2.setId(2);

//        ObservableList<User> data = FXCollections.observableArrayList(api.getCachedData().getAllUsers());
        ObservableList<User> data = FXCollections.observableArrayList(user1, user2);


        return data;
    }


    @Override
    public void setScreenParent(MainPane parentPane) {
        mainPane = parentPane;
    }
}
