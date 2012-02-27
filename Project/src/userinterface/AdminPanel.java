/**
 * 
 */
package userinterface;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import utilities.ImageHandler;
import utilities.SwingHelper;

/**
 * GUI layout for Administration Panel.
 * The Administration Panel allows ranking officers to do administrative tasks
 * within the system such as adding, removing, and editing personal, recording
 * video announcements, etc.
 *
 */
public class AdminPanel extends JPanel {
//-----------------------------------------------------------------------------
	AdminPanel(){
		JPanel adminPanel = new JPanel();
		
		adminPanel.add(createActionButtons());
		
		this.add(adminPanel);
	}
//-----------------------------------------------------------------------------
	public JPanel createActionButtons(){
		JPanel buttonsPanel = new JPanel();
		
		ImageIcon bigQuestionMark = ImageHandler.createImageIcon("images/unknown.png");
		ImageIcon iconSizedQMark = ImageHandler.getScaledImageIcon(bigQuestionMark, 50, 50);
		
		JButton addNewUserButton = new JButton(iconSizedQMark);
		JButton editUserButton = new JButton(iconSizedQMark);
		JButton recordVideoButton = new JButton(iconSizedQMark);
		
		JButton[] buttonArray = { addNewUserButton, editUserButton, recordVideoButton };
		
		String[] buttonLabelText = {"<html><h2>Add a new user to the system</h2></html",
				"<html><h2>Edit an existing user</h2></html", 
				"<html><h2>Record a video announcement</h2></html"
		};
		
		
		SwingHelper.addVerticalLabeledButtons(buttonsPanel, buttonArray, buttonLabelText);
		
		
		SwingHelper.addLineBorder(buttonsPanel);
		
		return buttonsPanel;
	}
//-----------------------------------------------------------------------------
}
