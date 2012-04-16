package shiftCdrTab;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import net.miginfocom.swing.MigLayout;
import progAdmin.itemsToReview.ItemRenderer;
import progAdmin.itemsToReview.ItemToReview;
import progAdmin.itemsToReview.ItemsListModel;
import program.ResourceManager;
import shiftCdrTab.reports.ReportsPanel;
import utilities.DatabaseHelper;
import utilities.ui.SwingHelper;
import utilities.xml.XmlParser;

//-----------------------------------------------------------------------------
public class ShiftCdrTab extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	static final String ADD = "add";
	static final String DELETE = "delete";
	static final String SUBMIT = "submit";
	static final String EDIT = "edit";
	static final String LAUNCH = "launch";
	int shiftTime;
	static int mostRecentShift;
	DatabaseHelper dbHelper = new DatabaseHelper();
	JTable table, editTable;
	JFrame parent;
	ResourceManager rm;
	DefaultTableModel tableModel;
	final static int GAP = 10;
//-----------------------------------------------------------------------------
	public ShiftCdrTab(ResourceManager rm) {
		this.setLayout(new BorderLayout());
		this.rm = rm;
		getShiftTime(rm);
		parent = rm.getGuiParent();
		JScrollPane itemsScroller = new ItemsSidePanel(rm);
		
		// Create Shift CDR tabbed display area
		JTabbedPane tabbedPane = new JTabbedPane();
		// Add roll call table
		JPanel rollCallTab = new JPanel(new MigLayout());
		tabbedPane.addTab("Roll Call", rollCallTab);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
		// Add reports tab
		JPanel summaryReportsTab = new ReportsPanel(rm);
		tabbedPane.addTab("Shift Commander Summary Reports", summaryReportsTab);
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

		// Create roll call table passing in the list of names
		JPanel tablePanel = makeTablePanel(rm.getRollCall());

		// Create button panel
		JPanel buttonPanel = new JPanel(new FlowLayout());
		JButton addButton = SwingHelper.createImageButton("Add Officer",
				"icons/plusSign_48.png");
		addButton.setActionCommand(ADD);
		addButton.addActionListener(this);
		JButton deleteButton = SwingHelper.createImageButton("Remove Officer",
				"icons/delete_48.png");
		deleteButton.setActionCommand(DELETE);
		deleteButton.addActionListener(this);
		JButton submitButton = SwingHelper.createImageButton(
				"Submit Roll Call", "icons/save_48.png");
		submitButton.addActionListener(this);
		submitButton.setActionCommand(SUBMIT);
		JButton editButton = SwingHelper.createImageButton(
				"Edit Last Roll Call", "icons/edit_48.png");
		editButton.addActionListener(this);
		editButton.setActionCommand(EDIT);
		JButton launchButton = SwingHelper.createImageButton(
				"Launch Scheduler", "icons/launcher_48.png");
		launchButton.addActionListener(this);
		launchButton.setActionCommand(LAUNCH);
		buttonPanel.add(addButton);
		buttonPanel.add(deleteButton);
		buttonPanel.add(submitButton);
		buttonPanel.add(editButton);
		buttonPanel.add(launchButton);

		// create a label
		Date date = Calendar.getInstance().getTime();
		SimpleDateFormat format = new SimpleDateFormat("EEEE,MMMM dd ");
		JLabel shiftLabel = new JLabel("Shift for " + format.format(date)
				+ " at " + rm.shiftTimeAsString(shiftTime) + ":00");
		// change the font at some point shiftLabel.setFont();

		// place panes in roll call tab
		rollCallTab.add(shiftLabel, "dock north");
		rollCallTab.add(tablePanel, "dock north");
		rollCallTab.add(buttonPanel, "dock south");
		this.add(tabbedPane, BorderLayout.CENTER);
		this.add(itemsScroller, BorderLayout.EAST);
	}
