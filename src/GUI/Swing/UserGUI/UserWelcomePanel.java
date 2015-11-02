package GUI.Swing.UserGUI;

import java.awt.Color;

import javax.swing.JPanel;

/**
 * @author XA-02
 * UserWelcomePanel er en velkomstskærm, som vises når en bruger logger ind, den er i øjeblikket tom. 
 */
public class UserWelcomePanel extends JPanel
{
	public UserWelcomePanel()
	{
		setLayout(null);
		setBounds(0, 72, 800, 400);
		setBackground(Color.decode("#4967AA"));
	}
}
