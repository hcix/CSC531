package userinterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;
import net.miginfocom.swing.MigLayout;
import utilities.SwingHelper;

//-----------------------------------------------------------------------------	
public class ShiftCdrReptForm extends JDialog implements ActionListener {
private static final long serialVersionUID = 1L;
//-----------------------------------------------------------------------------	
	public ShiftCdrReptForm(JFrame parent){
		super(parent, "New Shift Commander Summary Report", true);
		
		//Set the size of the form
		this.setPreferredSize(new Dimension(950,900));
 		this.setSize(new Dimension(950,900));
 		
 		//Put the form in the middle of the screen
 		this.setLocationRelativeTo(null);
 		
 		//Make sure that if the user hits the 'x', the window calls the closeAndCancel method
 		this.addWindowListener(new WindowAdapter( ) {
 			public void windowClosing(WindowEvent e) {
 				closeAndCancel( );
 			}
 		});
 		
 		JPanel reportPanel = new JPanel(new MigLayout("ins 20", "[]5%[]", ""));
 		
 		//Make the form scrollable
 		JScrollPane reportPanelScroller = new JScrollPane(reportPanel);
 		reportPanelScroller.setVerticalScrollBarPolicy(
 				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
 		reportPanelScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
 		
		reportPanel.add(createInputPanel());
	    
	    /*Add buttons panel to top of scroll panel as the row header
 	     *(the buttons panel stays at the top of the screen even if the top of the form isn't
 	     *currently visible) */
 	    JPanel buttonsPanel = createButtonsPanel();
 	    reportPanelScroller.setColumnHeaderView(buttonsPanel);
 	    
 	    
 	    //Add the BOLO form scrolling pane dialog to the screen
 	    Container contentPane = getContentPane();
 	    contentPane.add(reportPanelScroller);
 	    
 	    
	}
//-----------------------------------------------------------------------------		

//-----------------------------------------------------------------------------		
	JPanel makeTable(String[] tablecolNames, int rowCount){
		JPanel tablePanel = new JPanel();
				
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
	    
	    DefaultTableModel table1Model = new DefaultTableModel(tablecolNames,rowCount);
	    table.setModel(table1Model);
	    
	    return tablePanel;
	}
//-----------------------------------------------------------------------------
	public JPanel createInputPanel(){
		JPanel inputPanel=new JPanel(new MigLayout());
		
		
		String[] table1colNames = {"Name",
                "Patrol Assignment",
                "Building Checks",
                "Vehicle",
                "Other Duties/Assignments"};
		
		String[] table2colNames = {"Name",
                "Type of Leave",
                "Shift Covered By"};
		
		String[] table3colNames = {"Location",
                "Activity"};
		
		String[] table4colNames = {"Offense/Incident",
				"Case #",
				"Location",
				"Times of Occurance/Remarks/Case Status"};
		
		inputPanel.add(makeTable(table1colNames, 6), "wrap");
		inputPanel.add(makeTable(table2colNames, 6), "wrap");
		inputPanel.add(makeTable(table3colNames, 6), "wrap");
		inputPanel.add(makeTable(table4colNames, 6), "wrap");
		
		return inputPanel;
	}
	//-----------------------------------------------------------------------------

	public JPanel createButtonsPanel(){

	JPanel buttonsPanel = new JPanel(new MigLayout("fillx", "push"));
	
	//Add cancel button
	JButton cancelButton = SwingHelper.createImageButton("Cancel", "icons/cancel_48.png");
	cancelButton.setToolTipText("Cancel and do not save");
	cancelButton.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae) {
			closeAndCancel( );
		}
	});

    // Add save button
    JButton saveButton = SwingHelper.createImageButton("Save", "icons/save_48.png");
    saveButton.setToolTipText("Save Report");
    saveButton.addActionListener(new ActionListener( ) {
    	public void actionPerformed(ActionEvent e) {
    		closeAndSave( );
    	}
    });
    
    // Add preview button
    JButton previewButton = new JButton("<html>Preview<br>  BOLO</html>");
    previewButton.setToolTipText("Preview and print final BOLO document");
    saveButton.addActionListener(new ActionListener( ) {
    	public void actionPerformed(ActionEvent e) {
    		closeAndSave( );
    	}
    });
    
    
    JPanel saveAndCancelButtonsPanel = new JPanel();
    saveAndCancelButtonsPanel.add(saveButton, "tag ok, dock west");
    saveAndCancelButtonsPanel.add(cancelButton, "tag cancel, dock west");
    JPanel previewButtonPanel = new JPanel(new MigLayout("rtl"));
    previewButtonPanel.add(previewButton, "tag right");
    buttonsPanel.add(saveAndCancelButtonsPanel, "shrinky");
    buttonsPanel.add(previewButtonPanel, "growx, shrinky");
    SwingHelper.addLineBorder(buttonsPanel);
    return buttonsPanel;
}
//-----------------------------------------------------------------------------	
 public void closeAndSave( ) {
    // Save font and color information.
    //newFont = previewLabel.getFont( );
   // newColor = previewLabel.getForeground( );

    // Close the window.
    setVisible(false);
  }
//-----------------------------------------------------------------------------
  public void closeAndCancel( ) {
    // Erase any font information and then close the window.
//    newFont = null;
 //   newColor = null;
    setVisible(false);
  }
//-----------------------------------------------------------------------------
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
//-----------------------------------------------------------------------------
}
