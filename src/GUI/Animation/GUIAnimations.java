package GUI.Animation;

import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * Created by Kahlen on 25-11-2015.
 */
public class GUIAnimations {


    //Animation used to get the users attention on textfields with missing input
    public static void scaleTransition(int duration, Node node){
        node.setVisible(true);
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(duration), node);
        scaleTransition.setByX(0.2f);
        scaleTransition.setByX(0.2f);
        scaleTransition.setCycleCount(2);
        scaleTransition.setAutoReverse(true);
        scaleTransition.play();
    }


}