//-----------------------------------------------------------------------------
	JPanel makeTablePanel(ArrayList<String> names) {
		JPanel tablePanel = new JPanel();

		// Create initially empty table
		table = new JTable();
		table.setShowGrid(true);
		table.setGridColor(Color.black);
		table.setPreferredScrollableViewportSize(new Dimension(700, 150));
		table.setFillsViewportHeight(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		// Put the table in a scroll pane
		JScrollPane tableScrollPane = new JScrollPane();
		tableScrollPane.setViewportView(table);
		tablePanel.setLayout(new BorderLayout());
		tablePanel.add(tableScrollPane, BorderLayout.CENTER);

		/*
		 * Set the table model to be the one custom created for this table and
		 * passing in the list of names for the shift
		 */
		table.setModel(new RollCallTableModel(names));

		//Resize the columns
		TableColumn col;
		int[] sizes = { 150, 50, 100, 400 };

		for (int i = 0; i < sizes.length; i++) {
			col = table.getColumnModel().getColumn(i);
			col.setPreferredWidth(sizes[i]);
			col.setWidth(sizes[i]);
		}

		/*
		 * TableColumn timeCol = table.getColumnModel().getColumn(2);
		 * timeCol.setCellEditor(new SpinnerEditor());
		 */
		return tablePanel;
	}

//-----------------------------------------------------------------------------
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == ADD) {
			((RollCallTableModel) table.getModel()).addRow();
		} else if (e.getActionCommand() == DELETE) {
			int rowIndex = table.getSelectedRow();
			if (rowIndex >= 0) {
				((RollCallTableModel) table.getModel()).deleteRow(rowIndex);
			}
		} else if (e.getActionCommand() == SUBMIT) {
			submitRollCall();
			JOptionPane.showMessageDialog(parent, "Roll call Sumbitted!");
		} else if (e.getActionCommand() == LAUNCH) {
			launchScheduler();
		} else if (e.getActionCommand() == EDIT) {
		    editLastRollCall();
		}
	}

//-----------------------------------------------------------------------------
	public void submitRollCall() {
		int numOfRows, i, j, nextShift;
		String name, present, timeArrived, comment, shiftDate;
		Date date;
		DateFormat format;
		ArrayList<String> names;

		numOfRows = table.getModel().getRowCount();

		for (i = 0; i < numOfRows; i++) {
			// fill values
			j = 0;
			name = (String) table.getModel().getValueAt(i, j++);
			if ((Boolean) table.getModel().getValueAt(i, j++))
				present = "true";
			else
				present = "false";
			timeArrived = (String) table.getModel().getValueAt(i, j++);
			comment = (String) table.getModel().getValueAt(i, j++);
			Calendar cal = Calendar.getInstance();
			date = cal.getTime();
			format = new SimpleDateFormat("ddMMMyyyy:" + shiftTime + ":00");
			shiftDate = format.format(date);

			// push to person
			try {
				dbHelper.addRollCall(name, present, comment, timeArrived,
						shiftDate);
			} catch (Exception e) {
				// failed to push to person
				System.out.println("Failed to push to person");
				e.printStackTrace();
			}
		}
		// get the next roll call
		if (shiftTime == 6 || shiftTime == 18)
			nextShift = shiftTime + 4;
		else if (shiftTime == 10)
			nextShift = 18;
		else if (shiftTime == 22)
			nextShift = 6;
		else {
			System.out.println("couldn't increment shiftTime:line 226 ShiftCdrTab");
			return;
		}
		try {
			// TODO figure out best functionality for going back and forth
			// shifts
			names = rm.getRollCall(nextShift);
			table.setModel(new RollCallTableModel(names));
		} catch (Exception e) {
			System.out.println("couldn't get next roll call");
		}
		//mostRecentShift = shiftTime;
		rm.setLatestShiftTime(shiftTime);
		//TODO push to xml eventually
	}

//-----------------------------------------------------------------------------
	private void launchScheduler() {
		// launch schedule program
		try {
			// TODO changed to add project, FIX USER DIR!!!!!
			// Runtime.getRuntime().exec(System.getProperty("user.dir")
			// + "/Project/PatrolScheduler/UMPatrolScheduler.exe");//JAR
			Runtime.getRuntime().exec(
					System.getProperty("user.dir")
							+ "/PatrolScheduler/UMPatrolScheduler.exe");//ECLIPSE
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Could not launch Scheduler");
			System.out
					.println("Attempted to launch in directory currentDir/bin");
//TODO Tell usr w/ a dialog that sched prog couldn't be launched
	//JOptionPane.showMessageDialog(parentComponent, message, title, messageType);
		}
	}
	
