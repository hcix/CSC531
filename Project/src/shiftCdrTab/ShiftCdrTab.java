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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import net.miginfocom.swing.MigLayout;
import program.ResourceManager;
import reviewItems.ItemRenderer;
import reviewItems.ItemToReview;
import reviewItems.ItemsListModel;
import utilities.DatabaseHelper;
import utilities.SwingHelper;
import utilities.xml.XmlParser;
//-----------------------------------------------------------------------------
public class ShiftCdrTab extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	static final String ADD="add";
	static final String DELETE="delete";
	static final String SUBMIT="submit";
	static final String LAUNCH="launch";
	int shiftTime;
	JTable table;
	JFrame parent;
	DefaultTableModel tableModel;
	final static int GAP = 10;
//-----------------------------------------------------------------------------
	public ShiftCdrTab(ResourceManager rm) {
		this.setLayout(new BorderLayout());
		getShiftTime(rm);
		parent = rm.getParent();

		JPanel sidePanel = new JPanel(new MigLayout());
		String label = "<html><h3>Items to Review</h3></html>";
		/*JPanel scrollPanel = new JPanel(new MigLayout("ins 0, gap 0"));
		
		JScrollPane scroller = new JScrollPane(scrollPanel,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPanel.setLayout(new MigLayout("flowy"));
		*/
		JLabel titleLabel = new JLabel(label,JLabel.CENTER);
		
		sidePanel.setPreferredSize(new Dimension(300,625));		
		sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));		
		
		sidePanel.add(titleLabel, "alignx center, wrap");
		//sidePanel.add(scroller); 

		addItemsToReview(sidePanel);
		
		//Create Shift CDR tabbed display area
		JTabbedPane tabbedPane = new JTabbedPane();
		//Add roll call table
		JPanel rollCallTab = new JPanel(new MigLayout());
		tabbedPane.addTab("Roll Call", rollCallTab);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
	    //Add reports tab
		JPanel summaryReportsTab = new ReportsPanel(rm);
		tabbedPane.addTab("Shift Commander Summary Reports", summaryReportsTab);
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
		
		//Create roll call table passing in the list of names
		JPanel tablePanel = makeTablePanel(rm.getRollCall());
		
		//Create button panel
		JPanel buttonPanel = new JPanel(new FlowLayout());
		JButton addButton = SwingHelper.createImageButton("Add Officer", 
				"icons/plusSign_48.png");
		addButton.setActionCommand(ADD);
		addButton.addActionListener(this);
		JButton deleteButton = SwingHelper.createImageButton("Remove Officer", 
				"icons/delete_48.png");
		deleteButton.setActionCommand(DELETE);
		deleteButton.addActionListener(this);
		JButton submitButton = SwingHelper.createImageButton("Submit Roll Call", 
				"icons/save_48.png");
		submitButton.addActionListener(this);
		submitButton.setActionCommand(SUBMIT);
		JButton launchButton = SwingHelper.createImageButton("Launch Scheduler", 
				"icons/launcher_48.png");
		launchButton.addActionListener(this);
		launchButton.setActionCommand(LAUNCH);
		buttonPanel.add(addButton);
		buttonPanel.add(deleteButton);
		buttonPanel.add(submitButton);
		buttonPanel.add(launchButton);
		
		//create a label
		Date date = Calendar.getInstance().getTime();
		SimpleDateFormat format = new SimpleDateFormat("EEEE,MMMM dd ");
		JLabel shiftLabel = new JLabel("Shift for " + format.format(date) 
				+ " at "+ ResourceManager.shiftTimeAsString(shiftTime) + ":00");
		//change the font at some point shiftLabel.setFont();
		
		// place panes in roll call tab
		rollCallTab.add(shiftLabel, "dock north");
		rollCallTab.add(tablePanel, "dock north");
		rollCallTab.add(buttonPanel, "dock south");
		
		 this.add(tabbedPane, BorderLayout.CENTER);
		 this.add(sidePanel, BorderLayout.EAST);
	}
