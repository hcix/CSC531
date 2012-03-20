package shiftCdrTab;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import utilities.FileHelper;
import utilities.pdf.PDFHelper;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfStamper;
//-----------------------------------------------------------------------------
/**
 * Class to represent a Shift Commander Summary Report. Contains all the
 * information input into the Shift Commander Summary report form, as represented
 * by the <code>ShiftCdrReportForm</code> class which displays and gathers information
 * via the ShiftCDRSumReport.pdf form. 
 * One Shift Commander Summary Report exists per shift. This report is produced by
 * the Shift Commander and combines information gathered during the pre-shift 
 * roll call and the post-shift report. The final report is a pdf document. All
 * of these reports are stored in the ProgramDirectory/Documents/ShiftCdrSumReports 
 * directory in flattened form. Thus, the reports may be viewed outside the system, 
 * but cannot be existing forms cannot edited via outside means.
 */
public class ShiftCdrReport {
	ArrayList<OfficerAssignment> assignmentsList;
	ArrayList<AttendanceRecord> attendance;
	ArrayList<PatrolActivity> patrols;
	ArrayList<Crime> incidents;
	String remarks;
	String completedBy;
	String relievedBy;
	String shiftDate; 
	String shiftCode;
	Date timeRecieved;
	boolean ucrCrimeOccurred = false;
//-----------------------------------------------------------------------------
	ShiftCdrReport(){
		assignmentsList = new ArrayList<OfficerAssignment>();
		attendance = new ArrayList<AttendanceRecord>();
		patrols = new ArrayList<PatrolActivity>();
	}
//-----------------------------------------------------------------------------
	/**
	 * @return the assignments
	 */
	public ArrayList<OfficerAssignment> getAssignmentsList() {
		return assignmentsList;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param assignments the assignments to set
	 */
	public void setAssignmentsArrayList(ArrayList<OfficerAssignment> assignmentsList) {
		this.assignmentsList = assignmentsList;
	}
//-----------------------------------------------------------------------------
	/**
	 * Add an <code>OfficerAssignment</code> to the list of assignments for
	 * the shift represented by this ShiftCdrReport.
	 * @param assignment - assignment to add to the ArrayList
	 */
	public void addOfficerAssignment(OfficerAssignment assignment) {
		assignmentsList.add(assignment);
	}
//-----------------------------------------------------------------------------
	/**
	 * Set the name for the specified <code>OfficerAssignment</code>.
	 * @param name - the name to set 
	 * @param num - the num assignment of the name to set (index = num-1)
	 */
	public void addOfficerAssignmentName(String name, int num) {
		
		//add an OfficerAssignment object for the specified num if one dones't exist
		if(assignmentsList.size()<num){
			int elementsToAdd = num - assignmentsList.size();
			while(elementsToAdd>=0){
				assignmentsList.add(new OfficerAssignment());
			}
		}
			
		//set the name of the specified OfficerAssignment
		//(assignmentsArrayList.get(num-1)).setName(name);
		
	}
//-----------------------------------------------------------------------------
	/**
	 * @return the attendance
	 */
	public ArrayList<AttendanceRecord> getAttendance() {
		return attendance;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param attendance the attendance to set
	 */
	public void setAttendance(ArrayList<AttendanceRecord> attendance) {
		this.attendance = attendance;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return the patrols
	 */
	public ArrayList<PatrolActivity> getPatrols() {
		return patrols;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param patrols the patrols to set
	 */
	public void setPatrols(ArrayList<PatrolActivity> patrols) {
		this.patrols = patrols;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return the incidents
	 */
	public ArrayList<Crime> getIncidents() {
		return incidents;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param incidents the incidents to set
	 */
	public void setIncidents(ArrayList<Crime> incidents) {
		this.incidents = incidents;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return the completedBy
	 */
	public String getCompletedBy() {
		return completedBy;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param completedBy the completedBy to set
	 */
	public void setCompletedBy(String completedBy) {
		this.completedBy = completedBy;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return the relievedBy
	 */
	public String getRelievedBy() {
		return relievedBy;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param relievedBy the relievedBy to set
	 */
	public void setRelievedBy(String relievedBy) {
		this.relievedBy = relievedBy;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return the shiftDate
	 */
	public String getShiftDate() {
		return shiftDate;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param shiftDate the shiftDate to set
	 */
	public void setShiftDate(String shiftDate) {
		this.shiftDate = shiftDate;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return the shiftCode
	 */
	public String getShiftCode() {
		return shiftCode;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param shiftCode the shiftCode to set
	 */
	public void setShiftCode(String shiftCode) {
		this.shiftCode = shiftCode;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return the timeRecieved
	 */
	public Date getTimeRecieved() {
		return timeRecieved;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param timeRecieved the timeRecieved to set
	 */
	public void setTimeRecieved(Date timeRecieved) {
		this.timeRecieved = timeRecieved;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return the ucrCrime
	 */
	public boolean isUcrCrimeOccurred() {
		return ucrCrimeOccurred;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param ucrCrimeOccured - true if a UCR crime occurred during the shift
	 * represented by this ShiftCdrReport object; no need to call this method
	 * to set the ucrCrimeOccurred variable to false bc false is the variable's
	 * default value
	 */
	public void setUcrCrimeOccurred(boolean ucrCrimeOccurred) {
		this.ucrCrimeOccurred = ucrCrimeOccurred;
	}
//-----------------------------------------------------------------------------
	/**
	 * Save this ShiftCdrReport's information in a pdf on the file system
	 * 
	 */
	public void saveToFileSystem(){
		SimpleDateFormat dateFormat = (SimpleDateFormat) DateFormat.getDateInstance();
		dateFormat.applyPattern("MM_dd_yyyy_HH_mm");
		String dateTime = dateFormat.format(timeRecieved);
		
		String formPathName = FileHelper.getDocumentPathName("ShiftCDRSumReport.pdf");
		String saveAs = FileHelper.getDocumentPathName("ShiftCDRSumReport" + dateTime + ".pdf");
		
		PdfStamper stamper = PDFHelper.getPdfStampler(formPathName, saveAs);
		
		fill(stamper.getAcroFields());
		
		stamper.setFormFlattening(true);
		
		try{ stamper.close(); } 
		catch(Exception e){ e.printStackTrace(); }
	}
//-----------------------------------------------------------------------------
    /**
     * Fill out the fields using this ShiftCdrReport's info.
     * @param form - the form object
     */
    public void fill(AcroFields form) {
    	try{
	    	form.setField("shift_date", this.getShiftDate());
	        form.setField("shift_code", this.getShiftCode());
	        form.setField("completed_by", this.getCompletedBy());
	        form.setField("remarks_box", this.getRemarks());
	        
	     // ...
	        
    	} catch(Exception e){
    		e.printStackTrace();
    	}
        
    }
//-----------------------------------------------------------------------------
}
