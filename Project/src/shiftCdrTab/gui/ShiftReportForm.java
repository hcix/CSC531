package shiftCdrTab.gui;

import java.awt.Container;
import java.awt.Cursor;
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
	private boolean newReportWasCreated=false;
//-----------------------------------------------------------------------------	
	/**
	 * JDOC
	 */
	public ShiftReportForm(ResourceManager rm, ShiftCdrReport report){
		super(rm.getGuiParent(), "Shift Commander Summary Report", true);
		this.rm = rm;
		this.report=report;
		
		//Set the size of the form
		this.setPreferredSize(new Dimension(800,700));
		this.setSize(new Dimension(800,700));
		
		//Put the form in the middle of the screen
		this.setLocationRelativeTo(null);
		
		//Make sure that if the user hits the 'x', the window calls the closeAndCancel method
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closeAndCancel();
			}
		});
		
		//Either load info from given ShiftReport, or create a new ShiftReport
		if(report==null){ //create new report
			this.report = new ShiftCdrReport();
		} else { //load existing report info into form fields
			loadInfoIntoForm();
		}
			
		
		JPanel buttonsPanel = createButtonsPanel();
		 
		String shiftCdrForm = FileHelper.getFormTemplatePathName("ShiftCDRSumReport.pdf");
		JScrollPane scroller = new JScrollPane();
		    
		pdfv = new PDFView(shiftCdrForm, scroller, buttonsPanel);
		Container contentPane = this.getContentPane();
		contentPane.add(scroller);
	}
//-----------------------------------------------------------------------------		
	public JPanel createButtonsPanel(){
	
		JPanel buttonsPanel = new JPanel(new MigLayout("fillx", "push"));
		
		//Cancel button
		JButton cancelButton = SwingHelper.createImageButton("Cancel", "icons/cancel_48.png");
		cancelButton.setToolTipText("Cancel and do not save");
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				closeAndCancel();
			}
		});
	
	    //Save button
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
	/**
	 * Close and save info into new ShiftCdrReport.
	 */
	private void closeAndSave() {
		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	    
		//get a list of all the filled in fields and their values
		pdfv.createSCRFormFieldsList();
		ArrayList<FieldAndVal> formFields = 
				(ArrayList<FieldAndVal>) pdfv.getAllFormFields(); 
		
		//set corresponding vars in report based on text from form fields
		putInfoIntoReport(formFields);
		
		//save the
		String reportFileName = report.saveToFileSystem();
		if (reportFileName!=null){ rm.setLatestReportName(reportFileName); }
		
		setNewReportWasCreated(true);
		this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
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
	/**
	 * Grabs the info from the report pdf as a list of </code>FieldAndVal</code> objects
	 * and saves them into the global <code>ShiftCdrReport</code> object.
	 */
	private void putInfoIntoReport(ArrayList<FieldAndVal> formFields){
		String field, val;
		int row;
		
		//Process the form information
		for(FieldAndVal pair : formFields){
			field = pair.getField();
			val = pair.getVal();	
			
			//check if fv matches a form field not within a table first
			if(field.equals("remarks_box")){
//DEBUG System.out.println("remarks_box says " + field);
				report.setRemarks(val);
				continue;
			} else if(field.equals("completed_by")){
				continue;
			} else if(field.equals("relieved_by")){
				continue;
			} else if(field.equals("completed_by_sigID")){
				continue;
			} else if(field.equals("relieved_by_sigID")){
				continue;
			} else if(field.equals("date_time_recieved")){
				continue;
			} else if(field.equals("shift_date")){
				Date date = new Date(System.currentTimeMillis());
				report.setTimeRecieved(date);
				continue;
			} else if(field.equals("shift_code")){
				continue;
			} else if(field.equals("ucr_crimegrp")){
				continue;
			}
			
			/*If this point is reached, fv must be one of the form fields within
			 a table; these involve a bit more complex parsing and handling.
			 First we split the field value into 3 parts; due to form design the
			 1st part will give the table the field belongs to, the 2nd part
			 will give the column name, and the 3rd will give the rox index;
			 together these tell us which object's variable on which list 
			 should be set*/
			
			String[] split = field.split("_");
			
			//should never be true-if field doesn't split into 3, there's a problem
			if(split.length!=3){
				
				System.out.printf("error: field = %s", field);
				continue;
			}
//DEBUG
//System.out.println("split[0] = " + split[0] + "; split[1] = " + split[1] +
//"; split[2] = " + split[2]);
			
//DEBUG
//System.out.printf("report.addOfficerAssignmentFieldAndVal((new" +
//" FieldAndVal (%s, %s)), %d);\n", split[1], val, index);
			
			
			if(split[0].equals("offAssign")){		
				int index = Integer.valueOf(split[2]);
				report.addOfficerAssignmentFieldAndVal((new FieldAndVal(split[1], val)), index);
			} else if(split[0].equals("attend")){
				int index = Integer.valueOf(split[2]);
				report.addAttendanceRecord((new FieldAndVal(split[1], val)), index);
			} else if(split[0].equals("patrol")){
				int index = Integer.valueOf(split[2]);
				report.addPatrolActivity((new FieldAndVal(split[1], val)), index);
			} else if(split[0].equals("case")){
				int index = Integer.valueOf(split[2]);
				report.addCrime((new FieldAndVal(split[1], val)), index);
			}

	}//end of for-loop
	

	}
//-----------------------------------------------------------------------------
	/**
	 * Close without saving.
	 */
	private void closeAndCancel() {
		setNewReportWasCreated(false);
		setVisible(false);
	}
//-----------------------------------------------------------------------------
	/**
	 * Returns true if a new report was created the last time this dialog was
	 * was shown, and returns false otherwise.
	 * @return true if a new report was created the last time this dialog was
	 * was shown, false otherwise
	 */
	public boolean isNewReportWasCreated() {
		return newReportWasCreated;
	}
//-----------------------------------------------------------------------------
	/**
	 * Sets the value of newReportWasCreated.
	 */
	private void setNewReportWasCreated(boolean createdNew) {
		this.newReportWasCreated = createdNew;
	}
//-----------------------------------------------------------------------------
}