//-----------------------------------------------------------------------------
	JPanel makeTablePanel(ArrayList<String> names){
		JPanel tablePanel = new JPanel();
				
		//Create initially empty table
        table = new JTable();
	    table.setShowGrid(true);
	    table.setGridColor(Color.black);
	    table.setPreferredScrollableViewportSize(new Dimension(700, 150));
	    table.setFillsViewportHeight(true);
	    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	   
	    //Put the table in a scroll pane
	    JScrollPane tableScrollPane = new JScrollPane();
	    tableScrollPane.setViewportView(table);
	    tablePanel.setLayout(new BorderLayout());
	    tablePanel.add(tableScrollPane,BorderLayout.CENTER);
	    
	    /*
	     * Set the table model to be the one custom created for this table
	     * and passing in the list of names for the shift
	     */
	    table.setModel(new RollCallTableModel(names));
	    
		TableColumn commentColumn = table.getColumnModel().getColumn(3);
		JTextField commentText = new JTextField();
		commentColumn.setCellEditor(new DefaultCellEditor(commentText));
		
	    //Resize the columns
	    TableColumn col;
	    int[] sizes = {150, 50, 100, 400};
	    
	    for(int i=0; i<sizes.length; i++){
		    col = table.getColumnModel().getColumn(i);
		    col.setPreferredWidth(sizes[i]);
		    col.setWidth(sizes[i]);
	    }
	    
	/*    TableColumn timeCol = table.getColumnModel().getColumn(2);
	    timeCol.setCellEditor(new SpinnerEditor());
*/
	    return tablePanel;
	}
//-----------------------------------------------------------------------------
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand()==ADD){
			((RollCallTableModel)table.getModel()).addRow();
		}else if(e.getActionCommand()==DELETE){
			int rowIndex = table.getSelectedRow();
			if(rowIndex>=0){
				((RollCallTableModel)table.getModel()).deleteRow(rowIndex);
			}
		}else if (e.getActionCommand()==SUBMIT) {
			submitRollCall();
			JOptionPane.showMessageDialog(parent, "Roll call Sumbitted!");
		}
		else if (e.getActionCommand()==LAUNCH) {
			launchScheduler();
		}
	}
//-----------------------------------------------------------------------------
	public void submitRollCall() {
		int numOfRows, i, j, nextShift;
		DatabaseHelper dbHelper = new DatabaseHelper();
		String name, present, timeArrived, comment, shiftDate;
		Date date;
		DateFormat format;
		ArrayList<String> names;
		
		
		numOfRows = table.getModel().getRowCount();
		
		for (i = 0; i < numOfRows; i++) {
			//fill values
			j = 0;
			name = (String)table.getModel().getValueAt(i, j++);
			if ((boolean)table.getModel().getValueAt(i, j++)) 
				present = "true";
			else
				present = "false";
			timeArrived = (String)table.getModel().getValueAt(i, j++);
			comment = (String)table.getModel().getValueAt(i, j++);
			Calendar cal = Calendar.getInstance();
			date = cal.getTime();
			format = new SimpleDateFormat("ddMMMyyyy:" + shiftTime + ":00");
			shiftDate = format.format(date);
			
			//push to person
			try {
				dbHelper.addRollCall(name, present, comment, timeArrived, shiftDate);
			} catch (Exception e) {
				//failed to push to person
				System.out.println("Failed to push to person");
				e.printStackTrace();
			}
		}
		//get the next roll call
		if (shiftTime == 6 || shiftTime == 18)
			nextShift = shiftTime + 4;
		else if (shiftTime == 10)
			nextShift = 18;
		else if (shiftTime == 22)
			nextShift = 6;
		else
			System.out.println("couldn't increment shiftTime:line 226 ShiftCdrTab");
		try {
			//TODO figure out best functionality for going back and forth shifts
			//names = ResourceManager.getRollCall(shiftTime);
			//table.setModel(new RollCallTableModel(names));
		} catch (Exception e) {
			System.out.println("couldn't get next roll call");
		}
	}
//-----------------------------------------------------------------------------	
	private void launchScheduler() {
		//launch schedule program
		try {
			//TODO changed to add project, FIX USER DIR!!!!! 
			//Runtime.getRuntime().exec(System.getProperty("user.dir")
				//+ "/Project/PatrolScheduler/UMPatrolScheduler.exe");
			Runtime.getRuntime().exec(System.getProperty("user.dir")
					+ "/PatrolScheduler/UMPatrolScheduler.exe");		
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Could not launch Scheduler");
			System.out.println("Attempted to launch in directory currentDir/bin");
		}
	}