//-----------------------------------------------------------------------------
	private void editLastRollCall() {
		final JDialog popup = new JDialog(rm.getGuiParent(), "Edit Roll Call");
		final JPanel mainPanel = new JPanel(new MigLayout());
		JToolBar toolbar = new JToolBar("Toolbar", JToolBar.HORIZONTAL);
        ArrayList<String> rollNames = new ArrayList<String>();
        Date date = new Date();
        String mostRecentShiftAsString;
        ArrayList<RollCall> rollCallList = new ArrayList<RollCall>();
        
        //get formatted date, and get rollCall from db
        mostRecentShiftAsString = System.getProperty("UMPD.latestShiftTime");
        
        //check that a rollcall has been submitted
        if (mostRecentShiftAsString.equals("none")){
        	JOptionPane.showMessageDialog(rm.getGuiParent(), "No shift has been submitted yet.");
        	return;
        }
   
        Format format = new SimpleDateFormat("ddMMMyyyy:" + mostRecentShiftAsString + ":00");
        try {
			rollCallList = dbHelper.getRollCallFromDatabase(format.format(date));
		} catch (Exception e1) {
			System.out.println("Could not get roll call from db");
			//e1.printStackTrace();
		}
        
        for (RollCall rollCall : rollCallList) {
        	rollNames.add(rollCall.getName());
        	System.out.println(rollCall.getName());
        }
        // temp get roll call, change later TODO
        //editTable.setModel(new RollCallTableModel(rollNames));
    	JPanel tablePanel = makeTablePanel(rollNames);
        
    	int i = 0;
    	int j;
    	for (RollCall rollCall : rollCallList) {
    		j = 1;
    		//convert to boolean
    		if (rollCall.getPresent().equals("true")) 
    		    table.setValueAt(true, i, j++);
    		else if (rollCall.getPresent().equals("false"))
    			table.setValueAt(false, i, j++);
    		else  {
    			//debug
    			System.out.println("value unkown, set to false");
    			table.setValueAt(false, i, j++);
    		}
       		table.setValueAt(rollCall.getTimeArrived(), i, j++);
    		table.setValueAt(rollCall.getComment(), i++, j++);
    	}
    	
    	JButton finishedButton = SwingHelper.createImageButton("Save and Close", 
    			"icons/save_48.png");
    	finishedButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			    popup.dispose();
			}
    		
    	});

    	//Container contentPane = getContentPane();
    	toolbar.add(finishedButton);
    	mainPanel.add(table, BorderLayout.CENTER);
    	mainPanel.add(toolbar, BorderLayout.NORTH);
    	popup.add(mainPanel);
    	popup.setSize(700, 700); // dynamic sizing??
    	popup.setLocationRelativeTo(null);
		popup.setVisible(true);
	}

// -----------------------------------------------------------------------------
	public JSpinner createtimeSpinner() {
		JSpinner timeSpinner;

		// Set up times
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 1);
		calendar.set(Calendar.MINUTE, 0);
		Date initTime = calendar.getTime();
		calendar.set(Calendar.HOUR_OF_DAY, 24);
		calendar.set(Calendar.MINUTE, 59);
		Date finalTime = calendar.getTime();

		SpinnerModel timeModel = new SpinnerDateModel(initTime, initTime,
				finalTime, Calendar.HOUR);
		timeSpinner = new JSpinner(timeModel);
		timeSpinner.setEditor(new JSpinner.DateEditor(timeSpinner, "hh:mm a"));
		return timeSpinner;
	}
//-----------------------------------------------------------------------------
	public void getShiftTime(ResourceManager rm) {
		shiftTime = rm.getShiftTime();
	}
    public static int getMostRecentShift() {
		return mostRecentShift;
	}
