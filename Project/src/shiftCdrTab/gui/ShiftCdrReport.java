package shiftCdrTab.gui;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import shiftCdrTab.AttendanceRecord;
import shiftCdrTab.OfficerAssignment;
import shiftCdrTab.PatrolAssignment;
import shiftCdrTab.reports.Crime;
import shiftCdrTab.reports.PatrolActivity;
import utilities.FileHelper;
import utilities.pdf.FieldAndVal;
import utilities.pdf.PDFHelper;
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
 * 
 * @see ShiftReportForm
 * @see OfficerAssignment
 * @see AttendanceRecord
 * @see PatrolActivity
 * @see Crime
 */
public class ShiftCdrReport {
	ArrayList<OfficerAssignment> assignmentsList;
	ArrayList<AttendanceRecord> attendanceList;
	ArrayList<PatrolActivity> patrols;
	ArrayList<Crime> incidents;
	String remarks;
	String completedBy;
	String relievedBy;
	String shiftDate; 
	String shiftCode;
	Date timeRecieved;
	boolean ucrCrimeOccurred = false;
	/** is currently being displayed**/
	boolean isCurrentlyVisable;
	/** the filename that this report will be saved as **/
	String saveAs;	
	/** the path name of the original form template **/
	String formPathName = FileHelper.getFormTemplatePathName("ShiftCDRSumReport.pdf");
//-----------------------------------------------------------------------------
	public ShiftCdrReport(){
		//create the filename the saved form will have
		SimpleDateFormat dateFormat = (SimpleDateFormat) DateFormat.getDateInstance();
		dateFormat.applyPattern("MM_dd_yyyy_HH_mm");
		Date date = new Date(System.currentTimeMillis());
		String dateTime = dateFormat.format(date);
		saveAs = FileHelper.getReportPathName("ShiftCDRSumReport" + dateTime + ".pdf");
		
		//set the shift date to be the current time
		this.shiftDate = dateTime;

		//initialize the lists
		assignmentsList = new ArrayList<OfficerAssignment>(6);
		attendanceList = new ArrayList<AttendanceRecord>(6);
		patrols = new ArrayList<PatrolActivity>(6);
		incidents = new ArrayList<Crime>(6);
		
		for(int i=0; i<6; i++){
			assignmentsList.add(new OfficerAssignment());
			attendanceList.add(new AttendanceRecord());
			patrols.add(new PatrolActivity());
			incidents.add(new Crime());
		}

	}
//-----------------------------------------------------------------------------
	public ShiftCdrReport(String filename){

		this.saveAs = filename;
		File file = new File(saveAs);

		this.shiftDate = file.getName();

		//initialize the lists
		assignmentsList = new ArrayList<OfficerAssignment>(6);
		attendanceList = new ArrayList<AttendanceRecord>(6);
		patrols = new ArrayList<PatrolActivity>(6);
		incidents = new ArrayList<Crime>(6);
		
		for(int i=0; i<6; i++){
			assignmentsList.add(new OfficerAssignment());
			attendanceList.add(new AttendanceRecord());
			patrols.add(new PatrolActivity());
			incidents.add(new Crime());
		}
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
	 * Set the given field to the given value for the appropriate 
	 * <code>OfficerAssignment</code>.
	 * @param fv - 
	 * @param num - 
	 */
	public void addOfficerAssignmentFieldAndVal(FieldAndVal fv, int num) {
		
//DEBUG System.out.println("ShiftCdrReport: addOfficerAssignmentName: field = " 
//+fv.getField() +", "+" val = " + fv.getVal() +";_ assignmentsList.size() = "
//+ assignmentsList.size());

		//set the specified field to the specified value
		if(fv.getField().equals("name")){
			assignmentsList.get(num).setOfficer(fv.getVal());
		} else if(fv.getField().equals("patrol")){
			assignmentsList.get(num).addPatrolAssignment(
					new PatrolAssignment(fv.getVal()));
		} else if(fv.getField().equals("build")){
			assignmentsList.get(num).addBuildingCheck(fv.getVal());
		} else if(fv.getField().equals("vehicle")){
			try{
				int vehicle = Integer.valueOf(fv.getVal());
				assignmentsList.get(num).setVehicleID(vehicle);
			} catch(NumberFormatException e){
				assignmentsList.get(num).setVehicleIDString(fv.getVal());
			}
		} else if(fv.getField().equals("other")){
			assignmentsList.get(num).setOtherDuties(fv.getVal());
		}
		
//DEBUG System.out.println("addOfficerAssignmentName: "+

	}
//-----------------------------------------------------------------------------
	/**
	 * @return the attendanceList for the shift represented by this 
	 * <code>ShiftCdrReport</code>
	 */
	public ArrayList<AttendanceRecord> getAttendanceList() {
		return attendanceList;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param attendanceList - the attendanceList to set for the shift represented
	 * by this <code>ShiftCdrReport</code>
	 */
	public void setAttendanceList(ArrayList<AttendanceRecord> attendancelist) {
		this.attendanceList = attendancelist;
	}
//-----------------------------------------------------------------------------
	/**
	 * Adds an <code>AttendanceRecord</code> to the list of 
	 * <code>AttendanceRecord</code>s for the shift represented by this
	 * <code>ShiftCdrReport</code>.
	 * @param record - the <code>AttendanceRecord</code> to add
	 */
	public void addAttendanceRecord(AttendanceRecord record) {
		attendanceList.add(record);
	}
//-----------------------------------------------------------------------------
	/**
	 * Set the given field to the given value for the appropriate 
	 * <code>AttendanceRecord</code>.
	 * @param fv - 
	 * @param num - 
	 */
	public void addAttendanceRecord(FieldAndVal fv, int num) {
		
//DEBUG 
//System.out.println("ShiftCdrReport: addAttendanceRecordFieldAndVal: field = " 
//+fv.getField() +", "+" val = " + fv.getVal() +";_ attendanceList.size() = "
//+ attendanceList.size());

		//set the specified field to the specified value
		if(fv.getField().equals("name")){
			attendanceList.get(num).setEmployee(fv.getVal());
		} else if(fv.getField().equals("leavetype")){
			attendanceList.get(num).setLeaveType(fv.getVal());
		} else if(fv.getField().equals("coveredBy")){
			attendanceList.get(num).setCoveredBy(fv.getVal());
		}
		
//DEBUG System.out.println("addOfficerAssignmentName: "+

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
	 * Adds an <code>PatrolActivity</code> to the list of 
	 * <code>PatrolActivity</code>s for the shift represented by this
	 * <code>ShiftCdrReport</code>.
	 * @param patrol - the <code>PatrolActivity</code> to add
	 */
	public void addPatrolActivity(PatrolActivity patrol) {
		patrols.add(patrol);
	}
//-----------------------------------------------------------------------------
	/**
	 * Set the given field to the given value for the appropriate 
	 * <code>PatrolActivity</code>.
	 * @param fv - 
	 * @param num - 
	 */
	public void addPatrolActivity(FieldAndVal fv, int num) {
		
//DEBUG 
System.out.println("ShiftCdrReport: addPatrolActivity: field = " 
+fv.getField() +", "+" val = " + fv.getVal() + ";");

		//set the specified field to the specified value
		if(fv.getField().equals("loc")){
			patrols.get(num).setLocation(fv.getVal());
		} else if(fv.getField().equals("act")){
			patrols.get(num).setActivity(fv.getVal());
		}
		
//DEBUG System.out.println("addOfficerAssignmentName: "+

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
	 * Adds an <code>Crime</code> to the list of 
	 * <code>Crime</code>s for the shift represented by this
	 * <code>ShiftCdrReport</code>.
	 * @param crime - the <code>Crime</code> to add
	 */
	public void addCrime(Crime crime) {
		incidents.add(crime);
	}
//-----------------------------------------------------------------------------
	/**
	 * Set the given field to the given value for the appropriate 
	 * <code>Crime</code>.
	 * @param fv - 
	 * @param num - 
	 */
	public void addCrime(FieldAndVal fv, int num) {
		
//DEBUG 
System.out.println("ShiftCdrReport: addCrime: field = " 
+fv.getField() +", "+" val = " + fv.getVal() + ";");

		//set the specified field to the specified value
		if(fv.getField().equals("incid")){
			incidents.get(num).setIncident(fv.getVal());
		} else if(fv.getField().equals("num")){
			incidents.get(num).setCaseNum(fv.getVal());
		} else if(fv.getField().equals("loc")){
			incidents.get(num).setLocationName(fv.getVal());
		} else if(fv.getField().equals("remarks")){
			incidents.get(num).setCaseStatus(fv.getVal());
		}

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
	 * @return JDOC
	 */
	public boolean isCurrentlyVisable() {
		return isCurrentlyVisable;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param isCurrentlyVisable - JDOC
	 */
	public void setIsCurrentlyVisable(boolean isCurrentlyVisable) {
		this.isCurrentlyVisable = isCurrentlyVisable;
	}
//-----------------------------------------------------------------------------
	/**
	 * Save this ShiftCdrReport's information in a pdf on the file system
	 * 
	 */
	public String saveToFileSystem(){
		//used to save text to the form
		File saveAsFile = new File(saveAs);
		PdfStamper stamper;

		if(saveAsFile.exists()){
			stamper = PDFHelper.getPdfStampler(saveAs);
		} else{
			stamper = PDFHelper.getPdfStampler(formPathName, saveAs);
		}

		fill(stamper);

		//flattens the form so fields cannot be edited
		stamper.setFormFlattening(true);

		try{ stamper.close(); return saveAs; } 
		catch(Exception e){ e.printStackTrace(); return null; }
	}
//-----------------------------------------------------------------------------
	/**
	 * Save this ShiftCdrReport's information in a pdf on the file system
	 * 
	 */
	public void loadInfoIntoForm(){
		//used to put text into the form
		PdfStamper stamper = PDFHelper.getPdfStampler(formPathName, saveAs);

		fill(stamper);

		//flattens the form so fields cannot be edited
		stamper.setFormFlattening(false);

		try{ stamper.close(); } 
		catch(Exception e){ e.printStackTrace(); }
	} 
//-----------------------------------------------------------------------------
	/**
	 * Fill out the fields using this ShiftCdrReport's info.
	 * @param form - the form object
	 */
	public void fill(PdfStamper stamper) {
		AcroFields form = stamper.getAcroFields();

		int i=0;
		
		try{
			if(this.shiftDate!=null)
				form.setField("shift_date", this.getShiftDate()); 
			if(this.shiftCode!=null)
				form.setField("shift_code", this.getShiftCode());
			if(this.completedBy!=null)
				form.setField("completed_by", this.getCompletedBy());
			if(this.remarks!=null)
				form.setField("remarks_box", this.getRemarks());
			
			//fill in all officer assignments
			for(OfficerAssignment assignment:assignmentsList){
				if (assignment.getOfficer()!=null) {	
					String patrols="";
					for(PatrolAssignment patrol : assignment.getPatrolAssignments()){
						patrols = patrols.concat(patrol.toString());
					}
					form.setField(("offAssign_name_"+i+""), assignment.getOfficer());
					form.setField(("offAssign_patrol_"+i+""), patrols);
					form.setField(("offAssign_build_"+i+""), assignment.getOfficer());
					form.setField(("offAssign_vehicle_"+i+""), assignment.getOfficer());
					form.setField(("offAssign_other_"+i+""), assignment.getOfficer());
					i++;
				}
			}
			

			//fill in all attendance records	
			i = 0;
			for (AttendanceRecord attend:attendanceList) {
				if (attend.getEmployee()!=null) {
					form.setField("attend_name_"+i+"", attend.getEmployee());
					form.setField("attend_leavetype_"+i+"", attend.getLeaveType());
					form.setField("attend_coveredBy_"+i+"", attend.getCoveredBy());
					i++;
				}
			}

			//fill in all patrol activities	
			i = 0;
			for (PatrolActivity patrol:patrols) {
				if (patrol.getLocation()!=null) {
					form.setField("patrol_loc_"+i+"", patrol.getLocation());
					form.setField("patrol_act_"+i+"", patrol.getActivity());
					i++;
				}
			}

			//fill in all crime incidents
			i = 0;
			for (Crime crime:incidents) {
				if(crime.getIncident()!=null) {
					form.setField("case_incid_"+i+"", crime.getIncident());
					form.setField("case_num_"+i+"", crime.getCaseNum());
					form.setField("case_loc_"+i+"", crime.getLocationName());
					form.setField("case_remarks_"+i+"", crime.getIncident());
				}
				i++;
			}
			
		} catch(Exception e){
			e.printStackTrace();
		}

	}
//-----------------------------------------------------------------------------
}
