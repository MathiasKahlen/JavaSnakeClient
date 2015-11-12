package Controller.oldstuff;

import GUI.Swing.MainFrame;
import SDK.Api;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Kahlen on 31-10-2015.
 */
public class SnakeApp {

    private MainFrame frame;
    private UserController userController;
    private Api api = new Api();


    public SnakeApp(){
        frame = new MainFrame();
        frame.setVisible(true);
        userController = new UserController(this);
        injectActionListeners();
    }

    public void injectActionListeners()
    {
        //LoginPanel ActionListeners
        frame.getLoginPanel().addActionListener(new LoginScreenActionListener());

//        //User actionlisteners
        frame.getUserPanel().getUserNorthPanel().addActionListener(userController);
//        frame.getUserPanel().getUserCenterPanel().getDepositPanel().addActionListener(userController);
//        frame.getUserPanel().getUserCenterPanel().getWithdrawPanel().addActionListener(userController);
//        frame.getUserPanel().getUserCenterPanel().getTransferPanel().addActionListener(userController);
//
//        //Admin actionlisteners
//        frame.getAdminPanel().getAdminNorthPanel().addActionListener(adminController);
//        frame.getAdminPanel().getAdminCenterPanel().getExchangeRatePanel().addActionListener(adminController);
//        frame.getAdminPanel().getAdminCenterPanel().getCreateUserPanel().addActionListener(adminController);
//        frame.getAdminPanel().getAdminCenterPanel().getDeleteUserPanel().addActionListener(adminController);
//        frame.getAdminPanel().getAdminCenterPanel().getShowUsersPanel().addActionListener(adminController);
    }



    private class LoginScreenActionListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            String message = api.login(frame.getLoginPanel().getIdField().getText(),
                    frame.getLoginPanel().getPwField().getText());

            try {
                if (!api.getCurrentUser().equals(null)) {
                    frame.show(MainFrame.USER_WELCOME);
                }
            } catch (NullPointerException exc){
                JOptionPane.showMessageDialog(frame, message);
            }
        }
    }

    public void logout() {
        api.logout();
        frame.show(MainFrame.LOGIN);
        frame.getLoginPanel().getIdField().setText("");
        frame.getLoginPanel().getPwField().setText("");
    }
}
