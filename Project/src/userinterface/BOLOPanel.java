package userinterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import utilities.SwingHelper;

public class BOLOPanel extends JPanel{
private static final long serialVersionUID = 1L;
private static final int TEXT_FIELD_LENGTH = 20;
//-----------------------------------------------------------------------------
	public BOLOPanel(final JFrame parent){
		
		//Create the search panel
		JPanel searchPanel = createSearchPanel();

		
		//Create a button to create a new BOLO 
		JButton newBOLO = new JButton("Create new BOLO");
		newBOLO.addActionListener(new ActionListener() {
			//BOLO form dialog
			BOLOform formDialog = new BOLOform(parent);
			public void actionPerformed(ActionEvent e){
				formDialog.setVisible(true);
			}
		});

		//Create a button to import an existing BOLO
		JButton importBOLO = new JButton("Import an existing BOLO");
		importBOLO.addActionListener(new ActionListener() {
			//file chooser dialog
			public void actionPerformed(ActionEvent e){
				//file chooser dialog .setVisable(true);
			}
		});
		
		//Create a button panel & add the buttons to it
		JPanel buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.add(newBOLO);
		buttonPanel.add(importBOLO);
		
		//add the components to this panel
		this.add(searchPanel, BorderLayout.PAGE_START);
		this.add(buttonPanel, BorderLayout.CENTER);
		
	}
//-----------------------------------------------------------------------------
	JPanel createSearchPanel(){
		JPanel searchPanel = new JPanel();
		JPanel searchFields = new JPanel();
		searchFields.setLayout(new BoxLayout(searchFields, BoxLayout.Y_AXIS));
		
		//Create case # field
		JPanel caseNum = SwingHelper.createLabeledTextBox("Case #: ", TEXT_FIELD_LENGTH);
		
		//Create date range spinners
		JPanel dateRange = SwingHelper.createDateRangePanel();
		
		//Create location field
		JPanel location = SwingHelper.createLabeledTextBox("Location: ", TEXT_FIELD_LENGTH);
		
		//Create status field
		JPanel status = SwingHelper.createLabeledTextBox("Status: ", TEXT_FIELD_LENGTH);
		
		//Create search button
		JButton searchButton = new JButton("Search");
		
		//add the search field components to the search field panel
		searchFields.add(caseNum);
		searchFields.add(dateRange);
		searchFields.add(location);
		searchFields.add(status);
		
		//add the search fields and search button to the search panel
		searchPanel.add(searchFields);
		searchPanel.add(searchButton);

		return searchPanel;
	}
//-----------------------------------------------------------------------------	
	JPanel makeInfoChart(){
		JPanel tablePanel = new JPanel();
		
		String[] tableRowHeaderNames = {"Date; Time; Location of Incident:",
		"Reference & Case #:",
		"BOLO prepared by:",
		"BOLO approved by:",
		"Date & Time of BOLO:",
		"Status",
		"Subject description & information"};
		
		//Create initially empty table
		JTable table = new JTable();
		table.setShowGrid(true);
		table.setGridColor(Color.black);
		table.setPreferredScrollableViewportSize(new Dimension(895, 100));
		table.setFillsViewportHeight(true);
		
		//Put the table in a scroll pane
		JScrollPane tableScrollPane = new JScrollPane();
		tableScrollPane.setViewportView(table);
		
		tablePanel.setLayout(new BorderLayout());
		tablePanel.add(tableScrollPane,BorderLayout.CENTER);
		add(tablePanel, BorderLayout.CENTER);
		String[] colHeaders = {"", ""};
		
		DefaultTableModel tableModel = new DefaultTableModel(colHeaders,7);
		table.setModel(tableModel);
		
		for(int i=0; i<tableRowHeaderNames.length; i++){
			
		}
		return tablePanel;
	}
	
	
	
}
