package GUI.CustomComponents;

import Controller.ThreadUtil;
import javafx.application.Platform;
import javafx.scene.control.Button;


/**
 * Created by Kahlen on 07-12-2015.
 */

/**
 * Custom exit button
 */
public class CustomExitButton extends Button {

    public CustomExitButton(){
        setText("X");
        setId("btnExit");
        setOnAction(event -> {
            //Exit the application
            Platform.exit();
            //Shutdown the ExecutorService
            ThreadUtil.executorService.shutdownNow();
            if (ThreadUtil.executorService.isShutdown()){
                //System exit is necessary - otherwise all threads wont successfully
                System.exit(0);
            }
        });
    }
}
