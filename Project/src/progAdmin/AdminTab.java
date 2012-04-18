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
import progAdmin.itemsToReview.ManageItemsDialog;
import program.ResourceManager;
import userinterface.HomeTab;
import userinterface.MainInterfaceWindow;
import utilities.FileHelper;
import utilities.ui.SwingHelper;
//-----------------------------------------------------------------------------
/**
 * The <code>AdminTab</code> Panel allows users with Supervisor privileges to perform
 * administrative tasks within the system such as adding, removing, and editing
 * personal, recording video announcements, editing system properties etc. This
 * panel will only be displayed if the current user has Supervisor privileges.
 * 
 */
public class AdminTab extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	JFrame parent;
	ResourceManager rm;
	ManageItemsDialog manageItemsDialog;
	MainInterfaceWindow mainInterface;
//-----------------------------------------------------------------------------
	/**
	 * 
	 * 
	 * @param rm - <code>ResourceManager</code>
	 * @param mainInterface - 
	 */
	public AdminTab(ResourceManager rm, MainInterfaceWindow mainInterface) {
		JPanel adminPanel = new JPanel();
		this.parent = rm.getGuiParent();
		this.rm = rm;
		this.mainInterface=mainInterface;

		adminPanel.add(createActionButtons());

		//create the initially invisible dialog that will manage review items
		manageItemsDialog = new ManageItemsDialog(rm);
		manageItemsDialog.setVisible(false);
		
		this.add(adminPanel);
	}
//-----------------------------------------------------------------------------
	private JPanel createActionButtons() {
		JPanel buttonsPanel = new JPanel();

		JButton editUsrAcctsButton = SwingHelper.createImageButton("icons/group_48.png");
		editUsrAcctsButton.addActionListener(new ActionListener() {
			//Create dialog to edit user accounts
			EditUsrAccountsDialog usrAcctsDialog = new EditUsrAccountsDialog(parent);
			public void actionPerformed(ActionEvent e){
				usrAcctsDialog.setVisible(true);	
			}
		});
		
		JButton manageItemsButton = SwingHelper.createImageButton("icons/notepad_48.png");
		manageItemsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				manageItemsDialog.setVisible(true);	
				manageItemsDialog.setModal(true);
				mainInterface.refreshItemsList();
			}
		});

		JButton uploadVideoButton = SwingHelper.createImageButton("icons/videoCamera.png");
		uploadVideoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				chooseAndAddVideo();
			}
		});

		JButton editSystemButton = SwingHelper
				.createImageButton("icons/gear_48.png");
		editSystemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				//TODO edit syst setting dialog	
			}
		});

		JButton[] buttonArray = {editUsrAcctsButton, manageItemsButton, uploadVideoButton,
				editSystemButton };
		
		String[] buttonLabels = {"<html><h2>Edit User Accounts</html></h2>", 
				"<html><h2>Manage Items to Review</html></h2>",
				"<html><h2>Upload Video Announcement</html></h2>", 
				"<html><h2>Edit System Settings</html></h2>" 
				};
		
		SwingHelper.addVerticalLabeledButtons(buttonsPanel, buttonArray, buttonLabels);

		return buttonsPanel;
	}
//-----------------------------------------------------------------------------
	public void chooseAndAddVideo() {
		// show choose photo dialog
		final JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(parent);

		// if a photo was selected, add it to BOLO and load into photo area
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			// copy the chosen photo into the program's 'Photos' directory
			File file = fc.getSelectedFile();
			Path videoPath = FileHelper.copyVideoAnnoun(file);
			
			//set property then write to xml
			System.out.println("in admin tab " + videoPath.toString());
			System.setProperty("UMPD.latestVideo", videoPath.toString());
			//XmlParser.setSystemProperty("UMPD.latestVideo",
			//		videoPath.toString());
			rm.setLastestVideoName(videoPath.toString());
			//set visability true
            HomeTab.setVideoVisability(true);
			
			// put this after chooser has been closed
			JOptionPane.showMessageDialog(parent, "Video has been loaded "
					+ "to the home page!");

		}

	}
//-----------------------------------------------------------------------------

	public void refreshItemsTable(){
		manageItemsDialog.refreshItemsTable();
	}
//-----------------------------------------------------------------------------
	public void actionPerformed(ActionEvent e) {
	
	}
//-----------------------------------------------------------------------------
}
