/**
 * 
 */
package progAdmin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Path;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import program.ResourceManager;
import utilities.FileHelper;
import utilities.SwingHelper;
import utilities.xml.XmlParser;

/**
 * The Administration Panel allows users with Supervisor privileges to perform
 * administrative tasks within the system such as adding, removing, and editing
 * personal, recording video announcements, editing system properties etc. This
 * panel will only be displayed if the current user has Supervisor privileges.
 * 
 */

public class AdminTab extends JPanel implements ActionListener {
	static final String ADD_USER = "add";
	static final String EDIT_USER = "edit user";
	static final String DELETE_USER = "delete";
	static final String LAUNCH_VIDEO = "launch";
	static final String EDIT_SYSTEM = "edit system";
	JFrame parent;
	private static final long serialVersionUID = 1L;
	ResourceManager rm;

	// -----------------------------------------------------------------------------
	public AdminTab(ResourceManager rm) {
		JPanel adminPanel = new JPanel();
		this.parent = rm.getGuiParent();
		this.rm = rm;

		adminPanel.add(createActionButtons());

		this.add(adminPanel);
	}

	// -----------------------------------------------------------------------------
	private JPanel createActionButtons() {
		JPanel buttonsPanel = new JPanel();

		JButton addNewUserButton = SwingHelper
				.createImageButton("icons/addUser_48.png");
		addNewUserButton.addActionListener(this);
		addNewUserButton.setActionCommand(ADD_USER);

		JButton editUserButton = SwingHelper
				.createImageButton("icons/editUser_48.png");
		editUserButton.addActionListener(this);
		editUserButton.setActionCommand(EDIT_USER);

		JButton deleteUserButton = SwingHelper
				.createImageButton("icons/deleteUser_48.png");
		deleteUserButton.addActionListener(this);
		deleteUserButton.setActionCommand(DELETE_USER);

		JButton recordVideoButton = SwingHelper
				.createImageButton("icons/videoCamera.png");
		recordVideoButton.addActionListener(this);
		recordVideoButton.setActionCommand(LAUNCH_VIDEO);

		JButton editSystemButton = SwingHelper
				.createImageButton("icons/edit_48.png");
		editSystemButton.addActionListener(this);
		editSystemButton.setActionCommand(EDIT_SYSTEM);

		JButton addNewItemButton = SwingHelper
				.createImageButton("icons/plusSign_48.png");
		addNewItemButton.addActionListener(this);
		addNewItemButton.setActionCommand(EDIT_SYSTEM);

		JButton[] buttonArray = { addNewUserButton, editUserButton,
				deleteUserButton, addNewItemButton, recordVideoButton,
				editSystemButton };

		String[] buttonLabelText = {
				"<html><h2>Add a new user to the system</h2></html",
				"<html><h2>Edit an existing user</h2></html",
				"<html><h2>Delete an existing user</h2></html",
				"<html><h2>Set a new item to be reviewed</h2></html",
				"<html><h2>Load a video announcement</h2></html",
				"<html><h2>Edit system settings</h2></html" };

		addNewItemButton.addActionListener(new ActionListener() {
			AddItemDialog itemDialog = new AddItemDialog(rm.getGuiParent());

			public void actionPerformed(ActionEvent e) {
				itemDialog.setVisible(true);
				itemDialog.setModal(true);
			}
		});

		SwingHelper.addVerticalLabeledButtons(buttonsPanel, buttonArray,
				buttonLabelText);

		SwingHelper.addLineBorder(buttonsPanel);

		return buttonsPanel;
	}

	// -----------------------------------------------------------------------------
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == ADD_USER) {

		} else if (e.getActionCommand() == EDIT_USER) {

		} else if (e.getActionCommand() == DELETE_USER) {

		} else if (e.getActionCommand() == LAUNCH_VIDEO) {
			chooseAndAddVideo();
		} else if (e.getActionCommand() == EDIT_SYSTEM) {

		}
	}

	// -----------------------------------------------------------------------------
	public void chooseAndAddVideo() {
		// show choose photo dialog
		final JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(parent);

		// if a photo was selected, add it to BOLO and load into photo area
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			// copy the chosen photo into the program's 'Photos' directory
			File file = fc.getSelectedFile();
			Path videoPath = FileHelper.copyVideoAnnoun(file);
			// load the image into photo area
			if (videoPath == null) {
				System.out.println("Video already there");
			}
			// set property then write to xml
			System.out.println(" in admin tab " + videoPath.toString());
			System.setProperty("UMPD.latestVideo", videoPath.toString());
			XmlParser.setSystemProperty("UMPD.latestVideo",
					videoPath.toString());

			// put this after chooser has been closed
			JOptionPane.showMessageDialog(parent, "Video has been loaded "
					+ "to the home page!");

		}

	}
	// -----------------------------------------------------------------------------
}
