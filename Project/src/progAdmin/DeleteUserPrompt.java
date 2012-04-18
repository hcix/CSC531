package progAdmin;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;

public class DeleteUserPrompt extends JDialog implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int returnVal;

	public DeleteUserPrompt(JFrame parent, String message)
	{
		super(parent, "Delete User?", true);

		
		Toolkit toolkit =  Toolkit.getDefaultToolkit();
		Dimension dialogDim = new Dimension(toolkit.getScreenSize().width/2, toolkit.getScreenSize().height/6);
		
		this.setPreferredSize(dialogDim);
		this.setSize(dialogDim);
		
		this.setLayout(new MigLayout());
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
		//Make sure that if the user hits the 'x', the window calls the closeAndCancel method
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				saveAndCloseDelete(-1);
			}
		});
		
		
		
		JButton okB, cancelB;
		JLabel delPrompt = new JLabel(message);
		okB = new JButton("OK");
		okB.setActionCommand("okdel");
		okB.addActionListener(this);
		cancelB = new JButton("CANCEL");
		cancelB.setActionCommand("canceldel");
		cancelB.addActionListener(this);
		
		add(delPrompt, "align left, wrap");
		add(okB, "align left");
		add(cancelB, "align left");
	}
	public int getResult()
	{
		return returnVal;
	}
	public void saveAndCloseDelete(int r)
	{
		returnVal = r;
		this.dispose();
	}
	public void actionPerformed(ActionEvent e) 
	{
		String command  = e.getActionCommand();
		
		if(command.equals("okdel")) {
			this.saveAndCloseDelete(1);}
		else if(command.equals("canceldel")) {
			this.saveAndCloseDelete(-1);
		}
	}
}
