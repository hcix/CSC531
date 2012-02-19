package userinterface;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

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

		 
		// create a DefaultTableModel
		 DefaultTableModel table1Model = new DefaultTableModel();
		 
		 // add Columns to DefaultTableModel
		 for(int i=0; i<table1colNames.length; i++){
			 table1Model.addColumn( table1colNames[i] );
		 }
			 
		 JTable table1 = new JTable( table1Model );
		 
		 table1Panel.add(table1);
		 add(table1Panel);
	}
	
}
