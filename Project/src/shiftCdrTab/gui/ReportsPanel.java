/**
 * Tab available only to Shift Commanders.
 */
package shiftCdrTab.gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import net.miginfocom.swing.MigLayout;
import program.ResourceManager;
import shiftCdrTab.reports.ReportFile;
import shiftCdrTab.reports.ReportListRenderer;
import shiftCdrTab.reports.ReportsListModel;
import shiftCdrTab.rollCall.RollCall;
import utilities.DatabaseHelper;
import utilities.FileHelper;
import utilities.pdf.PDFView;
import utilities.ui.SwingHelper;


public class ReportsPanel extends JPanel implements MouseListener {
private static final long serialVersionUID = 1L;
	final ResourceManager rm;
	PDFView pdfv;
	ArrayList<ReportFile> reportsFileArrayList = new ArrayList<ReportFile>();
	JList<ReportFile> reportsJList;
	ReportsListModel reportsModel;
	DatabaseHelper dbHelper = new DatabaseHelper();
	ReportFile currDisplayed;
	JPanel itemsPanel;
	private JScrollPane reportsScroller;
//-----------------------------------------------------------------------------
		public ReportsPanel(final ResourceManager rm){
			this.setLayout(new MigLayout("fill"));
			this.rm = rm;
			//final JFrame parent = rm.getGuiParent();

			//Create the list of Reports
			createListOfReports();
			
			//Create list of Reports display panel
			itemsPanel = new JPanel(new MigLayout("ins 0, gap 0"));
			JScrollPane itemsScroller = new JScrollPane(itemsPanel,
					  ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
					  ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			itemsPanel.setLayout(new MigLayout("flowy"));
			itemsPanel.setPreferredSize(new Dimension(300, 625));
			addReportsList(itemsPanel);

			//Create the display report area
			reportsScroller = new JScrollPane();
			loadInLastReport(reportsScroller);
			
			//Create a split pane with the two scroll panes in it.
			JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
					itemsScroller, reportsScroller);
			
			splitPane.setOneTouchExpandable(true);
			splitPane.setDividerLocation(190);
 
			//Create the Create New Report & Search Reports Buttons
			JPanel buttonsPanel = createBottomButtonsPanel();
			
			//add components to main Reports panel
	        this.add(splitPane, "dock center");
	        this.add(buttonsPanel, "dock south");
		}
//-----------------------------------------------------------------------------
		private JPanel createBottomButtonsPanel(){
			JPanel buttonsPanel = new JPanel();
			
			//Create New Report Button
			JButton newButton = SwingHelper.createImageButton("Create new Report", 
					"icons/plusSign_48.png"); 
			newButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e){
					String mostRecentShift = System.getProperty("UMPD.latestShiftTime");
					ArrayList<RollCall> rollCall;
					
					//create new ShiftCdrReport object
					ShiftCdrReport shiftReport = new ShiftCdrReport();
					
					//check that a rollcall has been submitted
			        if (mostRecentShift.equals("none")){
			        	JOptionPane.showMessageDialog(rm.getGuiParent(), 
			        			"No shift has been submitted yet.");
			        } else {
					    Format format = new SimpleDateFormat("ddMMMyyyy:" + 
					    		mostRecentShift + ":00");
					    Date date = new Date();
					    try {
						    rollCall = dbHelper.getRollCallFromDatabase(format.format(date));
					    } catch (Exception e1) {
						    System.out.println("Couldn't get rollCall from db in reportsPanel");
					    	//e1.printStackTrace();
					    }
					    ShiftReportForm formDialog = new ShiftReportForm(rm, null);
					    formDialog.setVisible(true);
					    formDialog.setModal(true);
					    
					    //if new report was created, recreate reports list & display
					    if(formDialog.isNewReportWasCreated()){
					    	//recreate list
					    	reportsFileArrayList.clear();
					    	createListOfReports();
					    	//recreate list display
					    	itemsPanel.removeAll();
					    	addReportsList(itemsPanel);
					    	//load this new report into report display area
					    	loadInLastReport(reportsScroller);
					    }
				    }
			    }
			});
			
			//Search Button
			JButton searchButton = SwingHelper.createImageButton("Search Records", 
					"icons/search.png");
			searchButton.addActionListener(new ActionListener() {
				//Search dialog
				JDialog searchDialog = createSearchDialog(rm.getGuiParent());
				public void actionPerformed(ActionEvent e){
					searchDialog.setVisible(true);
					//TODO Implement Search
				}
			});
			
			buttonsPanel.add(newButton);
			buttonsPanel.add(searchButton);
			
			return buttonsPanel;
			
		}
