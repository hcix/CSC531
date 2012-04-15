/**
 * Tab available only to Shift Commanders.
 */
package shiftCdrTab.reports;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import net.miginfocom.swing.MigLayout;
import program.ResourceManager;
import shiftCdrTab.RollCall;
import utilities.DatabaseHelper;
import utilities.FileHelper;
import utilities.pdf.PDFView;
import utilities.ui.ImagePreview;
import utilities.ui.SwingHelper;


public class ReportsPanel extends JPanel {
private static final long serialVersionUID = 1L;
	final ResourceManager rm;
	PDFView pdfv;
	ArrayList<ShiftCdrReport> reports = new ArrayList<ShiftCdrReport>();
//-----------------------------------------------------------------------------
		public ReportsPanel(final ResourceManager rm){
			this.setLayout(new MigLayout("fill"));
			this.rm = rm;
			
			final JFrame parent = rm.getGuiParent();
			
			//Create a button to create a new Report 
			JButton newButton = SwingHelper.createImageButton("Create new Report", 
					"icons/plusSign_48.png"); 
			newButton.addActionListener(new ActionListener() {
				//Shift CDR form dialog
				//ShiftReportForm formDialog = new ShiftReportForm(rm, null);
				public void actionPerformed(ActionEvent e){
					ArrayList<RollCall> rollCall;
					//TODO: create new ShiftCdrReport object
					ShiftCdrReport shiftReport = new ShiftCdrReport();
					//TODO fill in w/e fields
					rollCall = DatabaseHelper.getRollCallFromDatabase(null);
					//ShiftReportForm formDialog = new ShiftReportForm(rm, shiftcdrreport);
					ShiftReportForm formDialog = new ShiftReportForm(rm, null);
					formDialog.setVisible(true);
				}
			});

			//Create a button to import an existing Report
			JButton importButton = SwingHelper.createImageButton("Import Existing Report", 
					"icons/Import.png");
			importButton.addActionListener(new ActionListener() {
				//file chooser dialog
				public void actionPerformed(ActionEvent e){
					//show choose photo dialog
					final JFileChooser fc = new JFileChooser();
					fc.addChoosableFileFilter(FileHelper.getPDFFilter());
					fc.setAcceptAllFileFilterUsed(false);
					fc.setAccessory(new ImagePreview(fc));
					int returnVal = fc.showOpenDialog(parent);
						
					//if a ShiftCdrReport pdf of the correct format was selected, load its info
					if(returnVal==JFileChooser.APPROVE_OPTION){
						//copy the chosen report into the program's 'ShiftCdrReports' directory
						File file = fc.getSelectedFile();
						
//DEBUG						
System.out.printf("filepath = %s\n", file.getName(), file.getPath());

						Path filePath = FileHelper.copyShiftCdrReport(file);
						//load the report's info into the program
						if(filePath==null){ return; }
						ShiftReportForm formDialog = 
								new ShiftReportForm(parent, filePath.toString());
						formDialog.setVisible(true);
					}
					
				}
			});

			//Create a button to search existing Reports
			JButton searchButton = SwingHelper.createImageButton("Search Records", 
					"icons/search.png");
			searchButton.addActionListener(new ActionListener() {
				//Search dialog
				JDialog searchDialog = createSearchDialog(parent);
				public void actionPerformed(ActionEvent e){
					searchDialog.setVisible(true);
				}
			});

			JScrollPane reportsScroller = new JScrollPane();
			loadInLastReport(reportsScroller);

			JPanel itemsPanel = new JPanel(new MigLayout("ins 0, gap 0"));
			JScrollPane itemsScroller = new JScrollPane(itemsPanel,
					  ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
					  ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			itemsPanel.setLayout(new MigLayout("flowy"));
			itemsPanel.setPreferredSize(new Dimension(300, 625));
			addReportsList(itemsPanel);

			
			//Create a split pane with the two scroll panes in it.
			JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
					itemsScroller, reportsScroller);
			
			splitPane.setOneTouchExpandable(true);
			splitPane.setDividerLocation(190);
 
			this.add(splitPane, "dock center");
			
			//Create and add the buttons to the bottom of the screen
	        JPanel buttonsPanel = new JPanel();
	        buttonsPanel.add(newButton);
	        buttonsPanel.add(importButton);
	        buttonsPanel.add(searchButton);
	        this.add(buttonsPanel, "dock south");
		}
//-----------------------------------------------------------------------------
	public void addReportsList(JPanel itemPanel){

		File folder = new File(FileHelper.getReportsDir());
	    File[] listOfFiles = folder.listFiles();

	    for (int i = 0; i < listOfFiles.length; i++) {
	    	if (listOfFiles[i].isFile() && !(listOfFiles[i].getName().startsWith(".")) ) {
//DEBUG 
System.out.println("File " + listOfFiles[i].getName());
reports.add(new ShiftCdrReport(FileHelper.getDocumentPathName(listOfFiles[i].getName()))); 
	    	} else if (listOfFiles[i].isDirectory()) {
//DEBUG System.out.println("Directory " + listOfFiles[i].getName());
	    	}
	    }
	    

		ReportsListModel reportsModel = new ReportsListModel(reports);
		
		JList<ShiftCdrReport> reportsList = new JList<ShiftCdrReport>(reportsModel);

		
		
		ReportListRenderer reportRenderer = new ReportListRenderer(rm.getGuiParent(), reportsList);
		reportsList.setCellRenderer(reportRenderer);
		reportsList.addMouseListener(reportRenderer);
		reportsModel.addListDataListener(reportRenderer);
		
		itemPanel.add(reportsList);

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
	 * Load the most recent report into the PDF view
	 */
	public void loadInLastReport(JComponent comp){
		String shiftCdrForm = 
				 System.getProperty("UMPD.latestReport");

        
		pdfv = new PDFView(shiftCdrForm, comp, rm);

	}
//-----------------------------------------------------------------------------		
	public JPanel createButtonsPanel(){
	
		JPanel buttonsPanel = new JPanel(new MigLayout("fillx", "push"));
		
		//Add cancel button
		JButton cancelButton = SwingHelper.createImageButton("Cancel", "icons/cancel_48.png");
		cancelButton.setToolTipText("Cancel and do not save");
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				//closeAndCancel( );
			}
		});
	
	    // Add save button
	    JButton saveButton = SwingHelper.createImageButton("Save", "icons/save_48.png");
	    saveButton.setToolTipText("Save Report");
	    saveButton.addActionListener(new ActionListener( ) {
	    	public void actionPerformed(ActionEvent e) {
	    		//closeAndSave();
	    	}
	    });
	      

	    JPanel saveAndCancelButtonsPanel = new JPanel();
	    saveAndCancelButtonsPanel.add(saveButton, "tag ok, dock west");
	    saveAndCancelButtonsPanel.add(cancelButton, "tag cancel, dock west");
	    buttonsPanel.add(saveAndCancelButtonsPanel, "shrinky");
	    return buttonsPanel;
	}
//-----------------------------------------------------------------------------	
	
//-----------------------------------------------------------------------------
	
//-----------------------------------------------------------------------------	
}

