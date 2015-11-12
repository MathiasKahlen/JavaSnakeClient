package Controller.oldstuff;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import SDK.Api;

/**
 * @author XA-02
 * Denner klasse indeholder actionlisteners til almindelige brugeres ui
 */
public class UserController implements ActionListener
{
	SnakeApp snakeApp;
	Api api = new Api();

	/**
	 * Constructor
	 * @param snakeApp
	 */
	public UserController(SnakeApp snakeApp)
	{
		this.snakeApp = snakeApp;
	}

	//Alle users actioncommands
	public void actionPerformed(ActionEvent l)
	{
		try
		{
			switch (l.getActionCommand())
			{

			//North panel action listeners
			case "DepositMenu":
				clearAllTextFields();

				break;
			case "WithdrawMenu":
				clearAllTextFields();

				break;
			case "TransferMenu":
				clearAllTextFields();

				break;
			case "Logout":
				clearAllTextFields();
				snakeApp.logout();
				break;

			//Center panel action listeners
			case "Deposit":

				break;
			case "Withdraw":

				break;
			case "Transfer":

				break;
			}
		} catch (Exception e)
		{

		}	
	}

	/**
	 * Sletter tekst i alle textfields i users ui
	 */
	public void clearAllTextFields()
	{
//		snakeApp.getFrame().getUserPanel().getUserCenterPanel().getDepositPanel().getDepositTf().setText("");
//		snakeApp.getFrame().getUserPanel().getUserCenterPanel().getWithdrawPanel().getWithdrawTf().setText("");
//		snakeApp.getFrame().getUserPanel().getUserCenterPanel().getTransferPanel().getReceiverTf().setText("");
//		snakeApp.getFrame().getUserPanel().getUserCenterPanel().getTransferPanel().getAmountTf().setText("");
	}

}
