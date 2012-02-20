package userinterface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ShiftCommanderSumReportForm extends JPanel{

	public ShiftCommanderSumReportForm(){
		super(new GridLayout(1,0));	

		//Table1
		JPanel table1Panel = new JPanel(new BorderLayout());
		String[] table1colNames = {"Name",
                "Patrol Assignment",
                "Building Checks",
                "Vehicle",
                "Other Duties/Assignments"};

		 

			 
		 JTable table1 = new JTable(null, table1colNames);
		 table1.setPreferredScrollableViewportSize(new Dimension(500, 70));
	     table1.setFillsViewportHeight(true);
		 
	     //Create the scroll pane and add the table to it.
	     JScrollPane scrollPane = new JScrollPane(table1);
	 
	     //Add the scroll pane to this panel.
	     table1Panel.add(scrollPane);

	     //Add table panel #1 
	     this.add(table1Panel);
	}
	
}