// =============================================================================
private class RollCallTableModel extends AbstractTableModel implements
		TableModelListener {
	private static final long serialVersionUID = 1L;
	private boolean DEBUG = true;
	private String[] columnNames = { "Name", "Present", "Time Arrived",
			"Comment" };

	// JSpinner spinner = createtimeSpinner();

	private Object[][] data = new Object[0][0];
//-----------------------------------------------------------------------------
	public RollCallTableModel(ArrayList<String> names) {

		/*
		 * Run through each name, setting the value of the name and all
		 * others blank, & incrementing as appropriate
		 */
		for (String name : names) {
			addRowWithName(name);
		}
	}

//-----------------------------------------------------------------------------
	public int getColumnCount() {
		return columnNames.length;
	}

//-----------------------------------------------------------------------------
	public int getRowCount() {
		return data.length;
	}

//-----------------------------------------------------------------------------
	public String getColumnName(int col) {
		return columnNames[col];
	}

//-----------------------------------------------------------------------------
	public Object getValueAt(int row, int col) {
		return data[row][col];
	}

//-----------------------------------------------------------------------------
	/*
	 * JTable uses this method to determine the default renderer/ editor for
	 * each cell. If we didn't implement this method, then the 'present'
	 * column would contain text ("true"/"false"), rather than a check box.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

//-----------------------------------------------------------------------------
	/*
	 * Don't need to implement this method unless your table's editable.
	 */
	public boolean isCellEditable(int row, int col) {
		return true;
	}

//-----------------------------------------------------------------------------
	/*
	 * Don't need to implement this method unless your table's data can
	 * change.
	 */
	public void setValueAt(Object value, int row, int col) {
		if (DEBUG) {
			System.out.println("Setting value at " + row + "," + col
					+ " to " + value + " (an instance of "
					+ value.getClass() + ")");
		}

		data[row][col] = value;

		if (col == 1) {
			if (!((Boolean) value)) {
				data[row][2] = "";
			}
		}

		this.fireTableDataChanged();

		if (DEBUG) {
			System.out.println("New value of data:");
			printDebugData();
		}
	}

//-----------------------------------------------------------------------------
	public void addRow() {
		Object[] newRow = { "Officer", new Boolean(false), "", "" };
		int curLength = this.getRowCount();
		Object[][] newData = new Object[curLength + 1][];
		System.arraycopy(data, 0, newData, 0, curLength);
		data = newData;
		data[curLength] = newRow;
		this.fireTableDataChanged();
	}

//-----------------------------------------------------------------------------
	public void addRowWithName(String name) {
		Object[] newRow = { name, new Boolean(false), "", "" };
		int curLength = this.getRowCount();
		Object[][] newData = new Object[curLength + 1][];
		System.arraycopy(data, 0, newData, 0, curLength);
		data = newData;
		data[curLength] = newRow;
		this.fireTableDataChanged();
	}

//-----------------------------------------------------------------------------
	public void deleteRow(int row) {
		int curLength = this.getRowCount();
		Object[][] newData = new Object[curLength - 1][];
		System.arraycopy(data, 0, newData, 0, row);
		System.arraycopy(data, row + 1, newData, row,
				((data.length - 1) - row));
		data = newData;
		this.fireTableDataChanged();
	}

//-----------------------------------------------------------------------------
	public void tableChanged(TableModelEvent e) {
		/*
		 * int row = e.getFirstRow(); int column = e.getColumn(); TableModel
		 * model = (TableModel)e.getSource(); String columnName =
		 * model.getColumnName(column); Object value = model.getValueAt(row,
		 * column);
		 * 
		 * // Do something with the data...
		 */
	}

//-----------------------------------------------------------------------------
	private void printDebugData() {
		int numRows = getRowCount();
		int numCols = getColumnCount();

		for (int i = 0; i < numRows; i++) {
			System.out.print("    row " + i + ":");
			for (int j = 0; j < numCols; j++) {
				System.out.print("  " + data[i][j]);
			}
			System.out.println();
		}
		System.out.println("--------------------------");
	}
}
//=============================================================================
}