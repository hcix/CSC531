package userinterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import net.miginfocom.swing.MigLayout;
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
		searchFields.setLayout(new MigLayout());
		searchPanel.setLayout(new MigLayout());

		JLabel caseNumLabel = new JLabel("Case #: ");
		JLabel locationLabel = new JLabel("Location: ");
		JLabel statusLabel = new JLabel("Status: ");
		JTextField caseNumField = new JTextField(15);
		JTextField locationField = new JTextField(20);
		JTextField statusField = new JTextField(20);
		
		JPanel dateRange = SwingHelper.createDateRangePanel();
		
	/*	SwingHelper.addLineBorder(caseNumLabel );
		SwingHelper.addLineBorder( locationLabel);
		SwingHelper.addLineBorder( statusLabel);
		SwingHelper.addLineBorder( caseNumField);
		SwingHelper.addLineBorder( locationField);
		SwingHelper.addLineBorder(statusField );
		SwingHelper.addLineBorder(dateRange );*/
		SwingHelper.addLineBorder(searchPanel );
		//SwingHelper.addLineBorder(searchFields);
		
		searchFields.add(caseNumLabel, "align left");
		searchFields.add(caseNumField, "align left, wrap");
		searchFields.add(locationLabel,"alignx left");
		searchFields.add(locationField, "align left, wrap");
		searchFields.add(statusLabel, "alignx left");
		searchFields.add(statusField, "align left, wrap");

		//Create search button
		JButton searchButton = new JButton("Search");
		
		//add the search field components to the search field panel

		
		//add the search fields and search button to the search panel
		searchPanel.add(searchFields, "wrap");
		searchPanel.add(dateRange);
		searchPanel.add(searchButton);

		return searchPanel;
	}
//-----------------------------------------------------------------------------		
}
