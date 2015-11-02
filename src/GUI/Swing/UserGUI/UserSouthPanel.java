package GUI.Swing.UserGUI;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * @author XA-02
 * UserSouthPanel vises i bunden af brugerens skærm. Den indeholder informationer om balance og vekselkurs. 
 */
public class UserSouthPanel extends JPanel
{
	private JLabel currentBalanceLbl;
	private JLabel currentRateLbl;

	public UserSouthPanel()
	{
		setLayout(null);
		setBackground(Color.decode("#3F5992"));
		setSize(800, 75);
		
		currentBalanceLbl = new JLabel("<html>Available balance:<br>BTC<br>DKK</html>");
		currentBalanceLbl.setVerticalAlignment(SwingConstants.TOP);
		currentBalanceLbl.setBounds(10, 11, 202, 58);
		add(currentBalanceLbl);
		
		currentRateLbl = new JLabel("Current exchangerate: ");
		currentRateLbl.setVerticalAlignment(SwingConstants.TOP);
		currentRateLbl.setBounds(222, 11, 202, 48);
		add(currentRateLbl);
	}

	public JLabel getCurrentBalanceLbl()
	{
		return currentBalanceLbl;
	}

	public JLabel getCurrentRateLbl()
	{
		return currentRateLbl;
	}
}
