package shiftCdrTab.gui;

import java.awt.Container;
import java.awt.Cursor;
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
import blueBookTab.BlueBookPreview;
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
/**
 * the <code>ReportsPanel<code/> Tab is available only to Shift Commanders. 
 * it allows one to view a report
 */
public class ReportsPanel extends JPanel implements MouseListener, ActionListener{
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
			newButton.addActionListener(this);
	
			
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
	 * 
	 */
	public void loadInLastReport(JComponent comp){
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
	 * Add all reports in the ShiftReports subdirectory to the list.
	 */
	private void addReportsList(JPanel itemPanel){
		reportsModel = new ReportsListModel(reportsFileArrayList);
		reportsJList = new JList<ReportFile>(reportsModel);
		
		ReportListRenderer reportRenderer = 
				new ReportListRenderer(rm.getGuiParent(), reportsJList);
		
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
	/**
	 * Called when the 'Create' button is clicked. Creates and shows a new
	 * <code>ShiftReportForm</code> pre-filled with the relevant information
	 * from the corresponding RollCall.
	 */
	public void actionPerformed(ActionEvent e){
		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
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
		    this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
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
//-----------------------------------------------------------------------------
	/**
	 * Called when one of the reports on the list is clicked. If the report
	 * was double clicked, it's loaded into the viewing panel and highlighted
	 * in the list.
	 */
	public void mouseClicked(MouseEvent e) {
		
		if(e.getClickCount() == 2){ //double click
			     int index = reportsJList.locationToIndex(e.getPoint());
			     this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			     Object item = reportsModel.getElementAt(index);
			     reportsJList.ensureIndexIsVisible(index);
			     
			     ReportFile rf = (ReportFile)item;   
			     
			     pdfv.openPdfFile(rf.getFilename());
			     this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			     
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

