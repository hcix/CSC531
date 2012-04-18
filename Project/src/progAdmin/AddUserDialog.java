package progAdmin;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 
 */
public class AddUserDialog extends JDialog 
{
	private static final long serialVersionUID = 1L;
	JTextField cnumberF, firstNameF, lastNameF, caneidF, emailF, permissionsF;
	JLabel cnumberL, firstNameL, lastNameL, caneidL, emailL, permissionsL;
	Employee newEmployee;
	JFrame parent;
	JPanel mainPanel;
	Dimension dialogDim;
	
	public AddUserDialog(JFrame parent)
	{
		super(parent, "New User", true);
		
		this.parent = parent;
		Toolkit toolkit =  Toolkit.getDefaultToolkit();
		dialogDim = new Dimension(toolkit.getScreenSize().width/2, toolkit.getScreenSize().height/2);
		
		this.setPreferredSize(dialogDim);
		this.setSize(dialogDim);

		//Put the form in the middle of the screen
		this.setLocationRelativeTo(null);
		
		//Make sure that if the user hits the 'x', the window calls the closeAndCancel method
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closeAndCancel();
			}
		});
		
		
		mainPanel = new JPanel();
		cnumberF = new JTextField();
		mainPanel.add(cnumberF, BorderLayout.NORTH);
	}
	private void closeAndCancel(){
		this.dispose();
	}
	

}
