/**
 * Class representing a BOLO.
 */
package boloTab;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.swing.ImageIcon;
import utilities.ImageHandler;

//-----------------------------------------------------------------------------
public class Bolo {
	private String age, race, sex, height, weight, build, eyes, hair;
	private String reference, caseNum, status, weapon;
	private String preparedBy, approvedBy;
	private String otherDescrip = null, narrative = null;
	private Path photoFilePath = null, videoFilePath = null;
	//private String photoFileName = null, videoFileName = null;
	private long incidentDate = 0, prepDate = 0;
	private String[] fieldArray;
	private int boloID;
//-----------------------------------------------------------------------------
		/**
		 * Creates a new BOLO object with all fields initially null
		 */
		public Bolo(){
			fieldArray = new String[16];
			for(int i=0; i<fieldArray.length; i++){
				fieldArray[i]=null;
			}
		}
//-----------------------------------------------------------------------------
	 /**
	 * @return the boloID
	 */
	public int getBoloID() {
		return boloID;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param boloID the boloID to set
	 */
	public void setBoloID(int boloID) {
		this.boloID = boloID;
	}
//-----------------------------------------------------------------------------
	 /**
	 * @return the age
	 */
	public String getAge() {
		return age;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param age the age to set
	 */
	public void setAge(String age) {
		this.age = age;
		fieldArray[0]=age;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return the race
	 */
	public String getRace() {
		return race;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param race the race to set
	 */
	public void setRace(String race) {
		this.race = race;
		fieldArray[1]=race;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param sex the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
		fieldArray[2]=sex;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return the height
	 */
	public String getHeight() {
		return height;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param height the height to set
	 */
	public void setHeight(String height) {
		this.height = height;
		fieldArray[3]=height;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return the weight
	 */
	public String getWeight() {
		return weight;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param weight the weight to set
	 */
	public void setWeight(String weight) {
		this.weight = weight;
		fieldArray[4]=weight;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return the build
	 */
	public String getBuild() {
		return build;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param build the build to set
	 */
	public void setBuild(String build) {
		this.build = build;
		fieldArray[5]=build;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return the eyes
	 */
	public String getEyes() {
		return eyes;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param eyes the eyes to set
	 */
	public void setEyes(String eyes) {
		this.eyes = eyes;
		fieldArray[6]=eyes;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return the hair
	 */
	public String getHair() {
		return hair;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param hair the hair to set
	 */
	public void setHair(String hair) {
		this.hair = hair;
		fieldArray[7]=hair;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return the otherDescrip
	 */
	public String getOtherDescrip() {
		return otherDescrip;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param otherDescrip the otherDescrip to set
	 */
	public void setOtherDescrip(String otherDescrip) {
		this.otherDescrip = otherDescrip;
		fieldArray[8]=otherDescrip;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return the reference
	 */
	public String getReference() {
		return reference;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param reference the reference to set
	 */
	public void setReference(String reference) {
		this.reference = reference;
		fieldArray[9]=reference;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return the caseNum
	 */
	public String getCaseNum() {
		return caseNum;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param caseNum the caseNum to set
	 */
	public void setCaseNum(String caseNum) {
		this.caseNum = caseNum;
		fieldArray[10]=caseNum;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
		fieldArray[11]=status;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return the preparedBy
	 */
	public String getPreparedBy() {
		return preparedBy;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param preparedBy the preparedBy to set
	 */
	public void setPreparedBy(String preparedBy) {
		this.preparedBy = preparedBy;
		fieldArray[12]=preparedBy;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return the approvedBy
	 */
	public String getApprovedBy() {
		return approvedBy;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param approvedBy the approvedBy to set
	 */
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
		fieldArray[13]=approvedBy;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return the narrative
	 */
	public String getNarrative() {
		return narrative;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param narrative the narrative to set
	 */
	public void setNarrative(String narrative) {
		this.narrative = narrative;
		fieldArray[14]=narrative;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return the weapon
	 */
	public String getWeapon() {
		return weapon;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param weapon the weapon to set
	 */
	public void setWeapon(String weapon) {
		this.weapon = weapon;
		fieldArray[15]=weapon;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return the incidentDate
	 */
	public long getincidentDate() {
		return incidentDate;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param incidentDate the incidentDate to set
	 */
	public void setincidentDate(long incidentDate) {
		this.incidentDate = incidentDate;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return the prepDate
	 */
	public long getprepDate() {
		return prepDate;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param prepDate the prepDate to set
	 */
	public void setprepDate(long prepDate) {
		this.prepDate = prepDate;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param photoFilePath - the path of the photo associated with this BOLO
	 */
	public void setPhotoFilePath(Path photoFilePath) {
		this.photoFilePath = photoFilePath;
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
	/**
	 * Returns an array filled with the values of the sting
	 * fields within this BOLO. The fields are placed in the same order
	 * as declared at the top. Undeclared fields are added as 
	 * <code>null</code>s.
	 * @return an array filled with values of string fields in this BOLO
	 */
	public String[] getStringFields(){
		return fieldArray;
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
	 * Adds this BOLO object to the 'bolo' table in the database.
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
	      "REPLACE into bolo(age, race, sex, height, weight, build, eyes, hair," +
	      " incidentDate, reference, caseNum, status, weapon, prepedBy, approvedBy, prepdate," +
	      " description, narrative, photoPath, videoPath, bolo_id)" + 
	      "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
	
	    //Add the data to the prepared statement
	    prep.setString(1, this.age);
	    prep.setString(2, this.race);
	    prep.setString(3, this.sex);
	    prep.setString(4, this.height);
	    prep.setString(5, this.weight);
	    prep.setString(6, this.build);
	    prep.setString(7, this.eyes);
	    prep.setString(8, this.hair);
	    prep.setLong(9, incidentDate);
	    prep.setString(10, this.reference);
	    prep.setString(11, this.caseNum);
	    prep.setString(12, this.status);
	    prep.setString(13, this.weapon);
	    prep.setString(14, this.preparedBy);
	    prep.setString(15, this.approvedBy);
	    prep.setLong(16, prepDate);
	    prep.setString(17, this.otherDescrip);
	    prep.setString(18, this.narrative);
	    if(this.boloID!=0){ prep.setInt(21, this.boloID); }
	
	    if(photoFilePath!=null){
		    Path absPhotoFilePath = photoFilePath.toAbsolutePath();
		   // URI imgURI = absPhotoFilePath.toUri();
		    photoPathName = absPhotoFilePath.toString();
	    }
	    prep.setString(19, photoPathName);	    
	    
	    if(videoFilePath!=null){
	    	Path absVideoFilePath = videoFilePath.toAbsolutePath();
	    	videoPathName = absVideoFilePath.toString();
	    } 
	    prep.setString(20, videoPathName);
	    

	    
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
