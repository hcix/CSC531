package shiftCdrTab;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import net.miginfocom.swing.MigLayout;
import progAdmin.itemsToReview.ItemsSidePanel;
import program.ResourceManager;
import shiftCdrTab.reports.ReportsPanel;
import userinterface.MainInterfaceWindow;
import utilities.DatabaseHelper;
import utilities.SearchHelper;
import utilities.ui.SwingHelper;

//-----------------------------------------------------------------------------
public class ShiftCdrTab extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	static final String ADD = "add";
	static final String DELETE = "delete";
	static final String SUBMIT = "submit";
	static final String EDIT = "edit";
	static final String LAUNCH = "launch";
	static final String NEXT = "next";
	static final String PREV = "prev";
	int shiftTime;
	Calendar currentShiftDate;
	static int mostRecentShift;
	DatabaseHelper dbHelper = new DatabaseHelper();
	JTable table, editTable;
	JFrame parent;
	JLabel shiftLabel;
	JTextField nameField;
	JDialog searchDialog;
	ResourceManager rm;
	DefaultTableModel tableModel;
	ItemsSidePanel itemsScroller;
	MainInterfaceWindow mainInterface;
	final static int GAP = 10;

	// -----------------------------------------------------------------------------
	public ShiftCdrTab(ResourceManager rm, MainInterfaceWindow mainInterface) {
		this.setLayout(new BorderLayout());
		this.rm = rm;
		this.mainInterface = mainInterface;
		getShiftTime(rm);
		parent = rm.getGuiParent();
		itemsScroller = new ItemsSidePanel(rm, mainInterface);
		ArrayList<RollCall> rollCallList = new ArrayList<RollCall>();
		JPanel tablePanel = new JPanel();
		boolean makeNew = false;

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

		currentShiftDate = Calendar.getInstance();
		tablePanel = makeTablePanel(rm.getRollCall());

		// start test

		// Create roll call table passing in the list of names
		// check first if a table is already in the database
		DateFormat format;
		if (shiftTime == 6) 
		    format = new SimpleDateFormat("ddMMMyyyy" +"0" + shiftTime + "00");
		else 
			format = new SimpleDateFormat("ddMMMyyyy" + shiftTime + "00");
		Date date = currentShiftDate.getTime();
		try {
			rollCallList = dbHelper
					.getRollCallFromDatabase(format.format(date));
			if (rollCallList.isEmpty()) {
			}
			// move on
			else {
				// get roll call names
				updateTable(rollCallList);
			}

		} catch (Exception e1) {
			System.out
					.println("Couldn't get rollcall from database- ShiftCdrTab line 257");
			e1.printStackTrace();
		}

		// end test

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

		JButton nextShiftButton = SwingHelper.createImageButton("Next",
				"icons/nextArrow48.png");
		nextShiftButton.setActionCommand(NEXT);
		nextShiftButton.addActionListener(this);

		JButton prevShiftButton = SwingHelper.createImageButton("Previous",
				"icons/prevArrow48.png");
		prevShiftButton.setActionCommand(PREV);
		prevShiftButton.addActionListener(this);

		// Create search button
		JButton searchButton = SwingHelper.createImageButton(
				"Search Roll Call", "icons/search.png");
		searchButton.addActionListener(new ActionListener() {
		    // Search dialog
		    JDialog searchDialog = createSearchDialog(parent);

			public void actionPerformed(ActionEvent e) {
			    searchDialog.setVisible(true);
		    }
		});

		buttonPanel.add(prevShiftButton);
		buttonPanel.add(addButton);
		buttonPanel.add(deleteButton);
		buttonPanel.add(submitButton);
		buttonPanel.add(launchButton);
        buttonPanel.add(searchButton);
		buttonPanel.add(nextShiftButton);

		// create a label
		date = Calendar.getInstance().getTime();
		format = new SimpleDateFormat("EEEE, MMMM dd, YYYY ");
		shiftLabel = new JLabel("Shift for " + format.format(date) + " at "

				+ rm.shiftTimeAsString(shiftTime) + ":00");
        shiftLabel.setFont(new Font("Serif", Font.BOLD, 32));
		// change the font at some point shiftLabel.setFont();

		// place panes in roll call tab
		rollCallTab.add(shiftLabel, "dock north");
		rollCallTab.add(tablePanel, "dock north");
		rollCallTab.add(buttonPanel, "dock south");
		this.add(tabbedPane, BorderLayout.CENTER);
		this.add(itemsScroller, BorderLayout.EAST);
	}

	// -----------------------------------------------------------------------------
	public void refreshItemsList() {
		itemsScroller.updateItemsList();
	}

	// -----------------------------------------------------------------------------
	private void refreshShiftLabel() {
		SimpleDateFormat format = new SimpleDateFormat("EEEE, MMMM dd, YYYY ");
		shiftLabel.setText("Shift for "
				+ format.format(currentShiftDate.getTime()) + " at "
				+ ResourceManager.shiftTimeAsString(shiftTime) + ":00");
		// change the font at some point shiftLabel.setFont();
	}

	// -----------------------------------------------------------------------------
	JPanel makeTablePanel(ArrayList<String> names) {
		JPanel tablePanel = new JPanel();

		// Create initially empty table
		table = new JTable();
		table.setShowGrid(true);
		table.setGridColor(Color.black);
		table.setPreferredScrollableViewportSize(new Dimension(1600, 500));
		table.setFillsViewportHeight(true);
		//table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		// Put the table in a scroll pane
		JScrollPane tableScrollPane = new JScrollPane();
		tableScrollPane.setViewportView(table);
		tablePanel.setLayout(new BorderLayout());
		tablePanel.add(tableScrollPane, BorderLayout.CENTER);

		/*
		 * Set the table model to be the one custom created for this table and
		 * passing in the list of names for the shift
		 */
		Font font = new Font("Serif", Font.PLAIN, 20);
		FontMetrics fm = new FontMetrics(font) {
		};
		table.setModel(new RollCallTableModel(names));
		table.setFont(font);
		table.setRowHeight(fm.getHeight() * 2);

		// Resize the columns
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

	// -----------------------------------------------------------------------------
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
		} else if (e.getActionCommand() == NEXT) {
			getNextRollCall();
		} else if (e.getActionCommand() == PREV) {
			getPrevRollCall();
		}
	}
