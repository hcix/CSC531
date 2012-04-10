/**
 * 
 */
package progAdmin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import program.ResourceManager;
import utilities.SwingHelper;

/**
 * The Administration Panel allows users with Supervisor privileges to 
 * perform administrative tasks within the system such as adding, removing,
 * and editing personal, recording video announcements, editing system 
 * properties etc.
 * This panel will only be displayed if the current user has Supervisor
 * privileges. 
 *
 */
public class AdminTab extends JPanel {
private static final long serialVersionUID = 1L;
	ResourceManager rm;
//-----------------------------------------------------------------------------
	public AdminTab(ResourceManager rm){
		
		JPanel adminPanel = new JPanel();
		this.rm=rm;
		
		adminPanel.add(createActionButtons());
		
		this.add(adminPanel);
	}
//-----------------------------------------------------------------------------
	private JPanel createActionButtons(){
		JPanel buttonsPanel = new JPanel();
		
		JButton addNewUserButton = SwingHelper.createImageButton("icons/addUser_48.png");
		JButton editUserButton = SwingHelper.createImageButton("icons/editUser_48.png");
		JButton deleteUserButton = SwingHelper.createImageButton("icons/deleteUser_48.png");
		JButton addNewItemButton = SwingHelper.createImageButton("icons/addUser_48.png");
		JButton recordVideoButton = SwingHelper.createImageButton("icons/videoCamera.png");
		JButton editStdBOLOfooterButton = SwingHelper.createImageButton("icons/edit_48.png");
		
		JButton[] buttonArray = {addNewUserButton, editUserButton, deleteUserButton,
				addNewItemButton, recordVideoButton, editStdBOLOfooterButton};
		
		String[] buttonLabelText = {"<html><h2>Add a new user to the system</h2></html",
				"<html><h2>Edit an existing user</h2></html", 
				"<html><h2>Delete an existing user</h2></html", 
				"<html><h2>Set a new item to be reviewed</h2></html", 
				"<html><h2>Load a video announcement</h2></html",
				"<html><h2>Edit system settings</h2></html"
		};
		
		addNewItemButton.addActionListener(new ActionListener(){	
			AddItemDialog itemDialog = new AddItemDialog(rm.getGuiParent());
			public void actionPerformed(ActionEvent e){
				itemDialog.setVisible(true);	
				itemDialog.setModal(true);
			}
		});
		
		
		SwingHelper.addVerticalLabeledButtons(buttonsPanel, buttonArray, buttonLabelText);
		
		SwingHelper.addLineBorder(buttonsPanel);
		
		return buttonsPanel;
	}
//-----------------------------------------------------------------------------
}
