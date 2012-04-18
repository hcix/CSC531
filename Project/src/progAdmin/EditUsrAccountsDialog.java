package progAdmin;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import progAdmin.itemsToReview.ItemToReview;
import net.miginfocom.swing.MigLayout;
import utilities.ui.SwingHelper;
//-----------------------------------------------------------------------------
/**
 *
 */
public class EditUsrAccountsDialog extends JDialog {
private static final long serialVersionUID = 1L;
	JFrame parent;
	ItemToReview item;
	JTextField titleField;
	JTextArea textArea;
//-----------------------------------------------------------------------------
	public EditUsrAccountsDialog(JFrame parent){
		super(parent, "User Accounts", true);
		
		this.parent=parent;
		
		this.setPreferredSize(new Dimension(700,500));
		this.setSize(new Dimension(700,500));

		//Put the form in the middle of the screen
		this.setLocationRelativeTo(null);

		//Make sure that if the user hits the 'x', the window calls the closeAndCancel method
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				closeAndCancel();
			}
		});

		JPanel mainPanel = new JPanel(new MigLayout());
		
		JButton addNewUserButton = SwingHelper
				.createImageButton("Add", "icons/addUser_48.png");
		//addNewUserButton.addActionListener(this);
		//addNewUserButton.setActionCommand(ADD_USER);

		JButton editUserButton = SwingHelper
				.createImageButton("Edit", "icons/editUser_48.png");
		//editUserButton.addActionListener(this);
		//editUserButton.setActionCommand(EDIT_USER);

		JButton deleteUserButton = SwingHelper
				.createImageButton("Delete", "icons/deleteUser_48.png");
		//deleteUserButton.addActionListener(this);
		//deleteUserButton.setActionCommand(DELETE_USER);
		
		
		String[] buttonLabelText = {
			"<html><h2>Add a new user to the system</h2></html",
			"<html><h2>Edit an existing user</h2></html",
			"<html><h2>Delete an existing user</h2></html"
		};
		
		mainPanel.add(addNewUserButton);
		mainPanel.add(editUserButton);
		mainPanel.add(deleteUserButton);
		
	    Container contentPane = getContentPane();
	    contentPane.add(mainPanel);
	}
//-----------------------------------------------------------------------------
	private void saveAndClose(){
		
		this.dispose();
	}
//-----------------------------------------------------------------------------
	private void closeAndCancel(){

		this.dispose();
	}
//-----------------------------------------------------------------------------
	
	

//=============================================================================
}