//-----------------------------------------------------------------------------
	public JSpinner createtimeSpinner(){
		JSpinner timeSpinner;
		
	    //Set up times
		Calendar calendar = Calendar.getInstance();
	    calendar.set(Calendar.HOUR_OF_DAY, 1);
	    calendar.set(Calendar.MINUTE, 0);
	    Date initTime = calendar.getTime();
	    calendar.set(Calendar.HOUR_OF_DAY, 24);
	    calendar.set(Calendar.MINUTE, 59);
	    Date finalTime = calendar.getTime();
		
		SpinnerModel timeModel = new SpinnerDateModel(initTime,initTime,finalTime,Calendar.HOUR); 
		timeSpinner = new JSpinner(timeModel);
		timeSpinner.setEditor(new JSpinner.DateEditor(timeSpinner, "hh:mm a"));
		return timeSpinner;
	}
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
	public void addItemsToReview(JPanel scrollPanel){
		ArrayList<ItemToReview> items = XmlParser.loadItemsToReviewList();
		
		ItemsListModel itemsModel = new ItemsListModel(items);
		
		JList itemsList = new JList(itemsModel);

		ItemRenderer itemRenderer = new ItemRenderer(parent, itemsList);
		itemsList.setCellRenderer(itemRenderer);
		itemsList.addMouseListener(itemRenderer);
		itemsModel.addListDataListener(itemRenderer);
		
//DEBUG: fix the layout for this; its real ugly
		
		/*for(ItemToReview item : items){
			JPanel itemPanel = new JPanel(new MigLayout());
			itemPanel.setSize(new Dimension(280, 110));
			itemPanel.setPreferredSize(new Dimension(280, 110));
			JCheckBox cb = new JCheckBox();
			itemPanel.add(cb, "split");
			itemPanel.add(new JLabel(item.getTitle()), "wrap");
			JTextArea details = new JTextArea(220, 60);
			details.setBackground(itemPanel.getBackground());
			details.setLineWrap(true);
			details.setWrapStyleWord(true);
			details.setEditable(false);
			details.setMaximumSize(new Dimension(240, 100));
			details.setText(item.getDetails());
			itemPanel.add(details);
			
			scrollPanel.add(itemPanel);
			itemPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		}
		*/
		
		scrollPanel.add(itemsList);
		
	}
//-----------------------------------------------------------------------------
   public void getShiftTime(ResourceManager rm) {
	   shiftTime = rm.getShiftTime(); 
   }
