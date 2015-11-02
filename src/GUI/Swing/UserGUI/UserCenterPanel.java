package GUI.Swing.UserGUI;

import java.awt.CardLayout;

import javax.swing.JPanel;

/**
 * @author XA-02
 * UserCenterPanel har et containerpanel, som bruges til at holde alle underskærmene til almindelige brugere.
 */
public class UserCenterPanel extends JPanel
{
	public static final String USERWELCOME = "USER_WELCOME";
	public static final String INSERT = "INSERT";
	public static final String WITHDRAW = "WITHDRAW";
	public static final String TRANSFER = "TRANSFER";
	
	private CardLayout cl;
	private JPanel containerPanel;
	
	private UserWelcomePanel userWelcomePanel;

	
	
	
	public UserCenterPanel()
	{
		setSize(800,600);
		setLayout(null);
		cl = new CardLayout();
		containerPanel = new JPanel();
		containerPanel.setSize(800, 500);
		containerPanel.setLayout(cl);
		add(containerPanel);
		
		userWelcomePanel = new UserWelcomePanel();
		containerPanel.add(userWelcomePanel, USERWELCOME);

	}

	public void show(String card)
	{
		cl.show(containerPanel, card);
	}

	public UserWelcomePanel getUserWelcomePanel()
	{
		return userWelcomePanel;
	}

}
