package userinterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
//-----------------------------------------------------------------------------	
public class ShiftCmdrReptForm extends JPanel{
private static final long serialVersionUID = 1L;
//-----------------------------------------------------------------------------	
	public ShiftCmdrReptForm(){
		super(new GridLayout(1,0));	

		//Table1
		JPanel table1Panel = new JPanel();
		
		String[] table1colNames = {"Name",
                "Patrol Assignment",
                "Building Checks",
                "Vehicle",
                "Other Duties/Assignments"};

		//Create initially empty table
        JTable table1 = new JTable();
	    table1.setShowGrid(true);
	    table1.setGridColor(Color.black);
		table1.setPreferredScrollableViewportSize(new Dimension(895, 70));
	    table1.setFillsViewportHeight(true);
	    
	    //Put the table in a scroll pane
	    JScrollPane table1ScrollPane = new JScrollPane();
	    table1ScrollPane.setViewportView(table1);

	    table1Panel.setLayout(new BorderLayout());
	    table1Panel.add(table1ScrollPane,BorderLayout.CENTER);
	    add(table1Panel, BorderLayout.CENTER);

	    DefaultTableModel table1Model = new DefaultTableModel(table1colNames,6);
	    table1.setModel(table1Model);
	}
//-----------------------------------------------------------------------------		
	JTableHeader makeExtraColumnHeader(String[] colHeaderNames){
		JTableHeader header;
	
		JTable table = new JTable();

		DefaultTableModel tableModel = new DefaultTableModel(colHeaderNames,1);
	    table.setModel(tableModel);
	    header = table.getTableHeader();
	    
		return header;
	}
//-----------------------------------------------------------------------------		
}
