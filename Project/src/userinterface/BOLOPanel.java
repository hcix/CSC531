package userinterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.table.DefaultTableModel;

import utilities.SwingHelper;

public class BOLOPanel extends JPanel{
private static final long serialVersionUID = 1L;
//-----------------------------------------------------------------------------
	public BOLOPanel(final JFrame parent){
		
		//Create the search panel
		JPanel searchPanel = createSearchPanel();

		//Create a button to create a new BOLO 
		JButton newBOLO = new JButton("Create new BOLO");
		newBOLO.addActionListener(new ActionListener() {
			BOLOform formDialog = new BOLOform(parent);
			public void actionPerformed(ActionEvent e){
				formDialog.setVisible(true);
			}
		});

		//Create a button to import an existing BOLO
		
		
		//add the components to this panel
		this.add(searchPanel, BorderLayout.PAGE_START);
		this.add(newBOLO, BorderLayout.CENTER);
		
	}
//-----------------------------------------------------------------------------
	JPanel createSearchPanel(){
		JPanel searchPanel = new JPanel();
		
		//Create case # field
		JPanel caseNum = SwingHelper.createLabeledTextBox("Case #: ");
		
		//Create date range spinners
		//JPanel datePanel = createDatePanel();
		
		//Create location field
		
		//Create status field
		
		//Create search button
		
		//add the components to this panel
		searchPanel.add(caseNum);

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
