package shiftCdrTab;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import net.miginfocom.swing.MigLayout;
import program.ResourceManager;
import utilities.FileHelper;
import utilities.pdf.FieldAndVal;
import utilities.pdf.PDFView;
import utilities.ui.SwingHelper;
//-----------------------------------------------------------------------------	
public class ShiftReportForm extends JDialog {
private static final long serialVersionUID = 1L;
	JButton[] buttons;
	ShiftCdrReport report;
	PDFView pdfv;
	ResourceManager rm;
//-----------------------------------------------------------------------------	
	public ShiftReportForm(ResourceManager rm, ShiftCdrReport report){
		super(rm.getGuiParent(), "New Shift Commander Summary Report", true);
		this.rm = rm;
		this.report=report;
		
		//Set the size of the form
		this.setPreferredSize(new Dimension(800,700));
 		this.setSize(new Dimension(800,700));
 		
 		//Put the form in the middle of the screen
 		this.setLocationRelativeTo(null);
 		
 		//Make sure that if the user hits the 'x', the window calls the closeAndCancel method
 		this.addWindowListener(new WindowAdapter( ) {
 			public void windowClosing(WindowEvent e) {
 				closeAndCancel( );
 			}
 		});
 		
 		if(report==null){ //create a new report
 			this.report = new ShiftCdrReport();
 		} else {//load info into form fields
 			loadInfoIntoForm();
 		}
 		
 	    JPanel buttonsPanel = createButtonsPanel();

 	    Container contentPane = this.getContentPane();
 	   
 	  //  String shiftCdrForm = FileHelper.getDocumentPathName("ShiftCDRSumReport.pdf");
 	   String shiftCdrForm = FileHelper.getFormTemplatePathName("ShiftCDRSumReport.pdf");
 	    JScrollPane scroller = new JScrollPane();
 	    
 	   
 	    pdfv = new PDFView(shiftCdrForm, scroller, buttonsPanel);
 	  	   

 	    contentPane.add(scroller);
 	    
 	    

	}
//-----------------------------------------------------------------------------	
	public ShiftReportForm(JFrame parent, String reportFileName){
		super(parent, "Shift Commander Summary Report", true);
		
		//Set the size of the form
		this.setPreferredSize(new Dimension(800,700));
 		this.setSize(new Dimension(800,700));
 		
 		//Put the form in the middle of the screen
 		this.setLocationRelativeTo(null);
 		
 		//Make sure that if the user hits the 'x', the window calls the closeAndCancel method
 		this.addWindowListener(new WindowAdapter( ) {
 			public void windowClosing(WindowEvent e) {
 				closeAndCancel( );
 			}
 		});
 		
 		report = new ShiftCdrReport();
 		
 	    JPanel buttonsPanel = createButtonsPanel();

 	    Container contentPane = this.getContentPane();
 	   
 	    JScrollPane scroller = new JScrollPane();

 	    pdfv = new PDFView(reportFileName, scroller, buttonsPanel);
 	  	   

 	    contentPane.add(scroller);

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
	    		closeAndSave();
	    	}
	    });
	      

	    JPanel saveAndCancelButtonsPanel = new JPanel();
	    saveAndCancelButtonsPanel.add(saveButton, "tag ok, dock west");
	    saveAndCancelButtonsPanel.add(cancelButton, "tag cancel, dock west");
	    buttonsPanel.add(saveAndCancelButtonsPanel, "shrinky");
	    return buttonsPanel;
	}
//-----------------------------------------------------------------------------	
	public void closeAndSave() {
		
		//get a list of all the filled in fields and their values
		pdfv.createFormFieldsList();
		ArrayList<FieldAndVal> formFields = 
				(ArrayList<FieldAndVal>) pdfv.getAllFormFields(); 
		
		//set the corresponding variables in the ShiftCdrReport based on
		//the text from the form fields
		putInfoIntoReport(formFields);
		
		//save the
		String reportFileName = report.saveToFileSystem();
		if (reportFileName!=null){ rm.setLatestReportName(reportFileName); }
		
		setVisible(false);
	}
	
//-----------------------------------------------------------------------------	
	public void loadInfoIntoForm() {
		
		report.loadInfoIntoForm();
		/*
		pdfv.createFormFieldsList();
		ArrayList<FieldAndVal> formFields = 
				(ArrayList<FieldAndVal>) pdfv.getAllFormFields(); 
		putInfoIntoReport(formFields);
		
		String reportFileName = report.saveToFileSystem();
		if (reportFileName!=null){ rm.setLatestReportName(reportFileName); }
		
		setVisible(false);
		*/
	}
//-----------------------------------------------------------------------------
	public void putInfoIntoReport(ArrayList<FieldAndVal> formFields){
		String field, val;
		
		for(FieldAndVal pair : formFields){
			field = pair.getField();
			val = pair.getVal();
			if(field.contains("NameRow")){
				//report.addOfficerAssignmentName(pair.getVal(), Integer.valueOf(field.substring(7)));
			} else if(field.contains("Patrol_AssignmentRow")){
				
			
			// ...
				// ...
			
			} else if(field.equals("remarks_box")){
				System.out.println("remarks_box says " + field);
				report.setRemarks(val);
			} else if(field.equals("completed_by")){
				
			} else if(field.equals("relieved_by")){
				
			} else if(field.equals("completed_by_sigID")){
				
			} else if(field.equals("relieved_by_sigID")){
				
			} else if(field.equals("date_time_recieved")){
				
			} else if(field.equals("shift_date")){
				Date date = new Date(System.currentTimeMillis());
				report.setTimeRecieved(date);
			} else if(field.equals("shift_code")){
				
			}
			
		}
		
	
	}
//-----------------------------------------------------------------------------
	public void closeAndCancel() {

		setVisible(false);
	}
//-----------------------------------------------------------------------------
}
