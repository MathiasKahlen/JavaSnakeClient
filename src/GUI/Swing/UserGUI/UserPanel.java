package GUI.Swing.UserGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;

/**
 * @author XA-02
 * UserPanel er det grunlæggende panel, for almindelige brugeres menu. Den indeholder UserNorth, UserCenter og UserSouthPanel.
 */
public class UserPanel extends JPanel
{
	private UserCenterPanel userCenterPanel;
	private UserNorthPanel userNorthPanel;
	private UserSouthPanel userSouthPanel;
	

	public UserPanel()
	{
		setLayout(new BorderLayout());
		
		userCenterPanel = new UserCenterPanel();
		add(userCenterPanel, BorderLayout.CENTER);
		
		userNorthPanel = new UserNorthPanel();
		userNorthPanel.setPreferredSize(new Dimension((int) 800, 75));
		add(userNorthPanel, BorderLayout.NORTH);
		
		userSouthPanel = new UserSouthPanel();
		userSouthPanel.setPreferredSize(new Dimension((int) 800, 75));
		add(userSouthPanel, BorderLayout.SOUTH);
	}

	public UserCenterPanel getUserCenterPanel()
	{
		return userCenterPanel;
	}

	public UserNorthPanel getUserNorthPanel()
	{
		return userNorthPanel;
	}

	public UserSouthPanel getUserSouthPanel()
	{
		return userSouthPanel;
	}
}