// -----------------------------------------------------------------------------
	private JDialog createSearchDialog(JFrame parent) {
		// Create the dialog and set the size
				final JDialog searchDialog = new JDialog(parent, "Search Blue Book Database",
						true);
				searchDialog.setPreferredSize(SwingHelper.SEARCH_DIALOG_DIMENSION);
				searchDialog.setSize(SwingHelper.SEARCH_DIALOG_DIMENSION);

				// Put the dialog in the middle of the screen
				searchDialog.setLocationRelativeTo(null);

				// Create the various search fields and add them to the dialog
				JPanel searchPanel = new JPanel();
				searchPanel.setLayout(new MigLayout("align left"));
				SwingHelper.addLineBorder(searchPanel);

				JLabel nameLabel = new JLabel("Name: ");
				nameField = new JTextField(SwingHelper.DEFAULT_TEXT_FIELD_LENGTH);
				//TODO add dates once spinners are working

				JButton searchButton = SwingHelper.createImageButton("Search",
						"icons/search.png");
				searchButton.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent arg0) {
						search();
						searchDialog.dispose();
					}
					
				});
				
				searchPanel.add(nameLabel, "alignx left");
				searchPanel.add(nameField, "alignx left, wrap");			
				SwingHelper.addDateRangePanel(searchPanel);
				searchPanel.add(searchButton, "alignx center, wrap");

				Container contentPane = searchDialog.getContentPane();
				contentPane.add(searchPanel);
				return searchDialog;
	}
