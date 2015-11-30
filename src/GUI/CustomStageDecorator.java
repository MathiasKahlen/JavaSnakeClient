package GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Created by Kahlen on 30-11-2015.
 */
public class CustomStageDecorator extends AnchorPane{

    private double xOffset = 0;
    private double yOffset = 0;
    private Stage primaryStage;

    public CustomStageDecorator(Stage stage, Node node){

        super();

        primaryStage = stage;
        this.setPadding(new Insets(0,0,0,0));
        this.getStylesheets().add("GUI/CSS/style.css");

        Button btnMax = buildButton("Mx",(e) -> primaryStage.setMaximized(!primaryStage.isMaximized()));
        AnchorPane.setRightAnchor(btnMax, 60.0);
        AnchorPane.setTopAnchor(btnMax, 10.0);

        Button btnClose = buildButton("X", (e) -> primaryStage.close());
        AnchorPane.setRightAnchor(btnClose, 10.0);
        AnchorPane.setTopAnchor(btnClose, 10.0);

        AnchorPane.setTopAnchor(node, 0.0);
        AnchorPane.setRightAnchor(node, 0.0);
        AnchorPane.setBottomAnchor(node, 0.0);
        AnchorPane.setLeftAnchor(node, 0.0);

        this.getChildren().addAll(node, btnMax, btnClose);

        this.setOnMouseClicked((MouseEvent e) ->{
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();
        });

        this.setOnMouseDragged((MouseEvent e) -> {
            primaryStage.setX(e.getScreenX() - xOffset);
            primaryStage.setY(e.getScreenY() - yOffset);
        });
    }

    private Button buildButton(String text, EventHandler<ActionEvent> onAction){
        Button btn = new Button(text);
        btn.setMinSize(44,44);
        btn.setMaxSize(44,44);
        btn.setOnAction(onAction);
        return btn;

    }




}
