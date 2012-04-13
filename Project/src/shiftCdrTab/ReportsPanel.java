/**
 * Tab available only to Shift Commanders.
 */
package shiftCdrTab;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;
import program.ResourceManager;
import utilities.DatabaseHelper;
import utilities.FileHelper;
import utilities.pdf.PDFView;
import utilities.ui.ImagePreview;
import utilities.ui.SwingHelper;


public class ReportsPanel extends JPanel {
private static final long serialVersionUID = 1L;
	final ResourceManager rm;
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

			//Automatically load the most recent report into the window
			JPanel lastReport = loadInLastReport();
			this.add(lastReport, "dock center");
			
			//Create and add the buttons to the bottom of the screen
	        JPanel buttonsPanel = new JPanel();
	        buttonsPanel.add(newButton);
	        buttonsPanel.add(importButton);
	        buttonsPanel.add(searchButton);
	        this.add(buttonsPanel, "dock south");
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
	public JPanel loadInLastReport(){
		JPanel panel = new JPanel();
		
		String shiftCdrForm = System.getProperty("UMPD.latestReport");
		 	    
		@SuppressWarnings("unused")
		PDFView pdfv = new PDFView(shiftCdrForm, panel, rm);

		return panel;
	}
//-----------------------------------------------------------------------------	
	}