// -----------------------------------------------------------------------------
    private void search() {
    	ArrayList<RollCall> searchResults = new ArrayList<RollCall>();
		ArrayList<String>fields = new ArrayList<String>();
		ArrayList<String>parameters = new ArrayList<String>();
		
		//TODO deal with all fields null case (probably pop up another dialog saying such)
	    //fill search terms		
	    if (!nameField.getText().equals("")) {
	        fields.add("name");
	        parameters.add(nameField.getText());
		}			    
	    //TODO deal with dates eventually
		try {
			searchResults = (ArrayList<RollCall>) SearchHelper.search("RollCall", fields, parameters);
			//DEBUG
//			for (BlueBookEntry entry : searchResults) {
//					System.out.println("case number :" + entry.getCaseNum());	
//			}
		} catch (Exception e) {
			System.out.println("Couldn't run search in Roll Call"); 
			e.printStackTrace();
		}
		JDialog searchDialog = new JDialog(parent, "Search Results", true);
		JPanel searchEntriesPanel = createRollCallPanel(searchResults);
		searchDialog.add(searchEntriesPanel, BorderLayout.CENTER);
		searchDialog.setLocationByPlatform(true);
		Toolkit toolkit =  Toolkit.getDefaultToolkit ();
		Dimension dialogDim = new Dimension(toolkit.getScreenSize().width/2, toolkit.getScreenSize().height/2);
		searchDialog.setSize(dialogDim); 
		searchDialog.setVisible(true);
    }
// -----------------------------------------------------------------------------
    private JPanel createRollCallPanel(ArrayList<RollCall> searchResults) {
    	JPanel entriesPanel = new JPanel(new MigLayout("gapx 30, wrap 4"));
		ArrayList<String> rollCallNames = new ArrayList<String>();
		// Date prepDate;

		// TODO: make scrollable!

		//DEBUG
		if (searchResults == null) {
			System.out.println("error with search results in create search entries"); 
		} //end DEBUG

		int listSize = searchResults.size();
		JPanel[] items = new JPanel[listSize];
		Format formatter = new SimpleDateFormat("E, MMM dd, yyyy");
		
		JPanel tablePanel = new JPanel();
		
		// Create initially empty table
		JTable popupTable = new JTable();
		popupTable.setShowGrid(true);
		popupTable.setGridColor(Color.black);
		popupTable.setPreferredScrollableViewportSize(new Dimension(700, 150));
		popupTable.setFillsViewportHeight(true);
		//popupTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		//mess with fonts
		Font font = new Font("Serif", Font.PLAIN, 20);
		FontMetrics fm = new FontMetrics(font) {
		};
		popupTable.setFont(font);
		popupTable.setRowHeight(fm.getHeight() * 2);
		
		// Put the table in a scroll pane
		JScrollPane tableScrollPane = new JScrollPane();
		tableScrollPane.setViewportView(popupTable);
		tablePanel.setLayout(new BorderLayout());
		tablePanel.add(tableScrollPane, BorderLayout.CENTER);

		/*
		 * Set the table model to be the one custom created for this table and
		 * passing in the list of names for the shift
		 */
		for (RollCall rollCall : searchResults) {
			rollCallNames.add(rollCall.getName());
		}
		popupTable.setModel(new RollCallTableModel(rollCallNames));
		
		int i = 0;
		int j;
		for (RollCall rollCall : searchResults) {
			j = 1;
			// convert to boolean
			if (rollCall.getPresent().equals("true"))
				popupTable.setValueAt(true, i, j++);
			else if (rollCall.getPresent().equals("false"))
				popupTable.setValueAt(false, i, j++);
			else {
				// debug
				System.out.println("value unkown, set to false");
				popupTable.setValueAt(false, i, j++);
			}
			popupTable.setValueAt(rollCall.getTimeArrived(), i, j++);
			popupTable.setValueAt(rollCall.getComment(), i++, j++);			
		}

		// Resize the columns
		TableColumn col;
		int[] sizes = { 150, 50, 100, 400 };

		for (i = 0; i < sizes.length; i++) {
			col = popupTable.getColumnModel().getColumn(i);
			col.setPreferredWidth(sizes[i]);
			col.setWidth(sizes[i]);
		}

		entriesPanel.add(tableScrollPane);

		return entriesPanel;
	}

