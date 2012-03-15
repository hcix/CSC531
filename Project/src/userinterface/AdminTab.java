/**
 * 
 */
package userinterface;

import javax.swing.JButton;
import javax.swing.JPanel;
import utilities.SwingHelper;

/**
 * GUI layout for Administration Panel.
 * The Administration Panel allows ranking officers to do administrative tasks
 * within the system such as adding, removing, and editing personal, recording
 * video announcements, editing system properties etc.
 *
 */
public class AdminTab extends JPanel {
private static final long serialVersionUID = 1L;
//-----------------------------------------------------------------------------
	public AdminTab(){
		JPanel adminPanel = new JPanel();
		
		adminPanel.add(createActionButtons());
		
		this.add(adminPanel);
	}
//-----------------------------------------------------------------------------
	private JPanel createActionButtons(){
		JPanel buttonsPanel = new JPanel();
		
		JButton addNewUserButton = SwingHelper.createImageButton("icons/addUser_48.png");
		JButton editUserButton = SwingHelper.createImageButton("icons/editUser_48.png");
		JButton deleteUserButton = SwingHelper.createImageButton("icons/deleteUser_48.png");
		JButton recordVideoButton = SwingHelper.createImageButton("icons/videoCamera.png");
		JButton editStdBOLOfooterButton = SwingHelper.createImageButton("icons/edit_48.png");
		
		JButton[] buttonArray = {addNewUserButton, editUserButton, deleteUserButton,
				recordVideoButton, editStdBOLOfooterButton};
		
		String[] buttonLabelText = {"<html><h2>Add a new user to the system</h2></html",
				"<html><h2>Edit an existing user</h2></html", 
				"<html><h2>Delete an existing user</h2></html", 
				"<html><h2>Load a video announcement</h2></html",
				"<html><h2>Edit system settings</h2></html"
		};
		
		
		SwingHelper.addVerticalLabeledButtons(buttonsPanel, buttonArray, buttonLabelText);
		
		
		SwingHelper.addLineBorder(buttonsPanel);
		
		return buttonsPanel;
	}
//-----------------------------------------------------------------------------
}
