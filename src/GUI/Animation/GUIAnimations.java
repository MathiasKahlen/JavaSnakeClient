package GUI.Animation;

import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * Created by Kahlen on 25-11-2015.
 */
public class GUIAnimations {


    /**
     * Animation that scales a node up and down
     * Used on empty text fields to get the user's attention
     * @param duration duration of the animation
     * @param node the node on which the animation is performed
     */
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
