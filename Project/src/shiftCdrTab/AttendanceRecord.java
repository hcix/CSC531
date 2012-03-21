package shiftCdrTab;

import java.util.Date;

//-----------------------------------------------------------------------------	
/**
 * The <code>AttendanceRecord</code> class represents a single personnel attendance record.
 * An <code>AttendanceRecord</code> represents a single occurrence of an employee's
 * non-presence at roll call. This applies to personnel who are out sick, on leave,
 * in training, etc (Including dispatchers and security division personnel).
 * These are recorded by the Shift Commander at the beginning of each shift during
 * roll call and reviewed and finalized on the Shift Commander Summary Report
 * at the end of the shift. 
 * A list of zero or more <code>AttendanceRecord</code>s comprises the attendance list
 * for a particular shift. There is exactly one attendance list in every 
 * <code>ShiftCdrReport</code>. The list and the <code>AttendanceRecord</code>s 
 * comprising the list are unique to the shift that the <code>ShiftCdrReport</code> 
 * represents. 
 * Once it has been created, an <code>AttendanceRecord</code> is attached to and
 * may be referenced in relation to both the shift it pertains to as well as the
 * employee it concerns. An employee may view all <code>AttendanceRecord</code>s
 * pertaining to him, but only a Supervisor or the Shift Commander who created
 * the record may edit or remove it. 
 * 
 * The permissions attached to an <code>AttendanceRecord</code> are as follows:
 * Officers - can view only their own <code>AttendanceRecord</code>s
 * Shift Commanders - can view all <code>AttendanceRecord</code>s, but can
 * edit only those which they have created
 * Supervisor - can view and edit all <code>AttendanceRecord</code>s
 * 
 * @see ShiftCdrReport
 */
public class AttendanceRecord {
//-----------------------------------------------------------------------------	
	/** the name of the non-present employee  */
	String employee;
	/** the type of leave the employee is out on (i.e. sick, on leave, in training, etc. */
	String leaveType;
	/** the name of employee covering the shift */
	String coveredBy;
	/** the date and time the shift this <code>AttendanceRecord</code> pertains to began */
	Date shiftDateTime;
	/** the name of the Shift Commander who created this <code>AttendanceRecord</code> */
	String creator;
//-----------------------------------------------------------------------------
	/**
	 * Creates a new <code>AttendanceRecord</code> object.
	 * @param creator - the name of the Shift Commander who is creating this
	 * <code>AttendanceRecord</code>
	 */
	AttendanceRecord(String creator){
		this.creator = creator;
	}
//-----------------------------------------------------------------------------	
	/**
	 * @return the name of the non-present employee
	 */
	public String getEmployee() {
		return employee;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param employee - the name of the non-present employee
	 */
	public void setEmployee(String employee) {
		this.employee = employee;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return the type of leave the employee is out on (i.e. sick, on leave,
	 * in training, etc.)
	 */
	public String getLeaveType() {
		return leaveType;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param leaveType - the type of leave the employee is out on (i.e. sick,
	 * on leave, in training, etc.)
	 */
	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return the name of the employee who is covering the shift
	 */
	public String getCoveredBy() {
		return coveredBy;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param coveredBy - the name of the employee who is covering the shift 
	 */
	public void setCoveredBy(String coveredBy) {
		this.coveredBy = coveredBy;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return a Date object holding the date and time the shift
	 */
	public Date getShiftDateTime() {
		return shiftDateTime;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param shiftDateTime - a Date object holding the date and time the shift began
	 */
	public void setShiftDateTime(Date shiftDateTime) {
		this.shiftDateTime = shiftDateTime;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return the name of the shift's Shift Commander
	 */
	public String getCreator() {
		return creator;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param creator - the name of the shift's Shift Commander
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}	
//-----------------------------------------------------------------------------	
}
