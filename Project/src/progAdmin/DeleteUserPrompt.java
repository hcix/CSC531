package progAdmin;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;

public class DeleteUserPrompt extends JDialog implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int returnVal;

	public DeleteUserPrompt(String message)
	{
		this.setTitle("Delete User?");
		this.setLayout(new MigLayout());
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
		Toolkit toolkit =  Toolkit.getDefaultToolkit();
		Dimension dialogDim = new Dimension(toolkit.getScreenSize().width/3, toolkit.getScreenSize().height/6);
		
		this.setPreferredSize(dialogDim);
		this.setSize(dialogDim);
		
		JButton okB, cancelB;
		JLabel delPrompt = new JLabel(message);
		okB = new JButton("OK");
		okB.setActionCommand("okdel");
		cancelB = new JButton("CANCEL");
		cancelB.setActionCommand("canceldel");
		
		add(delPrompt, "align left, wrap");
		add(okB, "align left");
		add(cancelB, "align left");
	}
	public int getResult()
	{
		return returnVal;
	}
	public void actionPerformed(ActionEvent e) 
	{
		String command  = e.getActionCommand();
		
		if(command.equals("okdel")) {returnVal = 0;}
		else if(command.equals("canceldel")) {
			returnVal = -1;
		}
		this.dispose();
	}
}
