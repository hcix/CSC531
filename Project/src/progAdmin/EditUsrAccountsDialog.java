package progAdmin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import progAdmin.itemsToReview.ItemToReview;
import net.miginfocom.swing.MigLayout;
import utilities.ui.SwingHelper;
import utilities.xml.XmlParser;
//-----------------------------------------------------------------------------
/**
 *
 */
public class EditUsrAccountsDialog extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;
	JFrame parent;
	ItemToReview item;
	JTextField titleField;
	JTextArea textArea;
	private static final String ADD_USER = "ADD_USER";
	private static final String EDIT_USER = "EDIT_USER";
	private static final String DELETE_USER = "DELETE_USER";
	
	List<Employee> employees;
	JTable table;
	Dimension dialogDim;
//-----------------------------------------------------------------------------
	public EditUsrAccountsDialog(JFrame parent){
		super(parent, "User Accounts", true);
		
		this.parent=parent;
		Toolkit toolkit =  Toolkit.getDefaultToolkit ();
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

		JPanel mainPanel = new JPanel(new MigLayout());
		
		JButton addNewUserButton = SwingHelper
				.createImageButton("Add", "icons/addUser_48.png");
		addNewUserButton.addActionListener(this);
		addNewUserButton.setActionCommand(ADD_USER);

		JButton editUserButton = SwingHelper
				.createImageButton("Edit", "icons/editUser_48.png");
		editUserButton.addActionListener(this);
		editUserButton.setActionCommand(EDIT_USER);

		JButton deleteUserButton = SwingHelper
				.createImageButton("Delete", "icons/deleteUser_48.png");
		deleteUserButton.addActionListener(this);
		deleteUserButton.setActionCommand(DELETE_USER);
		
		
		String[] buttonLabelText = {
			"<html><h2>Add a new user to the system</h2></html",
			"<html><h2>Edit an existing user</h2></html",
			"<html><h2>Delete an existing user</h2></html"
		};
		
		JToolBar toolbar = new JToolBar("Toolbar", SwingConstants.HORIZONTAL);
		toolbar.add(addNewUserButton);
		toolbar.add(deleteUserButton);
		toolbar.add(editUserButton);
		
		toolbar.setFloatable(false);
		
		JSeparator jsep = new JSeparator();
		
		JScrollPane tableScrollPane = new JScrollPane();
		tableScrollPane.add(createTable());
		
		mainPanel.add(toolbar, BorderLayout.NORTH);
		//mainPanel.add(jsep, BorderLayout.CENTER);
		mainPanel.add(tableScrollPane, BorderLayout.SOUTH);

	    Container contentPane = getContentPane();
	    contentPane.add(mainPanel);
	    
	}
	private JTable createTable()
	{
		table = new JTable();
		table.setShowGrid(true);
		table.setGridColor(Color.black);
		table.setPreferredScrollableViewportSize(dialogDim);
	    table.setFillsViewportHeight(true);
	    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		XmlParser xmlp = new XmlParser();
		employees = xmlp.getRoster();
		
		
		for(Employee e: employees)
			System.out.println(e);
		
		return table;
	}
//-----------------------------------------------------------------------------
	private void saveAndClose()
	{
		
		this.dispose();
	}
//-----------------------------------------------------------------------------
	private void closeAndCancel()
	{

		this.dispose();
	}
//-----------------------------------------------------------------------------
	public void actionPerformed(ActionEvent e) 
	{
		String command = e.getActionCommand();
		if(command.equals(ADD_USER))
		{
			AddUserDialog aud = new AddUserDialog();
			aud.setVisible(true);
			aud.setModal(true);
			//refresh items list from ResourceManager
			table.repaint();
		}
		else if(command.equals(EDIT_USER))
		{
			
		}
		else if(command.equals(DELETE_USER))
		{
			int rowIndex = table.getSelectedRow();
			rm.removeItem(rowIndex);
			table.repaint();
			// I ALSO WANT A PROMPT CHECKING IF ITS OK TO DELETE USER
		}
		else {System.err.print("get actioncommand error");} 
	}
}