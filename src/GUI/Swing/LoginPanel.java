package GUI.Swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by Kahlen on 31-10-2015.
 */
public class LoginPanel extends JPanel {

    private JTextField idField;
    private JPasswordField pwField;

    private JButton logInbtn;
    private JLabel passwordLbl;
    private JLabel userIdLbl;

    public LoginPanel()
    {
        setForeground(Color.BLACK);
        setLayout(null);
        setSize(800, 600);
        setBackground(Color.decode("#4967AA"));

        pwField = new JPasswordField("");
        pwField.setHorizontalAlignment(SwingConstants.CENTER);
        pwField.setBounds(275, 267, 250, 50);
        pwField.setFont(new Font("Arial Black", Font.PLAIN, 20));
        add(pwField);

        idField = new JTextField("");
        idField.setHorizontalAlignment(SwingConstants.CENTER);
        idField.setBounds(275, 206, 250, 50);
        idField.setFont(new Font("Arial Black", Font.PLAIN, 20));
        add(idField);

        logInbtn = new JButton("Log in");
        logInbtn.setActionCommand("login");
        logInbtn.setBounds(350, 328, 100, 50);
        add(logInbtn);

        userIdLbl = new JLabel("Login ID");
        userIdLbl.setFont(new Font("Arial Black", Font.PLAIN, 20));
        userIdLbl.setBounds(160, 211, 92, 32);
        add(userIdLbl);

        passwordLbl = new JLabel("Password");
        passwordLbl.setFont(new Font("Arial Black", Font.PLAIN, 20));
        passwordLbl.setBounds(160, 272, 105, 32);
        add(passwordLbl);
    }

    public void addActionListener(ActionListener l)
    {
        logInbtn.addActionListener(l);
    }

    public JTextField getIdField()
    {
        return idField;
    }

    public JPasswordField getPwField()
    {
        return pwField;
    }

    public JButton getLogInbtn()
    {
        return logInbtn;
    }

}
