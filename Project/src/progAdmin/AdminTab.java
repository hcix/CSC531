/**
 * 
 */
package progAdmin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import boloTab.BOLOform;
import program.ManageItemsDialog;
import program.ResourceManager;
import reviewItems.ItemRenderer;
import reviewItems.ItemToReview;
import reviewItems.ItemsListModel;
import userinterface.HomeTab;
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
	private static final long serialVersionUID = 1L;
	JFrame parent;
	ResourceManager rm;
//-----------------------------------------------------------------------------
	public AdminTab(ResourceManager rm) {
		JPanel adminPanel = new JPanel();
		this.parent = rm.getGuiParent();
		this.rm = rm;

		adminPanel.add(createActionButtons());

		this.add(adminPanel);
	}
//-----------------------------------------------------------------------------
	private JPanel createActionButtons() {
		JPanel buttonsPanel = new JPanel();

		JButton editUsrAcctsButton = SwingHelper.createImageButton(
				"Edit User Accounts", "icons/group_48.png");
		editUsrAcctsButton.addActionListener(new ActionListener() {
			//create editUsersDialog
			EditUsrAccountsDialog usrAcctsDialog = new EditUsrAccountsDialog(parent);
			public void actionPerformed(ActionEvent e){
				usrAcctsDialog.setVisible(true);	
			}
		});
		
		JButton manageItemsButton = SwingHelper.createImageButton(
				"Manage Items to Review", "icons/notepad_48.png");
		manageItemsButton.addActionListener(new ActionListener() {
			//create manageItemsDialog
			ManageItemsDialog manageItemsDialog = new ManageItemsDialog(parent);
			public void actionPerformed(ActionEvent e){
				manageItemsDialog.setVisible(true);	
			}
		});

		JButton uploadVideoButton = SwingHelper
				.createImageButton("Upload Video", "icons/videoCamera.png");
		uploadVideoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				chooseAndAddVideo();
			}
		});

		JButton editSystemButton = SwingHelper
				.createImageButton("Edit System Settings", "icons/gear_48.png");
		editSystemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				//TODO edit syst setting dialog	
			}
		});

		buttonsPanel.add(editUsrAcctsButton);
		buttonsPanel.add(manageItemsButton);
		buttonsPanel.add(uploadVideoButton);
		buttonsPanel.add(editSystemButton); 
		
		
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
			
			// set property then write to xml
			System.out.println("in admin tab " + videoPath.toString());
			System.setProperty("UMPD.latestVideo", videoPath.toString());
			XmlParser.setSystemProperty("UMPD.latestVideo",
					videoPath.toString());
			
			// set visability true
            HomeTab.setVideoVisability(true);
			
			// put this after chooser has been closed
			JOptionPane.showMessageDialog(parent, "Video has been loaded "
					+ "to the home page!");

		}

	}
//-----------------------------------------------------------------------------
	@Override
	public void actionPerformed(ActionEvent e) {
	
	}
//-----------------------------------------------------------------------------
}
