package shiftCdrTab;

import shiftCdrTab.gui.ShiftCdrReport;

//-----------------------------------------------------------------------------
/**
 * The <code>PatrolAssignment</code> class represents a patrol route that may
 * be assigned to an officer. This is a very simple class containing only
 * one variable, the name of the assignment. 
 * A <code>PatrolAssignment</code> object is created from the patrolsList.xml
 * file. <code>PatrolAssignment</code> objects are used by the
 * <code>OfficerAssignment</code> class to assign a particular patrol route
 * to an officer. Each <code>OfficerAssignment</code> contains at least one, if
 * not more <code>PatrolAssignment</code>s. A single <code>PatrolAssignment</code>
 * may be assigned to zero or more officers.
 * Both Supervisors and Shift Commanders can create new patrol routes to be
 * permanently added to the patrolsList.xml or temporary patrol routes to be
 * used one time only. Both kinds of patrol routes are identically represented
 * by <code>PatrolAssignment</code> objects.
 * 
 * @see OfficerAssignment
 * @see ShiftCdrReport
 */
public class PatrolAssignment {
//-----------------------------------------------------------------------------
	/** name of the patrol route */
	String name="";
	/** the time range the patrol route was patrolled 
	 * desired format: 'HH:mm - HH:mm' where HH represents the hour given in
	 * 24 hour format (12am/midnight = 00) */
	String timePatroled="";
//-----------------------------------------------------------------------------
	public PatrolAssignment(){
		
	}
//-----------------------------------------------------------------------------
	public PatrolAssignment(String name){
		this.name=name;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return the name of the patrol route
	 */
	public String getName() {
		return name;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param name - the name to set for the patrol route
	 */
	public void setName(String name) {
		this.name = name;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return timePatroled - the time range the patrol route was patrolled
	 */
	public String getTimePatroled() {
		return timePatroled;
	}
//-----------------------------------------------------------------------------
	/**
	 * Sets the timePatroled value which specifies the time range the patrol 
	 * route was patrolled. This value should be pre-formated into the desired 
	 * 'HH:mm - HH:mm' format before being passed into this method. 
	 * <br><u>Note</u> It is the responsibility of the caller to ensure the value
	 * passed to this method is pre-formated to match the 'HH:mm - HH:mm' format.
	 * This method does not employ any checks to prevent timePatroled from being
	 * set to <code>String</code>s not matching this format.
	 * @param timePatroled - the pre-formated time range the patrol route was patrolled
	 * <br>format = 'HH:mm - HH:mm' where HH represents the hour given in
	 * 24 hour format (12am/midnight = 00)
	 */
	public void setTimePatroled(String timePatroled) {
		this.timePatroled = timePatroled;
	}
//-----------------------------------------------------------------------------
	@Override 
	public String toString(){
		String toString = this.name;
		return name;
	}
//-----------------------------------------------------------------------------
	
}