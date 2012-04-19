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
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import progAdmin.itemsToReview.ItemToReview;
import net.miginfocom.swing.MigLayout;
import progAdmin.itemsToReview.ItemToReview;
import utilities.ui.SwingHelper;
import utilities.xml.XmlParser;
//-----------------------------------------------------------------------------
/**
 * JDOC
 * 
 * 
 * 
 * 
 * TODO: Lots of debugging in this class here. 
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
	
	JTable table;
	Dimension dialogDim;
	ArrayList<Employee> employeeList = new ArrayList<Employee>();
//-----------------------------------------------------------------------------
	/**
	 * JDOC
	 */
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
		
		//Add User button
		JButton addNewUserButton = SwingHelper
				.createImageButton("Add", "icons/addUser_48.png");
		addNewUserButton.addActionListener(this);
		addNewUserButton.setActionCommand(ADD_USER);

		//Edit User button
		JButton editUserButton = SwingHelper
				.createImageButton("Edit", "icons/editUser_48.png");
		editUserButton.addActionListener(this);
		editUserButton.setActionCommand(EDIT_USER);

		//Delete User button
		JButton deleteUserButton = SwingHelper
				.createImageButton("Delete", "icons/deleteUser_48.png");
		deleteUserButton.addActionListener(this);
		deleteUserButton.setActionCommand(DELETE_USER);
		
		
		JToolBar toolbar = new JToolBar("Toolbar", SwingConstants.HORIZONTAL);
		toolbar.add(addNewUserButton);
		toolbar.add(deleteUserButton);
		toolbar.add(editUserButton);	
		toolbar.setFloatable(false);
		
		employeeList = XmlParser.getRoster();
		
		EmployeeTableModel tableModel = new EmployeeTableModel(employeeList);

		
		table = createTable(tableModel);	
		JScrollPane tableScrollPane = new JScrollPane(table);
		mainPanel.add(tableScrollPane, BorderLayout.CENTER);

		mainPanel.add(toolbar, BorderLayout.NORTH);
		
	    Container contentPane = getContentPane();
	    contentPane.add(mainPanel);
	}
//-----------------------------------------------------------------------------
	private JTable createTable(EmployeeTableModel tableModel)
	{
		table = new JTable(tableModel);
		table.setShowGrid(true);
		table.setGridColor(Color.black);
		table.setPreferredScrollableViewportSize(dialogDim);
	    table.setFillsViewportHeight(true);
	    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

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
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if(command.equals(ADD_USER))
		{
			// add user dialog -- null because no employee is being edited
			AddUserDialog aud = new AddUserDialog(parent, null);
			aud.setVisible(true);
			aud.setModal(true);
			
			if(aud.checkCanceled() != true)
			{
				Employee emp = aud.getEmployee();
				employeeList.add(emp);
				try {
					XmlParser.saveRoster(employeeList);
				} catch (Exception ee) {
					ee.printStackTrace();
				}
				//refresh table
				table.repaint();
			}	
		}
		else if(command.equals(EDIT_USER))
		{
			int selected = table.getSelectedRow();
			Employee emp = employeeList.get(selected);
			AddUserDialog aud = new AddUserDialog(parent, emp);
			aud.setVisible(true);
			aud.setModal(true);
			if(aud.checkCanceled() != true)
			{
				int empLoc = employeeList.indexOf(emp);
				employeeList.remove(emp);
				employeeList.add(empLoc, aud.getEmployee());
				try {
					XmlParser.saveRoster(employeeList);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				table.repaint();
			}
		}
else if(command.equals(DELETE_USER))
		{
			int selected = table.getSelectedRow();
			Employee emp = employeeList.get(selected);
			
			int confirm = JOptionPane.showConfirmDialog(parent, ("Are you sure you want to" +
					" delete user" + emp.getLastname() + "?"), ("Delete User?"), 
					JOptionPane.YES_NO_OPTION);
			
			//if delete was confirmed, perform delete
			if(confirm == JOptionPane.YES_OPTION){ // ok to delete
				employeeList.remove(emp);
				try {
					XmlParser.saveRoster(employeeList);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			table.repaint();
		}
		this.getContentPane().revalidate();
	}
//=============================================================================
	/** INNER CLASS **/
	private class EmployeeTableModel extends AbstractTableModel implements TableModelListener 
	{
		private static final long serialVersionUID = 1L;
		public String [] columnNames =  {"cnumber", "firstname", "lastname",
				"caneid", "email", "permissions"};	
		
	//-----------------------------------------------------------------------------
		EmployeeTableModel(ArrayList<Employee> employeeList){
		}
	//-----------------------------------------------------------------------------
		public void tableChanged(TableModelEvent e) {
			//don't need to implement this bc table is only editable thru dialogs
		}
	 //-----------------------------------------------------------------------------
		public int getColumnCount() {
			return columnNames.length;
		}
	//-----------------------------------------------------------------------------
		public String getColumnName(int col) {
	        return columnNames[col];
	    }
	//-----------------------------------------------------------------------------
		public int getRowCount() {
			return employeeList.size();
		}
	//-----------------------------------------------------------------------------
		public Object getValueAt(int row, int col) {
			if(col==0){//cnumber
				return employeeList.get(row).getCnumber();
			}else if(col==1){//firstname
				return employeeList.get(row).getFirstname();
			}else if(col==2){//lastname
				return employeeList.get(row).getLastname();
			}else if(col==3){//caneid
				return employeeList.get(row).getCaneID();
			}else if(col==4){//email
				return employeeList.get(row).getEmail();
			}else if(col==5){//permissions
				return employeeList.get(row).getPermissions();
			}
			//if col is out of range, return null
			return null;
		}
	//-----------------------------------------------------------------------------
	    /*
	     * JTable uses this method to determine the default renderer/
	     * editor for each cell. In this case, all cells are strings so
	     * this is simple.
	     */
		public Class getColumnClass(int c) {
	        return getValueAt(0, c).getClass();
	    }
	//-----------------------------------------------------------------------------
	}
//=============================================================================
}
