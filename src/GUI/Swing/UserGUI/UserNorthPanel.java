package GUI.Swing.UserGUI;

import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * @author XA-02
 * UserNorthPanel indeholder alle menu-knapperne for almindelige brugere. 
 */
public class UserNorthPanel extends JPanel
{
	private JButton logOutBtn;
	private JButton insertMenuBtn;
	private JButton withdrawMenuBtn;
	private JButton transferMenuBtn;
	private JLabel loggedInAsLbl;

	public UserNorthPanel()
	{
		setLayout(null);
		setBackground(Color.decode("#3F5992"));
		setSize(800, 75);
		
		logOutBtn = new JButton("Log Out");
		logOutBtn.setActionCommand("Logout");
		logOutBtn.setBounds(674, 11, 100,50);
		add(logOutBtn);
		
		insertMenuBtn = new JButton("Deposit");
		insertMenuBtn.setActionCommand("DepositMenu");
		insertMenuBtn.setBounds(10, 11, 100,50);
		add(insertMenuBtn);
		
		withdrawMenuBtn = new JButton("Withdraw");
		withdrawMenuBtn.setActionCommand("WithdrawMenu");
		withdrawMenuBtn.setBounds(120, 11, 100,50);
		add(withdrawMenuBtn);
		
		transferMenuBtn = new JButton("Transfer");
		transferMenuBtn.setActionCommand("TransferMenu");
		transferMenuBtn.setBounds(230, 11, 100,50);
		add(transferMenuBtn);
		
		loggedInAsLbl = new JLabel();
		loggedInAsLbl.setHorizontalAlignment(SwingConstants.CENTER);
		loggedInAsLbl.setText("<html>Logged in as: <br> userID");
		loggedInAsLbl.setBounds(450, 11, 214,50);
		add(loggedInAsLbl);
	}
	
	public void addActionListener(ActionListener l)
	{
		logOutBtn.addActionListener(l);
		insertMenuBtn.addActionListener(l);
		withdrawMenuBtn.addActionListener(l);
		transferMenuBtn.addActionListener(l);
	}
	
	public JLabel getLoggedInAsLbl()
	{
		return loggedInAsLbl;
	}
}
