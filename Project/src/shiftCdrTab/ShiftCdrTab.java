package shiftCdrTab;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.border.Border;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import net.miginfocom.swing.MigLayout;
import program.ResourceManager;
import utilities.DatabaseHelper;
import utilities.SwingHelper;
//-----------------------------------------------------------------------------
public class ShiftCdrTab extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	static final String ADD="add";
	static final String DELETE="delete";
	static final String SUBMIT="submit";
	JTable table;
	DefaultTableModel tableModel;
	final static int GAP = 10;
//-----------------------------------------------------------------------------
	public ShiftCdrTab(ResourceManager rm) {
		this.setLayout(new BorderLayout());

		JPanel sidePanel = new JPanel();
		String label = "Items to Review";
		JPanel scrollPanel = new JPanel();
		
		JScrollPane scroller = new JScrollPane(scrollPanel,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		JLabel titleLabel = new JLabel(label,JLabel.CENTER);
		
		sidePanel.setPreferredSize(new Dimension(270,625));		
		sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));		
		sidePanel.setBorder(makeSidePanelBorder());
		
		sidePanel.add(titleLabel);
		sidePanel.add(scroller); 
		
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
		
		//Create roll call table
		JPanel tablePanel = makeTablePanel();
			  
		//Create button panel
		JPanel buttonPanel = new JPanel(new FlowLayout());
		JButton addButton = SwingHelper.createImageButton("Add Officer", "icons/plusSign_48.png");
		addButton.setActionCommand(ADD);
		addButton.addActionListener(this);
		JButton deleteButton = SwingHelper.createImageButton("Remove Officer", "icons/delete_48.png");
		deleteButton.setActionCommand(DELETE);
		deleteButton.addActionListener(this);
		JButton submitButton = SwingHelper.createImageButton("Submit Roll Call", "icons/save_48.png");
		submitButton.addActionListener(this);
		submitButton.setActionCommand(SUBMIT);
		buttonPanel.add(addButton);
		buttonPanel.add(deleteButton);
		buttonPanel.add(submitButton);
		
		// place panes in roll call tab
		rollCallTab.add(tablePanel, "dock north");
		rollCallTab.add(buttonPanel, "dock south");
		
		//rollCallTab.setPreferredSize(new Dimension(900, 1000));
		 this.add(tabbedPane, BorderLayout.CENTER);
		 this.add(sidePanel, BorderLayout.EAST);
		//this.add(tabbedPane, "grow, push");
	}
//-----------------------------------------------------------------------------
	JPanel makeTablePanel(){
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
	    
	    //Set the table model to be the one custom created for this table
	    table.setModel(new RollCallTableModel());
	    
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
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
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
		}
	}
//-----------------------------------------------------------------------------
	public void submitRollCall() {
		int numOfRows, numOfCols, i, j;
		DatabaseHelper dbHelper = new DatabaseHelper();
		String name, present, timeArrived, comment;
		Date shiftDate;
		
		numOfRows = table.getModel().getRowCount();
		numOfCols = table.getModel().getColumnCount();
		
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
			//get date
			Calendar cal = Calendar.getInstance();
			shiftDate = cal.getTime();
			//push to person
			try {
				dbHelper.addRollCall(name, present, comment, timeArrived, shiftDate);
			} catch (Exception e) {
				//failed to push to person
				System.out.println("Failed to push to person");
				e.printStackTrace();
			}
		}
		
		//TODO push to shift
		//TODO push to shiftPerson
		
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
	public Border makeSidePanelBorder(){
		Border spaceBorder = BorderFactory.createEmptyBorder(GAP/2,GAP,GAP/2,GAP);
		Border lineBorder = BorderFactory.createLineBorder(new Color(0x000000));
		Border border = BorderFactory.createCompoundBorder(spaceBorder, lineBorder);
		
		return border;
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
		
        private Object[][] data={
        		{"John Doe", new Boolean(true),"12:10 pm", "excused tardy" },
				{"Jane Roe", new Boolean(true),"11:55 am", " "},
				{"Ray Moe", new Boolean(false)," ","mysteriously late"}
								};
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