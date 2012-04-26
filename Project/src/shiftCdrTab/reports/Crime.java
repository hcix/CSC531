/**
 * The <code>Crime</code> class holds information pertaining to a single crime
 * incident. These incidents are gathered from data that is input into the 
 * <code>ShiftCdrReport</code>s filled out by the Shift Commander at the 
 * end of each shift. A <code>Crime</code> object is used to hold the crime
 * data that is either being written to, or read from the program's database.
 * The <code>Crime</code> object is manipulated as needed, and the final product
 * of the manipulations is written to the database either to update an existing 
 * entry or create a new entry.
 * 
 * Once it has been created, a <code>Crime</code> is associated with the 
 * <code>ShiftCdrReport</code> that it was generated from. The case number 
 * associated with the <code>Crime</code> ties it to any other documents 
 * that share that case number, such as BOLOs and Blue Book Entries. All 
 * crime incidents are stored in the program's database. A <code>Crime</code>
 * object provides access to an entries contents. While only Shift
 * Commanders can add crime entries to the database and edit existing 
 * crimes, all officers may view the database's crime table contents. 
 * 
 * The permissions attached to a <code>Crime</code> are as follows:
 * Officers - can view all crime incidents
 * Shift Commanders - can view all crimes incidents, but can only 
 * edit those which they have created
 * Supervisor - can view and edit all crime incidents
 * 
 * @see ShiftCdrReport
 */
package shiftCdrTab.reports;

import java.util.Date;
import shiftCdrTab.gui.ShiftCdrReport;

public class Crime {
//-----------------------------------------------------------------------------
	/** the case # associated with this <code>Crime</Code> incident */
	String caseNum;
	/** the name of the crime scene */
	String locationName;
	/** the FID number pertaining to the location */ 
	String locationFID;
	/** the offense code of the crime */ 
	String offenseCode;
	/** the crime incident type */
	String incident;
	/** the date and time the crime occurred */
	Date dateTime;
	/** the current status of the case */
	String caseStatus;
	/** the name of the Shift Commander that input the incident */
	String creator;
//-----------------------------------------------------------------------------
	/**
	 * Create a new, initially empty, <code>Crime</code> object.
	 */
	public Crime(){ }
//-----------------------------------------------------------------------------
	/**
	 * Create a new <code>Crime</code> object.
	 * @param creatorName - the Shift Commander creating the report containing
	 * the information for this <code>Crime</code>
	 */
	Crime(String creatorName){
		this.creator = creatorName;
	}
//-----------------------------------------------------------------------------
	/**
	 * Getter method for caseNum
	 * 
	 * @return the case # associated with this <code>Crime</Code> incident
	 */
	public String getCaseNum() {
		return caseNum;
	}
//-----------------------------------------------------------------------------
	/**
	 * Setter method for caseNum
	 * 
	 * @param caseNum - the case # associated with this <code>Crime</Code> incident
	 */
	public void setCaseNum(String caseNum) {
		this.caseNum = caseNum;
	}
//-----------------------------------------------------------------------------
	/**
	 * Getter method for locationName
	 * 
	 * @return the name of the crime scene
	 */
	public String getLocationName() {
		return locationName;
	}
//-----------------------------------------------------------------------------
	/**
	 * Setter method for locationName
	 * 
	 * @param locationName - the name of the crime scene
	 */
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
//-----------------------------------------------------------------------------
	/**
	 * Getter method for location's FID number
	 * 
	 * @return the FID number pertaining to the location
	 */
	public String getLocationFID() {
		return locationFID;
	}
//-----------------------------------------------------------------------------
	/**
	 * Setter method for location's FID number
	 * 
	 * @param locationFID - the FID number pertaining to the location
	 */
	public void setLocationFID(String locationFID) {
		this.locationFID = locationFID;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return the offense code of the crime
	 */
	public String getOffenseCode() {
		return offenseCode;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param offenseCode - the offense code of the crime
	 */
	public void setOffenseCode(String offenseCode) {
		this.offenseCode = offenseCode;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return the crime incident type 
	 */
	public String getIncident() {
		return incident;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param incident - the crime incident type
	 */
	public void setIncident(String incident) {
		this.incident = incident;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return a Date object holding the date and time the crime occurred or
	 * null if it is unknown
	 */
	public Date getDateTime() {
		return dateTime;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param dateTime - a Date object holding the date and time the crime 
	 * occurred or null if it is unknown
	 */
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return the current status of the case
	 */
	public String getCaseStatus() {
		return caseStatus;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param caseStatus - the current status of the case
	 */
	public void setCaseStatus(String caseStatus) {
		this.caseStatus = caseStatus;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return the name of the Shift Commander that input the incident
	 */
	public String getCreator() {
		return creator;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param creator - the name of the Shift Commander that input the incident
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}
//-----------------------------------------------------------------------------
}