//=============================================================================
	private class RollCallTableModel extends AbstractTableModel implements TableModelListener {
		private static final long serialVersionUID = 1L;
		private boolean DEBUG = true;
		private String[] columnNames = {"Name",
                                        "Present",
                                        "Time Arrived",
                                        "Comment"
                                        };
		
		//JSpinner spinner = createtimeSpinner();
		
        private Object[][] data = new Object[0][0];
        	/*{
		{"John Doe", new Boolean(true),"12:10 pm", "excused tardy" },
		{"Jane Roe", new Boolean(true),"11:55 am", " "},
		{"Ray Moe", new Boolean(false)," ","mysteriously late"}
						};*/
//-----------------------------------------------------------------------------
        // unneeded I believe 
        /*public RollCallTableModel() {
        	
        	// initialize the data object
        	data = new Object[0][0];
        	
        	// add some default values
        	addRowWithName("John Doe");
        	addRowWithName("Jane Roe");
        	addRowWithName("Ray Moe");
        	
        }*/
//-----------------------------------------------------------------------------
        public RollCallTableModel(ArrayList<String> names) {
        	
        	/*
        	 * run through each name, setting the value of the name
        	 * and all others blank, incrementing as appropriate
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
         * JTable uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the 'present' column would contain text ("true"/"false"),
         * rather than a check box.
         */
        @SuppressWarnings({ "unchecked", "rawtypes" })
		public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }
//-----------------------------------------------------------------------------
        /*
         * Don't need to implement this method unless your table's
         * editable.
         */
        public boolean isCellEditable(int row, int col) {  
        	return true;
        }
//-----------------------------------------------------------------------------
        /*
         * Don't need to implement this method unless your table's
         * data can change.
         */
        public void setValueAt(Object value, int row, int col) {
            if (DEBUG) {
                System.out.println("Setting value at " + row + "," + col
                                   + " to " + value
                                   + " (an instance of "
                                   + value.getClass() + ")");
            }

            data[row][col] = value;

            if(col==1){
            	if(!((Boolean)value)){
            		data[row][2]="";
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
            Object[] newRow = {"Officer", new Boolean(false), "", ""};
            int curLength = this.getRowCount();
            Object[][] newData = new Object[curLength+1][];
            System.arraycopy(data, 0, newData, 0, curLength);
            data=newData;
            data[curLength]=newRow;
            this.fireTableDataChanged();  
            }  
      //-----------------------------------------------------------------------------  
        public void addRowWithName(String name) {  
            Object[] newRow = {name, new Boolean(false), "", ""};
            int curLength = this.getRowCount();
            Object[][] newData = new Object[curLength+1][];
            System.arraycopy(data, 0, newData, 0, curLength);
            data=newData;
            data[curLength]=newRow;
            this.fireTableDataChanged();  
            }  
//-----------------------------------------------------------------------------  
        public void deleteRow(int row) {  
		    int curLength = this.getRowCount();
		    Object[][] newData = new Object[curLength-1][];
		    System.arraycopy(data, 0, newData, 0, row);
		    System.arraycopy(data, row+1, newData, row, ((data.length-1)-row));
		    data=newData;
		    this.fireTableDataChanged();  
    }          
//-----------------------------------------------------------------------------        
       public void tableChanged(TableModelEvent e) {
/*          int row = e.getFirstRow();
            int column = e.getColumn();
            TableModel model = (TableModel)e.getSource();
            String columnName = model.getColumnName(column);
            Object value = model.getValueAt(row, column);
       
            // Do something with the data...
             
*/
        }
       
//-----------------------------------------------------------------------------
        private void printDebugData() {
            int numRows = getRowCount();
            int numCols = getColumnCount();

            for (int i=0; i < numRows; i++) {
                System.out.print("    row " + i + ":");
                for (int j=0; j < numCols; j++) {
                    System.out.print("  " + data[i][j]);
                }
                System.out.println();
            }
            System.out.println("--------------------------");
        }
	}
//=============================================================================
/*	public class SpinnerEditor extends AbstractCellEditor implements TableCellEditor {
		private static final long serialVersionUID = 1L;
		final JSpinner spinner = new JSpinner();

		// Initializes the spinner.
		public SpinnerEditor() {
			 //Set up times
			Calendar calendar = Calendar.getInstance();
		    calendar.set(Calendar.HOUR_OF_DAY, 1);
		    calendar.set(Calendar.MINUTE, 0);
		    Date initTime = calendar.getTime();
		    calendar.set(Calendar.HOUR_OF_DAY, 24);
		    calendar.set(Calendar.MINUTE, 59);
		    Date finalTime = calendar.getTime();
		    
		    String[] values = new String[]{"item1", "item2", "item3"};
			SpinnerModel timeModel = new SpinnerDateModel(initTime,initTime,finalTime,Calendar.HOUR); 
			spinner.setModel(new SpinnerListModel(java.util.Arrays.asList(values)));
			//spinner.setModel(timeModel);
		}

		// Prepares the spinner component and returns it.
		public Component getTableCellEditorComponent(JTable table, Object value,
		        boolean isSelected, int row, int column) {
		    spinner.setValue(value);
		    return spinner;
		}

		// Enables the editor only for double-clicks.
		public boolean isCellEditable(EventObject evt) {
		    if (evt instanceof MouseEvent) {
		        return ((MouseEvent)evt).getClickCount() >= 2;
		    }
		    return true;
		}

		// Returns the spinners current value.
		public Object getCellEditorValue() {
		    return spinner.getValue();
		}

}*/
//=============================================================================
}