package blueBookTab;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.swing.ImageIcon;
import utilities.ImageHandler;

//-----------------------------------------------------------------------------
/**
 *Class representing a BOLO.
 */
public class BlueBookEntry {
	/** Name of individual associated with a BOLO */
	private String name;
	/** Date of birth of individual associated with a BOLO*/
	private String dob;
	/** Affiliation of individual associated with a BOLO */
	private String affili;
	/** Address of a reported BOLO */
	private String address;
	/** Description of crime associated with a BOLO */
	private String crimeDescrip;
	/** Narrative of individual reporting a BOLO */
	private String narrative;
	/** Date BOLO was reported */
	private String date;
	/** Time BOLO was reported */
	private String time;
	/** Location BOLO was reported at*/
	private String location;
	/** BOLO's case number */
	private String caseNum;
	/** Status of BOLO */
	private String status;
	/** Weapon carried by an individual associated with the BOLO */
	private String weapon;
	/** Indicates if associated individual is armed with a weapon */
	private boolean isArmed=false;
	/** Officer that prepared the BOLO */
	private String preparedBy;
	/** Path in file system leading to photo pertaining to the BOLO*/
	private Path photoFilePath = null;
	/** Path in file system leading to a video pertaining to the BOLO*/
	private Path videoFilePath = null;
	/** Name of BOLO's photo */
	private String photoFileName = null;
	/** Name of BOLO's video */
	private String videoFileName = null;
	/** ID of BOLO in the Blue book*/