// -----------------------------------------------------------------------------
	private void getPrevRollCall() {

		boolean moveOn = false;
		DateFormat format;
		ArrayList<String> names;
		ArrayList<RollCall> rollCallList;
		// get the next roll call
		if (shiftTime == 10 || shiftTime == 22) {
			shiftTime -= 4;
			//currentShiftDate.set(Calendar.HOUR_OF_DAY, shiftTime);
			currentShiftDate.add(Calendar.HOUR_OF_DAY, -4);
		}
		else if (shiftTime == 18) {
			shiftTime = 10;
			//currentShiftDate.set(Calendar.HOUR_OF_DAY, shiftTime);
			currentShiftDate.add(Calendar.HOUR_OF_DAY, -8);
		}
		else if (shiftTime == 6) {
			shiftTime = 22;
			//currentShiftDate.set(Calendar.HOUR_OF_DAY, shiftTime);
			currentShiftDate.add(Calendar.HOUR_OF_DAY, -8);
			//currentShiftDate.add(Calendar.DAY_OF_MONTH, -1);
		} else {
			System.out
					.println("couldn't increment shiftTime:line 226 ShiftCdrTab");
			return;
		}

		// first see if a roll call exists for this shift
		if (shiftTime == 6) 
		    format = new SimpleDateFormat("ddMMMyyyy" +"0" + shiftTime + "00");
		else 
			format = new SimpleDateFormat("ddMMMyyyy" + shiftTime + "00");
		Date date = currentShiftDate.getTime();
		
		try {
			rollCallList = dbHelper
					.getRollCallFromDatabase(format.format(date));
			if (rollCallList.isEmpty())
				moveOn = true;
			else
				updateTable(rollCallList);
		} catch (Exception e1) {
			System.out
					.println("Couldn't get rollcall from database- ShiftCdrTab line 257");
			e1.printStackTrace();
			moveOn = true;
		}

		if (moveOn) {
			try {
				if (currentShiftDate.getTime().compareTo(
						Calendar.getInstance().getTime()) > 0) {
					names = rm.getRollCall(shiftTime, currentShiftDate);
					table.setModel(new RollCallTableModel(names));
					// table.setModel(new RollCallTableModel());
				} else
					table.setModel(new RollCallTableModel());
			} catch (Exception e) {
				System.out.println("couldn't get next roll call");
			}
			// shiftLabel.setText("No shift found for " +
			// format.format(currentShiftDate.getTime())
			// + " at " + rm.shiftTimeAsString(shiftTime) + ":00");
			// table.setModel(new RollCallTableModel());
		}
		refreshShiftLabel();
	}

	// -----------------------------------------------------------------------------
	private void getNextRollCall() {

		boolean moveOn = false;
		ArrayList<String> names;
		DateFormat format;
		ArrayList<RollCall> rollCallList;
		
		//currentShiftDate.set(Calendar.HOUR, shiftTime);
		System.out.println("ShiftTime at start of next Roll call is : " + shiftTime);
		System.out.println("currentShiftTime at start of next Roll call is : " + currentShiftDate.getTime().toString());
		// get the next roll call
		if (shiftTime == 6 || shiftTime == 18) {
			// nextShift = shiftTime + 4;
			shiftTime += 4;
			//currentShiftDate.set(Calendar.HOUR_OF_DAY, shiftTime);
			currentShiftDate.add(Calendar.HOUR_OF_DAY, 4);
		}
		else if (shiftTime == 10) {
			// nextShift = 18;
			shiftTime = 18;
			//currentShiftDate.set(Calendar.HOUR_OF_DAY, shiftTime);
			currentShiftDate.add(Calendar.HOUR_OF_DAY, 8);
		}
		else if (shiftTime == 22) {
			// nextShift = 6;
			shiftTime = 6;
			//currentShiftDate.set(Calendar.HOUR_OF_DAY, shiftTime);
			currentShiftDate.add(Calendar.HOUR_OF_DAY, 8);
			//currentShiftDate.add(Calendar.DAY_OF_MONTH, 1);
		} else {
			System.out
					.println("couldn't increment shiftTime:line 226 ShiftCdrTab");
			return;
		}
		
		// first see if a roll call exists for this shift
		if (shiftTime == 6) 
		    format = new SimpleDateFormat("ddMMMyyyy" +"0" + shiftTime + "00");
		else 
			format = new SimpleDateFormat("ddMMMyyyy" + shiftTime + "00");
		
		Date date = currentShiftDate.getTime();
		try {
			rollCallList = dbHelper
					.getRollCallFromDatabase(format.format(date));
			if (rollCallList.isEmpty())
				moveOn = true;
			else
				updateTable(rollCallList);
		} catch (Exception e1) {
			System.out
					.println("Couldn't get rollcall from database- ShiftCdrTab line 257");
			// e1.printStackTrace();
			moveOn = true;
		}

		if (moveOn) {
			try {
				System.out.println("current shift date is : " + currentShiftDate.getTime().toString());
				System.out.println("actual date is : " + Calendar.getInstance().getTime().toString());
				if (currentShiftDate.getTime().compareTo(
						Calendar.getInstance().getTime()) > 0) {
					names = rm.getRollCall(shiftTime, currentShiftDate);
					table.setModel(new RollCallTableModel(names));
				} else
					table.setModel(new RollCallTableModel());
			} catch (Exception e) {
				System.out.println("couldn't get next roll call");
			}
		}
		refreshShiftLabel();
	}

	// -----------------------------------------------------------------------------
	public void submitRollCall() {
		int numOfRows, i, j;
		String name, present, timeArrived, comment, shiftDate;
		Date date;
		DateFormat format;
		boolean notThere = false;

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
			date = currentShiftDate.getTime();
			if (shiftTime == 6) 
			    format = new SimpleDateFormat("ddMMMyyyy" +"0" + shiftTime + "00");
			else 
				format = new SimpleDateFormat("ddMMMyyyy" + shiftTime + "00");
			shiftDate = format.format(date);

			//test code
			
			try {
				ArrayList<String> fields = new ArrayList<String>();
				ArrayList<String> parameters = new  ArrayList<String>();
				fields.add("shiftdate");
				fields.add("name");
				parameters.add(shiftDate);
				parameters.add(name);
				//if it throws an exception at the below line, you know
				//it is not in the table yet
			    if (SearchHelper.search("RollCall", fields, parameters).isEmpty())
			        notThere = true;
			    else 
			    	notThere = false;
			} catch (Exception e) {
				notThere = true;
			}
			
			
			
			//end test
			
			
			// push to person
			try {
				if (notThere) {
				dbHelper.addRollCall(name, present, comment, timeArrived,
						shiftDate);
				} else {
					dbHelper.replaceRollCall(name, present, comment, timeArrived, shiftDate);
				}
			} catch (Exception e) {
				// failed to push to person
				System.out.println("Failed to push to person");
				e.printStackTrace();
			}
		}
		getNextRollCall();
		// mostRecentShift = shiftTime;
		rm.setLatestShiftTime(shiftTime);
	}

	// -----------------------------------------------------------------------------
	private void launchScheduler() {
		// launch schedule program
		try {
			 //TODO changed to add project, FIX USER DIR!!!!!
			//Runtime.getRuntime().exec(System.getProperty("user.dir")
			 //+ "/Project/PatrolScheduler/UMPatrolScheduler.exe");//JAR
			Runtime.getRuntime().exec(
					System.getProperty("user.dir")
							+ "/PatrolScheduler/UMPatrolScheduler.exe");// ECLIPSE
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Could not launch Scheduler");
			System.out
					.println("Attempted to launch in directory currentDir/bin");
			// TODO Tell usr w/ a dialog that sched prog couldn't be launched
			// JOptionPane.showMessageDialog(parentComponent, message, title,
			// messageType);
		}
	}

	// -----------------------------------------------------------------------------
	@Deprecated
	private void editLastRollCall() {
		final JDialog popup = new JDialog(rm.getGuiParent(), "Edit Roll Call");
		final JPanel mainPanel = new JPanel(new MigLayout());
		JToolBar toolbar = new JToolBar("Toolbar", SwingConstants.HORIZONTAL);
		ArrayList<String> rollNames = new ArrayList<String>();
		Date date = new Date();
		String mostRecentShiftAsString;
		ArrayList<RollCall> rollCallList = new ArrayList<RollCall>();

		// get formatted date, and get rollCall from db
		mostRecentShiftAsString = System.getProperty("UMPD.latestShiftTime");

		// check that a rollcall has been submitted
		if (mostRecentShiftAsString.equals("none")) {
			JOptionPane.showMessageDialog(rm.getGuiParent(),
					"No shift has been submitted yet.");
			return;
		}

		Format format = new SimpleDateFormat("ddMMMyyyy"
				+ mostRecentShiftAsString + "00");
		try {
			rollCallList = dbHelper
					.getRollCallFromDatabase(format.format(date));
		} catch (Exception e1) {
			System.out.println("Could not get roll call from db");
			// e1.printStackTrace();
		}

		for (RollCall rollCall : rollCallList) {
			rollNames.add(rollCall.getName());
			System.out.println(rollCall.getName());
		}
		// temp get roll call, change later TODO
		// editTable.setModel(new RollCallTableModel(rollNames));
		JPanel tablePanel = makeTablePanel(rollNames);

		int i = 0;
		int j;
		for (RollCall rollCall : rollCallList) {
			j = 1;
			// convert to boolean
			if (rollCall.getPresent().equals("true"))
				table.setValueAt(true, i, j++);
			else if (rollCall.getPresent().equals("false"))
				table.setValueAt(false, i, j++);
			else {
				// debug
				System.out.println("value unkown, set to false");
				table.setValueAt(false, i, j++);
			}
			table.setValueAt(rollCall.getTimeArrived(), i, j++);
			table.setValueAt(rollCall.getComment(), i++, j++);
		}

		JButton finishedButton = SwingHelper.createImageButton(
				"Save and Close", "icons/save_48.png");
		finishedButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				popup.dispose();
			}

		});

		// Container contentPane = getContentPane();
		toolbar.add(finishedButton);
		mainPanel.add(table, BorderLayout.CENTER);
		mainPanel.add(toolbar, BorderLayout.NORTH);
		popup.add(mainPanel);
		popup.setSize(700, 700); // dynamic sizing??
		popup.setLocationRelativeTo(null);
		popup.setVisible(true);
	}

	// -----------------------------------------------------------------------------
	private void updateTable(ArrayList<RollCall> rollCallList) {

		ArrayList<String> rollNames = new ArrayList<String>();

		// get the list of names
		for (RollCall rollCall : rollCallList) {
			rollNames.add(rollCall.getName());
		}

		/*
		 * run this method, adds the appropriate number of rows to the table
		 * should be done differently, will work for now
		 */
		// makeTablePanel(rollNames);
		// add the rest of the fields

		table.setModel(new RollCallTableModel(rollNames));
		int i = 0;
		int j;
		for (RollCall rollCall : rollCallList) {
			j = 1;
			// convert to boolean
			if (rollCall.getPresent().equals("true"))
				table.setValueAt(true, i, j++);
			else if (rollCall.getPresent().equals("false"))
				table.setValueAt(false, i, j++);
			else {
				// debug
				System.out.println("value unkown, set to false");
				table.setValueAt(false, i, j++);
			}
			table.setValueAt(rollCall.getTimeArrived(), i, j++);
			table.setValueAt(rollCall.getComment(), i++, j++);
		}
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

	// -----------------------------------------------------------------------------
	public void getShiftTime(ResourceManager rm) {
		shiftTime = ResourceManager.getShiftTime();
	}

	// -----------------------------------------------------------------------------
	public static int getMostRecentShift() {
		return mostRecentShift;
	}

	private class RollCallTableModel extends AbstractTableModel implements
			TableModelListener {
		private static final long serialVersionUID = 1L;
		private boolean DEBUG = true;
		private String[] columnNames = { "Name", "Present", "Time Arrived",
				"Comment" };

		// JSpinner spinner = createtimeSpinner();

		private Object[][] data = new Object[0][0];

		// -----------------------------------------------------------------------------
		public RollCallTableModel() {

			/*
			 * blank table
			 */
		}

		// -----------------------------------------------------------------------------
		public RollCallTableModel(ArrayList<String> names) {

			/*
			 * Run through each name, setting the value of the name and all
			 * others blank, & incrementing as appropriate
			 */
			for (String name : names) {
				addRowWithName(name);
			}
		}

		// -----------------------------------------------------------------------------
		public int getColumnCount() {
			return columnNames.length;
		}

		// -----------------------------------------------------------------------------
		public int getRowCount() {
			return data.length;
		}

		// -----------------------------------------------------------------------------
		@Override
		public String getColumnName(int col) {
			return columnNames[col];
		}

		// -----------------------------------------------------------------------------
		public Object getValueAt(int row, int col) {
			return data[row][col];
		}

		// -----------------------------------------------------------------------------
		/*
		 * JTable uses this method to determine the default renderer/ editor for
		 * each cell. If we didn't implement this method, then the 'present'
		 * column would contain text ("true"/"false"), rather than a check box.
		 */
		@Override
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Class getColumnClass(int c) {
			return getValueAt(0, c).getClass();
		}

		// -----------------------------------------------------------------------------
		/*
		 * Don't need to implement this method unless your table's editable.
		 */
		@Override
		public boolean isCellEditable(int row, int col) {
			return true;
		}

		// -----------------------------------------------------------------------------
		/*
		 * Don't need to implement this method unless your table's data can
		 * change.
		 */
		@Override
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

		// -----------------------------------------------------------------------------
		public void addRow() {
			Object[] newRow = { "Officer", new Boolean(false), "", "" };
			int curLength = this.getRowCount();
			Object[][] newData = new Object[curLength + 1][];
			System.arraycopy(data, 0, newData, 0, curLength);
			data = newData;
			data[curLength] = newRow;
			this.fireTableDataChanged();
		}

		// -----------------------------------------------------------------------------
		public void addRowWithName(String name) {
			Object[] newRow = { name, new Boolean(false), "", "" };
			int curLength = this.getRowCount();
			Object[][] newData = new Object[curLength + 1][];
			System.arraycopy(data, 0, newData, 0, curLength);
			data = newData;
			data[curLength] = newRow;
			this.fireTableDataChanged();
		}

		// -----------------------------------------------------------------------------
		public void deleteRow(int row) {
			int curLength = this.getRowCount();
			Object[][] newData = new Object[curLength - 1][];
			System.arraycopy(data, 0, newData, 0, row);
			System.arraycopy(data, row + 1, newData, row,
					((data.length - 1) - row));
			data = newData;
			this.fireTableDataChanged();
		}

		// -----------------------------------------------------------------------------
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

		// -----------------------------------------------------------------------------
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
	// =============================================================================
}