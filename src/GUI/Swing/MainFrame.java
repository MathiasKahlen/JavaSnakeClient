package GUI.Swing;
import GUI.Swing.UserGUI.UserPanel;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;

/**
 * Created by Kahlen on 31-10-2015.
 */
public class MainFrame extends JFrame {

        public static final String LOGIN = "LOGIN";
        public static final String USER_WELCOME = "USER_WELCOME";
        public static final String ADMIN_WELCOME = "ADMIN_WELCOME";

        private UserPanel userPanel;
//        private AdminPanel adminPanel;
        private LoginPanel loginPanel;

        private CardLayout cl;
        private JPanel contentPane;


        public MainFrame()
        {
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(800, 600);
            getContentPane().setLayout(null);
            contentPane = new JPanel();
            setContentPane(contentPane);
            cl = new CardLayout();
            contentPane.setLayout(cl);

            loginPanel = new LoginPanel();
            contentPane.add(loginPanel, LOGIN);

            userPanel = new UserPanel();
            contentPane.add(userPanel, USER_WELCOME);

//            adminPanel = new AdminPanel();
//            contentPane.add(adminPanel, ADMIN_WELCOME);

            //Sørger for at LOGIN er det første panel der vises
            show(MainFrame.LOGIN);
        }

        public void show(String card){
            cl.show(contentPane, card);
        }

        public UserPanel getUserPanel()
        {
            return userPanel;
        }
//
//        public AdminPanel getAdminPanel()
//        {
//            return adminPanel;
//        }

        public LoginPanel getLoginPanel()
        {
            return loginPanel;
        }
    }