	private int bluebkID=0;
//-----------------------------------------------------------------------------
		/**
		 * Creates a new BlueBookEntry with all fields initially empty
		 */
		public BlueBookEntry(){
			
		}
//-----------------------------------------------------------------------------
	/**
	 * Returns the name on file for the BOLO
	 * @return name of individual affiliated with this particular BOLO
	 */
	public String getName() {
		return name;
	}
//-----------------------------------------------------------------------------
	/**
	 * Sets the name of a BOLO individual
	 * @param name - the name value to set
	 */
	public void setName(String name) {
		this.name = name;
	}
//-----------------------------------------------------------------------------
	/**
	 * Sets the date of birth for a BOLO individual
	 * @return dob - 
	 */
	public String getDob() {
		return dob;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param dob - the date of birth value to set
	 */
	public void setDob(String dob) {
		this.dob = dob;
	}
//-----------------------------------------------------------------------------
	/**
	 * Returns affiliation of a BOLO individual
	 * @return affili - 
	 */
	public String getAffili() {
		return affili;
	}
//-----------------------------------------------------------------------------
	/**
	 * Sets affiliation of a BOLO individual
	 * @param affili - the affili value to set
	 */
	public void setAffili(String affili) {
		this.affili = affili;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return address - 
	 */
	public String getAddress() {
		return address;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param address - the address value to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
//-----------------------------------------------------------------------------
	/**
	 * Returns description of the crime
	 * @return crimeDescrip - 
	 */
	public String getCrimeDescrip() {
		return crimeDescrip;
	}
//-----------------------------------------------------------------------------
	/**
	 * Sets description of the crime
	 * @param crimeDescrip - the crimeDescrip value to set
	 */
	public void setCrimeDescrip(String crimeDescrip) {
		this.crimeDescrip = crimeDescrip;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return narrative - 
	 */
	public String getNarrative() {
		return narrative;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param narrative - the narrative value to set
	 */
	public void setNarrative(String narrative) {
		this.narrative = narrative;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return date - 
	 */
	public String getDate() {
		return date;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param date - the date value to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return time - 
	 */
	public String getTime() {
		return time;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param time - the time value to set
	 */
	public void setTime(String time) {
		this.time = time;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return location - 
	 */
	public String getLocation() {
		return location;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param location - the location value to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return caseNum - 
	 */
	public String getCaseNum() {
		return caseNum;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param caseNum - the caseNum value to set
	 */
	public void setCaseNum(String caseNum) {
		this.caseNum = caseNum;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return status - 
	 */
	public String getStatus() {
		return status;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param status - the status value to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return weapon - 
	 */
	public String getWeapon() {
		return weapon;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param weapon - the weapon value to set
	 */
	public void setWeapon(String weapon) {
		this.weapon = weapon;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return true if offender isArmed; false otherwise
	 */
	public boolean isArmed() {
		return isArmed;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param isArmed - the isArmed value to set
	 */
	public void setArmed(boolean isArmed) {
		this.isArmed = isArmed;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return preparedBy - 
	 */
	public String getPreparedBy() {
		return preparedBy;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param preparedBy - the preparedBy value to set
	 */
	public void setPreparedBy(String preparedBy) {
		this.preparedBy = preparedBy;
	}		
//-----------------------------------------------------------------------------
	 /**
	 * @return the bluebkID
	 */
	public int getBluebkID() {
		return bluebkID;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param bluebkID the bluebkID to set
	 */
	public void setBluebkID(int bluebkID) {
		this.bluebkID = bluebkID;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param photoFilePath - the path of the photo associated with this BOLO
	 */
	public static void setPhotoFilePath(Path p_photoFilePath) {
		Path photoFilePath = p_photoFilePath;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return the path of the associated photo
	 */
	public Path getPhotoFilePath() {
		return photoFilePath;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param videoFilePath - the path of the video associated with this BOLO
	 */
	public void setVideoFilePath(Path videoFilePath) {
		this.videoFilePath = videoFilePath;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return the path of the associated video
	 */
	public Path getVideoFilePath() {
		return videoFilePath;
	}
//-----------------------------------------------------------------------------
	public ImageIcon getPhoto(){
		ImageIcon photo = ImageHandler.getThumbnailImageIcon(photoFilePath);
		if(photo==null){ System.out.printf("null photo\n"); }
		return photo;
	}
//-----------------------------------------------------------------------------
	/**
	 * <b> addToDB </b>
	 * <pre>public void addToDB() throws Exception</pre> 
	 * <blockquote> 
	 * Adds this BlueBookEntry to the 'bluebook' table in the database.
	 * </blockquote>
	 * @throws Exception
	 */
	public void addToDB() throws Exception{
		String photoPathName = null, videoPathName = null;
		//Create the connection to the database
		Class.forName("org.sqlite.JDBC");
	    Connection conn = DriverManager.getConnection("jdbc:sqlite:Database/umpd.db");
	
	    //Create a prepared statement to add the crime data
	    PreparedStatement prep = conn.prepareStatement(
	      "REPLACE into bluebook(armed, narrartive, description, location, address, affili," +
	      " dob, offenderName, caseNum, incidentTime, incidentDate, photoFileName, " +
	      " videoFileName, bbID)" + 
	      "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
	
	    //Add the data to the prepared statement
	    if(isArmed){ prep.setInt(1, 1); } else { prep.setInt(1, 0); }
	    prep.setString(2, this.narrative);
	    prep.setString(3, this.crimeDescrip);
	    prep.setString(4, this.location);
	    prep.setString(5, this.address);
	    prep.setString(6, this.affili);
	    prep.setString(7, this.dob);
	    prep.setString(8, this.name);
	    prep.setString(9, this.caseNum);
	    prep.setString(10, this.time);
	    prep.setString(11, this.date);
	    
	    if(this.bluebkID!=0){ prep.setInt(14, this.bluebkID); }
	
	    if(photoFilePath!=null){
		    Path absPhotoFilePath = photoFilePath.toAbsolutePath();
		   // URI imgURI = absPhotoFilePath.toUri();
		    photoPathName = absPhotoFilePath.toString();
	    }
	    prep.setString(12, photoPathName);	    
	    
	    if(videoFilePath!=null){
	    	Path absVideoFilePath = videoFilePath.toAbsolutePath();
	    	videoPathName = absVideoFilePath.toString();
	    } 
	    prep.setString(13, videoPathName);
	    

	    prep.addBatch();
	    
	    //Create new row in the table for the data
	    conn.setAutoCommit(false);
	    prep.executeBatch();
	    conn.setAutoCommit(true);
	    
	    //Close the connection
	    conn.close();
	    
	}
//-----------------------------------------------------------------------------
}