//-----------------------------------------------------------------------------
	/**
	 * Load the most recent report into the PDF view
	 * JDOC
	 */
	public void loadInLastReport(JComponent comp){
		//String shiftCdrForm = System.getProperty("UMPD.latestReport");
	
		//check that there is a report to load
		if(reportsFileArrayList.size()<=0){
			return;
		}
		
		ReportFile mostRecent = reportsFileArrayList.get(0);
		
		//get the latest report from the list
		for(ReportFile r : reportsFileArrayList){
			if(r.getDateCreated().after(mostRecent.getDateCreated())){ 
				mostRecent = r; 
			}
		}
	    
		//display the lastest report
		currDisplayed = mostRecent;
		mostRecent.setCurrentlyDisplayed(true);
		pdfv = new PDFView(mostRecent.getFilename(), comp, rm);
}
//-----------------------------------------------------------------------------
	/**
	 * JDOC
	 */
	public void addReportsList(JPanel itemPanel){
		reportsModel = new ReportsListModel(reportsFileArrayList);
		reportsJList = new JList<ReportFile>(reportsModel);
		
		ReportListRenderer reportRenderer = new ReportListRenderer(rm.getGuiParent(), reportsJList);
		reportsJList.setCellRenderer(reportRenderer);
		reportsJList.addMouseListener(this);
		reportsModel.addListDataListener(reportRenderer);
		
		itemPanel.add(reportsJList);
	}
//-----------------------------------------------------------------------------
	private void createListOfReports(){
		File folder = new File(FileHelper.getReportsDir());
	    File[] listOfFiles = folder.listFiles();
	
	    for (int i = 0; i < listOfFiles.length; i++) {
	    	//check that the listing is a visible file
	    	if (listOfFiles[i].isFile() && !(listOfFiles[i].getName().startsWith("."))){
	    		//create a new report object from the listing and load it
	    		reportsFileArrayList.add(new ReportFile(
	    				FileHelper.getReportPathName(listOfFiles[i].getName())));//
//DEBUG System.out.println("File " + listOfFiles[i].getName());	    	
	    	} 
	    	//else if (listOfFiles[i].isDirectory()) {
//DEBUG System.out.println("Directory " + listOfFiles[i].getName());
	    	//}
	    }
	}
//-----------------------------------------------------------------------------
	JDialog createSearchDialog(JFrame parent){
		//Create the dialog and set the size
		JDialog searchDialog = new JDialog(parent, "Search Past Reports", true);
		searchDialog.setPreferredSize(SwingHelper.SEARCH_DIALOG_DIMENSION);
		searchDialog.setSize(SwingHelper.SEARCH_DIALOG_DIMENSION);
			
		//Put the dialog in the middle of the screen
		searchDialog.setLocationRelativeTo(null);
	
		//Create the various search fields and add them to the dialog
		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(new MigLayout("align left"));
		SwingHelper.addLineBorder(searchPanel);
		
		JLabel completedByLabel = new JLabel("Completed by: ");
		JLabel relievedByLabel = new JLabel("Relieved by: ");
		
		JTextField completedByField = new JTextField(SwingHelper.DEFAULT_TEXT_FIELD_LENGTH);
		JTextField relievedByField = new JTextField(SwingHelper.DEFAULT_TEXT_FIELD_LENGTH);
		
		searchPanel.add(completedByLabel, "align left");
		searchPanel.add(completedByField, "align left, wrap");
		searchPanel.add(relievedByLabel,"alignx left");
		searchPanel.add(relievedByField, "alignx left, wrap");

		SwingHelper.addDateRangePanel(searchPanel);

		Container contentPane = searchDialog.getContentPane();
		contentPane.add(searchPanel);
		return searchDialog;
	}
//-----------------------------------------------------------------------------		
	private JPanel createButtonsPanel(){
	
		JPanel buttonsPanel = new JPanel(new MigLayout("fillx", "push"));
		
		//Cancel button
		JButton cancelButton = SwingHelper.createImageButton("Cancel", "icons/cancel_48.png");
		cancelButton.setToolTipText("Cancel and do not save");
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				//closeAndCancel( );
			}
		});
	
	    //Save button
	    JButton saveButton = SwingHelper.createImageButton("Save", "icons/save_48.png");
	    saveButton.setToolTipText("Save Report");
	    saveButton.addActionListener(new ActionListener( ) {
			public void actionPerformed(ActionEvent e) {
	    		//closeAndSave();
	    	}
	    });
	      
	    
	    //add save & cancel buttons to panel
	    JPanel saveAndCancelButtonsPanel = new JPanel();
	    saveAndCancelButtonsPanel.add(saveButton, "tag ok, dock west");
	    saveAndCancelButtonsPanel.add(cancelButton, "tag cancel, dock west");
	    buttonsPanel.add(saveAndCancelButtonsPanel, "shrinky");
	    return buttonsPanel;
	}
//-----------------------------------------------------------------------------	
	public void mouseClicked(MouseEvent e) {
		
		if(e.getClickCount() == 2){ //double click
			     int index = reportsJList.locationToIndex(e.getPoint());
			     
			     Object item = reportsModel.getElementAt(index);
			     reportsJList.ensureIndexIsVisible(index);
			     
			     ReportFile rf = (ReportFile)item;
			     pdfv.openPdfFile(rf.getFilename());
			     
			     currDisplayed.setCurrentlyDisplayed(false);
			     
			     currDisplayed=rf;
			     rf.setCurrentlyDisplayed(true);
			     reportsJList.repaint();
		}
		
	}
//-----------------------------------------------------------------------------
	public void mousePressed(MouseEvent e) { }
//-----------------------------------------------------------------------------
	public void mouseReleased(MouseEvent e) { }
//-----------------------------------------------------------------------------
	public void mouseEntered(MouseEvent e) { }
//-----------------------------------------------------------------------------
	public void mouseExited(MouseEvent e) { }
//-----------------------------------------------------------------------------	
}
