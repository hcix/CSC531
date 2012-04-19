package blueBookTab;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import utilities.ui.ImageHandler;

//-----------------------------------------------------------------------------
/**
 * The <code>BlueBookEntry</code> class represents a particular incident instance 
 * including name, date of birth, affiliation, address, crime description, narrative
 * date, time, location, case number, weapon, and attached photos and videos
 */
public class BlueBookEntry {
	/** Name of individual associated with Entry */
	private String name;
	/** Date of birth of individual associated with Entry*/
	private String dob;
	/** Affiliation of individual associated with Entry */
	private String affili;
	/** Address of a reported BlueBook Entry */
	private String address;
	/** Description of crime associated with a BlueBook Entry */
	private String crimeDescrip;
	/** Narrative of individual reporting a BlueBook Entry */
	private String narrative;
	/** Date BlueBook Entry was reported */
	private String date;
	/** Time BlueBook Entry was reported */
	private String time;
	/** Location BlueBook Entry was reported at*/
	private String location;
	/** BlueBook Entry's case number */
	private String caseNum;
	/** Status of BlueBook Entry */
	private String status;
	/** Weapon carried by an individual associated with the BlueBook Entry */
	private String weapon;
	/** Indicates if associated individual is armed with a weapon */
	private boolean isArmed=false;
	/** Officer that prepared the BlueBook Entry */
	private String preparedBy;
	/** Path in file system leading to photo pertaining to the BlueBook Entry*/
	private Path photoFilePath = null;
	/** Path in file system leading to a video pertaining to the BlueBook Entry*/
	private Path videoFilePath = null;
	/** ID of BlueBook Entry in the Blue book*/
	private Integer bbID=null;
	/** list of absolute filenames of photos associated with this BlueBook Entry */
	ArrayList<String> photoFilePaths;
	/**JDOC*/
	private String[] fieldArray;
//-----------------------------------------------------------------------------
	/**
	 * Creates a new <code>BlueBookEntry</code> with all fields initially empty
	 */
	public BlueBookEntry(){
		fieldArray = new String[16];
		for(int i=0; i<fieldArray.length; i++){
			fieldArray[i]=null;
		}
	}
//-----------------------------------------------------------------------------
	/**
	 * 
	 * @return name
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
	 * @return dob - date of birth
	 */
	public String getDob() {
		return dob;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param dob - date of birth
	 */
	public void setDob(String dob) {
		this.dob = dob;
	}
//-----------------------------------------------------------------------------
	/**
	 * 
	 * @return affili - affiliation
	 */
	public String getAffili() {
		return affili;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param affili - affiliation
	 */
	public void setAffili(String affili) {
		this.affili = affili;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return address - address of the individual 
	 */
	public String getAddress() {
		return address;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param address - address of the individual 
	 */
	public void setAddress(String address) {
		this.address = address;
	}
//-----------------------------------------------------------------------------
	/**
	 * 
	 * @return crimeDescrip - description of the crime
	 */
	public String getCrimeDescrip() {
		return crimeDescrip;
	}
//-----------------------------------------------------------------------------
	/**
	 * 
	 * @param crimeDescrip - description of the crime
	 */
	public void setCrimeDescrip(String crimeDescrip) {
		this.crimeDescrip = crimeDescrip;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return narrative - account of events
	 */
	public String getNarrative() {
		return narrative;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param narrative - account of events
	 */
	public void setNarrative(String narrative) {
		this.narrative = narrative;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return date 
	 */
	public String getDate() {
		return date;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param date
	 */
	public void setDate(String date) {
		this.date = date;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return time 
	 */
	public String getTime() {
		return time;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param time
	 */
	public void setTime(String time) {
		this.time = time;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return location 
	 */
	public String getLocation() {
		return location;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param location
	 */
	public void setLocation(String location) {
		this.location = location;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return caseNum - case number
	 */
	public String getCaseNum() {
		return caseNum;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param caseNum - case number
	 */
	public void setCaseNum(String caseNum) {
		this.caseNum = caseNum;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return status 
	 */
	public String getStatus() {
		return status;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return weapon 
	 */
	public String getWeapon() {
		return weapon;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param weapon 
	 */
	public void setWeapon(String weapon) {
		this.weapon = weapon;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return isArmed
	 */
	public boolean isArmed() {
		return isArmed;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param isArmed
	 */
	public void setArmed(boolean isArmed) {
		this.isArmed = isArmed;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return preparedBy - the officer who prepared the document 
	 */
	public String getPreparedBy() {
		return preparedBy;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param preparedBy - the officer who prepared the document 
	 */
	public void setPreparedBy(String preparedBy) {
		this.preparedBy = preparedBy;
	}		
//-----------------------------------------------------------------------------
	 /**
	 * @return bbID - unique ID used in the database
	 */
	public int getBbID() {
		return bbID;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param bbID - unique ID used in the database
	 */
	public void setBbID(int bbID) {
		this.bbID = bbID;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param photoFilePath - the path of the photo associated with this each entry
	 */
	public void setPhotoFilePath(Path p_photoFilePath) {
		this.photoFilePath = p_photoFilePath;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return path - where on the disk the associated photo lies
	 */
	public Path getPhotoFilePath() {
		return photoFilePath;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param videoFilePath - where on the disk the associated video lies
	 */
	public void setVideoFilePath(Path videoFilePath) {
		this.videoFilePath = videoFilePath;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return videoFilePath - where on the disk the associated video lies
	 */
	public Path getVideoFilePath() {
		return videoFilePath;
	}
//-----------------------------------------------------------------------------
	/**
	 * 
	 * @return photo - an ImageIcon of the respective photo
	 */
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
	      "REPLACE into bluebook(armed, narrative, description, location, address, affili," +
	      " dob, name, caseNum, time, date, photoFileName, " +
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

	    if(this.bbID!=null){ prep.setInt(14, this.bbID); }

	    if(photoFilePath!=null){
		    Path absPhotoFilePath = photoFilePath.toAbsolutePath();
		    photoPathName = absPhotoFilePath.toString();
		    System.out.println("photoPathName = " + photoPathName);
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
	/**
	 * Deletes this BlueBook Entry object from the 'BlueBook Entry' table in the database.
	 * @throws Exception
	 */
	public void deleteFromDB() throws Exception {
		//check that this BlueBook Entry is in the DB before attempting to delete it
		if(bbID==null){
			//do nothing, this BlueBook Entry was never written to the database 
			return;
		}

		//create the connection to the database
		Class.forName("org.sqlite.JDBC");
	    Connection conn = DriverManager.getConnection("jdbc:sqlite:Database/umpd.db");	
	    Statement stat = conn.createStatement();

	    //perform delete
	    stat.executeUpdate("DELETE FROM bluebook WHERE bbID = " + bbID);

	    //close the connection
	    conn.close();

	}
//-----------------------------------------------------------------------------
}
