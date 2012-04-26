package shiftCdrTab.reports;

import shiftCdrTab.gui.ShiftCdrReport;
//-----------------------------------------------------------------------------	
/**
 * The <code>PatrolActivity</code> class represents a directed patrol activity.
 * These are assigned to the officers at the beginning of the shift during roll
 * call and recorded on the Shift Commander Summary Report by the Shift Commander
 * at the end of the shift. A list of <code>PatrolActivity</code>s is included
 * in every <code>ShiftCdrReport</code>. The list and the 
 * <code>PatrolActivity</code>s comprising the list are unique to the shift that
 * the <code>ShiftCdrReport</code> represents.
 * 
 * @see ShiftCdrReport
 */
public class PatrolActivity {
//-----------------------------------------------------------------------------	
	/** location where the patrol activity took place */
	String location;
	/** watcher orders & targeted activities to promote safety & crime reduction */
	String activity;
//-----------------------------------------------------------------------------		
	PatrolActivity(String location, String activity){
		this.location = location;
		this.activity = activity;
	}
//-----------------------------------------------------------------------------		
	public PatrolActivity(){ }
//-----------------------------------------------------------------------------
	/**
	 * @return location where the patrol activity took place
	 */
	public String getLocation() {
		return location;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param location - the location value to set for this PatrolActivity
	 */
	public void setLocation(String location) {
		this.location = location;
	}
//-----------------------------------------------------------------------------	
	/**
	 * @return activity - watcher orders & targeted activities to promote 
	 * safety & crime reduction
	 */
	public String getActivity() {
		return activity;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param activity - the activity value to set for this PatrolActivity
	 */
	public void setActivity(String activity) {
		this.activity = activity;
	}
//-----------------------------------------------------------------------------	
}
