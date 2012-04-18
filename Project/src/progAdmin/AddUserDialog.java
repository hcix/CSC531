package progAdmin;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;
import utilities.ui.SwingHelper;

/**
 * 
 */
public class AddUserDialog extends JDialog implements ActionListener
{
	private static final long serialVersionUID = 1L;
	JTextField cnumberF, firstNameF, lastNameF, caneidF, emailF;
	JComboBox<String> permissionsB;
	String [] authLevels = {"Supervisor", "Command", "Officer", "None"};
	JLabel cnumberL, firstNameL, lastNameL, caneidL, emailL, permissionsL;
	Employee employee;
	JFrame parent;
	JPanel mainPanel;
	Dimension dialogDim;
	JButton ok, cancel;
	
	public AddUserDialog(JFrame parent, Employee editEmployee)
	{
		super(parent, "", true);
		
		if(editEmployee == null) {this.setTitle("New User");}
		else {this.setTitle("Edit User");}
		
		this.parent = parent;
		
		Toolkit toolkit =  Toolkit.getDefaultToolkit();
		dialogDim = new Dimension(toolkit.getScreenSize().width/2, toolkit.getScreenSize().height/4);
		
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
		
		MigLayout layout = new MigLayout();
		
		mainPanel = new JPanel(layout);
		createFieldsandLabels();
		doPanelLayout();
		
		if(editEmployee != null)
		{
			populate(editEmployee);
		}
		
		this.add(mainPanel);
	}
	private void createFieldsandLabels()
	{
		cnumberF = new JTextField(SwingHelper.DEFAULT_TEXT_FIELD_LENGTH);
		cnumberF.setEditable(true);
		firstNameF = new JTextField(SwingHelper.DEFAULT_TEXT_FIELD_LENGTH);
		firstNameF.setEditable(true);
		lastNameF = new JTextField(SwingHelper.DEFAULT_TEXT_FIELD_LENGTH);
		lastNameF.setEditable(true);
		caneidF = new JTextField(SwingHelper.DEFAULT_TEXT_FIELD_LENGTH);
		caneidF.setEditable(true);
		emailF = new JTextField(SwingHelper.DEFAULT_TEXT_FIELD_LENGTH);
		emailF.setEditable(true);
		permissionsB = new JComboBox<String>(authLevels);
		
		cnumberL = new JLabel("C Number");
		firstNameL = new JLabel("First Name");
		lastNameL = new JLabel("Last Name");
		caneidL = new JLabel("Cane ID");
		emailL = new JLabel("Miami Email Address");
		permissionsL = new JLabel("Permissions Level");
		
		ok = new JButton("OK");
		ok.addActionListener(this);
		ok.setActionCommand("ok");
		
		cancel = new JButton("CANCEL");
		cancel.addActionListener(this);
		cancel.setActionCommand("cancel");
		
	}
	private void doPanelLayout()
	{
		mainPanel.add(firstNameL, "align left");
		mainPanel.add(firstNameF, "align left");
		
		mainPanel.add(lastNameL, "align left");
		mainPanel.add(lastNameF, "align left, wrap");
		
		mainPanel.add(caneidL, "align left");
		mainPanel.add(caneidF, "align left");
		
		mainPanel.add(emailL, "align left");
		mainPanel.add(emailF, "align left, wrap");
				
		mainPanel.add(cnumberL, "align left");
		mainPanel.add(cnumberF, "align left");
		
		mainPanel.add(permissionsL, "align left");
		mainPanel.add(permissionsB, "align left, wrap");
		
		mainPanel.add(ok, "align left");
		mainPanel.add(cancel, "align left");
	}
	private void saveAndClose()
	{
		employee = new Employee();
		employee.setCaneID(caneidF.getText().trim());
		employee.setCnumber(cnumberF.getText().trim());
		employee.setEmail(emailF.getText().trim());
		employee.setPermissions(permissionsB.getSelectedItem().toString());
		employee.setFirstname(firstNameF.getText().trim());
		employee.setLastname(lastNameF.getText().trim());

		this.dispose();
	}
	public Employee getEmployee()
	{
		return employee;
	}
	private void closeAndCancel(){
		this.dispose();
	}
	private void populate(Employee e)
	{
		firstNameF.setText(e.getFirstname());
		lastNameF.setText(e.getLastname());
		caneidF.setText(e.getCaneID());
		emailF.setText(e.getEmail());
		cnumberF.setText(e.getCnumber());
		permissionsB.setSelectedItem(e.getPermissions());
	}
	public void actionPerformed(ActionEvent e) 
	{
		String command = e.getActionCommand();
		
		if(command.equals("ok"))
		{
			saveAndClose();
		}
		else if(command.equals("cancel"))
		{
			closeAndCancel();
		}
	}
}
