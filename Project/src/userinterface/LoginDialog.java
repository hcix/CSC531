/**
 * Login Screen GUI.
 * Upon opening the program, user is presented with the dialog made in this class.
 * Login button sends the login info to the UM web portal for verification. If
 * a 'login successful' result is returned, this dialog closes. If 'login fail' 
 * result is returned, this dialog stays open and text appears at the top stating 
 * that the login has failed and prompts the user to try again.
 */
package userinterface;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import utilities.SwingHelper;

//-----------------------------------------------------------------------------
public class LoginDialog extends JDialog implements ActionListener {
private static final long serialVersionUID = 1L;
	private boolean loginSuccessful=false;
	private static final String LOGIN = "login";
	private static final String CANCEL = "cancel";
	private static final String HELP = "help";
	JLabel retryLabel;
//-----------------------------------------------------------------------------	
	public LoginDialog(final JFrame frame){
		super(frame, "UMPD Login", true);
		
		//Set the size of the form
		this.setPreferredSize(SwingHelper.LOGIN_DIALOG_DIMENSION);
		this.setSize(SwingHelper.LOGIN_DIALOG_DIMENSION);
				
		JPanel dialogPanel = new JPanel(new MigLayout("nogrid, fillx"));
		
		
		//Put the form in the middle of the screen
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		//Set up the GUI
		retryLabel = new JLabel();
		dialogPanel.add(retryLabel, "wrap");
		JLabel badgeLabel = SwingHelper.createImageLabel("images/badge.png");
		dialogPanel.add(badgeLabel);
		JPanel inputPanel = createInputPanel();
		dialogPanel.add(inputPanel, "wrap");
		JPanel buttonsPanel = createButtonsPanel();
		dialogPanel.add(buttonsPanel, "align center");
		
		//Add the dialog to the screen
		Container contentPane = getContentPane();
		contentPane.add(dialogPanel);
	}
//-----------------------------------------------------------------------------
	public JPanel createInputPanel(){
		JPanel inputPanel = new JPanel(new MigLayout());
		
		SwingHelper.addLabeledTextField(inputPanel, "Cane ID: ", 
				SwingHelper.DEFAULT_TEXT_FIELD_LENGTH, true);
		
		SwingHelper.addLabeledTextField(inputPanel, "Password: ", 
				SwingHelper.DEFAULT_TEXT_FIELD_LENGTH, true);
		
		return inputPanel;
	}
//-----------------------------------------------------------------------------
	public JPanel createButtonsPanel(){
		JPanel buttonsPanel = new JPanel(new MigLayout("fillx"));
		
	    // Add login button
	    JButton loginButton = new JButton("Login");
	    loginButton.setActionCommand(LOGIN);
	    loginButton.addActionListener(this);
	    
		//Add cancel button
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand(CANCEL);
		cancelButton.addActionListener(this);

		//Add help button
		JButton helpButton = new JButton("Help");
		helpButton.setActionCommand(HELP);
		helpButton.addActionListener(this);
		
	    buttonsPanel.add(loginButton);
	    buttonsPanel.add(cancelButton);
	    buttonsPanel.add(helpButton);
	    
		return buttonsPanel;
	}
//-----------------------------------------------------------------------------
	@Override
	public void actionPerformed(ActionEvent ev) {
		
		if(ev.getActionCommand()==LOGIN){
			//check user name and password
			loginSuccessful = authenicateUser();
			if(loginSuccessful){
				//close the dialog
				setVisible(false);
				//play the login sound
				//TODO: find and play login sound
			} else {
				//display retry login text
				displayRetryLabel();
			}
		} else if(ev.getActionCommand()==CANCEL){
			//close the dialog
			loginSuccessful=false;
			setVisible(false);
		} else if(ev.getActionCommand()==HELP){
			/*TODO: display help info telling user to enter their caneID and password
			and displaying the UM site they can go to to reset it if they forgot it*/
			
		}
		
	}
//-----------------------------------------------------------------------------
	  public boolean authenicateUser() {
	/* 
	 * TODO Code to check user name and password against UM's web portal
	 * will return true if successful, false if unsuccessful
	 * Currently, just returns true all the time.
	 * 
	*/
		  return true;
	  }
//-----------------------------------------------------------------------------
	  public boolean isSuccessful() {
		  return loginSuccessful;
	  }
//-----------------------------------------------------------------------------
	  public void displayRetryLabel() {
		  retryLabel.setText("<html><b><font color=#ff0000>You entered incorrect credentials.<br>" +
		  		"Please try again.</font></b></html>");

  }
//-----------------------------------------------------------------------------
}
