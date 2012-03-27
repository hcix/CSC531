package shiftCdrTab;

import java.util.ArrayList;

//-----------------------------------------------------------------------------	
/**
 * The <code>OfficerAssignment</code> class represents a single officer's assigned
 * tasks for a particular shift. These are set by the Shift Commander at the
 * beginning of each shift during roll call and reviewed, edited for any changes,
 * and finalized on the Shift Commander Summary Report at the end of the shift. 
 * A list of <code>OfficerAssignment</code>s comprises the assignments list
 * for a particular shift. There is exactly one assignments list in every 
 * <code>ShiftCdrReport</code>. The list and the <code>OfficerAssignment</code>s 
 * comprising the list are unique to the shift that the <code>ShiftCdrReport</code> 
 * represents. 
 * 
 * The permissions attached to an <code>OfficerAssignment</code> are as follows:
 * Officers - can view only their own past and present <code>OfficerAssignment</code>s
 * Shift Commanders - can view all <code>OfficerAssignment</code>s, but can
 * edit only those which they have created
 * Supervisor - can view and edit all <code>OfficerAssignment</code>s
 * 
 * @see ShiftCdrReport
 */
public class OfficerAssignment {
	/** the name of the officer whom this <code>OfficerAssignment</code> refers to */
	String officer;
	/** the patrols performed by the officer */
	ArrayList<PatrolAssignment> patrolAssignments;
	/** the building checks performed by the officer */
	ArrayList<String> buildingChecks;
	/** the id number of the vehicle used by the officer */
	//TODO: provide workaround for bikes
	int vehicleID;
	/** other duties/assignments performed by the officer */
	String otherDuties;
//-----------------------------------------------------------------------------	
	/**
	 * Creates a new <code>OfficerAssignment</code> object
	 */
	OfficerAssignment(){
		patrolAssignments = new ArrayList<PatrolAssignment>();
		buildingChecks = new ArrayList<String>();
	}
//-----------------------------------------------------------------------------	
	OfficerAssignment(String officer){
		this.officer = officer;
		//call other OfficerAssignment constructor
	}
//-----------------------------------------------------------------------------	
	/**
	 * @return the name of the officer whom this <code>OfficerAssignment</code> refers to
	 */
	public String getOfficer() {
	return officer;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param officer - the name of the officer whom this 
	 * <code>OfficerAssignment</code> refers to
	 */
	public void setOfficer(String officer) {
	this.officer = officer;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return a list of the patrols performed by the officer
	 */
	public ArrayList<PatrolAssignment> getPatrolAssignments() {
	return patrolAssignments;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param patrolAssignments - a list of the patrols performed by the officer
	 */
	public void setPatrolAssignments(ArrayList<PatrolAssignment> patrolAssignments) {
	this.patrolAssignments = patrolAssignments;
	}
//-----------------------------------------------------------------------------
	/**
	 * Adds a <code>PatrolAssignment</code> object to the list of patrols performed
	 * by the officer.
	 * @param patrolAssignment - a <code>PatrolAssignment</code> object to add to
	 * the list of patrols performed by the officer
	 */
	public void addPatrolAssignment(PatrolAssignment patrolAssignment) {
		patrolAssignments.add(patrolAssignment);
	}
//-----------------------------------------------------------------------------	
	/**
	 * @return a list of building checks performed by the officer
	 */
	public ArrayList<String> getBuildingChecks() {
	return buildingChecks;
	}
//-----------------------------------------------------------------------------	
	/**
	 * @param buildingChecks - a list of building checks performed by the officer
	 */
	public void setBuildingChecks(ArrayList<String> buildingChecks) {
	this.buildingChecks = buildingChecks;
	}
//-----------------------------------------------------------------------------	
	/**
	 * Add a building check to the list of building checks performed by the officer.
	 * @param buildingCheck - a building check to add to the buildingChecks list 
	 */
	public void addBuildingCheck(String buildingCheck) {
		buildingChecks.add(buildingCheck);
	}
//-----------------------------------------------------------------------------	
	/**
	 * @return the id number of the vehicle used by the officer
	 */
	public int getVehicleID() {
	return vehicleID;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param vehicleID - the id number of the vehicle used by the officer
	 */
	public void setVehicleID(int vehicleID) {
	this.vehicleID = vehicleID;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return other duties/assignments performed by the officer 
	 */
	public String getOtherDuties() {
	return otherDuties;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param otherDuties - other duties/assignments performed by the officer 
	 */
	public void setOtherDuties(String otherDuties) {
	this.otherDuties = otherDuties;
	}
//-----------------------------------------------------------------------------
}
